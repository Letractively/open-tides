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
package org.opentides.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hightides.annotations.Controller;
import org.hightides.annotations.Dao;
import org.hightides.annotations.DropDown;
import org.hightides.annotations.Page;
import org.hightides.annotations.Service;
import org.hightides.annotations.TextField;
import org.opentides.util.CrudUtil;
import org.opentides.util.StringUtil;

/**
 * This is the definition table containing the user defined fields.
 * 
 * @author allantan
 */
@Entity
@Table(name="USER_DEFINED_META")
@Page
@Controller
@Service
@Dao
@SuppressWarnings("rawtypes")
public class UserDefinedField extends BaseEntity 
	implements Serializable, Searchable, Auditable, Sortable {

	private static final long serialVersionUID = -3948691559674056769L;

	private static final int    DEFAULT_NUMBER_FRACTION = 2;
	private static final String DEFAULT_DATE_PATTERN    = "MM/dd/yyyy";
	
	@Column(name = "CLASS")
	@DropDown(category="CLAXX", requiredField=true, auditable=true, 
			searchable=true, listed=true,label="Class")
	private Class clazz;
	
	@Column(name = "USERFIELD")
	@DropDown(category="USERFIELD", requiredField=true, auditable=true, 
			searchable=true, listed=true, label="Field")
	private String userField;
	
	@Column(name = "LABEL")
	@TextField(titleField=true, requiredField=true, searchable=true, 
			listed=true, auditable=true, label="Label")
	private String label;
		
	@Column(name = "CONDITION_") 
	private String condition;
	
	@Column(name = "REFERENCE")
	private String reference;
	
	@Column(name = "ORDER_")
	private Integer order;
	
	@Column(name = "SEARCHABLE")
	private Boolean searchable = Boolean.FALSE;
	
	@Column(name = "LISTED")
	private Boolean listed = Boolean.FALSE;
	
	@Column(name = "REQUIRED")
	private Boolean required = Boolean.FALSE;
	
	/* (non-Javadoc)
	 * @see org.opentides.bean.Auditable#getPrimaryField()
	 */
	@Override
	public AuditableField getPrimaryField() {
		return new AuditableField("label", "label");
	}

	/**
	 * Getter method for clazz.
	 *
	 * @return the clazz
	 */
	public final Class getClazz() {
		return clazz;
	}

	/**
	 * Setter method for clazz.
	 *
	 * @param clazz the clazz to set
	 */
	public final void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	/**
	 * Getter method for userField.
	 *
	 * @return the userField
	 */
	public final String getUserField() {
		return userField;
	}

	/**
	 * Setter method for userField.
	 *
	 * @param userField the userField to set
	 */
	public final void setUserField(String userField) {
		this.userField = userField;
	}

	/**
	 * Getter method for label.
	 *
	 * @return the label
	 */
	public final String getLabel() {
		if(!StringUtil.isEmpty(label))
			return label.trim();
		else
			return label;
	}

	/**
	 * Setter method for label.
	 *
	 * @param label the label to set
	 */
	public final void setLabel(String label) {
		if(!StringUtil.isEmpty(label))
			this.label = label.trim();
	}

	/**
	 * Getter method for order.
	 *
	 * @return the order
	 */
	public final Integer getOrder() {
		return order;
	}

	/**
	 * Setter method for order.
	 *
	 * @param order the order to set
	 */
	public final void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * Getter method for reference.
	 *
	 * @return the reference
	 */
	public final String getReference() {
		return reference;
	}

	/**
	 * Setter method for reference.
	 *
	 * @param reference the reference to set
	 */
	public final void setReference(String reference) {
		this.reference = reference;
	}
	
	/**
	 * Getter method for condition.
	 *
	 * @return the condition
	 */
	public final String getCondition() {
		return condition;
	}

	/**
	 * Setter method for condition.
	 *
	 * @param condition the condition to set
	 */
	public final void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * Getter method for searchable.
	 *
	 * @return the searchable
	 */
	public final Boolean getSearchable() {
		return searchable;
	}

	/**
	 * Setter method for searchable.
	 *
	 * @param searchable the searchable to set
	 */
	public final void setSearchable(Boolean searchable) {
		this.searchable = searchable;
	}

	/**
	 * Getter method for listed.
	 *
	 * @return the listed
	 */
	public final Boolean getListed() {
		return listed;
	}

	/**
	 * Setter method for listed.
	 *
	 * @param listed the listed to set
	 */
	public final void setListed(Boolean listed) {
		this.listed = listed;
	}

	/**
	 * Getter method for required.
	 *
	 * @return the required
	 */
	public final Boolean getRequired() {
		return required;
	}

	/**
	 * Setter method for required.
	 *
	 * @param required the required to set
	 */
	public final void setRequired(Boolean required) {
		this.required = required;
	}

	/**
	 * Returns the preferred display pattern
	 * for this user defined field.
	 */
	public final String getDatePattern() {
		if (StringUtil.isEmpty(this.reference))
			return DEFAULT_DATE_PATTERN;
		else
			return reference;
	}
	
	/**
	 * Returns the preferred display pattern
	 * for this user defined field.
	 */
	public final long getFraction() {
		long fraction = StringUtil.convertToLong(this.reference, -1);
		
		if (fraction < 0)
			return DEFAULT_NUMBER_FRACTION;
		else
			return fraction;
	}
	
	/**
	 * Passthrough method for retrieving object value dynamically as needed in
	 * the jsp page (taglib).
	 * 
	 * @param object
	 * @param property
	 * @return
	 */
	public final String retrieveValue(UserDefinable object, String property) {
		return CrudUtil.retrieveObjectValue(object, property).toString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		result = prime * result
				+ ((userField == null) ? 0 : userField.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		UserDefinedField other = (UserDefinedField) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		if (userField == null) {
			if (other.userField != null)
				return false;
		} else if (!userField.equals(other.userField))
			return false;
		return true;
	}

}
