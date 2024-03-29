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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.opentides.bean.BaseEntity;

/**
 * 
 * @author allantan
 * 
 */
public class DBUtil {
	private static Logger _log = Logger.getLogger(DBUtil.class);
	private static Map<List<String>, SQLQuery> queryCache = new HashMap<List<String>, SQLQuery>();
	
	public static void importCSV(String filename, String tableName, Session session) 
			throws Exception{
		importCSV(filename, tableName, session, false);
	}
	
	public static void importCSVAsObject(String filename, String tableName, Session session)
			throws Exception {
		importCSV(filename, tableName, session, true);
	}

	public static void importCSV(String filename, String tableName, Session session, boolean useHibernate)
			throws Exception {
		int line = 1;

		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(DBUtil.class.getClassLoader()
							.getResourceAsStream(filename)));
			// read the column header
			String csvLine = reader.readLine();
			if (csvLine==null) {
				_log.warn("Import file ["+filename+"] has no contents.");
				return;
			}
			List<String> headers = StringUtil.parseCsvLine(csvLine);
			
			while ((csvLine = reader.readLine()) != null) {
				List<String> values = StringUtil.parseCsvLine(csvLine);
				List<String> tmpHeaders = new ArrayList<String>(headers);
				if (headers.size() != values.size() )
					_log.error("Column number mismatch. "
							+ "Failed to import line #:" + line
							+ " with data as follows: \n[" + csvLine + "].");
				// get all columns with null values
				List<Integer> nullColumns = new ArrayList<Integer>();
				for (int i=values.size()-1; i>=0; i--) {
					String value = values.get(i);					
					if (StringUtil.isEmpty(value)) {
						nullColumns.add(i);
					}
				}
				// remove headers and values with null values
				for (int index:nullColumns) {
					tmpHeaders.remove(index);
					values.remove(index);
				}
				// execute this query
				if(useHibernate) {
					DBUtil.executeHqlQuery(tableName, tmpHeaders, values, session);
				} else {
					DBUtil.executeQuery(tableName, tmpHeaders, values, session);
				}
				line++;
			}
			reader.close();
		} catch (Exception e) {
			_log.error("Failed to import csv file [" + filename + "] at line #"+line, e);
			throw e;
		}
		return;
	}
	
	/**
	 * Private helper that inserts record into the given tableName and values.
	 * This method keeps a static reference to all SQLQueries issued.
	 * 
	 * @param tableName
	 * @param headers
	 * @param values
	 * @param session
	 */
	private static void executeQuery(String tableName, List<String> headers, List<String> values, Session session) {
		SQLQuery query;
		if (queryCache.containsKey(headers)) {
			query = queryCache.get(headers);
		} else {
			StringBuffer baseQuery = new StringBuffer(100);
			baseQuery.append("INSERT INTO ").append(tableName).append("(");
			StringBuffer valueQuery = new StringBuffer(30);
			int count = 0;
			for (String column : headers) {
				if (count++ > 0) {
					baseQuery.append(",");
					valueQuery.append(",");
				}
				baseQuery.append(column);
				valueQuery.append("?");
			}
			baseQuery.append(") VALUES (").append(valueQuery).append(")");
			query = session.createSQLQuery(baseQuery.toString());
			queryCache.put(headers, query);
		}
		int index=0;
		for (String value : values) {
				query.setParameter(index++, value);
		}
		query.executeUpdate();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void executeHqlQuery(String className, List<String> headers, List<String> values, Session session) 
			throws Exception {
		Object entity = Class.forName(className).newInstance();
		if(!(entity instanceof BaseEntity)) {
			throw new Exception("Entity is not an instance of BaseEntity");
		}
		for(int i = 0; i < headers.size(); i++) {
			String property = headers.get(i);
			String value = values.get(i);
			Class<?> type = CrudUtil.retrieveObjectType(entity, property);
			Method method = entity.getClass().getMethod(CrudUtil.getSetterMethodName(property), CrudUtil.retrieveObjectType(entity, property));
			_log.debug("Casting value : [" + value + "] to " + type.getName());
			if(type.equals(Long.class) && !StringUtil.isEmpty(value)) {
				method.invoke(entity, new Long(value));
			} else if(Enum.class.isAssignableFrom(type)) {
				method.invoke(entity, Enum.valueOf((Class<Enum>)type, value));
			} else if(BaseEntity.class.isAssignableFrom(type)) {
				Long id = Long.parseLong(value);
				method.invoke(entity, session.load(type, id));
			} else {
				method.invoke(entity, type.cast(value));
			}
		}
		BaseEntity baseEntity = (BaseEntity)entity;
		baseEntity.setSkipAudit(true);
		session.persist(baseEntity);
	}
	
}
