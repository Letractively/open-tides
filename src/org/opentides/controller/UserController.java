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

import java.beans.PropertyEditorSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.opentides.bean.SystemCodes;
import org.opentides.bean.user.BaseUser;
import org.opentides.bean.user.UserCredential;
import org.opentides.bean.user.UserGroup;
import org.opentides.editor.SystemCodeEditor;
import org.opentides.editor.UserGroupEditor;
import org.opentides.service.UserGroupService;
import org.opentides.util.SecurityUtil;
import org.opentides.util.StringUtil;
import org.springframework.web.bind.ServletRequestDataBinder;


/**
 * This controller is responsible handling user management.
 * 
 * @author allanctan
 */
public class UserController extends BaseCrudController<BaseUser> {
	private UserGroupService userGroupService;
	
	@Override
	protected void preCreateAction(BaseUser command) {
		UserCredential credential = command.getCredential();
		if (!StringUtil.isEmpty(credential.getNewPassword()))
			credential.setPassword(SecurityUtil.encryptPassword(credential.getNewPassword()));
	}

	@Override
	protected void preUpdateAction(BaseUser command) {
		UserCredential credential = command.getCredential();
        if (!StringUtil.isEmpty(credential.getNewPassword()))
            credential.setPassword(SecurityUtil.encryptPassword(credential.getNewPassword()));
	}

	/**
	 * Overidden to provide usergroup list to page
	 */
	@Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		List<UserGroup> groups = userGroupService.findAll();
		model.put("groups", (groups.size() > 0) ? groups : null);
		model.put("officeList", getSystemCodesByCategory(BaseUser.CATEGORY_OFFICE));
		return model;
	}

	/**
	 * Implements initBinder from BaseCrudController to bind UserGroup class
	 */
	@Override
	protected void initBinder(HttpServletRequest arg0,
			ServletRequestDataBinder binder) throws Exception {
		PropertyEditorSupport pes = new UserGroupEditor(userGroupService);
		binder.registerCustomEditor(UserGroup.class, pes);
		binder.registerCustomEditor(SystemCodes.class, new SystemCodeEditor(getSystemCodesService()));
	}

	/**
	 * @param userGroupService
	 *            the userGroupService to set
	 */
	public void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}

}
