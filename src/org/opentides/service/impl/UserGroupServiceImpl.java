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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.opentides.bean.user.UserGroup;
import org.opentides.bean.user.UserRole;
import org.opentides.persistence.UserGroupDAO;
import org.opentides.service.UserGroupService;
import org.opentides.util.StringUtil;
import org.springframework.transaction.annotation.Transactional;


public class UserGroupServiceImpl extends BaseCrudServiceImpl<UserGroup>
		implements UserGroupService {

	private static Map<String, String> roles;
	
	private UserGroupDAO userGroupDAO;

	@SuppressWarnings("unused")
	private static final Logger log = Logger
			.getLogger(UserGroupServiceImpl.class);
	
	/** inner class to do soring of the map **/
	private static class ValueComparer implements Comparator<String> {
		private Map<String, String>  _data = null;
		public ValueComparer (Map<String, String> data){
			super();
			_data = data;
		}

         public int compare(String o1, String o2) {
        	 String e1 = _data.get(o1);
             String e2 = _data.get(o2);
             return e1.compareTo(e2);
         }
	}
	
	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(Map<String, String> roles) {
		// added process of sorting the rolesMap by the values in ascending order
		SortedMap<String, String> sortedData = 
			new TreeMap<String, String>(new UserGroupServiceImpl.ValueComparer(roles));
		
		sortedData.putAll(roles);

		UserGroupServiceImpl.roles = sortedData;
	}
	
	/**
	 * @return the roles
	 */
	public Map<String, String> getRoles() {
		return UserGroupServiceImpl.roles;
	}

	@Transactional(readOnly = true)
	public UserGroup loadUserGroupByName(String name){
		return userGroupDAO.loadUserGroupByName(name);
	}
	
	/**
	 * Removes the UserRole from the database.
	 */
	public boolean removeUserRole(UserRole role) {
		return userGroupDAO.removeUserRole(role);
	}
	
	/* (non-Javadoc)
	 * @see org.opentides.service.UserGroupService#setAuthoritiesFromCsvFile(java.lang.String)
	 */
	@Override
	public void setAuthoritiesFromCsvFile(String csvFile) throws Exception {
		int line = 1;
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(csvFile)));
			// read the column header
			String csvLine = reader.readLine();
			if (csvLine==null) {
				log.error("Import file ["+csvFile+"] has no contents.");
				throw new Exception("Import file ["+csvFile+"] has no contents.");
			}
			List<String> headers = StringUtil.parseCsvLine(csvLine);
			int roleColumn = -1, groupNameColumn = -1;
			for(int i = 0; i < headers.size(); i++) {
				String header = headers.get(i);
				if(header.equals(CsvColumns.GROUP_NAME.name())) {
					groupNameColumn = i;
				} else if(header.equals(CsvColumns.ROLE.name())) {
					roleColumn = i;
				}
			}
			if(roleColumn == -1 || groupNameColumn == -1) {
				throw new Exception("No columns named [ROLE] and/or [GROUP_NAME]");
			}
			String currentGroupName = "";
			UserGroup userGroup = null;
			while ((csvLine = reader.readLine()) != null) {
				List<String> values = StringUtil.parseCsvLine(csvLine);
				List<String> tmpHeaders = new ArrayList<String>(headers);
				// get all columns with null values
				List<Integer> nullColumns = new ArrayList<Integer>();
				for (int i=values.size()-1; i>=0; i--) {
					String value = values.get(i);					
					if (StringUtil.isEmpty(value)) {
						nullColumns.add(i);
					}
				}
				// remove headers and values with null values
				for (int index:nullColumns) {
					tmpHeaders.remove(index);
					values.remove(index);
				}
				if(!currentGroupName.equals(values.get(groupNameColumn))) {
					currentGroupName = values.get(groupNameColumn);
					userGroup = this.loadUserGroupByName(currentGroupName);
				}
				UserRole userRole = new UserRole(userGroup, values.get(roleColumn));
				this.userGroupDAO.assignRoles(userGroup, userRole);
				// execute this query
				line++;
			}
			reader.close();
		} catch (Exception e) {
			log.error("Failed to import csv file [" + csvFile + "] at line #"+line, e);
			throw e;
		}
	}

	/**
	 * @param userGroupDAO the userGroupDAO to set
	 */
	public void setUserGroupDAO(UserGroupDAO userGroupDAO) {
		this.userGroupDAO = userGroupDAO;
	}
	
	
}
