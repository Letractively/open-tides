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

package org.opentides.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.opentides.bean.DynamicReport;
import org.opentides.bean.ReportDefinition;
import org.opentides.service.ReportService;
import org.opentides.util.StringUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.opentides.bean.BaseReportValidator;

/**
 * @author allantan
 * 
 */
public class GenerateReportController extends AbstractController {
	
    private static Logger _log = Logger.getLogger(GenerateReportController.class);
	
	private String paramView = "/core/report/prompt-parameter";
	
	private ReportService service;
	
	private String reportWebPath = "/jasper";
	
	private Map<String,BaseReportValidator> reportValidators;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// validate the parameters
		ModelAndView view = validateReport(request, response);
		if (view==null) {
			// no error
			return generateReport(request, response);
		} else
			return view;
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView validateReport(HttpServletRequest request,
			HttpServletResponse response) {
		InputStream jasperStream = null;
		InputStream jrXmlStream = null;
		try {			
			jasperStream = getReportStream(request.getParameter(DynamicReport.REPORT_FILE), "jasper");
			jrXmlStream = getReportStream(request.getParameter(DynamicReport.REPORT_FILE), "jrxml");
			
			// if report file is not found, show error message
			if (jasperStream==null || jrXmlStream==null) {
				Map<String, String> model = new HashMap<String, String>();
				model.put("title","error.report");
				model.put("message","error.report-file-not-found");			
				return new ModelAndView("user-message", model);
			}
			
			// check parameters
			List<ReportDefinition>  missingParameters = service.getMissingParameters(
					request.getParameterMap(), jrXmlStream);
			if (!missingParameters.isEmpty()) {
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("missingParameters", missingParameters);
				model.put("requestParameters", request.getParameterMap());
				return new ModelAndView(paramView, model);
			}
			return null;
		} finally {
			try {
				if (jasperStream != null) jasperStream.close();
				if (jrXmlStream != null) jrXmlStream.close();
			} catch (Exception ex) {
				// do nothing
			}
		}			
	}

	@SuppressWarnings("unchecked")
	public ModelAndView generateReport(HttpServletRequest request,
			HttpServletResponse response) {
		InputStream jasperStream = null;
		InputStream jrXmlStream = null;
		try {
			jasperStream = getReportStream(request.getParameter(DynamicReport.REPORT_FILE), "jasper");
			jrXmlStream = getReportStream(request.getParameter(DynamicReport.REPORT_FILE), "jrxml");
			Map<String,String[]> parameters = request.getParameterMap();
			String[] validatorParam = parameters.get("validator");
			if(validatorParam != null){
		    String key = validatorParam[0]; 
		    BaseReportValidator validator = reportValidators.get(key); //retrieve the particular validator of a report
		    if(validator.supports(DynamicReport.class)){  //only validate from the Report Module
		    List<String> errorMessages = validator.validate(request); //validate entered data
			if(errorMessages != null){
				Map<String,Object> model = new HashMap<String,Object>();
			    Map<String,String[]> requestParameters = transformRequest(request);  //retrieve original request parameters
				List<ReportDefinition>  missingParameters = service.getMissingParameters(
						requestParameters , jrXmlStream); //retrieve original missed parameters
				if (!missingParameters.isEmpty()) {
					model.put("missingParameters", missingParameters);
				}
				model.put("requestParameters", requestParameters);
			    model.put("errorMessages",errorMessages);     
				return new ModelAndView(paramView,model);
			     }
			   }else{
				   throw new IllegalArgumentException("Validator does not support command class [" + 
						              DynamicReport.class.getName() + "]");
			   }
		    }
		
			Map<String, Object> reportParam = service.getParameterValues(parameters, jrXmlStream);
			String reportFormat = request.getParameter(DynamicReport.REPORT_FORMAT);
			this.setHeader(response);
			
			
			if (DynamicReport.FORMAT_IMAGE.equals(reportFormat)) {
				byte[] byteArray = service.generateImage(jasperStream, reportParam);
				if(byteArray != null)
				{
					response.setContentLength(byteArray.length);
					response.setContentType("image/png");
					ServletOutputStream out = response.getOutputStream();
					out.write(byteArray, 0, byteArray.length);
					out.flush();
					out.close();
				}
			} else if (DynamicReport.FORMAT_HTML.equals(reportFormat)) {
				String html = service.generateHtml(jasperStream, reportParam);
				response.setContentLength(html.length());
				response.setContentType("text/html");
				ServletOutputStream out = response.getOutputStream();
				out.write(html.getBytes(), 0, html.length());
				out.flush();
				out.close();
			} else if (DynamicReport.FORMAT_PDF.equals(reportFormat)) {
				byte[] byteArray = service.generatePdf(jasperStream, reportParam);
				if(byteArray != null)
				{
					response.setContentLength(byteArray.length);
					response.setContentType("application/pdf");
					if (!StringUtil.isEmpty(request.getParameter("reportName"))){
						response.setHeader("Content-Disposition", "attachment; filename=\"" + request.getParameter("reportName") + ".pdf\"");
					}
					ServletOutputStream out = response.getOutputStream();
					out.write(byteArray, 0, byteArray.length);
					out.flush();
					out.close();
				}
			} else if (DynamicReport.FORMAT_EXCEL.equals(reportFormat)) {
				// not supported yet
			}
		} catch (Exception e) {
			_log.error(e,e);
			Map<String, String> model = new HashMap<String, String>();
			model.put("title","error.report");
			model.put("message","error.report-generation-failed");			
			return new ModelAndView("user-message", model);
		} finally {
			try {
				if (jasperStream != null) jasperStream.close();
				if (jrXmlStream != null) jrXmlStream.close();
			} catch (Exception ex) {
				// do nothing
			}
		}
		return null;
	}
	
	/**
	 * Helper close to retrieve the report file (jasper, jrxml)
	 * from upload path or within the web application.
	 * @param reportFile
	 * @param extType
	 * @return
	 */
	private InputStream getReportStream(String reportFile, String extType) {
		InputStream stream = null;
		DynamicReport report = service.findByReportFile(reportFile);
		if (report!=null) {
			// load report from uploaded path
			String uploadPath = report.getReportPath() + "/" + reportFile + "." + extType;
			try {
				stream = new FileInputStream(uploadPath);
			} catch (FileNotFoundException e) {
				_log.error("Report file not found. ["+uploadPath+"]",e);
			}
		} else {
			// load report from webapp path
			String webPath= this.reportWebPath + "/" + reportFile + "." + extType;
			stream = this.getServletContext().getResourceAsStream(webPath);
		}		
		return stream;
	}
	
	private Map<String,String[]> transformRequest(HttpServletRequest request){
		String[] requestKeys = request.getParameterValues("requestParameters"); 
		Map<String,String[]> parameters = request.getParameterMap();
		Map<String,String[]> newRequest =  new HashMap<String,String[]>();
		for(String key: requestKeys){
			  newRequest.put(key,parameters.get(key));
		}
		return newRequest;
	}
 
	/**
	 * Disables page caching of report.
	 * @param response
	 */
	private void setHeader(HttpServletResponse response) {
		response.setHeader("Cache-Control",
				"no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		response.setHeader("encoding", "utf-8");
	}

	public void setReportWebPath(String reportWebPath) {
		this.reportWebPath = reportWebPath;
	}

	public void setService(ReportService reportService) {
		this.service = reportService;
	}

	public void setReportValidators(Map<String, BaseReportValidator> reportValidators) {
		this.reportValidators = reportValidators;
	}

	
	
	
}