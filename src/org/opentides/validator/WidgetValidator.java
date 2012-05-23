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

package org.opentides.validator;

import javax.persistence.EntityNotFoundException;

import org.opentides.bean.Widget;
import org.opentides.service.WidgetService;
import org.opentides.util.StringUtil;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


/**
 * @author allanctan
 * 
 */
public class WidgetValidator implements Validator {
	
	private WidgetService widgetService;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	public boolean supports(Class clazz) {
		return Widget.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object clazz, Errors e) {
		Widget widget = (Widget) clazz;

		if(!StringUtil.isEmpty(widget.getName()) && isDuplicateName(widget))
			e.reject("error.duplicate-name", new Object[]{"\""+widget.getName()+"\"","name"}, "\""+widget.getName() +"\" already exists. Please try a different name.");
	
		if(!StringUtil.isEmpty(widget.getTitle()) && isDuplicateUrl(widget))
			e.reject("error.duplicate-url", new Object[]{"\""+widget.getUrl()+"\"","url"}, "\""+widget.getUrl() +"\" already exists. Please update the existing url.");
		
		
		ValidationUtils.rejectIfEmpty(e, "name", "error.required", new Object[] { "Name" });
		ValidationUtils.rejectIfEmpty(e, "title", "error.required", new Object[] { "Title" });
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "url", "error.required", new Object[] { "Url" });		
	}
	
	/**
	 * 
	 * @param fieldName name of field
	 * @return boolean returns true if duplicate name was found, false otherwise
	 */
	public boolean isDuplicateName(Widget widget){
		Widget key;
		try {
			key = this.widgetService.findByName(widget.getName());
			if (key != null){
				if(!key.getId().equals(widget.getId()))
					return true;
			}
		} catch (EntityNotFoundException e) {
			return false;
		}
		return false;
	}

	/**
	 * 
	 * @param fieldName name of field
	 * @return boolean returns true if duplicate name was found, false otherwise
	 */
	public boolean isDuplicateUrl(Widget widget){
		Widget key;
		try {
			key = this.widgetService.findByUrl(widget.getUrl());
			if (key != null){
				if (!key.getId().equals(widget.getId()))
					return true;
			}
		} catch (EntityNotFoundException e) {
			return false;
		}
		return false;
	}

	public void setWidgetService(WidgetService widgetService) {
		this.widgetService = widgetService;
	}
}
