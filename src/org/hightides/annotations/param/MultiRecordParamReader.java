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
package org.hightides.annotations.param;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.hightides.annotations.util.NamingUtil;

/**
 * Populates the parameters needed for multirecord fields.
 * @author allantan
 */

public class MultiRecordParamReader extends BaseParamReader implements
		FieldParamReader {
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> getParameters(Field field) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (field.getGenericType() instanceof ParameterizedType) {
		    ParameterizedType type = (ParameterizedType) field.getGenericType();
		    Type[] typeArguments = type.getActualTypeArguments();
		    for (Type typeArgument : typeArguments) {
		    	params.putAll(populatePageParams((Class) typeArgument, field.getName()));
			    Class clazz = (Class) typeArgument;
		        params.put("modelName", NamingUtil.toAttributeName(field.getName()));
		        params.put("formName", NamingUtil.toElementName(clazz.getSimpleName()));
		        params.put("prefix", NamingUtil.toElementName(clazz.getSimpleName()));
		    }			
		}
		params.put("fieldType", "multiRecord");
		
		params.putAll(super.getStandardParams(field));
		
		return params;
	}

}
