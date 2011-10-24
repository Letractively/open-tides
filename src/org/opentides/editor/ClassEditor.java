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
package org.opentides.editor;

import java.beans.PropertyEditorSupport;

import org.apache.log4j.Logger;
import org.opentides.util.StringUtil;

/**
 * PropertyEditor for handling Class.
 * Used for User Defined Fields mapping dropdown of user definable classes.
 * @author allantan
 */
public class ClassEditor extends PropertyEditorSupport {
	
	private static Logger _log = Logger.getLogger(ClassEditor.class);
	
	@SuppressWarnings("rawtypes")
	@Override
	public String getAsText() {
		if (getValue() != null) {
			Class editor = (Class) getValue();
			return editor.getName();
		}
		return "";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (!StringUtil.isEmpty(text)) {
			try {
				Class clazz = Class.forName(text);
				setValue(clazz);
			} catch (ClassNotFoundException e) {
				_log.error("Failed to convert "+text+" to class object.");
				setValue(null);
			}
		} else
			setValue(null);
	}
}
