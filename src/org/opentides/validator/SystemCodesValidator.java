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

import org.opentides.bean.SystemCodes;
import org.opentides.service.SystemCodesService;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


/**
 * @author allanctan
 * 
 */
public class SystemCodesValidator implements Validator {
	
	private SystemCodesService systemCodesService;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return SystemCodes.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object clazz, Errors e) {
		SystemCodes systemCodes = (SystemCodes) clazz;
		if (systemCodes.getCategory().equals("0"))
			e.reject("error.required", new Object[]{"Category"}, "category");

		if(isDuplicateKey(systemCodes)){
			e.reject("error.duplicate-key", new Object[]{"\""+systemCodes.getKey()+"\"","key"}, "\""+systemCodes.getKey() +"\" already exists. Please try a different key.");
		}
		
		ValidationUtils.rejectIfEmpty(e, "category", "error.required",
				new Object[] { "Category" });
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "key", "error.required",
				new Object[] { "Key" });
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "value", "error.required",
				new Object[] { "Value" });
	}
	
	/**
	 * 
	 * @param fieldName name of field
	 * @param templateId template id 
	 * @return boolean returns true if duplicate name was found, false otherwise
	 */
	public boolean isDuplicateKey(SystemCodes systemCodes){
		SystemCodes key;
		try {
			key = this.systemCodesService.findByKey(systemCodes);
			if (key != null){
				if(!key.getId().equals(systemCodes.getId()))
					return true;
			}
			
		} catch (EntityNotFoundException e) {
			return false;
		}
		return false;
	}

	public void setSystemCodesService(SystemCodesService systemCodesService) {
		this.systemCodesService = systemCodesService;
	}
}
