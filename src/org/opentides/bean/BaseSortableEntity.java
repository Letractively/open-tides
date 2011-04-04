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

import javax.persistence.Transient;

import org.apache.log4j.Logger;

/**
 * This is the base class to support sorted results. Extend this entity to
 * implement a class that can be sorted.
 * 
 * @author allantan
 */
public class BaseSortableEntity extends BaseEntity implements Sortable {

    /**
     * Auto-generated class UID.
     */
    private static final long serialVersionUID = 5186833944332422259L;

    /**
     * Class logger.
     */
    private static Logger _log = Logger.getLogger(BaseSortableEntity.class);

    /**
     * Temporary variable for order direction (e.g. ASC or DESC).
     */
    @Transient
    private transient String orderFlow;

    /**
     * Temporary variable for order field
     */
    @Transient
    private transient String orderOption;

    /**
     * Getter method of order flow.
     * 
     * @return the orderFlow
     */
    public final String getOrderFlow() {
        return this.orderFlow;
    }

    /**
     * Setter method of order flow.
     * 
     * @param orderFlow
     *            the orderFlow to set
     */
    public final void setOrderFlow(final String orderFlow) {
        if ("ASC".equalsIgnoreCase(orderFlow)
                || "DESC".equalsIgnoreCase(orderFlow)) {
            this.orderFlow = orderFlow;
        } else {
            _log.warn("Attempt to set orderOption with value [" + orderFlow
                    + "] for class [" + this.getClass().getSimpleName() + "].");
        }
    }

    /**
     * Getter method for order option.
     * 
     * @return the orderOption
     */
    public final String getOrderOption() {
        return this.orderOption;
    }

    /**
     * Setter method for order option.
     * 
     * @param orderOption
     *            the orderOption to set
     */
    public final void setOrderOption(final String orderOption) {
        // TODO: Add validation to ensure orderOption refers to valid fields.
        this.orderOption = orderOption;
    }
}
