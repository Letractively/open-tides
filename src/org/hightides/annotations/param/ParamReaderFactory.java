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

import org.hightides.annotations.CheckBox;
import org.hightides.annotations.DropDown;
import org.hightides.annotations.HiddenField;
import org.hightides.annotations.MultiRecord;
import org.hightides.annotations.RadioButton;
import org.hightides.annotations.TextArea;
import org.hightides.annotations.TextField;

/**
 * @author jc
 *
 */
public class ParamReaderFactory {

	/**
	 * Creates an instance of ParamReader based on field
	 * @param field
	 * @return
	 */
	public static FieldParamReader getReader(Field field) {
		if (field.isAnnotationPresent(TextField.class)) {
			return new TextFieldParamReader();
		} else if (field.isAnnotationPresent(TextArea.class)) {
			return new TextAreaParamReader();
		} else if (field.isAnnotationPresent(CheckBox.class)) {
			return new CheckBoxParamReader();
		} else if (field.isAnnotationPresent(DropDown.class)) {
			return new DropDownParamReader();
		} else if (field.isAnnotationPresent(RadioButton.class)) {
			return new RadioButtonParamReader();
		} else if (field.isAnnotationPresent(HiddenField.class)) {
			return new HiddenFieldParamReader();
		} else if (field.isAnnotationPresent(MultiRecord.class)) {
			return new MultiRecordParamReader();
		} else
			return null;
	}
	
	/**
	 * Creates an instance of ParamReader based on annotation class
	 * @param annotation
	 * @return
	 */
	public static ClassParamReader getReader(String annotation) {
		BaseParamReader.setValidation(false);
		if ("Page".equals(annotation)) {
			return new PageParamReader();
		} else if ("Controller".equals(annotation)) {
			return new ControllerParamReader();
		} else if ("Dao".equals(annotation)) {
			return new DaoParamReader();
		} else if ("Service".equals(annotation)) {
			return new ServiceParamReader();
		} else 
			return null;
	}
}
