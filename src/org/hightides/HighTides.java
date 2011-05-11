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
package org.hightides;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.velocity.app.Velocity;
import org.hightides.annotations.Controller;
import org.hightides.annotations.Dao;
import org.hightides.annotations.Page;
import org.hightides.annotations.Service;
import org.hightides.annotations.factory.CodeFactory;
import org.hightides.annotations.factory.CodeFactory.Language;
import org.hightides.annotations.param.ClassParamReader;
import org.hightides.annotations.param.ParamReaderFactory;
import org.hightides.annotations.processor.Processor;
import org.hightides.annotations.util.NamingUtil;
import org.hightides.annotations.util.PackageUtil;
import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

/**
 * This is the main application program of HighTides. HighTides is the code
 * generation tool of open-tides to automatically create CRUD pages based on
 * open-tides framework.
 * 
 * 
 * @author allantan
 * 
 */
public class HighTides {

    /**
     * Static class logger
     */
    private static Logger _log = Logger.getLogger(HighTides.class);

    /**
     * List of annotations for processing.
     */
    @SuppressWarnings("rawtypes")
	private List<Class> annotationList;
    /**
     * Internal instance of AnnotationDB.
     */
    private AnnotationDB db;
    /**
     * Location of property file.
     */
    private static final String propFilename = "resources/hightides.properties";

    /**
     * 
     */
    @SuppressWarnings("rawtypes")
	private void initializeList() {
        annotationList = new ArrayList<Class>();
        annotationList.add(Service.class);
        annotationList.add(Dao.class);
        annotationList.add(Controller.class);
        annotationList.add(Page.class);
    }

    /**
     * Main entry point.
     * 
     * @param args
     */
    public static void main(String[] args) {
        HighTides ht = new HighTides();
        if (args.length == 1)
            ht.generate(Language.valueOf(args[0]));
        else
            ht.generate(Language.OPENTIDES_JAR);
    }

    private void locateFilesForAnnotationScanning() throws IOException {
        URL[] urls = ClasspathUrlFinder.findClassPaths();
        String location = "";
        for (URL url : urls) {
        	if (!url.toString().endsWith(".jar"))
        		location += url.toString() + ", ";
        }
        _log.info("Scanning files at " + location);
        db = new AnnotationDB();
        db.scanArchives(urls);
    }

    private void initializePaths() throws IOException, Exception {
        Properties props = new Properties();
        props.load(ClassLoader.getSystemResourceAsStream(propFilename));
        Velocity.init(props);
        PackageUtil.setBaseTemplatePath(props
                .getProperty("file.resource.loader.path"));
        PackageUtil.setBaseOutputPath(props.getProperty("file.output.path"));
        PackageUtil.setProperties(props);
    }

    public HighTides() {
        try {
            initializeList();
            initializePaths();
            locateFilesForAnnotationScanning();
        } catch (IOException e) {
            _log.error("Initialization failed.", e);
        } catch (Exception e) {
            _log.error("Initialization failed.", e);
        }
    }

    /**
     * This method checks all registered annotations and search for annotated
     * classes. Templates are then applied to the classes.
     * 
     */
    @SuppressWarnings("rawtypes")
	public void generate(Language language) {
        CodeFactory cf = CodeFactory.getFactory(language);
        for (Class annotation : annotationList) {
            _log.info("============================");
            _log.info("Processing " + annotation.getName() + "...");
            // retrieve the processor for this annotation
            Processor p = cf.getProcessor(annotation);
            // get all classes that are marked with this annotation
            Set<String> serviceClasses = db.getAnnotationIndex().get(
                    annotation.getName());
            if (serviceClasses == null || serviceClasses.size() == 0) {
                _log.info("    No classes found.");
                continue;
            }
            for (String className : serviceClasses) {
                _log.info("    Found " + className);
                try {
                    Map<String, Object> params = populateParams(annotation
                            .getSimpleName(), className);
                    params.put("annotation", annotation.getName());
                    p.execute(params);
                } catch (Exception e) {
                    _log.error("Failed to process class [" + className
                            + "] for annotation [" + annotation + "]", e);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
	private Map<String, Object> populateParams(String annotation,
            String className) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        Class clazz = Class.forName(className);

        // add standard parameters
        params.put("className", clazz.getSimpleName());
        params.put("modelName", NamingUtil.toAttributeName(clazz
                .getSimpleName()));
        params.put("formName", NamingUtil.toElementName(clazz.getSimpleName()));
        params.put("prefix", NamingUtil.toElementName(clazz.getSimpleName()));
        params.put("modelPackage", clazz.getPackage().getName());
        params.put("fieldTemplatePath", CodeFactory.getFieldTemplatePath());
        params.put("pageName", "/"
                + NamingUtil.toElementName(clazz.getSimpleName()) + ".jspx");
        String jspFullPath = PackageUtil.getProperties().getProperty("jsp.output.path");
        int idx = jspFullPath.indexOf("/jsp/");
        String jspFolder = "";
        if (idx > 0) {
        	jspFolder = jspFullPath.substring(idx+5).replace("modelName", "");
        }
        jspFolder += params.get("modelName");
        params.put("jspFolder", jspFolder);

        // call helper to add additional parameters per annotation
        ClassParamReader paramReader = ParamReaderFactory.getReader(annotation);
        if (paramReader != null) {
            params.putAll(paramReader.getParameters(clazz));
        }

        return params;
    }

}