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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opentides.bean.DynamicReport;
import org.opentides.persistence.ReportDAO;


/**
 * This is the dao implementation for report.
 * Auto generated by high tides.
 * @author hightides
 *
 */
public class ReportDAOJpaImpl extends BaseEntityDAOJpaImpl<DynamicReport, Long>
		implements ReportDAO {
//-- Start custom codes. Do not delete this comment line.
	
	@SuppressWarnings("unchecked")
    public List<Object[]> getParamOptionResults(String queryString){
		List<Object[]> queryResult = getEntityManager().createNativeQuery(
				queryString).getResultList();				
		return queryResult;
	}
    
	/**
	 * Returns all the reports ordered by report group.
	 * @return
	 */
	public List<DynamicReport> findAllReportsOrderByReportGroup() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DynamicReport> list = findByNamedQuery("jpql.report.findAllReportsOrderByReportGroup", map);
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
	
//- End custom codes. Do not delete this comment line.
}
