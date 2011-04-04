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
package org.opentides.bean.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.opentides.bean.BaseCriteria;
import org.opentides.bean.BaseEntity;
import org.opentides.util.StringUtil;


@Entity
@Table(name = "USERGROUP")
public class UserGroup extends BaseEntity implements BaseCriteria {
	private static final long serialVersionUID = 1959110420702540834L;

	@Column(name = "NAME", unique = true, nullable = false)
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@ManyToMany(mappedBy = "groups")
	private Set<BaseUser> users;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userGroup", fetch = FetchType.EAGER)
	private Set<UserRole> roles;

	@Transient
	private transient List<String> roleNames; // used for checkboxes in UI
	
	@Transient
	private transient Map<String, String> userRoleMap; // user for permission matrix in UI

	public UserGroup() {
		roles = new HashSet<UserRole>();
		roleNames = new ArrayList<String>();
	}

	/**
	 * @return the roleNames
	 */
	public List<String> getRoleNames() {
		if (roleNames == null || roleNames.isEmpty())
			syncRoleNames();
		return roleNames;
	}

	/**
	 * @param roleNames
	 *            the roleNames to set
	 */
	public void setRoleNames(List<String> roleNames) {
		List<UserRole> removeList = new ArrayList<UserRole>();
		this.roleNames = new ArrayList<String>();
		if (roleNames == null) {
			for (UserRole role : roles) {
				removeList.add(role);
			}
			for (UserRole role : removeList) {
				this.removeRole(role);
			}
			return;
		}
		this.roleNames.addAll(roleNames);
		for (UserRole role : roles) {
			if (roleNames.contains(role.getRole())) {
				// we keep the role, and remove the name
				roleNames.remove(role.getRole());
			} else {
				// this role has been removed
				removeList.add(role);
			}
		}
		for (UserRole role : removeList) {
			this.removeRole(role);
		}
		// now we need to add what's left in rNames
		for (String name : roleNames) {
			roles.add(new UserRole(this, name));
		}
	}

	/**
	 * Add role to group roles
	 * 
	 * @param role
	 * @return true if add successful otherwise false
	 */
	public boolean addRole(UserRole role) {
		if (role == null)
			throw new IllegalArgumentException("Null role.");
		if (roles != null) {
			roles.remove(role);
		}
		role.setUserGroup(this);
		return roles.add(role);
	}

	/**
	 * Remove role from group roles
	 * 
	 * @param role
	 * @return true if remove successful otherwise false
	 */
	public boolean removeRole(UserRole role) {
		if (role != null)
			return roles.remove(role);
		else
			return false;
	}
	

	/**
	 * Remove role from group roles by name
	 * 
	 * @param role
	 * @return true if remove successful otherwise false
	 */
	public boolean removeRole(String roleName) {
		if (StringUtil.isEmpty(roleName))
			return false;
		for(UserRole role: getRoles()) {
			if (roleName.equals(role.getRole())) {
				removeRole(role);
				return true;
			}
		}
		return false;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getVerticalTitle() {
		StringBuffer vert = new StringBuffer(); 
		for (int i=0; i<name.length(); i++) {
			vert.append(name.charAt(i)).append("<br/>");
		}
		return vert.toString();
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void mapRoleMatrix(Map<String, String> allRoles) {
		userRoleMap = new HashMap<String, String>();
		List<String> roleKeys = new ArrayList<String>();
		for (UserRole role:this.roles)
			roleKeys.add(role.getRole());
		for (String key:allRoles.keySet()) {
			if (roleKeys.contains(key)) {
				userRoleMap.put(key, allRoles.get(key));
			}
		}
	}
	
	public Map<String, String> getUserRoleMap() {
		return userRoleMap;
	}
	
	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
		syncRoleNames();
	}

	public void syncRoleNames() {
		roleNames = new ArrayList<String>();
		for (UserRole role : roles) {
			roleNames.add(role.getRole());
		}
	}

	public List<String> getSearchProperties() {
		List<String> props = new ArrayList<String>();
		props.add("name");
		props.add("description");
		return props;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final UserGroup other = (UserGroup) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * @return the users
	 */
	public Set<BaseUser> getUsers() {
		return users;
	}

	/**
	 * @param users
	 *            the users to set
	 */
	public void setUsers(Set<BaseUser> users) {
		this.users = users;
	}
}