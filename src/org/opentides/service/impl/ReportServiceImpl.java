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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.opentides.bean.DynamicReport;
import org.opentides.bean.DynamicReportParameter;
import org.opentides.bean.ReportDefinition;
import org.opentides.bean.SortedProperties;
import org.opentides.persistence.ReportDAO;
import org.opentides.service.ReportService;
import org.opentides.util.SecurityUtil;
import org.opentides.util.FileUtil;
import org.opentides.util.StringUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author allantan
 *
 */
public class ReportServiceImpl extends BaseCrudServiceImpl<DynamicReport> implements
		ReportService {
    private static Logger _log = Logger.getLogger(ReportServiceImpl.class);

	private DataSource dataSource;
	private Map<String, DynamicReportParameter> dynamicParameters;
	private String jasperPath;

	@Transactional(readOnly=true)
	public DynamicReport findByName(String name) {
		DynamicReport example = new DynamicReport();
		example.setName(name);
		List<DynamicReport> reportList = getDao().findByExample(example, true);
		if (reportList != null && reportList.size() > 0) {
			return reportList.get(0);
		}
		return null;
	}
	
	@Transactional(readOnly=true)
	public DynamicReport findByReportFile(String reportFile) {
		DynamicReport example = new DynamicReport();
		example.setReportFile(reportFile);
		List<DynamicReport> reportList = getDao().findByExample(example, true);
		if (reportList != null && reportList.size() > 0) {
			return reportList.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)	
	public Map<String, Object> getParameterValues(
			Map<String, String[]> requestParams, InputStream reportFile) {
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(reportFile);
			List<Element> list = document.getRootElement().elements("parameter");
			for (String param:requestParams.keySet()) {
				// skip parameter - "report_format" and "report_file"
				if (DynamicReport.REPORT_FILE.equals(param) || DynamicReport.REPORT_FORMAT.equals(param))
					continue;
				for(Element elem:list) {
					Attribute attrib = elem.attribute("name");
					if (attrib.getValue().equals(param)) {
						Attribute attrClass = elem.attribute("class");
						String className = "java.lang.String";
						String[] valueArr = requestParams.get(param);
						String value = StringUtil.explode(',', valueArr);
						//ignore empty parameters
						if (StringUtil.isEmpty(value)) 
							continue;
						if (attrClass!=null) 
							className = attrClass.getValue();
						try {
							if ("java.io.InputStream".equals(className)) {
								File image = new File(jasperPath + value);
	 							InputStream stream = new FileInputStream(image);
	 							if (stream == null)
	 								_log.warn("Unable to find file ["+ jasperPath + value+"]");
	 							else
	 								ret.put(param, stream);
							} else if("java.util.List".equals(className)){
								if(value!=null){
									String[] values = value.split(","); 
									List<String> temp = Arrays.asList(values);
									ret.put(param, temp);
							    }
							}else {
								Class classDefinition = Class.forName(className);
						        Object objectValue = classDefinition.getConstructor(String.class).newInstance(value);					
						        ret.put(param, objectValue);
							}
						} catch (Exception e) {
							_log.error("Failed to parse parameter ["+param+"] with value ["+value+"].", e);
						}
					}
				}
				
			}
		} catch (DocumentException e) {
			_log.error(e,e);
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)	
	public List<ReportDefinition> getMissingParameters(
			Map<String, String[]> requestParams, InputStream reportFile) {
		List<ReportDefinition> missing = new ArrayList<ReportDefinition>();
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(reportFile);
			List<Element> list = document.getRootElement().elements("parameter");
			for(Element elem:list) {
				Attribute name = elem.attribute("name");
				boolean found = false;
				for(String param:requestParams.keySet()) {
					if (name.getValue().equals(param)) {
						found = true;
						continue;
					}
				}
				if (!found) {
					/* Sample entry
					 * 	<parameter name="office" class="java.lang.String">
					 *		<property name="prompt.type" value="dropdown"/>
					 *		<property name="prompt.query" value="select key as key_, value as value_ from system_codes
					 *			where category_ = :category"/>
					 *	</parameter>
					 *
					 *  :[paramName] - where paramName will be replaced by the value passed as parameter to the jasper 
					 *  	report with the same name as [paramName]
					 */
					// add to missingParameter
					ReportDefinition missed = new ReportDefinition();
					// set the name
					missed.setName(name.getValue());
					// set the class
					Attribute clazz = elem.attribute("class");
					missed.setClazz(clazz.getValue());
					List<Element> props =elem.elements("property");
					Properties property = new SortedProperties();
					for (Element prop:props) {
						String propName = prop.attributeValue("name");
						String propValue = prop.attributeValue("value");
						if ("prompt.type".equals(propName)) {
							missed.setType(propValue);
						} else if ("prompt.query".equals(propName)) {
							// extract the native query to database							
							String queryName = propValue;
							if(!StringUtil.isEmpty(queryName)){
								//try to match :[paramName]
								Matcher matcher = Pattern.compile(":(\\w+)").matcher(queryName); 
								while (matcher.find()) {
									String paramName = matcher.group(0).substring(1);
									if (!StringUtil.isEmpty(paramName) && requestParams.get(paramName)!=null){
										String[] paramValueArr = requestParams.get(paramName);
										String paramValue = StringUtil.explode(',', paramValueArr);
										queryName = queryName.replaceAll(":" + paramName, paramValue);
									}
								}
							}
							
							List<Object[]> rs = ((ReportDAO) getDao()).getParamOptionResults(queryName);
							for (Object[] option : rs) {
								property.put(Long.valueOf(option[1].toString()), option[0].toString());
							}
						}else if("prompt.command".equals(propName)){
							missed.setType(propName);
							property.put(propValue, dynamicParameters.get(propValue));
						}else
							property.put(propName, propValue);
					}
					missed.setProperties(property);
					missing.add(missed);
				}
			}
			if (!requestParams.containsKey(DynamicReport.REPORT_FORMAT)) {
				ReportDefinition missed = new ReportDefinition();
				missed.setName("reportFormat");
				missed.setClazz("java.lang.String");
				missed.setType("dropdown");
				Properties props = new Properties();
				props.put(DynamicReport.FORMAT_EXCEL, "Excel");
				props.put(DynamicReport.FORMAT_HTML, "Html");
				props.put(DynamicReport.FORMAT_IMAGE, "Graph Image");
				props.put(DynamicReport.FORMAT_PDF, "PDF");
				missed.setProperties(props);
				missing.add(missed);
			}
		} catch (DocumentException e) {
			_log.error(e,e);
		}
		return missing;
	}
	
	/**
	 * Generates Jasper Reports in PDF format.
	 * @param reportFile
	 * @param parameterMap
	 * @return
	 */
	@Transactional(readOnly=true)	
	public byte[] generatePdf(InputStream reportFile, Map<String, Object> parameterMap) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile, parameterMap, connection);
			byte[] pdfByteArray = JasperExportManager.exportReportToPdf(jasperPrint);
			return pdfByteArray;
		} catch (Exception e) {
			throw new RuntimeException("Error generating report.",e);
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// do nothing
			}
		}
	}
	
	/**
	 * Generates Jasper Reports in PDF format.
	 * @param reportFile
	 * @param parameterMap
	 * @return
	 */
	@Transactional(readOnly=true)	
	public String generateHtml(InputStream reportFile, Map<String, Object> parameterMap) {
		File targetFile;
		try {
			targetFile = File.createTempFile("Jasper_", ".html");
		} catch (IOException e1) {
			throw new RuntimeException("Unable to create temporary report file.",e1);
		}
		targetFile.deleteOnExit();
		this.generateHtml(reportFile, parameterMap, targetFile.getAbsolutePath());
		return FileUtil.readFile(targetFile);
	}
	
	/**
	 * Generates report images as png.
	 * Useful for displaying graph/charts.
	 * 
	 * @param reportFile
	 * @param parameterMap
	 * @param fileName
	 * @return
	 */
	@Transactional(readOnly=true)
	public byte[] generateImage(InputStream reportFile, 
			Map<String, Object> parameterMap) {
		File targetFile;
		try {
			targetFile = File.createTempFile("Jasper_", ".html");
		} catch (IOException e1) {
			throw new RuntimeException("Unable to create temporary report file.",e1);
		}
		targetFile.deleteOnExit();
		String filename = targetFile.getAbsolutePath();
		// generate the image
		this.generateHtml(reportFile, parameterMap, filename);
		File imageFile = new File(filename + "_files/img_0_0_0");
		if (imageFile.exists()) {
			return FileUtil.readFileAsBytes(imageFile);
		} else
			return null;
	}

	/**
	 * Generates reports as HTML.
	 * 
	 * @param reportFile
	 * @param parameterMap
	 * @param targetFile
	 */
	private void generateHtml(InputStream reportFile,
			Map<String, Object> parameterMap, String targetFile) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile, parameterMap, connection);
			JasperExportManager.exportReportToHtmlFile(jasperPrint, targetFile);
		} catch (Exception e) {
			throw new RuntimeException("Error generating report.",e);
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				// do nothing
			}
		}
	}
	
	/**
	 * Returns all the report that are available and accessible
	 * to the current user.
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<DynamicReport> getCurrentUserReports() {
		DynamicReport example = new DynamicReport();
		List<DynamicReport> reports = new ArrayList<DynamicReport>();
		for (DynamicReport report:findByExample(example, true)) {
			if (StringUtil.isEmpty(report.getAccessCode()))
				reports.add(report);
			else if (SecurityUtil.currentUserHasPermission(report.getAccessCode())) 
				reports.add(report);					
		}
		return reports;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setJasperPath(String jasperPath) {
		this.jasperPath = jasperPath;
	}

	public void setDynamicParameters(
			Map<String, DynamicReportParameter> dynamicParameters) {
		this.dynamicParameters = dynamicParameters;
	}
	
	
	
}


