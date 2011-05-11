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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.jar.JarEntry;

import org.apache.log4j.Logger;
import org.opentides.InvalidImplementationException;

/**
 * Helper class for high-tides in generating package and folder paths.
 * This class needs to be instantiated to set the appropriate package 
 * variables.
 * @author allantan
 *
 */
public class PackageUtil {
	
	// For logging 
	private static Logger _log = Logger.getLogger(PackageUtil.class);
	
	// Base path where application is executed
	private static String basePath;
	// Relative path where template is located
	private static String baseTemplatePath;
	// Relative path of root source location 
	private static String baseOutputPath;
	// Folder of template to process
	private String templateFolder;
	// Folder where output will be written
	private String outputFolder; 
	// Complete set of properties found in hightides.properties
	private static Properties properties;
	
	public PackageUtil(String templateFolder, String outputFolder) {
		super();
		this.outputFolder = outputFolder;
		this.templateFolder = templateFolder;
	}

	/**
	 * Returns the file location of the template folder
	 * @param templateFolder
	 * @return
	 */
	public final File getTemplateFolder() {
		return new File(baseTemplatePath + templateFolder);
	}
	
	/**
	 * Returns the package location of the output code.
	 * @return
	 */
	public final String getPackageFolder(String modelPackage, File template) {
		String out = outputFolder;
		// if outputFolder starts with "/", then there is no need to extract modelPackage
		// this rule is used for outputting non-Java files (e.g.JSP) which is in a different 
		// location than the bean
		if (outputFolder.startsWith("/")) {
			// remove "/" in front
			out = outputFolder.substring(1);
		} else {
			// extract model package.
			// output directory is generated as outputPath + modelPackage + outputFolder + template subFolder
			out = baseOutputPath + modelPackage.replaceAll("\\.", "/") + "/"+outputFolder;
		}
		// retrieve the subfolder in template if exist
		String sub = template.getParentFile().getAbsolutePath()
						.replace(this.getTemplateFolder().getAbsolutePath(), "");
		return out + sub;
	}
	
	/**
	 * Returns the java package value for the given file. 
	 * @return 
	 */
	public final String getPackage(File outputFile) {
		try {
			// if outputFolder starts with "/", then there is no need to extract modelPackage
			// this rule is used for outputting non-Java files (e.g.JSP) which is in a different 
			// location than the bean
			if (outputFolder.startsWith("/")) {
				return "";
			} else {
				String outputPackage;
				outputPackage = outputFile.getParentFile().getCanonicalPath().replaceAll("\\\\", "/");
				int index = outputPackage.indexOf(baseOutputPath);
				if (index<0)
					throw new InvalidImplementationException("Unable to retrieve package. BaseOutputPath ["
							+baseOutputPath+"] is invalid.");
				index += baseOutputPath.length();
				String packagePath = outputPackage.substring(index);
				return packagePath.replaceAll("/", "\\.");		
			}
		} catch (IOException e) {
			_log.error("Cannot retrieve package value.",e);
			return "";
		}
	}
	
	/**
	 * Returns the location of the jar file
	 * @param jarPath
	 * @return
	 */
	public static final String getJarFile(String jarPath) {
		File file = new File("");
		return file.getAbsolutePath() + "/" + jarPath.substring(jarPath.lastIndexOf(":")+1);
	}
	
	/**
	 * 
	 * @param classLoader
	 * @param jarEntry
	 * @return
	 */
	public static final String getTemplateInJar(ClassLoader classLoader, JarEntry jarEntry) {
		URL urlEntry = classLoader.getResource(jarEntry.getName());
		return urlEntry.getPath().substring(urlEntry.getPath().indexOf('!')+2).replace("/", "\\");
	}
	
	/**
	 * @param basePath the basePath to set
	 */
	public static final void setBasePath(String basePath) {
		PackageUtil.basePath = basePath;
	}
	/**
	 * @param baseTemplatePath the baseTemplatePath to set
	 */
	public static final void setBaseTemplatePath(String baseTemplatePath) {
		PackageUtil.baseTemplatePath = baseTemplatePath;
	}
	/**
	 * @param baseOutputPath the baseOutputPath to set
	 */
	public static final void setBaseOutputPath(String baseOutputPath) {
		PackageUtil.baseOutputPath = baseOutputPath;
	}

	/**
	 * @return the basePath
	 */
	public static final String getBasePath() {
		return basePath;
	}

	/**
	 * @return the baseTemplatePath
	 */
	public static final String getBaseTemplatePath() {
		return baseTemplatePath;
	}

	/**
	 * @return the baseOutputPath
	 */
	public static final String getBaseOutputPath() {
		return baseOutputPath;
	}

	/**
	 * Getter method for properties.
	 *
	 * @return the properties
	 */
	public final static Properties getProperties() {
		return properties;
	}

	/**
	 * Setter method for properties.
	 *
	 * @param properties the properties to set
	 */
	public final static void setProperties(Properties properties) {
		PackageUtil.properties = properties;
	}
}
