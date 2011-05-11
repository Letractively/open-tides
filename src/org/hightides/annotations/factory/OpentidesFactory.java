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
package org.hightides.annotations.factory;

import java.util.HashMap;

import org.hightides.annotations.Controller;
import org.hightides.annotations.Page;
import org.hightides.annotations.Dao;
import org.hightides.annotations.Service;
import org.hightides.annotations.filter.OpentidesFilter;
import org.hightides.annotations.processor.FileCloningProcessor;
import org.hightides.annotations.processor.Processor;

/**
 * @author allantan
 *
 */
public class OpentidesFactory extends CodeFactory {
	
	@SuppressWarnings("unchecked")
	protected final void initializeProcessors() {
		processorMap = new HashMap<Class, Processor>();
		
		// initialize the service processor
		FileCloningProcessor serviceProcessor = new FileCloningProcessor("/opentides/service/","../service/");
		serviceProcessor.setFilter(new OpentidesFilter());
		processorMap.put(Service.class, serviceProcessor);

		// initialize the dao processor
		FileCloningProcessor daoProcessor = new FileCloningProcessor("/opentides/dao/","../persistence/");
		daoProcessor.setFilter(new OpentidesFilter());
		processorMap.put(Dao.class, daoProcessor);
		
		// initialize the controller processor
		FileCloningProcessor controllerProcessor = new FileCloningProcessor("/opentides/web/controller/","../web/controller/");
		controllerProcessor.setFilter(new OpentidesFilter());
		processorMap.put(Controller.class, controllerProcessor);
		
		// initialize the view/jsp processor
		FileCloningProcessor viewProcessor = new FileCloningProcessor("/opentides/jsp/","/WebContent/jsp/core/modelName");
		viewProcessor.setFilter(new OpentidesFilter());
		processorMap.put(Page.class, viewProcessor);
	}
	
	public OpentidesFactory() {
		language = Language.OPENTIDES;
	}
}