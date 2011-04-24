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
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.hightides.annotations.bean.SyncMode;
import org.hightides.annotations.filter.ProcessorFilter;
import org.hightides.annotations.param.BaseParamReader;
import org.hightides.annotations.util.CodeUtil;
import org.hightides.annotations.util.PackageUtil;
import org.opentides.util.FileUtil;
import org.opentides.util.StringUtil;

public abstract class BaseCloningProcessor {
	private static Logger _log = Logger.getLogger(BaseCloningProcessor.class);

	public static final String ENCODING = "utf-8";

	// helper class for retrieving package, path or file locations
	private PackageUtil packager;
	
	// filter for inserting values in param
	private ProcessorFilter filter;
			
	/**
	 * Constructor with outputFolder defaulted to same directory of model
	 * @param templatePath
	 */
	public BaseCloningProcessor(String templateFolder) {
		this(templateFolder,".");
	}

	/**
	 * Constructor
	 * @param templatePath
	 */
	public BaseCloningProcessor(String templateFolder, String outputFolder) {
		packager = new PackageUtil(templateFolder, outputFolder);
	}

	/**
	 * Recurse all the directories under templatePath and clone all vm files.
	 */
	public abstract void recurseTemplates(File path, Map<String, Object> params);

	/**
	 * Returns location of the template
	 * @param template
	 * @return
	 */
	public abstract String getTemplateForMerge(File template);


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opentides.annotations.processor.Processor#execute(java.lang.Class,
	 * java.util.Map)
	 */
	public void execute(Map<String, Object> params) {
		// recurse all the subdirectories in the templatePath
		File filePath = packager.getTemplateFolder();
		recurseTemplates(filePath, params);
		
		// update spring configuration files
		updateSpringConfig(params);
		
		// update message properties
		updateMessages(params);

		// create validators
		if (BaseParamReader.isValidation())
			addValidators(params);
	}

	/**
	 * Updates the necessary spring configuration file.
	 * @param params
	 */
	private void updateSpringConfig(Map<String, Object> params) {
		SpringConfigProcessor scp  = new SpringConfigProcessor();
		scp.execute(params);
	}
	
	/**
	 * Updates the message property file for default language.
	 * @param params
	 */
	private void updateMessages(Map<String, Object> params) {
		MessagePropertyProcessor mpp  = new MessagePropertyProcessor();
		mpp.execute(params);
	}

	/**
	 * Add validation if a field is required.   
	 */
	private void addValidators(Map<String, Object> params) {
		ValidatorProcessor vp = new ValidatorProcessor();
		vp.execute(params);
	}
	
	/**
	 * Clones the velocity file and injects params accordingly
	 */
	public void clone(File path, Map<String, Object> params) {		
		File outputFile = getOutputFile(path, params);
		params.put("package", packager.getPackage(outputFile));
		SyncMode syncMode = (SyncMode) params.get("syncMode");
		if (syncMode == SyncMode.CREATE) {
			if (outputFile.exists()) {
				// file already exist. Do not overwrite.
				_log.info(outputFile.getName()+" not updated. File already exist. SyncMode: ["+syncMode+"]");
			} else {
				writeTemplateFile(path, params, outputFile);
			}
		} else if (syncMode == SyncMode.UPDATE) {
			if (outputFile.exists()) {
				// file already exist. Check if updates were made...
				// get hashcode of the file
				String code = FileUtil.readFile(outputFile);
				String newHash = StringUtil.hashSourceCode(code);
				// get the original hash of the generated code
				String oldHash = CodeUtil.getHashCode(params.get("package")+"."+outputFile.getName());
				if (!StringUtil.isEmpty(oldHash) && newHash.equals(oldHash)) {
					// no changes since last code generation, then let's overwrite.
					writeTemplateFile(path, params, outputFile);
				} else {
					// changes have been made, do not overwrite the file.
					_log.info(outputFile.getName()+" not updated. File already exist in path ["+outputFile.getAbsolutePath()+"] and changes were found. SyncMode: ["+syncMode+"]");
				}
			} else {
				writeTemplateFile(path, params, outputFile);
			}			
		} else if (syncMode == SyncMode.OVERWRITE) {
			writeTemplateFile(path, params, outputFile);
		}
	}
	
	/**
	 * Private helper to write velocity template into file.
	 * 
	 * @param path
	 * @param params
	 * @param outputFile
	 */
	private void writeTemplateFile(File path, Map<String, Object> params, File outputFile) {
		try {
	        FileUtil.createDirectory(outputFile.getParent());
			outputFile.createNewFile();
			mergeTemplate(path, params, new FileWriter(outputFile));
			CodeUtil.updateHashCode(outputFile, params.get("package")+"."+outputFile.getName());
			_log.info(outputFile.getName()+" successfully generated.");
		} catch (IOException e) {
			_log.error("Failed to create target file. ["+outputFile.getAbsoluteFile()+"]",e);
		}		
	}
	
	/**
	 * Outputs the velocity template along with specified parameters
	 * to the given output writer.
	 * @param path
	 * @param params
	 * @param output
	 */
	private void mergeTemplate(File path, Map<String, Object> params, Writer output) {
		try {
			// execute the filter
			if (filter!=null)
				filter.doFilter(path, params);
			// Put all params to context
			VelocityContext context = new VelocityContext();
			for (Entry<String,Object> entry:params.entrySet()) {
				context.put(entry.getKey(), entry.getValue());
			}

			// Get the relative path for velocity template merging
			String relativePath = getTemplateForMerge(path);
			Velocity.mergeTemplate(relativePath,ENCODING, context, output);
		} catch (ResourceNotFoundException e) {
			// handle this templating error
			_log.error(e, e);
		} catch (ParseErrorException e) {
			// handle this templating error
			_log.error(e, e);
		} catch (MethodInvocationException e) {
			// handle this templating error
			_log.error(e, e);
		} catch (Exception e) {
			// report this unknown error
			throw new RuntimeException(e);
		} finally {
			// just make sure file is closed
			try { output.close(); } catch (IOException e) { };
		}
	}
	
	/**
	 * Builds the path to the output file.
	 * 
	 * @param template
	 * @param params
	 * @return
	 */
	private File getOutputFile(File template, Map<String, Object> params) {
		String outputDir = packager.getPackageFolder(""+params.get("modelPackage"),template);
		outputDir = outputDir
					.replace("className", params.get("className").toString())
					.replace("modelName", params.get("modelName").toString());
		try {
			// create the output directory if not existing
			FileUtil.createDirectory(outputDir);
		} catch (Exception e) {
			_log.error("Failed to create output directory ["+outputDir+"]",e);
			throw new RuntimeException(e);
		}
		// build the filename
		String outputName = template.getName()
								.replace("className", params.get("className").toString())
								.replace("modelName", params.get("modelName").toString())
								.replaceAll(".vm$", "");
		return new File(outputDir+"/"+outputName);
	}

	/**
	 * @param filter the filter to set
	 */
	public final void setFilter(ProcessorFilter filter) {
		this.filter = filter;
	}

	/**
	 * @return the filter
	 */
	protected final ProcessorFilter getFilter() {
		return filter;
	}

	/**
	 * @return the packager
	 */
	protected final PackageUtil getPackager() {
		return packager;
	}
	
}