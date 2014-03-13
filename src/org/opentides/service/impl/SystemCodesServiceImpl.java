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
package org.opentides.service.impl;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.stat.Statistics;
import org.opentides.bean.SystemCodes;
import org.opentides.persistence.SystemCodesDAO;
import org.opentides.service.SystemCodesService;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author allantan
 * 
 */
public class SystemCodesServiceImpl extends BaseCrudServiceImpl<SystemCodes>
		implements SystemCodesService {
	
	private SystemCodesDAO systemCodesDAO;
	
	public void setSystemCodesDAO(SystemCodesDAO systemCodesDAO) {
		this.systemCodesDAO = systemCodesDAO;
	}
	
	private static Logger _log = Logger.getLogger(SystemCodesServiceImpl.class);

	/**
	 * Increments value of numberValue by used. Useful for custom generation of
	 * id.
	 */	
	@Transactional
	public Long incrementValue(String key) {
		return systemCodesDAO.incrementValue(key);
	}

	@Transactional(readOnly=true)
	public List<SystemCodes> getAllCategories() {
		return systemCodesDAO.getAllCategories();
	}
	
	/* (non-Javadoc)
	 * @see org.opentides.service.SystemCodesService#getAllCategoryValues()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<String> getAllCategoryValues() {
		return systemCodesDAO.getAllCategoryValues();
	}

	@Transactional(readOnly=true)
	public SystemCodes findByKey(SystemCodes systemCodes) {
		return systemCodesDAO.loadBySystemCodesByKey(systemCodes.getKey());
	}
	
	@Transactional(readOnly=true)
	public SystemCodes findByKey(String systemCodes) {
		SystemCodesDAO dao = (SystemCodesDAO) getDao();
		return dao.loadBySystemCodesByKey(systemCodes);
	}

	@Transactional(readOnly=true)
	public Statistics getHibernateStatistics() {
		try {
			return systemCodesDAO.getHibernateSession().getSessionFactory().getStatistics();
		} catch (Exception e) {
			_log.warn("Unable to retrieve hibernate statistics.");
			return null;
		}
	}

	@Transactional(readOnly=true)
	public List<SystemCodes> findSystemCodesByCategory(String category) {
		return systemCodesDAO.findSystemCodesByCategory(category);
	}

	public List<SystemCodes> getAllCategoriesExcept(String... categories) {
		return systemCodesDAO.getAllCategoriesExcept(categories);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<String> getAllCategoryValuesExcept(String... categories) {
		return systemCodesDAO.getAllCategoryValuesExcept(categories);
	}
}
