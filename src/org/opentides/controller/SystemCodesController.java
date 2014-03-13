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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentides.bean.SystemCodes;
import org.opentides.bean.user.BaseUser;
import org.opentides.editor.SystemCodeEditor;
import org.opentides.service.SystemCodesService;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;


/**
 * This controller is responsible in handling system codes (aka lookup) 
 * management. This class extended BaseCrudController to extend
 * standard CRUD operations.
 * 
 * @author allanctan
 */
public class SystemCodesController extends BaseCrudController<SystemCodes> {
	
	private SystemCodesService systemCodesService;

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#initBinder(javax.servlet.http.HttpServletRequest, org.springframework.web.bind.ServletRequestDataBinder)
	 */
	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		// TODO Auto-generated method stub
		binder.registerCustomEditor(SystemCodes.class, new SystemCodeEditor(systemCodesService));
	}
	
	/* (non-Javadoc)
	 * @see org.opentides.controller.BaseCrudController#preDeleteAction(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.validation.BindException, java.lang.String)
	 */
	@Override
	protected void preDeleteAction(HttpServletRequest request,
			HttpServletResponse response, BindException errors, String id) {
		this.setSkipAction(true);
		try{
			getService().delete(id);
		} catch (Exception e) {
			 try {
				 response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			 } catch (IOException err) {
				 err.printStackTrace();
			 }
		}
	}



	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#referenceData(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.Errors)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String,Object> model = new HashMap<String,Object>();
		SystemCodesService scService = (SystemCodesService) this.getService();
		//model.put("categories", scService.getAllCategories());
		model.put("categories", scService.getAllCategoryValuesExcept("KEYGEN"));
		model.put("officeList", scService.findSystemCodesByCategory(BaseUser.CATEGORY_OFFICE));
		return model;
	}

	/**
	 * Setter method for systemCodesService.
	 *
	 * @param systemCodesService the systemCodesService to set
	 */
	public final void setSystemCodesService(SystemCodesService systemCodesService) {
		this.systemCodesService = systemCodesService;
	}	
}
