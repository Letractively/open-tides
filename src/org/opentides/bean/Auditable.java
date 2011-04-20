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

import java.util.List;

/**
 * This is the marker to indicate if a class will have audit trail.
 * 
 * @author allantan
 * 
 */
public interface Auditable {

    /**
     * Retrieve the primary key
     * 
     * @return primary key
     */
    Long getId();

    /**
     * Returns the list of fields that should be audited.
     * 
     * @return list of auditable fields
     */
    List<AuditableField> getAuditableFields();

    /**
     * Returns the field of the primary identifier (e.g. title). This field is
     * used to uniquely identify the record.
     * 
     * @return primary key field
     */
    AuditableField getPrimaryField();

    /**
     * Returns whether to skip audit for this object. This field can be used to
     * programmatically disable logging of auditable objects.
     * 
     * @return boolean
     */
    Boolean skipAudit();

    /**
     * Returns customized audit log message. When empty, audit logging uses
     * standard audit message.
     * 
     * @return audit message
     */
    String getAuditMessage();

    /**
     * Returns a reference for this audit object. Useful for querying detail
     * objects from a reference.
     * 
     * @return String - reference code
     */
    String getReference();

    /**
     * Returns the user ID who made the change. This is needed because SecurityUtil
     * is not able to return correct user session on Hibernate Interceptor.
     * 
     * @return Long - userId
     */
    Long getAuditUserId();

    /**
     * Sets the value of userId based on Acegi Context implemented by
     * BaseEntity.
     */
    void setUserId();

    /**
     * Getter of office based on Acegi Context.
     * 
     * @return String - office name
     */
    String getAuditOfficeName();

    /**
     * Getter of username based on Acegi Context.
     * 
     * @return String - username
     */
    String getAuditUsername();
    
    /**
     * Returns the basic readable name of the auditable object
     * 
     * @return friendlyName
     */
    String getFriendlyName();

    /**
     * Returns customized friendly log message. When empty, audit logging uses
     * standard audit message.
     * 
     * @return friendlyMessage
     */
    String getFriendlyMessage();
}
