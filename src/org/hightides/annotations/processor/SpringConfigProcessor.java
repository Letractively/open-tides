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

package org.hightides.annotations.processor;

import java.io.File;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hightides.annotations.param.BaseParamReader;
import org.hightides.annotations.util.SpringXMLUtil;
import org.opentides.util.StringUtil;

/**
 * This class is responsible in updating the spring configuration file.
 * 
 * @author allantan
 */
public class SpringConfigProcessor implements Processor {
	
	public static final String controllerContextFile = "applicationContext-controller.xml";
	public static final String serviceContextFile    = "applicationContext-service.xml";
	public static final String daoContextFile        = "applicationContext-dao.xml";
	private static final String contextPath 		 = "/WebContent/WEB-INF/context/";
	
	/* (non-Javadoc)
	 * @see org.hightides.annotations.processor.Processor#execute(java.util.Map)
	 */
	public void execute(Map<String, Object> params) {
		String annotation = (String) params.get("annotation");
		if (StringUtil.isEmpty(annotation))
			return;
		if ("org.hightides.annotations.Controller".equals(annotation)) {
			addControllerConfiguration(params);
		} else if ("org.hightides.annotations.Service".equals(annotation)) {
			addServiceConfiguration(params);
		} else if ("org.hightides.annotations.Dao".equals(annotation)) {
			addDaoConfiguration(params);
		}
	}
	
	/**
	 * Adds controller bean and url mapping to spring config
	 * @param params
	 */
	private void addControllerConfiguration(Map<String, Object> params) {
		// Add controller bean
		//		<!-- Template Controller -->
		//		<bean id="templateController" class="com.ideyatech.veeui.controller.TemplateController" autowire="byName">
		//			<property name="service" ref="templateService"></property>
		//			<property name="commandName" value="template" />
		//			<property name="commandClass" value="com.ideyatech.veeui.bean.Template" />
		//			<property name="formView" value="template/template-form" />
		//			<property name="searchView" value="template/template-list" />
		//			<property name="refreshView" value="template/template-refresh" />
		//			<property name="pageSize" value="${search.page.size}" />
		//			<property name="numLinks" value="${search.links.displayed}" />
		//		</bean>
		String xmlFilename = (new File(".")).getAbsolutePath() + contextPath + controllerContextFile;
		Element bean = DocumentHelper.createElement("bean");
		bean.addAttribute("id", params.get("modelName")+"Controller");
		bean.addAttribute("class", params.get("package")+"."+params.get("className")+"Controller");
		bean.addAttribute("autowire", "byName");
		addPropertyByRef(bean,"service",params.get("modelName")+"Service");
		if (BaseParamReader.isValidation()) {
			addPropertyByRef(bean,"validator",params.get("modelName")+"Validator");
		}
		addPropertyByValue(bean,"commandName", params.get("modelName")   +"");
		addPropertyByValue(bean,"commandClass",params.get("modelPackage")+"."+params.get("className"));
		addPropertyByValue(bean,"formView",    params.get("modelName")   +"/"+params.get("modelName")+"-form");
		addPropertyByValue(bean,"searchView",  params.get("modelName")   +"/"+params.get("modelName")+"-list");
		addPropertyByValue(bean,"refreshView", params.get("modelName")   +"/"+params.get("modelName")+"-refresh");
		addPropertyByValue(bean,"pageSize","${search.page.size}");
		addPropertyByValue(bean,"numLinks","${search.links.displayed}");
		Element ret = SpringXMLUtil.addBean(xmlFilename, bean, params.get("syncMode").toString());
		if (ret != null) {	
			// Add URL mapping
			// <prop key="/template.jspx">templateController</prop>
			Element urlMap = DocumentHelper.createElement("prop");
			urlMap.addAttribute("key", params.get("pageName")+"");
			urlMap.addText(params.get("modelName")+"Controller");
			// no need to backup because backup is already done by addBean earlier
			SpringXMLUtil.addURLMapProperty(xmlFilename, urlMap, false);
		}
	}

	/**
	 * Adds service bean to spring config
	 * @param params
	 */
	private void addServiceConfiguration(Map<String, Object> params) {
		// Add service bean
		//	<bean id="templateService" class="com.ideyatech.veeui.service.impl.TemplateServiceImpl"
		//		autowire="byName">
		//		<property name="dao" ref="templateDAO" />
		//	</bean>
		String xmlFilename = (new File(".")).getAbsolutePath() + contextPath + serviceContextFile;
		Element bean = DocumentHelper.createElement("bean");
		bean.addAttribute("id", params.get("modelName")+"Service");
		bean.addAttribute("class", params.get("package")+"."+params.get("className")+"ServiceImpl");
		bean.addAttribute("autowire", "byName");
		addPropertyByRef(bean,"dao",params.get("modelName")+"DAO");
		SpringXMLUtil.addBean(xmlFilename, bean, params.get("syncMode").toString());
	}

	/**
	 * Adds dao bean to spring config
	 * @param params
	 */
	private void addDaoConfiguration(Map<String, Object> params) {
		//		<bean id="templateDAO" class="com.ideyatech.veeui.dao.impl.TemplateJpaImpl"
		//			parent="baseDAO" autowire="byName" />		
		String xmlFilename = (new File(".")).getAbsolutePath() + contextPath + daoContextFile;
		Element bean = DocumentHelper.createElement("bean");
		bean.addAttribute("id", params.get("modelName")+"DAO");
		bean.addAttribute("class", params.get("package")+"."+params.get("className")+"DAOJpaImpl");
		bean.addAttribute("autowire", "byName");
		bean.addAttribute("parent", "baseDAO");		
		SpringXMLUtil.addBean(xmlFilename, bean, params.get("syncMode").toString());
	}
	
	/**
	 * Adds validator bean to spring config
	 * @param params
	 */
	protected void addValidatorConfiguration(Map<String, Object> params) {
		//		<bean id="templateValidator" class="com.ideyatech.veeui.validator.TemplateValidator"/>		
		String xmlFilename = (new File(".")).getAbsolutePath() + contextPath + controllerContextFile;
		Element bean = DocumentHelper.createElement("bean");
		bean.addAttribute("id", params.get("modelName")+"Validator");
		bean.addAttribute("class", params.get("package")+"."+params.get("className")+"Validator");
		SpringXMLUtil.addBean(xmlFilename, bean, params.get("syncMode").toString());
	}
	
	/**
	 * Helper to add a property element in a bean.
	 * @param element
	 * @param name
	 * @param value
	 */
	private void addPropertyByValue(Element element, String name, String value) {
		Element newEl = element.addElement("property");
		newEl.addAttribute("name",  name);
		newEl.addAttribute("value", value);		
	}
	
	/**
	 * Helper to add a property element in a bean.
	 * @param element
	 * @param name
	 * @param ref
	 */
	private void addPropertyByRef(Element element, String name, String ref) {
		Element service = element.addElement("property");
		service.addAttribute("name", name);
		service.addAttribute("ref", ref);
	}
}