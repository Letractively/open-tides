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

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.opentides.bean.AuditLog;
import org.opentides.bean.Auditable;
import org.opentides.bean.user.SessionUser;
import org.opentides.listener.ApplicationStartupListener;
import org.opentides.persistence.AuditLogDAO;
import org.opentides.util.DateUtil;
import org.opentides.util.HibernateUtil;
import org.opentides.util.SecurityUtil;
import org.opentides.util.StringUtil;

/**
 * Logging DAO for audit tracking.
 * @author allantan
 *
 */
public class AuditLogDAOImpl extends BaseEntityDAOJpaImpl<AuditLog, Long> 
		implements AuditLogDAO {	
	
	private static Logger _log = Logger.getLogger(AuditLogDAOImpl.class);
	
    /* (non-Javadoc)
	 * @see com.ideyatech.core.persistence.impl.BaseEntityDAOJpaImpl#appendClauseToExample(com.ideyatech.core.bean.BaseEntity, boolean)
	 */
	@Override
	protected String appendOrderToExample(AuditLog example) {
		return "order by createDate desc";
	}

	public static void logEvent(String friendlyMessage, String message, Auditable entity) { 		
		Long userId = entity.getAuditUserId();
		String officeName = entity.getAuditOfficeName();
		String username = entity.getAuditUsername();
		if (ApplicationStartupListener.isApplicationStarted()) {
			if (userId==null) {
				_log.warn("No userId specified for audit logging on object ["+entity.getClass().getName()
							+ "] for message ["+message+"]. Retrieving user from interceptor.");
				SessionUser user = SecurityUtil.getSessionUser();
				userId = user.getRealId();
				officeName = user.getOffice();	
				username = user.getUsername();
			} 
		} else {
			userId = new Long(0);
			officeName = "System Evolve";
		}
		
    	Session tempSession = 
            HibernateUtil.getSessionFactory().openSession(); 
		try { 
			AuditLog record = 
	            new AuditLog(friendlyMessage,
	            			message, 
	            			entity.getId(), 
	                        entity.getClass(), 
	                        entity.getReference(),
	                        userId,
	                        username,
	                        officeName); 
		    tempSession.save(record); 
		    tempSession.flush(); 
		} finally { 
		    tempSession.close(); 
		}
    }
	
	@Override
	protected String appendClauseToExample(AuditLog example, boolean exactMatch) {
		StringBuilder append = new StringBuilder("");
		if (example.getStartDate() != null){
			if (!StringUtil.isEmpty(append.toString())){
				append.append(" and ");
			}
			String startDate = DateUtil.dateToString(example.getStartDate(), "yyyy-MM-dd");
			append.append(" obj.createDate >= '");
			append.append(startDate + "'");
		}
		
		if (example.getEndDate() != null){
			if (!StringUtil.isEmpty(append.toString())){
				append.append(" and ");
			}
			String endDate = DateUtil.dateToString(example.getEndDate(), "yyyy-MM-dd");
			append.append(" obj.createDate <= '");
			append.append(endDate + "'");
		}

		if(!StringUtil.isEmpty(example.getLogAction())){
			if (!StringUtil.isEmpty(append.toString())){
				append.append(" and ");
			}
			append.append(" obj.message like '%").append(example.getLogAction()).append("%' ");
		}
		return append.toString();
	}
}
