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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.opentides.bean.Auditable;
import org.opentides.bean.AuditableField;
import org.opentides.bean.Searchable;
import org.opentides.bean.BaseProtectedEntity;
import org.opentides.bean.SystemCodes;

@Entity
@Table(name = "USER_PROFILE")
public class BaseUser extends BaseProtectedEntity implements Searchable, Auditable {

	private static final long serialVersionUID = 7634675501487373408L;
	
	/**
	 * Category name referenced by SystemCodes
	 */
	public static final String CATEGORY_OFFICE="OFFICE";

	@Column(name = "FIRSTNAME")
	private String firstName;

	@Column(name = "LASTNAME")
	private String lastName;
	
	@Column(name = "MIDDLENAME", nullable=true)
	private String middleName;

	@Column(name = "EMAIL", unique=true)
	private String emailAddress;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "OFFICE", nullable=true, referencedColumnName="KEY_")
	private SystemCodes office;

	/*
	 * Lob Annotation Specifies that a persistent property or field should be
	 * persisted as a large object to a database-supported large object type
	 */
	@Lob
	@Column(name = "IMAGE", columnDefinition = "LONGBLOB")
	private byte[] image;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
	private UserCredential credential;

	@ManyToMany
	@JoinTable(name = "USER_GROUP", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "GROUP_ID") })
	private Set<UserGroup> groups;
	
	@Column(name = "LASTLOGIN")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;
	
	@Transient
	private transient boolean skipAudit;
	
	@Transient
	private transient String auditMessage;
	
	@Transient
	private transient String friendlyMessage;
	
	@Transient
	private transient String friendlyName = "Base User";
	
	public BaseUser() {
		super();
		this.setCredential(new UserCredential());
		groups = new HashSet<UserGroup>();
	}
	
	/**
	 * Creates a clone of this object containing basic information including the following:
	 * firstName, lastName, middleName, emailAddress, lastLogin, and image.
	 * This function is used to populate the user object associated to AuditLog.
	 * 
	 * Note: groups and credentials are not cloned.
	 * @param clone
	 * @return
	 */
	public BaseUser cloneUserProfile() {
		BaseUser clone = new BaseUser();
		clone.firstName    = this.firstName;
		clone.lastName     = this.lastName;
		clone.middleName   = this.middleName;
		clone.emailAddress = this.emailAddress;
		clone.image        = this.image;
		clone.lastLogin    = this.lastLogin;
		return clone;
	}

	public void addGroup(UserGroup group) {
		if (group == null)
			throw new IllegalArgumentException("Null group.");
		if (groups != null)
			groups.remove(group);
		groups.add(group);
	}

	public void removeGroup(UserGroup group) {
		if (group == null)
			throw new IllegalArgumentException("Null group.");
		if (groups != null)
			groups.remove(group);
	}

	public List<String> getSearchProperties() {
		List<String> props = new ArrayList<String>();
		props.add("firstName");
		props.add("lastName");
		props.add("emailAddress");
		props.add("office");
		return props;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getCompleteName() {
		return this.firstName + " " + this.lastName;
	}

	public UserCredential getCredential() {
		return credential;
	}

	public void setCredential(UserCredential userAccount) {
		this.credential = userAccount;
		userAccount.setUser(this);
	}
	
	public boolean hasPermission(String permission) {
		for (UserGroup group : groups) {
			for (UserRole userRole : group.getRoles()) {
				if (permission.equals(userRole.getRole())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @return the groups
	 */
	public Set<UserGroup> getGroups() {
		return groups;
	}

	/**
	 * @param groups
	 *            the groups to set
	 */
	public void setGroups(Set<UserGroup> groups) {
		this.groups = groups;
	}

	/**
	 * @return the image
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}
	

	/**
	 * @return the lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin
	 *            the lastLogin to set
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * @return the office
	 */
	public SystemCodes getOffice() {
		return office;
	}

	/**
	 * @param office the office to set
	 */
	public void setOffice(SystemCodes office) {
		this.office = office;
	}

	@Override
	public String toString() {
		return this.lastName + ", " + this.firstName;
	}

	/* (non-Javadoc)
	 * @see org.opentides.bean.Auditable#getPrimaryField()
	 */
	@Override
	public AuditableField getPrimaryField() {
		return new AuditableField("username","Username");
	}

	public final void setSkipAudit(Boolean skipAudit) {
		this.skipAudit = skipAudit;
	}
	
	public String getReference() {
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((emailAddress == null) ? 0 : emailAddress.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BaseUser other = (BaseUser) obj;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		return true;
	}
}
