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
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hightides.annotations.CheckBox;
import org.hightides.annotations.DropDown;
import org.hightides.annotations.HiddenField;
import org.hightides.annotations.MultiRecord;
import org.hightides.annotations.Page;
import org.hightides.annotations.RadioButton;
import org.hightides.annotations.TextArea;
import org.hightides.annotations.TextField;
import org.hightides.annotations.bean.SyncMode;
import org.hightides.annotations.util.AnnotationUtil;
import org.hightides.annotations.util.NamingUtil;
import org.opentides.bean.BaseEntity;

/**
 * @author jc
 *
 */
public class BaseParamReader {
	
	//TODO: Revisit ParamReader implementation
	
	private static Logger _log = Logger.getLogger(BaseParamReader.class);

	private static boolean validation = false;
	
	@SuppressWarnings("rawtypes")
	protected static Class fieldAnnotations[] = {
		TextField.class,
		DropDown.class,
		TextArea.class,
		CheckBox.class,
		RadioButton.class,
		HiddenField.class,
		MultiRecord.class
	};
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List<Field> getAllAnnotatedFields(Class clazz) {
		List<Field> fields = new ArrayList<Field>();
		if (BaseEntity.class.isAssignableFrom(clazz))
			fields.addAll(getAllAnnotatedFields(clazz.getSuperclass()));
		for (Field field:clazz.getDeclaredFields()) {
			// get all annotation of this field
			field:for (Annotation vAnnot:field.getAnnotations()) {
				for (Class fAnnot:fieldAnnotations) {
					if (fAnnot.isAssignableFrom(vAnnot.getClass())) {
						// there is a valid annotation, add this to fields
						fields.add(field);
						break field;
					}
				}
			}
		}
		return fields;
	}
	
	/**
	 * Populate standard parameters for all fields
	 * @param field
	 * @param categoryName
	 * @param options
	 * @return
	 */
	protected Map<String, Object> getStandardParams(Field field) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fieldName", field.getName());
		params.put("getterName", NamingUtil.toGetterName(field.getName()));
		params.put("setterName", NamingUtil.toSetterName(field.getName()));
		params.put("fieldLabel", NamingUtil.toLabel(field.getName()));
		return params;
	}
	
	/**
	 * Populates the page parameters for the given class.
	 * Shared by PageParamReader and MultiRecordParamReader
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Map<String, Object> populatePageParams(Class clazz, String fieldsPrefix) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<Field>fields = getAllAnnotatedFields(clazz);
		List<Map<String,Object>> fieldParams = new ArrayList<Map<String,Object>>();
		for (Field field:fields) {
			FieldParamReader paramReader = ParamReaderFactory.getReader(field);
			if (paramReader != null) {
				fieldParams.add(paramReader.getParameters(field));				
			}
		}		
		params.put(fieldsPrefix +  "fields", fieldParams);
		params.put("titleField", AnnotationUtil.getTitleField(clazz));
		Annotation annotation = clazz.getAnnotation(Page.class);
		if (annotation instanceof Page) {
			Page page = (Page) annotation;
			params.put("syncMode", page.synchronizeMode());
			params.put("pageType", page.pageType());
		} else {
			// unexpected case... but handle it nevertheless
			params.put("syncMode", SyncMode.UPDATE);
			_log.error("Unable to retrieve syncMode for ["+clazz.getName()+"]");
		}
		return params;
	}
	/**
	 * Populate parameters for list types (dropdown, checkbox, radiobutton)
	 * @param field
	 * @param categoryName
	 * @param options
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Map<String, Object> populateListTypeParams(Field field, String categoryName, String[] options) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (!categoryName.equals("")) {
			params.put("isByCategory","true");
			params.put("categoryName", categoryName);
		} else if (!(options.length == 1)) {
			params.put("isByOptions", "true");
			params.put("options", options);
		} else if (BaseEntity.class.isAssignableFrom(field.getType())) {
			// retrieve by field variable type
			params.put("isByObject", "true");
			params.put("objectClass", field.getType().getSimpleName());
			params.put("objectName", NamingUtil.toAttributeName(field.getType().getSimpleName()));
			params.put("objectPackage", field.getType().getPackage().getName());
			params.put("objectTitleField", AnnotationUtil.getTitleField(field.getType()));
		} else if (List.class.isAssignableFrom(field.getType())) {
			Class objectClass = (Class)((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
			params.put("isByObject", "true");
			params.put("objectClass", objectClass.getSimpleName());
			params.put("objectName", NamingUtil.toAttributeName(objectClass.getSimpleName()));
			params.put("objectPackage", objectClass.getPackage().getName());
			params.put("objectTitleField", AnnotationUtil.getTitleField(objectClass));
		} else if (field.getType().getSimpleName().equals("boolean")) {
			// do nothing
		}
		else {
			_log.error("["+field.getName()+"] is not properly annotated.");
		}	
		return params;
	}

	/**
	 * @return the validation
	 */
	public static boolean isValidation() {
		return validation;
	}

	/**
	 * @param validation the validation to set
	 */
	public static void setValidation(boolean validation) {
		BaseParamReader.validation = validation;
	}

}
