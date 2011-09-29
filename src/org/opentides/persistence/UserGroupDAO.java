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
package org.opentides.persistence;

import java.util.Set;

import org.opentides.bean.user.UserGroup;
import org.opentides.bean.user.UserRole;


/**
 * UserGroup DAO, methods for accessing data related to user group such as
 * saving, editing, deleting and querying specific to usergroup
 * 
 * @author rjimenez
 * 
 */
public interface UserGroupDAO extends BaseEntityDAO<UserGroup, Long> {
	/**
	 * Add roles to a userGroup
	 * 
	 * @param userGroup
	 * @param roles
	 *            set of roles to add
	 */
	public void assignRoles(UserGroup userGroup, Set<UserRole> roles);

	/**
	 * Add a role to a userGroup
	 * 
	 * @param userGroup
	 * @param role
	 *            role to add
	 */
	public void assignRoles(UserGroup userGroup, UserRole role);
	
	/**
	 * get User Group by Name
	 * 
	 * @param name
	 */
	public UserGroup loadUserGroupByName(String name);
	
	/**
	 * Removes the user role from the usergroup.
	 * @param role
	 * @return
	 */
	public boolean removeUserRole(UserRole role);
}
