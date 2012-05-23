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
package org.opentides.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.opentides.bean.DynamicReport;
import org.opentides.bean.ReportDefinition;

/**
 * @author allantan
 *
 */
public interface ReportService extends BaseCrudService<DynamicReport> {
	/**
	 * Converts the parameters to the given datatype in the report.
	 * @param requestParams
	 * @param reportFile
	 * @return
	 */
	public Map<String, Object> getParameterValues(Map<String, String[]> requestParams, 
			InputStream reportFile); 
	/**
	 * Returns the list of missing parameters based on the 
	 * specified report parameters.
	 * @param requestParams
	 * @param reportFile
	 * @return
	 */
	public List<ReportDefinition> getMissingParameters(Map<String, String[]> requestParams, 
			InputStream reportFile); 
	/**
	 * Generates the report as graphical image.
	 * @param reportFile
	 * @param parameterMap
	 * @return
	 */
	public byte[] generateImage(InputStream reportFile,
			Map<String, Object> parameterMap);
	/**
	 * Generates the report in html format.
	 * @param reportFile
	 * @param parameterMap
	 * @return
	 */
	public String generateHtml(InputStream reportFile,
			Map<String, Object> parameterMap);
	/**
	 * Generates the report in pdf format.
	 * @param reportFile
	 * @param parameterMap
	 * @return
	 */
	public byte[] generatePdf(InputStream reportFile,
			Map<String, Object> parameterMap);
	/**
	 * Returns the report object based on the given name
	 * @param name
	 * @return
	 */
	public DynamicReport findByName(String name);	
	/**
	 * Returns the report object based on the given report file
	 * @param reportFile
	 * @return
	 */
	public DynamicReport findByReportFile(String reportFile);
	/**
	 * Returns all the reports that are available and accessible
	 * to the current user.
	 * @return
	 */
	public List<DynamicReport> getCurrentUserReports();
	
	/**
	 * Returns all the reports ordered by report group.
	 * @return
	 */
	public List<DynamicReport> findAllReportsOrderByReportGroup();
}
