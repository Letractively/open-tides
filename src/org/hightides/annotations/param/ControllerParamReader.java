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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hightides.annotations.Controller;
import org.hightides.annotations.Page;
import org.hightides.annotations.bean.SyncMode;
import org.hightides.annotations.util.AnnotationUtil;

/**
 * Appends parameters for Controller annotation.
 * 
 * @author allantan
 *
 */
public class ControllerParamReader extends BaseParamReader implements
		ClassParamReader {
	
	private static Logger _log = Logger.getLogger(ControllerParamReader.class);
			
	/**
	 * Returns list of field parameters and list options
	 * Parameters created:
	 *   - isByCategory
	 *   - isByOptions
	 *   - isByObject
	 *   - fields
	 *   - objectFields
	 *   - containsList
	 *   - syncMode
	 * Parameters inherited:
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getParameters(Class clazz) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<Field>fields = getAllAnnotatedFields(clazz);
		// parameters of every field
		List<Map<String,Object>> fieldParams = new ArrayList<Map<String,Object>>();
		// parameters of every object referenced by field
		List<Map<String,Object>> objectParams = new ArrayList<Map<String,Object>>();
		boolean containsList = false;
		boolean containsDate = false;
		boolean initializing = false;
		for (Field field:fields) {
			FieldParamReader paramReader = ParamReaderFactory.getReader(field);
			if (paramReader != null) {
				Map<String,Object> fieldParam = paramReader.getParameters(field);
				fieldParams.add(fieldParam);			
				if ("true".equals(fieldParam.get("isByCategory"))) {
					params.put("containsByCategory", "true");
				} else if ("true".equals(fieldParam.get("isByOptions"))) {
					params.put("containsByOptions", "true");
				} else if ("true".equals(fieldParam.get("isByObject"))) {
					params.put("containsByObject", "true");
					boolean objectFound = false;
					// add to objectParams if not yet there
					for (Map<String,Object> objectParam:objectParams) {
						if (objectParam.get("objectClass").equals(fieldParam.get("objectClass"))) {
							objectFound = true;
						}
					}
					if (!objectFound) 
						objectParams.add(fieldParam);
				}
				if (fieldParam.containsKey("defaultValue") && fieldParam.get("defaultValue").toString().length()>0) {
					// check for default value					
					String initializer  = "";
					if (String.class.isAssignableFrom(field.getType()))
						initializer = "\""+fieldParam.get("defaultValue")+"\"";
					else 
						initializer = ""+fieldParam.get("defaultValue");
					fieldParam.put("initializer", initializer);
					initializing = true;
				} else
					fieldParam.put("initializer", null);
			}
			if (AnnotationUtil.isListField(field))
				containsList = true;
			if (Date.class.isAssignableFrom(field.getType()))
				containsDate = true;				
		}
		params.put("fields", fieldParams);
		params.put("objectFields", objectParams);
		params.put("containsList", containsList);
		params.put("containsDate", containsDate);
		params.put("initializing", initializing);
		Annotation annotation = clazz.getAnnotation(Controller.class);
		if (annotation instanceof Controller) {
			Controller controller = (Controller) annotation;
			params.put("syncMode", controller.synchronizeMode());
		} else {
			// unexpected case... but handle it nevertheless
			params.put("syncMode", SyncMode.UPDATE);
			_log.error("Unable to retrieve syncMode for ["+clazz.getName()+"]");
		}
		// add pageType parameter if available
		Annotation pageAnnotation = clazz.getAnnotation(Page.class);
		if ( (pageAnnotation != null) && (pageAnnotation instanceof Page) ){
			Page page = (Page) pageAnnotation;
			params.put("pageType", page.pageType());
		}
		return params;
	}

}
