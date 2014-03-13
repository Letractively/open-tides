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
package org.opentides.service;

import java.util.Map;

import org.opentides.bean.user.UserGroup;
import org.opentides.bean.user.UserRole;


/**
 * Service responsible for handling UserGroup and UserRole.
 * 
 * @author allantan
 */
public interface UserGroupService extends BaseCrudService<UserGroup> {
	
	public static enum CsvColumns {
		ROLE, GROUP_NAME
	}
	
	public Map<String, String> getRoles();

	public void setRoles(Map<String, String> roles);
	
	public UserGroup loadUserGroupByName(String name); 
	
	public boolean removeUserRole(UserRole role);
	
	/**
	 * Set the authorities from a CSV file. The CSV file requires columns for ROLE and GROUP_NAME.
	 * @param csvFile
	 * @throws Exception
	 */
	public void setAuthoritiesFromCsvFile(String csvFile) throws Exception;
}
