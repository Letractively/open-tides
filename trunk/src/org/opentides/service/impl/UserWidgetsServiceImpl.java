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
package org.opentides.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.opentides.bean.UserWidgets;
import org.opentides.bean.Widget;
import org.opentides.bean.user.BaseUser;
import org.opentides.bean.user.UserGroup;
import org.opentides.bean.user.UserRole;
import org.opentides.persistence.UserWidgetsDAO;
import org.opentides.service.UserService;
import org.opentides.service.UserWidgetsService;
import org.opentides.service.WidgetService;
import org.opentides.util.SecurityUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is the service implementation for UserWidgets.
 * Auto generated by high tides.
 * @author hightides
 *
 */
public class UserWidgetsServiceImpl extends BaseCrudServiceImpl<UserWidgets>
		implements UserWidgetsService {

//-- Start custom codes. Do not delete this comment line.
	private UserService userService;
	private WidgetService widgetService;
	
	private static Logger _log = Logger
		.getLogger(UserWidgetsServiceImpl.class);


	/* (non-Javadoc)
	 * @see org.opentides.service.UserWidgetsService#addUserWidgets(long, java.lang.String)
	 */
	public void addUserWidgets(long userId, String selectedWidgets) {
		String[] widgetIds = selectedWidgets.split(",");
		BaseUser user = userService.load(SecurityUtil.getSessionUser().getRealId());
		for (String widgetId:widgetIds) {
			
			int[] pos = getColumnRowOfUserWidget(userId);
			
			Widget widget = widgetService.load(widgetId);
			if (widget==null) {			
				_log.error("Attempting to add new widget with id["+widgetId+"] but widget does not exist.");
			} else {
				UserWidgets userWidget = new UserWidgets();
				userWidget.setUser(user);
				userWidget.setWidget(widget);
				userWidget.setColumn(pos[0]);
				userWidget.setRow((int)++pos[1]);
				userWidget.setStatus(1);
				getDao().saveEntityModel(userWidget);
			}
		}
	}
	@Transactional(readOnly = true)
	public List<UserWidgets> findUserWidgets(long userId, Integer... widgetStatus) {
		return ((UserWidgetsDAO) getDao()).findUserWidgets(userId, widgetStatus);
	}
	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @param widgetService the widgetService to set
	 */
	public void setWidgetService(WidgetService widgetService) {
		this.widgetService = widgetService;
	}
	@Transactional(readOnly = true)
	public UserWidgets findSpecificUserWidgets(BaseUser user, String widgetName) {
		UserWidgets example = new UserWidgets();
		Widget widgetObj = new Widget();
		//widgetObj.setIsUserDefined(true);
		widgetObj.setName(widgetName);
		example.setUser(user);
		example.setWidget(widgetObj);
		return this.findByExample(example, 0, 1).get(0);
	}

	public void updateUserWidgetsOrder(UserWidgets userWidgets, int column, int row) {
		userWidgets.setColumn(column);
		userWidgets.setRow(row);
		this.save(userWidgets);
	}

	public void updateUserWidgetsStatus(UserWidgets userWidgets, Integer status) {
		if (status == WidgetService.WIDGET_STATUS_REMOVE) {
			this.delete(userWidgets.getId());
		}else {
			userWidgets.setStatus(status);
			this.save(userWidgets);
		}
	}

	private int[] getColumnRowOfUserWidget(long userId) {
		int COLUMN = widgetService.getColumnConfig();
		long row = 0;
		int col = 1;
		int[] val = new int[2];
		for (int start=1; start<=COLUMN; start++) {
			long count = countUserWidgetsColumn(start, userId);
			/* I cannot get the proper count when using countByExample
			UserWidgets columnWidgets = new UserWidgets();
			columnWidgets.setUser(user);
			columnWidgets.setColumn(start);
			long count = this.countByExample(columnWidgets, true);
			*/
			if (start == 1) {
				row = count;
				col = start;
			}
			if (row > count) {
				row = count;
				col = start;
			}
		}
		val[0] = col;
		val[1] = (int)row;
		return val;
	}
	
	
	public void addUserWidgets(long userId, Widget widget) {
		/* Steps in adding user widget on dashboard
		 * 1. Find the column and row where we're going to add the widget
		 * 2. Add the widget
		 */
		BaseUser user = userService.load(userId);
		
		int[] pos = getColumnRowOfUserWidget(userId);
		
		UserWidgets userWidget = new UserWidgets();
		userWidget.setUser(user);
		userWidget.setWidget(widget);
		userWidget.setColumn(pos[0]);
		userWidget.setRow((int)++pos[1]);
		userWidget.setStatus(1);
		_log.debug("saving widget "+userWidget.getWidget().getName()+" at column "+ userWidget.getColumn()+", row "+ userWidget.getRow());
		getDao().saveEntityModel(userWidget);
		
	}
	@Transactional(readOnly = true)
	public long countUserWidgetsColumn(Integer column, long userId) {
		return ((UserWidgetsDAO) getDao()).countUserWidgetsColumn(column, userId);
	}
	
	public void removeUserGroupWidgetsWithAccessCodes(UserGroup userGroup, List<UserRole> userAccessRoles) {
		List<String> rolesList = new ArrayList<String>(userAccessRoles.size());
		for (UserRole userRole : userAccessRoles) {
			rolesList.add(userRole.getRole());
		}
		
		//get all the widgets with the access roles from the user
		List<Widget> widgets = widgetService.findWidgetWithAccessCode(rolesList);
		for (Widget widget : widgets) {
			for(BaseUser baseUser : userGroup.getUsers()) {
				((UserWidgetsDAO) getDao()).deleteUserWidget(widget.getId(), baseUser.getId());
			}
		}
	}
	
//-- End custom codes. Do not delete this comment line.
}
