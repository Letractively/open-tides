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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.acegisecurity.context.SecurityContextHolder;
import org.hightides.annotations.util.AnnotationUtil;
import org.opentides.bean.user.SessionUser;
import org.opentides.persistence.listener.EntityDateListener;
import org.opentides.util.AcegiUtil;
import org.opentides.util.CrudUtil;

/**
 * 
 * This is the base class for all entity objects (model) of open-tides.
 * 
 * @author allantan
 */
@MappedSuperclass
@EntityListeners({ EntityDateListener.class })
public abstract class BaseEntity implements Serializable {
    /**
     * Auto-generated class UID.
     */
    private static final long serialVersionUID = -2166505182954839082L;
    
    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    
    /**
     * Create date.
     */
    @Column(name = "CREATEDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    
    /**
     * Last update date.
     */
    @Column(name = "UPDATEDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    
    /**
     * Id of user who created, updated or deleted the entity.
     * Used by AuditLog. 
     */
    @Transient
    private Long auditUserId;
    
    /**
     * Username who created, updated or deleted the entity.
     * Used by AuditLog. 
     */
    @Transient
    private String auditUsername;
    
    /**
     * Office of the user who created, updated or deleted the entity.
     * Used by AuditLog. 
     */
    @Transient
    private String auditOfficeName;
    
    /**
     * List of searchable properties. 
     * Used when default search properties is used.
     */
    @Transient
    private List<String> searchProperties;

    /**
     * List of auditable fields. 
     * Used when default auditable is used.
     */
    @Transient
    private List<AuditableField> auditableFields;

    /**
     * Setter method of Id.
     * 
     * @param id primary key
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * Getter method of id.
     * 
     * @return the id
     */
    public final Long getId() {
        return this.id;
    }

    /**
     * Checks if object is new (not persisted).
     * 
     * @return true if new, else false.
     */
    public final boolean isNew() {
        return this.getId() == null;
    }

    /**
     * Checks if object is new (not persisted).
     * @See isNew()
     * 
     * @return true if new, else false.
     */
    public final boolean getIsNew() {
        return this.isNew();
    }

    /**
     * Getter method for create date.
     * 
     * @return create date
     */
    public final Date getCreateDate() {
        return this.createDate;
    }

    /**
     * Setter method for create date.
     * 
     * @param createDate create date
     */
    public final void setCreateDate(final Date createDate) {
        if (this.createDate == null) {
            this.createDate = createDate;
        }
    }

    /**
     * Getter method for last update date.
     * 
     * @return last update date.
     */
    public final Date getUpdateDate() {
        return this.updateDate;
    }

    /**
     * Setter method for the last update date.
     * 
     * @param updateDate last update date
     */
    public final void setUpdateDate(final Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * Getter method of user id.
     * 
     * @return the auditUserId
     */
    public final Long getAuditUserId() {
        return this.auditUserId;
    }

    /**
     * Setter method of user id.
     * 
     * @param auditUserId the auditUserId to set
     */
    public final void setAuditUserId(final Long auditUserId) {
        this.auditUserId = auditUserId;
    }

    /**
     * Getter method of username.
     * 
     * @return the auditUsername
     */
    public final String getAuditUsername() {
        return this.auditUsername;
    }

    /**
     * Setter method of username.
     * 
     * @param auditUsername the auditUsername to set
     */
    public final void setAuditUsername(final String auditUsername) {
        this.auditUsername = auditUsername;
    }

    /**
     * Getter method of office name.
     * It is recommended that office is referenced in SystemCodes 
     * under category 'OFFICE'.
     * 
     * @return the auditOfficeName
     */
    public final String getAuditOfficeName() {
        return this.auditOfficeName;
    }

    /**
     * Setter method of office name.
     * It is recommended that office is referenced in SystemCodes 
     * under category 'OFFICE'.
     *
     * @param auditOfficeName the auditOfficeName to set
     */
    public final void setAuditOfficeName(final String auditOfficeName) {
        this.auditOfficeName = auditOfficeName;
    }

    /**
     * Sets the userId based on Acegi Context
     */
    public final void setUserId() {
        if (this.auditUserId == null
                && SecurityContextHolder.getContext() != null
                && SecurityContextHolder.getContext().getAuthentication() != null) {
            final SessionUser user = AcegiUtil.getSessionUser();
            this.auditUserId = user.getRealId();
            this.auditOfficeName = user.getOffice();
            this.auditUsername = user.getUsername();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final BaseEntity other = (BaseEntity) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * Default implementation, can be overridden if necessary. Returns the list
     * of field names that are included in search criteria. This method uses
     * reflection and annotation to generate the list of variables declared with
     * isSearchable attribute in high-tides field annotation.
     * 
     * For efficiency searchProperties is kept as a class variable so as not to
     * recurse all fields for every access.
     */
    public List<String> getSearchProperties() {
        if (this.searchProperties == null) {
            this.searchProperties = new ArrayList<String>();
            final List<Field> fields = CrudUtil.getAllFields(this.getClass());
            for (Field field : fields) {
                if (AnnotationUtil.isAnnotatedWith("searchable",field)) {
                    this.searchProperties.add(field.getName());
                }
            }
        }
        return this.searchProperties;
    }
    
    /**
     * Default implementation, can be overridden if necessary. Returns the list
     * of field names that are auditable. This method uses
     * reflection and annotation to generate the list of variables declared with
     * isAuditable attribute in high-tides field annotation.
     * 
     * For efficiency auditableFields is kept as a class variable so as not to
     * recurse all fields for every access.
     */
	public List<AuditableField> getAuditableFields() {
		if (this.auditableFields == null) {
            this.auditableFields = new ArrayList<AuditableField>();
            final List<Field> fields = CrudUtil.getAllFields(this.getClass());
            for (Field field : fields) {
            	if (AnnotationUtil.isAnnotatedWith("auditable",field)) {
                    this.auditableFields.add(new AuditableField(field.getName()));
                }
            }
        }
        return this.auditableFields;
	}

}
