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
package org.opentides.validator;

import javax.persistence.EntityNotFoundException;

import org.opentides.bean.DynamicReport;
import org.opentides.service.ReportService;
import org.opentides.util.FileUtil;
import org.opentides.util.StringUtil;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


/**
 * @author allanctan
 * 
 */
public class ReportValidator implements Validator {
	
	private ReportService reportService;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return DynamicReport.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object clazz, Errors e) {
		DynamicReport report = (DynamicReport) clazz;

		if(isDuplicateName(report))
			e.reject("error.duplicate-name", new Object[]{"\""+report.getName()+"\"","name"}, "\""+report.getName() +"\" already exists. Please try a different name.");
		
		ValidationUtils.rejectIfEmpty(e, "name", "error.required", new Object[] { "Name" });
		
		String reportFile = "";
		
		if (report.getJasperFile()==null && report.isNew())
			e.reject("error.required", new Object[] { "Jasper File" }, "Jasper File is required.");
		else {
			String jasperFile = FileUtil.getFilename(report.getJasperFile().getOriginalFilename());
			if (!StringUtil.isEmpty(jasperFile)) {
				if (jasperFile.endsWith(".jasper"))
					reportFile = jasperFile.substring(0, jasperFile.length()-7);
				else
					e.reject("error.jasper-file-must-have-extension-of-jasper", "Jasper File must have extension of .jasper");
			} else if (report.isNew())
				e.reject("error.required", new Object[] { "Jasper File" }, "Jasper File is required.");				
		}
		if (report.getJrxmlFile()==null && report.isNew())
			e.reject("error.required", new Object[] { "Jrxml File" }, "Jrxml File is required.");
		else {
			String jrxmlFile = FileUtil.getFilename(report.getJrxmlFile().getOriginalFilename());
			if (!StringUtil.isEmpty(jrxmlFile)) {
				if (jrxmlFile.endsWith(".jrxml")) {
					if (reportFile.equals(jrxmlFile.substring(0, jrxmlFile.length()-6))) 
						report.setReportFile(reportFile);
					else 
						e.reject("error.jrxml-file-must-have-same-filename-with-jasper-file","Jrxml File must have same filename with Jasper File.");	
				} else
					e.reject("error.jrxml-file-must-have-extension-of-jrxml", "Jrxml File must have extension of .jrxml");
			} else if (report.isNew()) 
				e.reject("error.required", new Object[] { "Jrxml File" }, "Jrxml File is required.");
		}
	}
	
	/**
	 * 
	 * @param fieldName name of field
	 * @return boolean returns true if duplicate name was found, false otherwise
	 */
	public boolean isDuplicateName(DynamicReport report){
		DynamicReport key;
		try {
			key = this.reportService.findByName(report.getName());
			if (key != null){
				if(!key.getId().equals(report.getId()))
					return true;
			}
		} catch (EntityNotFoundException e) {
			return false;
		}
		return false;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
}
