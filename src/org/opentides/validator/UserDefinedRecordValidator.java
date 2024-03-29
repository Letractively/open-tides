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

import org.opentides.bean.UserDefinable;
import org.opentides.bean.UserDefinedField;
import org.opentides.bean.UserDefinedRecord;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Brent
 * 
 */
public class UserDefinedRecordValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return UserDefinable.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object clazz, Errors e) {
		UserDefinable userDefinable = (UserDefinable) clazz;
		UserDefinedRecord udr = userDefinable.getUdf();
		if (udr != null) {
			List<UserDefinedField> udfList = UserDefinedRecord.getUdf(udr
					.getEntityClass());
			if(udfList != null)
				for (UserDefinedField udf : udfList) {
					if (udf.getRequired() != null && udf.getRequired()) {
						ValidationUtils.rejectIfEmpty(e, "udf." + udf.getUserField(),
								"error.required", new Object[] { udf.getLabel() });
					}
				}
		}
	}

}
