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

package org.hightides.annotations.processor;

import java.io.File;
import java.util.Map;

import org.hightides.annotations.util.PackageUtil;

/**
 * This class is responsible in adding validator class and its corresponding 
 * spring configuration file.
 * 
 * @author allantan
 */
public class ValidatorProcessor extends SpringConfigProcessor implements Processor {

	/* (non-Javadoc)
	 * @see org.hightides.annotations.processor.Processor#execute(java.util.Map)
	 */
	public void execute(Map<String, Object> params) {
		String templateFolder = PackageUtil.getProperties().getProperty("validator.template.path");
		JarCloningProcessor jcp = new JarCloningProcessor(
				templateFolder,
				PackageUtil.getProperties().getProperty("validator.output.path"));
		String validatorTemplateFile = "resources/templates"+templateFolder+"classNameValidator.java.vm";
		File path = new File(validatorTemplateFile);
		jcp.clone(path, params);
		// Insert validator bean to applicationContext-controller.xml
		addValidatorConfiguration(params);
	}

}