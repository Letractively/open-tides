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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opentides.bean.AuditLog;
import org.opentides.persistence.AuditLogDAO;
import org.opentides.service.AuditLogService;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service for audit log.
 * 
 * @author allantan
 */
public class AuditLogServiceImpl implements AuditLogService {

	private AuditLogDAO auditLogDAO;
	
	/**
	 * Counts all the record of this object
	 */
	@Transactional(readOnly=true)
	public final long countAll() {
		return auditLogDAO.countAll();
	}

	/**
	 * Counts the matching record of this object
	 */
	@Transactional(readOnly=true)
	public final long countByExample(AuditLog example) {
		return auditLogDAO.countByExample(example);
	}
	
	/**
	 * For retrieving audit log based on the given example.
	 */
	@Override
	public List<AuditLog> findByExample(AuditLog example, int start, int total) {
		return auditLogDAO.findByExample(example, start, total);
	}

	/**
	 * For retrieving all audit log.
	 */
	@Override
	public List<AuditLog> findAll(int start, int total) {
		return auditLogDAO.findAll(start, total);
	}
	
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly=true)
	public List<AuditLog> findLogByReferenceAndClass(String reference,
			List<Class> types) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("reference", reference);
		if (types==null || types.isEmpty())
			return new ArrayList<AuditLog>();
		params.put("entityClass", types);
		return auditLogDAO.findByNamedQuery("jpql.audit.findByReferenceAndClass",params);
	}

	/**
	 * Setter method for auditLogDAO.
	 *
	 * @param auditLogDAO the auditLogDAO to set
	 */
	public final void setAuditLogDAO(AuditLogDAO auditLogDAO) {
		this.auditLogDAO = auditLogDAO;
	}

}
