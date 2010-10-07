/*
   Licensed to the Apache Software Foundation (ASF) under one
   or more contributor license agreements.  See the NOTICE file
   distributed with this work for additional information
   regarding copyright ownership.  The ASF licenses this file
   to you under the Apache License, Version 2.0 (the
   "License"); you may not use this file except in compliance
   with the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing,
   software distributed under the License is distributed on an
   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
   KIND, either express or implied.  See the License for the
   specific language governing permissions and limitations
   under the License.    
 */
package org.opentides.persistence.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.opentides.InvalidImplementationException;
import org.opentides.bean.Auditable;
import org.opentides.bean.BaseCriteria;
import org.opentides.bean.BaseEntity;
import org.opentides.bean.BaseProtectedEntity;
import org.opentides.bean.Sortable;
import org.opentides.bean.user.BaseUser;
import org.opentides.bean.user.SessionUser;
import org.opentides.listener.ApplicationStartupListener;
import org.opentides.persistence.BaseEntityDAO;
import org.opentides.util.AcegiUtil;
import org.opentides.util.CrudUtil;
import org.opentides.util.StringUtil;


/**
 * Base class for all business data access objects. Extend this class to inherit
 * all CRUD operations.
 * This implementation is patterned after GenericEJB3DAO from Hibernate CaveatEmptor.
 *
 * @author allanctan
 */
public class BaseEntityDAOJpaImpl<T extends BaseEntity,ID extends Serializable> 
		implements BaseEntityDAO<T, ID>  {
	
	private static Logger _log = Logger.getLogger(BaseEntityDAOJpaImpl.class);

    private Properties properties;
	// contains the class type of the bean    
    private Class<T> entityBeanType;
    // list of filters available
    private Map<String, String> securityFilter;
    // the entity manager
	@PersistenceContext
    private EntityManager em;
   
	private int batchSize = 20;
	
	@SuppressWarnings("unchecked")
	public BaseEntityDAOJpaImpl() {
		// identify the bean we are processing for this DAO 
		try {
	        this.entityBeanType = (Class<T>) ((ParameterizedType) getClass()
	                .getGenericSuperclass()).getActualTypeArguments()[0];
		} catch (ClassCastException cc) {
			// if dao is extended from the generic dao class
			this.entityBeanType = (Class<T>) ((ParameterizedType) getClass().getSuperclass()
	                .getGenericSuperclass()).getActualTypeArguments()[0];
		}
	}

	public final T loadEntityModel(ID id, boolean lock) {
        T entity;
        entity = getEntityManager().find(getEntityBeanType(), id);
        if (lock)
        	getEntityManager().lock(entity, javax.persistence.LockModeType.WRITE);
        return entity;
	}
	
	public final T loadEntityModel(ID id) {
		return this.loadEntityModel(id, false);
	}
	
	public final void saveEntityModel(T obj) {
		// if class is auditable, we need to ensure userId is present
		setAuditUserId(obj);
		if (obj.isNew())
			this.addEntityModel(obj);
		else
			this.updateEntityModel(obj);
	}
	
	public final void deleteEntityModel(ID id) {
		T obj = getEntityManager().find(getEntityBeanType(), id);
		deleteEntityModel(obj);
	}
	
	public final void deleteEntityModel(T obj) {
		setAuditUserId(obj);
		getEntityManager().remove(obj);
		getEntityManager().flush();
	}

	public final List<T> findAll() {
    	return findAll(-1,-1);
    }
    
	@SuppressWarnings("unchecked")
	public final List<T> findAll(int start, int total) {
		Query query =  getEntityManager().createQuery("from " + 
        		getEntityBeanType().getName() +" obj ");
		if (start > -1) 
			query.setFirstResult(start);
		if (total > -1)
			query.setMaxResults(total);		
        return query.getResultList();
	}
    
	public final long countAll() {
		return (Long) getEntityManager().createQuery("select count(*) from " + 
				getEntityBeanType().getName() + " obj " +
				doSQLAppend("", this.buildSecurityFilterClause(null))).getSingleResult();
	}
	
	public final long countByExample(T example) {
		return countByExample(example,false);
	}
	
	public final long countByExample(T example, boolean exactMatch) {
		if (example instanceof BaseCriteria) {
			BaseCriteria criteria = (BaseCriteria) example;
			String whereClause = CrudUtil.buildJpaQueryString(criteria, exactMatch);
			String filterClause = this.buildSecurityFilterClause(example);
			String append = appendClauseToExample(example, exactMatch);
			whereClause = doSQLAppend(whereClause, append);
			whereClause = doSQLAppend(whereClause, filterClause);
			if (_log.isDebugEnabled()) _log.debug("Count QBE >> "+whereClause);
			return (Long) getEntityManager().createQuery("select count(*) from " + 
					getEntityBeanType().getName() + " obj " + whereClause).getSingleResult();
		} else {
			throw new InvalidImplementationException("Parameter example ["+example.getClass().getName()+"] is not an instance of BaseCriteria");
		}
	}
	
	public final List<T> findByExample(T example, boolean exactMatch) {
		return findByExample(example, exactMatch, -1,-1);
	}
	
	public final List<T> findByExample(T example) {
		return findByExample(example,false,-1,-1);
	}

	public final List<T> findByExample(T example, int start, int total) {
		return findByExample(example,false,start,total);
	}
	
	@SuppressWarnings("unchecked")
	public final List<T> findByExample(T example, boolean exactMatch, int start, int total) {
		if (example instanceof BaseCriteria) {
			BaseCriteria criteria = (BaseCriteria) example;
			String whereClause = CrudUtil.buildJpaQueryString(criteria, exactMatch);
			String orderClause = " " + appendOrderToExample(example);
			String filterClause = this.buildSecurityFilterClause(example);
			String append = appendClauseToExample(example, exactMatch);
			whereClause = doSQLAppend(whereClause, append);
			whereClause = doSQLAppend(whereClause, filterClause);
			if (_log.isDebugEnabled()) _log.debug("QBE >> "+whereClause+orderClause);
			Query query = getEntityManager().createQuery("from " + 
	        		getEntityBeanType().getName() + " obj "+ whereClause + orderClause);
			if (start > -1) 
				query.setFirstResult(start);
			if (total > -1)
				query.setMaxResults(total);	
			return query.getResultList();
		} else {
			throw new InvalidImplementationException("Parameter example ["+example.getClass().getName()+"] is not an instance of BaseCriteria");
		}
	}

	public final List<T> findByNamedQuery(final String name, final Map<String,Object> params) {
		return findByNamedQuery(name, params, -1, -1);
	}
	
	@SuppressWarnings("unchecked")
	public final List<T> findByNamedQuery(final String name, final Map<String,Object> params, int start, int total) {
		String queryString = getJpqlQuery(name);
		Query queryObject = getEntityManager().createQuery(queryString);
		if (params != null) {
			for (Map.Entry<String, Object> entry:params.entrySet())
				queryObject.setParameter(entry.getKey(), entry.getValue());
		}
		if (start > -1) 
			queryObject.setFirstResult(start);
		if (total > -1)
			queryObject.setMaxResults(total);	
		return queryObject.getResultList();
	}
	@SuppressWarnings({ "unchecked" })
	public final T findSingleResultByNamedQuery(final String name, final Map<String,Object> params) {
		String queryString = getJpqlQuery(name);
		Query queryObject = getEntityManager().createQuery(queryString);
		if (params != null) {
			for (Map.Entry<String, Object> entry:params.entrySet())
				queryObject.setParameter(entry.getKey(), entry.getValue());
		}
		return (T) queryObject.getSingleResult();
	}

    public final Class<T> getEntityBeanType() {
        return entityBeanType;
    }

	public final void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	/**
	 * @return the properties
	 */
	public final Properties getProperties() {
		return properties;
	}

	public final String getJpqlQuery(String key) {
		String query = (String) properties.get(key);
		if (StringUtil.isEmpty(query)) {
			throw new InvalidImplementationException("Key ["+key+"] is not defined in custom jpql property file.");
		} else
			return query;
	}
	
	public final Session getHibernateSession() {
		return (Session) em.getDelegate();
	}
	
	public final void setEntityManager(EntityManager em) {
        this.em = em;
    }
	  
	/**
	 * @param securityFilter the securityFilter to set
	 */
	public final void setSecurityFilter(Map<String, String> filter) {
		this.securityFilter = filter;
	}

    protected final EntityManager getEntityManager() {
        if (em == null)
            throw new IllegalStateException("EntityManager has not been set on DAO before usage");
        return em;
    }

	/**
	 * Override this method to append additional query conditions for findByExample.
	 * Useful for date range and other complex queries.
	 * @param example
	 * @param exactMatch
	 * @return
	 */
	protected String appendClauseToExample(T example, boolean exactMatch) {
		return "";
	}
	
	/**
	 * Override this method to append order clause to findByExample.
	 * @param example
	 * @return
	 */
	protected String appendOrderToExample(T example) {
		String clause="";
		
		if (example instanceof Sortable) {
			Sortable criteria = (Sortable) example;
			//for search list ordering
			if(!StringUtil.isEmpty(criteria.getOrderOption()) && !StringUtil.isEmpty(criteria.getOrderFlow())){
				clause="ORDER BY "+ criteria.getOrderOption() +" "+ criteria.getOrderFlow() +"";
			}
		}
		return clause;
	}

	protected final void addEntityModel(T obj) {
		getEntityManager().persist(obj);
	}
	
	protected final void updateEntityModel(T obj) {
		getEntityManager().merge(obj);
		getEntityManager().flush();
	}
	
	/**
	 * Private helper to build clause for security based filtering restriction 
	 * @param example
	 * @return
	 */
	private String buildSecurityFilterClause(T example) {
		// no protection implemented during application startup 
		// and when not implementing BaseProtectedEntity 
		if ( ApplicationStartupListener.isApplicationStarted() && 
				example != null &&
				BaseProtectedEntity.class.isAssignableFrom(example.getClass())) {
			BaseProtectedEntity bpe = (BaseProtectedEntity) example;
			if (!bpe.isDisableProtection()) {
				// retrieve list of available security filters
				for (String key:securityFilter.keySet()) {
					if (AcegiUtil.currentUserHasPermission(key)) {
						String filterClause = securityFilter.get(key);
						SessionUser sessionUser = AcegiUtil.getSessionUser();
						BaseUser user = getEntityManager().find(BaseUser.class, sessionUser.getId());
						return CrudUtil.replaceSQLParameters(filterClause, user);
					}
				}
				return "1!=1";
			}
			// no filter found?!? fine, let's disable access then...
		}
		// no security needed
		return "";
	}
	
	private String doSQLAppend(String whereClause, String append) {
		if (!StringUtil.isEmpty(append)) {
			if (StringUtil.isEmpty(whereClause))
				whereClause += " where ";
			else
				whereClause += " and ";
			whereClause += append;
		}			
		return whereClause;
	}
	
	/**
	 * Sets the userId within the web session for audit logging.
	 * @param obj
	 */
	private void setAuditUserId(T obj) {
		if (Auditable.class.isAssignableFrom(obj.getClass())) {
			Auditable auditable = (Auditable) obj;
			if (auditable.getAuditUserId()==null)
				auditable.setUserId();			
		}
	}

	/**
	 * Adds or updates a collection of model objects using
	 * the recommended way of saving collections from the Hibernate site.
	 * @param objects
	 */
	public void saveAllEntityModel(Collection<T> objects) {
		int ctr = 0;
		for (T t : objects) {
			saveEntityModel(t);
			if (++ctr % batchSize == 0){
				getEntityManager().flush();
				getEntityManager().clear();
			}
		}
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
}
