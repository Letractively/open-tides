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
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Use this controller to map URL to jsp page directly.
 * 
 * @author allanctan
 */
public class ViewMapController extends AbstractController {
	private Map<String,String> viewMap;
	private String defaultView;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String uri = request.getRequestURI();
		String key;
		String viewName;

		int idx = uri.lastIndexOf("/");
		if (idx >= 0)
			key = uri.substring(idx+1);
		else
			key = uri;
		
		// 07/31/2011: allanctan 
		// added filtering of URL postfix for ; and ?
		// (e.g. login.jspx;sessionid=... or login.jspx?username=...)
		int semi = key.indexOf(";");
		int question = key.indexOf("?");
		if (semi > 0 || question > 0) {
			int post = (semi > question)?semi:question;
			key = key.substring(0, post);
		}
		if (viewMap.containsKey(key))
			viewName = viewMap.get(key);
		else
			viewName = defaultView;
		
		Map<String, String> model = new HashMap<String, String>();
		for(Object name:request.getParameterMap().keySet()) {
			model.put(name.toString(), request.getParameter(name.toString()));
		}
		
		return new ModelAndView(viewName, model);
	}

	public void setViewMap(Map<String,String> viewMap) {
		this.viewMap = viewMap;
	}

	public void setDefaultView(String defaultView) {
		this.defaultView = defaultView;
	}
}
