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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * The system codes class is a lookup table used for drop-downs.
 * Developer must ensure category is consistently used in the application.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name="SYSTEM_CODES")
public class SystemCodes extends BaseProtectedEntity 
	implements Serializable, Searchable, Auditable, Sortable {
	
	private static final long serialVersionUID = -4142599915292096152L;
	private static final int CATEGORY_LIST_LENGTH = 52;
	
	@Column(name = "KEY_", unique=true)
	private String key;
	@Column(name = "VALUE_", nullable=true)
	private String value;
	@Column(name = "NUMBER_VALUE", nullable=true)
	private Long numberValue; 
	@Column (name="CATEGORY_")
	private String category;
		
	public SystemCodes() {
	}
	
	public SystemCodes(String key) {
		super();
		this.key = key;
	}

	
	public SystemCodes(String category, String key, String value) {
		super();
		setCategory(category);
		this.key = key;
		this.value = value;
	}
	
	public SystemCodes(String category, String key, String value, boolean skipAudit) {
		super();
		setCategory(category);
		this.key = key;
		this.value = value;
		this.setSkipAudit(skipAudit);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return key +":"+value;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the numberValue
	 */
	public final synchronized Long getNumberValue() {
		return this.numberValue;
	}

	/**
	 * @param numberValue the numberValue to set
	 */
	public final synchronized void setNumberValue(Long numberValue) {
	    this.numberValue = numberValue;
	}
	
	public final synchronized void incrementNumberValue() {
		if (numberValue == null)
			numberValue = Long.valueOf(1l);
		else
			numberValue++;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SystemCodes other = (SystemCodes) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
	
   public String getTruncatedValue() {
       if (value.length() > CATEGORY_LIST_LENGTH) {
           return value.substring(0, CATEGORY_LIST_LENGTH - 4) + "...";
       }
       return value;
   }

	public List<String> getSearchProperties() {
		List<String> props = new ArrayList<String>();
		props.add("key");
		props.add("value");
		props.add("numberValue");
		props.add("category");
		props.add("owner");
		props.add("ownerOffice");
		return props;
	}

	public List<AuditableField> getAuditableFields() {
		List<AuditableField> props = new ArrayList<AuditableField>();
		props.add(new AuditableField("key","Key"));
		props.add(new AuditableField("value","Value"));
		props.add(new AuditableField("numberValue","Number Value"));
		props.add(new AuditableField("category","Category"));
		props.add(new AuditableField("owner","Access Owner"));
		props.add(new AuditableField("ownerOffice","Access Office"));
		return props;
	}

	public AuditableField getPrimaryField() {
		return new AuditableField("value","Value");
	}
	
	public String getReference() {
		// TODO Auto-generated method stub
		return null;
	}

}