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

import java.util.List;

import org.opentides.bean.UserDefinedField;
import org.opentides.service.UserDefinedFieldService;
import org.opentides.util.StringUtil;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


/**
 * @author allanctan
 * 
 */
public class UserDefinedFieldValidator implements Validator {
	
	private UserDefinedFieldService userDefinedFieldService;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	public boolean supports(Class clazz) {
		return UserDefinedField.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object clazz, Errors e) {
		UserDefinedField udf = (UserDefinedField) clazz;
		
		ValidationUtils.rejectIfEmpty(e, "clazz", "error.required",
				new Object[] { "Class" });
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "userField", "error.required",
				new Object[] { "User Field" });
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "label", "error.required",
				new Object[] { "Label" });
		
		// there must be no duplicate field use per class
		if (udf.getClass()!=null && 
				!StringUtil.isEmpty(udf.getUserField()) ) {
			UserDefinedField example = new UserDefinedField();
			example.setUserField(udf.getUserField());
			example.setClazz(udf.getClazz());
			List<UserDefinedField> duplicate = userDefinedFieldService.findByExample(example, true);
			if (duplicate!=null && !duplicate.isEmpty()
					&& duplicate.get(0).getId() != udf.getId()) {
				e.reject("error.duplicate-field", 
						new Object[]{udf.getClazz().getSimpleName() + " " + udf.getUserField(), "field"}, 
						"Field is already in use. Please use a different field.");
			}
		}
	}

	/**
	 * Setter method for userDefinedFieldService.
	 *
	 * @param userDefinedFieldService the userDefinedFieldService to set
	 */
	public final void setUserDefinedFieldService(
			UserDefinedFieldService userDefinedFieldService) {
		this.userDefinedFieldService = userDefinedFieldService;
	}

}
