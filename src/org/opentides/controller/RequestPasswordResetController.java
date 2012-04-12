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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.opentides.bean.PasswordReset;
import org.opentides.service.UserService;
import org.opentides.service.impl.UserServiceImpl;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;


/**
 * @author allanctan
 *
 */
@SuppressWarnings("deprecation")
public class RequestPasswordResetController extends SimpleFormController {
	
	private static Logger _log = Logger.getLogger(UserServiceImpl.class);

	private UserService userService;
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Map<String,String> referenceData(HttpServletRequest request) throws Exception {
		Map<String,String> model = new HashMap<String,String>();
		model.put("action", "request");
		return model;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java.lang.Object)
	 */
	@Override
	protected ModelAndView onSubmit(Object command) throws Exception {
		PasswordReset passwd = (PasswordReset) command;
		Map<String,String> model = new HashMap<String,String>();
		try{
			userService.requestPasswordReset(passwd.getEmailAddress());
			model.put("message", "msg.instructions-for-reset-sent");
			model.put("title", "label.forgot-password");
		} catch (Exception ce) {
			try {
				_log.error("Failed to send password request email.",ce);
			} catch (Exception e) {
				_log.error("Failed to send password request email.");
			}
			model.put("message", "msg.failed-to-send-email");
			model.put("title", "label.forgot-password");			
		}
		return new ModelAndView(getSuccessView(), model);
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
