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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentides.bean.UserWidgets;
import org.opentides.service.UserWidgetsService;
import org.opentides.service.WidgetService;
import org.opentides.util.SecurityUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @author ideyatech
 * 
 */
public class DisplayWidgetController extends AbstractController {
	private UserWidgetsService userWidgetsService;
	private WidgetService widgetService;
	private String viewName;
	private String sampleViewName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		String modelAndView = getViewName();
		
		List<UserWidgets> userWidgets = userWidgetsService.findUserWidgets(SecurityUtil.getSessionUser().getRealId(), WidgetService.WIDGET_STATUS_MINIMIZE, WidgetService.WIDGET_STATUS_SHOW);
		Map<String, Object> model = new HashMap<String, Object>();
		int i=0;
		StringBuffer sbParams = new StringBuffer();
		for(Object name:req.getParameterMap().keySet()) {
			if (i != 0) {
				sbParams.append("&");
			}
			sbParams.append(name.toString()+ "="+req.getParameter(name.toString()));
			i++;
		}
		model.put("params", sbParams.toString());
		
		model.put("columnNumber", widgetService.getColumnConfig());
		model.put("userWidgets", userWidgets);
		return new ModelAndView(modelAndView, model);
	}


	/**
	 * @return the viewName
	 */
	public String getViewName() {
		return viewName;
	}

	/**
	 * @param viewName
	 *            the viewName to set
	 */
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}


	public UserWidgetsService getUserWidgetsService() {
		return userWidgetsService;
	}


	public void setUserWidgetsService(UserWidgetsService userWidgetsService) {
		this.userWidgetsService = userWidgetsService;
	}


	public String getSampleViewName() {
		return sampleViewName;
	}


	public void setSampleViewName(String sampleViewName) {
		this.sampleViewName = sampleViewName;
	}


	public WidgetService getWidgetService() {
		return widgetService;
	}


	public void setWidgetService(WidgetService widgetService) {
		this.widgetService = widgetService;
	}

}
