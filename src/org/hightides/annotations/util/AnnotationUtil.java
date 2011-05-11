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

package org.hightides.annotations.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.hightides.annotations.CheckBox;
import org.hightides.annotations.DropDown;
import org.hightides.annotations.RadioButton;
import org.hightides.annotations.bean.SyncMode;
import org.opentides.bean.BaseEntity;
import org.opentides.bean.SystemCodes;

/**
 * Utilities to retrieve annotation values
 * 
 * @author allantan
 */
public final class AnnotationUtil {

	private static Logger _log = Logger.getLogger(AnnotationUtil.class);

	private AnnotationUtil() {

	}

	public static final SyncMode getSyncMode(AnnotatedElement element) {
		Annotation[] classAnnotations = element.getAnnotations();
		for (Annotation annotation : classAnnotations) {
			Method m;
			try {
				m = annotation.getClass().getDeclaredMethod("synchronizeMode");
				if (m != null) {
					return (SyncMode) m.invoke(annotation);
				}
			} catch (SecurityException e1) {
				_log.error(e1, e1);
			} catch (NoSuchMethodException e1) {
				_log.error(e1, e1);
			} catch (IllegalArgumentException e) {
				_log.error(e, e);
			} catch (IllegalAccessException e) {
				_log.error(e, e);
			} catch (InvocationTargetException e) {
				_log.error(e, e);
			}
		}
		return null;
	}

	/**
	 * Checks if the field is a list field (e.g. dropdown, radio button).
	 * 
	 * @param field
	 * @return
	 */
	public static final boolean isListField(Field field) {
		if (field.isAnnotationPresent(DropDown.class)
				|| field.isAnnotationPresent(CheckBox.class)
				|| field.isAnnotationPresent(RadioButton.class)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if a field is annotated with the given annotation.
	 * 
	 * @param annotName
	 * @param field
	 * @return
	 */
	public static Boolean isAnnotatedWith(String annotName, Field field) {
		Annotation[] classAnnotations = field.getAnnotations();
		for (Annotation annotation : classAnnotations) {
			Method m;
			try {
				m = annotation.getClass().getDeclaredMethod(annotName);
				if (m != null) {
					return (Boolean) m.invoke(annotation);
				}
			} catch (Exception e) {
				// do nothing
			}
		}
		return false;
	}

	/**
	 * @param clazz
	 *            - bean class to check the titleField
	 * @param isObject
	 *            - determine if the titleField to retrieve is an
	 *            objectTitleField or not.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getTitleField(Class clazz) {
		try {
			for (Field field : clazz.getDeclaredFields()) {
				if (isAnnotatedWith("titleField", field)) {
					if (SystemCodes.class.isAssignableFrom(field.getType())) {
						return field.getName() + ".value";
					} else if (BaseEntity.class.isAssignableFrom(field.getType())) {
						return field.getName()
								+ "."
								+ AnnotationUtil.getTitleField(field
										.getType());
					} else
						return field.getName();
				}
			}
		} catch (Exception e) {
			// do nothing
			_log.debug("Unable to find annotation attribute titleField for class ["
							+ clazz.getName());
		}
		return "";
	}
}
