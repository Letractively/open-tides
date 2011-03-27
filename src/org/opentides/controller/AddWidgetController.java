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
import org.opentides.bean.Widget;
import org.opentides.service.UserService;
import org.opentides.service.UserWidgetsService;
import org.opentides.service.WidgetService;
import org.opentides.util.SecurityUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @author allanctan
 *
 */
public class AddWidgetController extends AbstractController {
	
	private WidgetService widgetService;	
	private UserWidgetsService userWidgetsService;
	private UserService userService;
	
	private String formView = "/core/widget/widget-add";
	private String dashboardView = "home";

	@Override
	protected ModelAndView handleRequestInternal(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// is Form Submit?
		if ("POST".equals(request.getMethod())) {
			Map<String, Object> model = new HashMap<String, Object>();
			try {
				userWidgetsService.addUserWidgets(SecurityUtil.getSessionUser().getRealId(), 
						request.getParameter("mergedCheckboxes"));
				model.put("frameRedirect", true);
				return new ModelAndView(formView,model);
				//return new ModelAndView("redirect:"+dashboardView+".jspx",model);
			}catch (Exception e) {
				//error occurs, just redirect page
				model.put("frameRedirect", true);
				return new ModelAndView(formView,model);
				//return new ModelAndView("redirect:"+dashboardView+".jspx",model);
			}
		} else {
			Map<String,Object> model = new HashMap<String,Object>();
			List<Widget> widgets = widgetService.getCurrentUserWidgets();
			List<UserWidgets> installedWidgets = userWidgetsService.findUserWidgets(SecurityUtil.getSessionUser().getRealId(), WidgetService.WIDGET_STATUS_MINIMIZE, WidgetService.WIDGET_STATUS_SHOW);
			for (Widget widget:widgets) {
				for (UserWidgets userWidget:installedWidgets) {
					if (userWidget.getWidget() == widget)
						widget.setInstalled(true);				
				}
			}
			model.put("widgets", widgets);			
			return new ModelAndView(formView, model);
		}
	}
	
	/**
	 * @param formView the formView to set
	 */
	public void setFormView(String formView) {
		this.formView = formView;
	}


	/**
	 * @param successView the successView to set
	 */
	public void setDashboardView(String dashboardView) {
		this.dashboardView = dashboardView;
	}

	/**
	 * @param widgetService the widgetService to set
	 */
	public void setWidgetService(WidgetService widgetService) {
		this.widgetService = widgetService;
	}

	/**
	 * @param userWidgetService the userWidgetService to set
	 */
	public void setUserWidgetsService(UserWidgetsService userWidgetsService) {
		this.userWidgetsService = userWidgetsService;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
