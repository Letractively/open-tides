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
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.opentides.bean.user.BaseUser;
import org.opentides.persistence.listener.AuditLogListener;
import org.opentides.util.CrudUtil;
import org.opentides.util.StringUtil;

/**
 * This class is responsible for handling all audit functions needed to be
 * attached to the classes.
 * 
 * @author allantan
 */
@Entity
@EntityListeners({ AuditLogListener.class })
@Table(name = "HISTORY_LOG")
public class AuditLog extends BaseProtectedEntity implements Serializable,
		BaseCriteria, Cloneable {

    /**
     * Auto-generated class UID.
     */
    private static final long serialVersionUID = 269168041517643087L;

    /**
     * Primary key of object being tracked.
     */
    @Column(name = "ENTITY_ID", nullable = false, updatable = false)
    private Long entityId;
    
    /**
     * Class type of object being tracked.
     */
    @SuppressWarnings({ "rawtypes" })
    @Column(name = "ENTITY_CLASS", nullable = false, updatable = false)
    private Class entityClass;
    
    /**
     * Arbitrary reference to object being tracked.
     * Use this attribute to store single reference string to different 
     * classes that are interrelated.
     */
    @Column(name = "REFERENCE")
    private String reference;
    
    /**
     * Message about the actions done.
     */
    @Lob
    @Column(name = "MESSAGE", nullable = false, updatable = false)
    private String message;
    
    @Lob
    @Column(name = "FRIENDLY_MESSAGE", nullable = false, updatable = false)
    private String friendlyMessage;
    
    /**
     * User who performed the change.
     */
    @Column(name = "USER_ID", nullable = false, updatable = false)
    private Long userId;

    /**
     * Temporary reference to object being tracked.
     * Used by AuditLogListener when loading audit log object.
     */
    @Transient
    private transient Object object;
    
    /**
     * Temporary reference to used who made the change.
     * Used by AuditLogListener when loading audit log object.
     * 
     */
    @Transient
    private transient BaseUser user;
	
    @Transient
	private transient Date startDate;
	
    @Transient
	private transient Date endDate;
	
	@Transient
	private transient String entityName = "";

	@Transient
	private transient String logAction;
    
	/**
     * Default constructor.
     */
    public AuditLog(){
    }
    
    /**
     * Standard constructor.
     * 
     * @param message message to log. If blank, message is automatically generated.
     * @param entityId id of object being tracked.
     * @param entityClass class name of object being tracked.
     * @param reference reference for group query.
     * @param userId user id of who made the change.
     * @param owner username of who made the change.
     * @param ownerOffice group of user who made the change.
     */
    @SuppressWarnings({ "rawtypes" })
    public AuditLog(final String message, 
            final Long entityId, 
            final Class entityClass,
            final String reference,
            final Long userId,
            final String owner,
            final String ownerOffice) {
        this.message = message;
        this.entityId = entityId;
        this.entityClass = entityClass;
        this.reference = reference;
        this.userId = userId;
        this.setCreateDate(new Date());
        this.setOwner(owner);
        this.setOwnerOffice(ownerOffice);
    }
    
    /**
     * 
     * @param friendlyMessage
     * @param message
     * @param entityId
     * @param entityClass
     * @param reference
     * @param userId
     * @param owner
     * @param ownerOffice
     */
    @SuppressWarnings("rawtypes")
	public AuditLog(final String friendlyMessage,
    		final String message, 
            final Long entityId, 
            final Class entityClass,
            final String reference,
            final Long userId,
            final String owner,
            final String ownerOffice){
    	this.friendlyMessage = friendlyMessage;
    	this.message = message;
        this.entityId = entityId;
        this.entityClass = entityClass;
        this.reference = reference;
        this.userId = userId;
        this.setCreateDate(new Date());
        this.setOwner(owner);
        this.setOwnerOffice(ownerOffice);
    }

    /**
     * Getter of entity id.
     * 
     * @return entityId
     */
    public final Long getEntityId() {
        return this.entityId;
    }

    /**
     * Setter of entity id.
     * 
     * @param entityId
     *            the entityId to set
     */
    protected final void setEntityId(final Long entityId) {
        this.entityId = entityId;
    }

    /**
     * Getter of entity class.
     * 
     * @return the entityClass
     */
    @SuppressWarnings("rawtypes")
    public final Class getEntityClass() {
        return this.entityClass;
    }

    /**
     * Setter of entity class.
     * 
     * @param entityClass
     *            the entityClass to set
     */
    @SuppressWarnings("rawtypes")
    protected final void setEntityClass(final Class entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Returns a human readable name of the logged entity.
     * 
     * @return entity name
     */
    public final String getEntityName() {
        if (!StringUtil.isEmpty(entityName)){
            return entityName;
        }
		return CrudUtil.getReadableName(entityClass.getName());
    }

    /**
     * Getter of message.
     * 
     * @return the message
     */
    public final String getMessage() {
        return this.message;
    }

    /**
     * Getter of user id.
     * 
     * @return the userId
     */
    public final Long getUserId() {
        return this.userId;
    }

    /**
     * Setter of user id.
     * 
     * @param userId
     *            the userId to set
     */
    protected final void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * Getter of object.
     * 
     * @return the object
     */
    public final Object getObject() {
        return this.object;
    }

    /**
     * Setter of object.
     * 
     * @param object
     *            the object to set
     */
    public final void setObject(final Object object) {
        this.object = object;
    }

    /**
     * Getter of user object.
     * 
     * @return the user
     */
    public final BaseUser getUser() {
        return this.user;
    }

    /**
     * Setter of user object.
     * 
     * @param user
     *            the user to set
     */
    public final void setUser(final BaseUser user) {
        this.user = user;
    }


	public List<String> getSearchProperties() {
		List<String> fields = new ArrayList<String>();
		fields.add("userId");
		fields.add("entityClass");
		fields.add("entityId");
		fields.add("reference");
		fields.add("ownerOffice");
		fields.add("updateDate");
		return fields;
	}

    /**
     * Getter of reference id.
     * 
     * @return the referenceId
     */
    public final String getReference() {
        return this.reference;
    }

    /**
     * Setter of reference id.
     * 
     * @param reference
     *            the referenceId to set
     */
    protected final void setReference(final String reference) {
        this.reference = reference;
    }

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public AuditLog getCopy(){
		if (this == null){
			return null;
		}
		
		try {
			return (AuditLog) clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getFriendlyMessage() {
		return friendlyMessage;
	}

	public void setFriendlyMessage(String friendlyMessage) {
		this.friendlyMessage = friendlyMessage;
	}

	public String getLogAction() {
		return logAction;
	}

	public void setLogAction(String logAction) {
		this.logAction = logAction;
	}
}
