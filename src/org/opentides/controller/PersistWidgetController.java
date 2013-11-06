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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.opentides.bean.UserWidgets;
import org.opentides.bean.user.BaseUser;
import org.opentides.service.UserService;
import org.opentides.service.UserWidgetsService;
import org.opentides.service.WidgetService;
import org.opentides.util.SecurityUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * @author ideyatech
 * 
 */
public class PersistWidgetController extends MultiActionController {
	
	private static Logger _log = Logger.getLogger(PersistWidgetController.class);
	private UserWidgetsService userWidgetsService;
	private UserService userService;
	
	/**
	 * Public method that will parse the order of our widget and save it to our database
	 * @param req
	 * @param resp
	 * @return
	 */
	public ModelAndView saveWidgetOrder(HttpServletRequest req,
			HttpServletResponse resp) {
		_log.debug("saving widget order...");
		//TODO: 1. validation in parsing the string order (valid, widget exists, proper format, etc) 
		//		2. acegi security
		
		String order = req.getParameter("order");
		BaseUser user = userService.load(SecurityUtil.getSessionUser().getRealId());
		String[] col = order.split("\\|");
		int countCol = 1;
		for (int i=0;i<col.length;i++) {
			String[] widget = col[i].split(",");
			int countRow = 1;
			for (int j=0;j<widget.length;j++) {
				UserWidgets newUserWidget = userWidgetsService.findSpecificUserWidgets(user, widget[j]);
				userWidgetsService.updateUserWidgetsOrder(newUserWidget,countCol,countRow);
				countRow++;
			}
			countCol++;
		}
		
		return null;
	}
	
	
	
	
	/**
	 * show / minimize & hidden (in case of hidden, delete the specific user widget)
	 * @param req
	 * @param resp
	 * @return
	 */
	public ModelAndView saveWidgetStatus(HttpServletRequest req,
			HttpServletResponse resp) {
		_log.debug("saving widget status...");
		//TODO: 1. validation in parsing the string order (valid, widget exists, proper format, etc) 
		//		2. acegi security
		
		String status = req.getParameter("status");
		//must be a valid state before processing the code...
		if (status.equals(""+WidgetService.WIDGET_STATUS_MINIMIZE) || status.equals(""+WidgetService.WIDGET_STATUS_SHOW) 
				|| status.equals(""+WidgetService.WIDGET_STATUS_REMOVE)) {
			String widgetName = req.getParameter("widgetName");
			BaseUser user = userService.load(SecurityUtil.getSessionUser().getRealId());
			UserWidgets userWidgets = userWidgetsService.findSpecificUserWidgets(user, widgetName);
			if (userWidgets != null) {
				try {
					Integer statusNum = Integer.parseInt(status);
					userWidgetsService.updateUserWidgetsStatus(userWidgets, statusNum);
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	
	

	public UserWidgetsService getUserWidgetsService() {
		return userWidgetsService;
	}

	public void setUserWidgetsService(UserWidgetsService userWidgetsService) {
		this.userWidgetsService = userWidgetsService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}


}
