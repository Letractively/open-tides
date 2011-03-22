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

import org.apache.log4j.Logger;
import org.opentides.service.UserService;
import org.opentides.util.StringUtil;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class ViewSessionController extends AbstractController {
	@SuppressWarnings("unused")
	private static Logger _log = Logger.getLogger(ViewSessionController.class);
	private UserService userService;
	private String viewName;

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String username = request.getParameter("logout");
		if (!StringUtil.isEmpty(username)) {
			userService.forceLogout(username);
		}
		Map<String, Object> model = referenceData(request);
		return new ModelAndView(viewName, model);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) throws Exception {
		List<SessionInformation> sessions = userService.getAllLoggedUsers();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sessionList", sessions);
		return model;
	}

	/**
	 * @param userService the userService to set
	 */
	public final void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @param viewName the viewName to set
	 */
	public final void setViewName(String viewName) {
		this.viewName = viewName;
	}

}
