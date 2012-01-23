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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentides.bean.user.UserGroup;
import org.opentides.service.UserGroupService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Retrieves list of roles for the given userGroupId
 * Used by copy-from.
 * @author allantan
 *
 */
public class RolesListController extends AbstractController {

	private UserGroupService userGroupService;
	private String roleListView;
	
	/**
	 * Setter method for roleListView.
	 *
	 * @param roleListView the roleListView to set
	 */
	public final void setRoleListView(String roleListView) {
		this.roleListView = roleListView;
	}

	/**
	 * Setter method for userGroupService.
	 *
	 * @param userGroupService the userGroupService to set
	 */
	public final void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		String userGroupId = req.getParameter("userGroupId");
		UserGroup userGroup = userGroupService.load(userGroupId);
		List<String> roleNames = userGroup.getRoleNames();
		return new ModelAndView(roleListView, "roleNames", roleNames);
	}
	
}
