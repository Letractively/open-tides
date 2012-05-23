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

import org.opentides.bean.user.UserGroup;
import org.opentides.bean.user.UserRole;
import org.opentides.service.UserGroupService;
import org.opentides.service.UserWidgetsService;


/**
 * This controller is responsible handling usergroup management.
 * 
 * 
 */
public class UserGroupController extends BaseCrudController<UserGroup> {
	
	UserWidgetsService userWidgetsService;
	
	/* (non-Javadoc)
	 * @see org.opentides.controller.BaseCrudController#preUpdateAction(org.opentides.bean.BaseEntity)
	 */
	@Override
	protected void preUpdateAction(UserGroup command) {
		for (UserRole deleteRole : command.getRemoveList()) {
			((UserGroupService) getService()).removeUserRole(deleteRole);			
		}
		
		List<UserRole> addedList = command.getAddedList();
		if(addedList != null && !addedList.isEmpty())
			userWidgetsService.setupUserGroupWidgets(command, command.getAddedList());
		
		List<UserRole> removeList = command.getRemoveList();
		if(removeList != null && !removeList.isEmpty())
			userWidgetsService.removeUserGroupWidgetsWithAccessCodes(command, command.getRemoveList());
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roles", ((UserGroupService) getService()).getRoles());
		model.put("userGroupList", getService().findAll());
		return model;
	}
	
	public void setUserWidgetsService(UserWidgetsService userWidgetsService) {
		this.userWidgetsService = userWidgetsService;
	}
	
}
