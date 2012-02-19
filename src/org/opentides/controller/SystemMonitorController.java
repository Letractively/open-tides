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
package org.opentides.controller;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.stat.Statistics;
import org.opentides.service.SystemCodesService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class SystemMonitorController extends AbstractController {
	
	private String viewName = "core/monitor/system-monitor";
	
	private SystemCodesService systemCodesService;
	
	private static Logger _log = Logger.getLogger(SystemMonitorController.class);

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// Get number of available processors
		int procs = Runtime.getRuntime().availableProcessors();
		
		// Get current size of heap in bytes
		long heapSize = Runtime.getRuntime().totalMemory();

		// Get maximum size of heap in bytes. The heap cannot grow beyond this size.
		// Any attempt will result in an OutOfMemoryException.
		long heapMaxSize = Runtime.getRuntime().maxMemory();

		// Get amount of free memory within the heap in bytes. This size will increase
		// after garbage collection and decrease as new objects are created.
		long heapFreeSize = Runtime.getRuntime().freeMemory();
		
		Statistics stats = systemCodesService.getHibernateStatistics();
		
		Date startDate = new Date(stats.getStartTime());
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("processors", procs);
		model.put("heapSize", heapSize/1024);
		model.put("heapMaxSize", heapMaxSize/1024);
		model.put("heapFreeSize", heapFreeSize/1024);
		model.put("startDate", startDate);
		model.put("statistics", stats);
		
		// Display MANIFEST.MF settings for version tracking
		InputStream fis = null;
        try {
			fis = getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF");
			Manifest manifest = new Manifest(fis);
	        Attributes atts = manifest.getMainAttributes();
	        model.put("buildVendor",	atts.getValue("Implementation-Vendor"));
	        model.put("buildTitle",		atts.getValue("Implementation-Title"));
	        model.put("buildVersion",	atts.getValue("Implementation-Version"));
        } catch(Exception e) {
        	_log.warn("Failed to retrieve information from MANIFEST.MF", e);
        } finally {
        	if (fis!=null)
        		fis.close();        	
        }

		return new ModelAndView(viewName, model);
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public void setSystemCodesService(
			SystemCodesService systemCodesService) {
		this.systemCodesService = systemCodesService;
	}
	
}
