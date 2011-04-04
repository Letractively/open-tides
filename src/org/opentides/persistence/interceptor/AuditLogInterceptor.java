/**
 * 
 */
package org.opentides.persistence.interceptor;

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
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.opentides.bean.Auditable;
import org.opentides.persistence.impl.AuditLogDAOImpl;
import org.opentides.util.CrudUtil;
import org.opentides.util.HibernateUtil;
import org.opentides.util.StringUtil;

/**
 * This is the interceptor responsible in tracking audit trails.
 * Source is patterned after the book "Java Persistence with Hibernate" - page 546 onwards
 * and merged with http://www.hibernate.org/318.html
 * 
 * @author allantan
 */
public class AuditLogInterceptor extends EmptyInterceptor {
	private static final long serialVersionUID = 582549003254963262L;
	
	private static Logger _log = Logger.getLogger(AuditLogInterceptor.class);
	
    private Set<Auditable> inserts      = Collections.synchronizedSet(new HashSet<Auditable>()); 
    private Set<Auditable> updates      = Collections.synchronizedSet(new HashSet<Auditable>()); 
    private Set<Auditable> deletes      = Collections.synchronizedSet(new HashSet<Auditable>()); 
    private Map<Long, Auditable> oldies = Collections.synchronizedMap(new HashMap<Long, Auditable>()); 
    
    @Override
    public boolean onSave(Object entity, 
                          Serializable id, 
                          Object[] state, 
                          String[] propertyNames, 
                          Type[] types) 
            throws CallbackException { 
        if (entity instanceof Auditable) {
        	synchronized(inserts) {
        		inserts.add((Auditable)entity);
        	}
        }
        return false; 
    } 
    
	/* (non-Javadoc)
	 * @see org.hibernate.EmptyInterceptor#onDelete(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
	 */
	@Override
	public void onDelete(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
        if (entity instanceof Auditable) {
        	synchronized(deletes) {
        		deletes.add((Auditable)entity);
        	}
        }
	}

    
    @Override
    public boolean onFlushDirty(Object entity, 
                                Serializable id, 
                                Object[] currentState, 
                                Object[] previousState, 
                                String[] propertyNames, 
                                Type[] types) 
            throws CallbackException { 
        if (entity instanceof Auditable) {
        	try {
            	Auditable auditable = (Auditable) entity;
            	if (!auditable.skipAudit()) {
		        	// Use the id and class to get the pre-update state from the database
		        	Session tempSession = 
		                HibernateUtil.getSessionFactory().openSession(); 
		        	Auditable old = (Auditable) tempSession.get(entity.getClass(), auditable.getId());
		        	synchronized(oldies) {
		        		oldies.put(old.getId(), old);
		        	}
		        	synchronized(updates) {
		        		updates.add((Auditable)entity);
		        	}
            	}
        	} catch (Throwable e) {
        		_log.error(e,e);
        	}
        }
        return false; 
    } 
    
	@Override
    public void postFlush(Iterator iterator) 
                    throws CallbackException { 
        try { 
        	synchronized(inserts) {
	        	for (Auditable entity:inserts) {
	        		if (!entity.skipAudit()) {
	        			if (StringUtil.isEmpty(entity.getAuditMessage()))
	        				AuditLogDAOImpl.logEvent(
	        						CrudUtil.buildCreateMessage(entity), entity); 
	        			else
	        				AuditLogDAOImpl.logEvent(
	        						entity.getAuditMessage(), entity);         				
	        		}
	        	}
        	}
        	synchronized(deletes) {
	        	for (Auditable entity:deletes) {
	        		if (!entity.skipAudit()) {
	        			if (StringUtil.isEmpty(entity.getAuditMessage()))
	        				AuditLogDAOImpl.logEvent(
	        						CrudUtil.buildDeleteMessage(entity), entity); 
	        			else
	        				AuditLogDAOImpl.logEvent(
	        						entity.getAuditMessage(), entity);         				
	        		}
	        	}        	
        	}
        	synchronized (updates) {
               	for (Auditable entity:updates) {
	        		if (!entity.skipAudit()) {
	        			if (StringUtil.isEmpty(entity.getAuditMessage())) {
			        		Auditable old = oldies.get(entity.getId());
			        		String message = CrudUtil.buildUpdateMessage(old, entity);
			        		if (!StringUtil.isEmpty(message))
			        		AuditLogDAOImpl.logEvent(message, entity); 
	        			} else
	        				AuditLogDAOImpl.logEvent(
	        						entity.getAuditMessage(), entity);        				
	        		}
               	}
        	}
        } catch (Throwable e) {
    		_log.error(e,e);
    	} finally {
    		synchronized (inserts) {
	            inserts.clear(); 
			} 
    		synchronized (updates) {
	            updates.clear();
    		}
    		synchronized (deletes) {
	            deletes.clear();
    		}
	    	synchronized (oldies) {
	            oldies.clear();
    		}
        } 
    }    
}