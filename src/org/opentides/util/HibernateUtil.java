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

package org.opentides.util;

import java.net.URL;
import java.util.Properties;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

/**
 * This utility allows creation of Hibernate session directly.
 * Used for logging purposes.
 *
 * @author allantan
 */
public class HibernateUtil {

    /**
     * Local session factory to manage database sessions.
     */
    private static SessionFactory sessionFactory;

    /**
     * Database driver class name (e.g. com.mysql.jdbc.Driver)
     */
    private static String driverClassName;

    /**
     * Database connection url (e.g. jdbc:mysql://localhost/javatest)
     */
    private static String url;

    /**
     * Database connection username
     */
    private static String username;

    /**
     * Database connection password
     */
    private static String password;

    /**
     * Database connection jndi name.
     * Optional but if this has value, url, username and password is ignored.
     */
    private static String jndiPath;

    /**
     * Static initializer to establish database connection.
     */
    private static void initialize() {
        try { 
        	URL[] urls = ClasspathUrlFinder.findResourceBases("META-INF/persistence.xml");
        	AnnotationDB db = new AnnotationDB();
        	db.scanArchives(urls);
        	Set<String> entityClasses = db.getAnnotationIndex().get(javax.persistence.Entity.class.getName());
            // Create the SessionFactory
        	AnnotationConfiguration ac =  new AnnotationConfiguration();
        	Properties properties = XMLPersistenceUtil.getProperties("META-INF/persistence.xml", "hibernateConfig");
        	ac.setProperties(properties);
        	if (StringUtil.isEmpty(jndiPath)) {
	            ac.setProperty("hibernate.connection.driver_class", driverClassName);
	            ac.setProperty("hibernate.connection.url", url);
	            ac.setProperty("hibernate.connection.username", username);
	            ac.setProperty("hibernate.connection.password", password);
        	} else
        		ac.setProperty("hibernate.connection.datasource", jndiPath);
            for (String clazz:entityClasses) {
            	ac.addAnnotatedClass(Class.forName(clazz));
            }
            sessionFactory = ac.buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    	
    }

    public static SessionFactory getSessionFactory() {
    	if (sessionFactory==null)
    		HibernateUtil.initialize();
        return sessionFactory;
    }

	/**
	 * @param driverClass the driverClass to set
	 */
	public void setDriverClassName(String driverClass) {
		HibernateUtil.driverClassName = driverClass;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		HibernateUtil.url = url;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		HibernateUtil.username = username;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		HibernateUtil.password = password;
	}

	public void setJndiPath(String jndiPath) {
		HibernateUtil.jndiPath = jndiPath;
	}
}