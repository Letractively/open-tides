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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.opentides.InvalidImplementationException;
import org.opentides.bean.Auditable;
import org.opentides.bean.AuditableField;
import org.opentides.bean.BaseCriteria;
import org.opentides.bean.BaseEntity;
import org.opentides.bean.SystemCodes;

/**
 * @author allanctan
 *
 */
public class CrudUtil {
	
    private static Logger _log = Logger.getLogger(CrudUtil.class);
    
	private static final String SQL_PARAM = ":([^\\s]+)"; 
	private static final Pattern SQL_PARAM_PATTERN = Pattern.compile(
			SQL_PARAM, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    
    /**
     * Creates the logging message for new audit logs 
     * @param obj
     * @return
     */
    public static String buildCreateMessage(Auditable obj) {
    	StringBuffer message = new StringBuffer("Added ");
    	message.append(obj.getClass().getSimpleName())  // class name
    		.append(" ")
    		.append(obj.getPrimaryField().getTitle())
    		.append(":");
    	Object value = retrieveNullableObjectValue(obj, obj.getPrimaryField().getFieldName());
    	if (value!=null) 
    		message.append(value.toString())
    		.append(" - ");
    	// loop through the fields list
		List<AuditableField> auditFields = obj.getAuditableFields();
		int count = 0;
		for (AuditableField property:auditFields) {
			Object ret = retrieveNullableObjectValue(obj, property.getFieldName());
			if (ret!=null && ret.toString().trim().length()>0) {
				if (count > 0) 
					message.append(" and ");
				message.append(property.getTitle())
					.append("=")
					.append(ret.toString());
				count++;
			}
		}
    	
    	return message.toString();    	
    }
    
    /**
     * Creates the friendly message for auditable obj
     * @param obj
     * @return
     */
    public static String buildFriendlyCreateMessage(Auditable obj){
    	StringBuffer message = new StringBuffer("Added new ");
    	message.append(obj.getFriendlyName());
    	AuditableField primaryField = obj.getPrimaryField();
    	if (primaryField != null){
    		Object value = retrieveNullableObjectValue(obj, obj.getPrimaryField().getFieldName());
        	if (value!=null) 
        		message.append(": " + value.toString());
    	}
    	return message.toString();
    }
    
    /**
     * Creates the logging message for update audit logs 
     * @param obj
     * @return
     */ 
    public static String buildUpdateMessage(Auditable oldObject, Auditable newObject) {
    	
    	StringBuffer message = new StringBuffer("Changed ");
    	message.append(oldObject.getClass().getSimpleName())  // class name
    		.append(" ")
    		.append(oldObject.getPrimaryField().getTitle())
    		.append(":");
    	Object value = retrieveNullableObjectValue(oldObject, oldObject.getPrimaryField().getFieldName());
    	if (value!=null) 
    		message.append(value.toString())
    		.append(" - ");
    	// loop through the fields list
		List<AuditableField> auditFields = oldObject.getAuditableFields();
		int count = 0;
		for (AuditableField property:auditFields) {
			Object oldValue = retrieveNullableObjectValue(oldObject, property.getFieldName());
			Object newValue = retrieveNullableObjectValue(newObject, property.getFieldName());
			if (oldValue == null) oldValue = "";
			if (newValue == null) newValue = "";
			if (!oldValue.equals(newValue)) {
				if (count > 0) 
					message.append(" and ");
				message.append(property.getTitle());
				if (!StringUtil.isEmpty(oldValue.toString()))
				message.append(" from '")
						.append(oldValue.toString())
						.append("'");
				message.append(" to '")
					.append(newValue.toString())
					.append("'");
				count++;
			}
		}
    	if (count==0)
    		return "";
    	else
    		return message.toString();    	
    }
    
    /**
     * Creates the friendly message for update logs
     * @param obj
     * @return
     */
    public static String buildFriendlyUpdateMessage(Auditable obj){
    	StringBuffer message = new StringBuffer("Updated ");
    	message.append(obj.getFriendlyName());
    	AuditableField primaryField = obj.getPrimaryField();
    	if (primaryField != null){
    		Object value = retrieveNullableObjectValue(obj, obj.getPrimaryField().getFieldName());
        	if (value!=null) 
        		message.append(": " + value.toString());
    	}
    	return message.toString();
    }
    
    /**
     * Creates the logging message for new audit logs 
     * @param obj
     * @return
     */
    public static String buildDeleteMessage(Auditable obj) {
    	StringBuffer message = new StringBuffer("Deleted ");
    	message.append(obj.getClass().getSimpleName())  // class name
    		.append(" ")
    		.append(obj.getPrimaryField().getTitle())
    		.append(":");
    	Object value = retrieveNullableObjectValue(obj, obj.getPrimaryField().getFieldName());
    	if (value!=null) 
    		message.append(value.toString());
    	return message.toString();    	
    }
    
    public static String buildFriendlyDeleteMessage(Auditable obj){
    	StringBuffer message = new StringBuffer("Deleted ");
    	message.append(obj.getFriendlyName());
    	AuditableField primaryField = obj.getPrimaryField();
    	if (primaryField != null){
    		Object value = retrieveNullableObjectValue(obj, obj.getPrimaryField().getFieldName());
        	if (value!=null) 
        		message.append(": " + value.toString());
    	}
    	return message.toString();
    }

    /**
     * Builds the query string appended to queryByExample
     * @param example
     * @param exactMatch
     * @return
     */
    @SuppressWarnings("unchecked")
	public static String buildJpaQueryString(BaseCriteria example, boolean exactMatch) {
		int count = 0;
		StringBuffer clause = new StringBuffer(" where ");
		List<String> exampleFields = example.getSearchProperties();
		for (String property:exampleFields) {
			// get the value
			Object ret = retrieveObjectValue(example, property);
			// append the alias
			property = "obj." + property; 
			if (ret!=null && ret.toString().trim().length()>0) {
				if (count > 0) 
					clause.append(" and ");
				if (String.class.isAssignableFrom(ret.getClass()) && !exactMatch) {
					clause.append(property)
						.append(" like '%")
						.append(ret.toString())
						.append("%'");
				} else if(SystemCodes.class.isAssignableFrom(ret.getClass())) {
					SystemCodes sc = (SystemCodes) ret;
					clause.append(property)
					.append(".key")
					.append(" = ")
					.append(sc.getKey());
				} else if(BaseEntity.class.isAssignableFrom(ret.getClass())) {
					BaseEntity be = (BaseEntity) ret;
					clause.append(property)
					.append(".id")
					.append(" = ")
					.append(be.getId());
				} else if (Integer.class.isAssignableFrom(ret.getClass()) ||
						   Float.class.isAssignableFrom(ret.getClass()) ||
						   Long.class.isAssignableFrom(ret.getClass()) ||
						   Double.class.isAssignableFrom(ret.getClass()) ||
						   BigDecimal.class.isAssignableFrom(ret.getClass()) ||
						   Boolean.class.isAssignableFrom(ret.getClass()) ) {
					// numeric types doesn't need to be enclosed in single quotes
					clause.append(property)
						.append(" = ")
						.append(ret.toString());
				} else if (Class.class.isAssignableFrom(ret.getClass())){
					Class clazz = (Class) ret;
					clause.append(property)
					.append(" = '")
					.append(clazz.getName())
					.append("'");
				} else {
					clause.append(property)
						.append(" = '")
						.append(ret.toString())
						.append("'");
				}
				count++;
			}
		}
	    if (count > 0) 
	    	return clause.toString();
	    else
	    	return "";
    }

    /**
     * Retrieves the property name for a method name. 
     * (e.g. getName will return name)
     * @param methodName
     * @return
     */
    public static String getPropertyName(String methodName) {
    	if (StringUtil.isEmpty(methodName) || methodName.length()<=3)
    		return null;
    	if (methodName.startsWith("get") || methodName.startsWith("set")) {
    		String prop = methodName.substring(4);
    		char c = Character.toLowerCase(methodName.charAt(3));
    		return c+prop;
    	} else 
    		return null;
    }
    /**
     * Retrieves the getter method name for a given property.
     * (e.g. name will return getName)
     * @param propertyName
     * @return
     */
    public static String getGetterMethodName(String propertyName) {
    	if (StringUtil.isEmpty(propertyName) || propertyName.length()<=0)
    		return null;
    	char c = Character.toUpperCase(propertyName.charAt(0));
    	return "get"+c+propertyName.substring(1);
    }

	/**
	 * This method retrieves the object value that corresponds to the property specified.
	 * This method can recurse inner classes until specified property is reached.
	 * 
	 * For example:
	 * obj.firstName
	 * obj.address.Zipcode
	 * 
	 * @param obj
	 * @param property
	 * @return
	 */
	public static Object retrieveObjectValue(Object obj, String property) {
		if (property.contains(".")) {
			// we need to recurse down to final object
			String props[] = property.split("\\.");
			try {
				Method method = obj.getClass().getMethod(getGetterMethodName(props[0]));
				Object ivalue = method.invoke(obj);
				if (ivalue==null)
					return null;
				return retrieveObjectValue(ivalue,property.substring(props[0].length()+1));
			} catch (Exception e) {
				throw new InvalidImplementationException("Failed to retrieve value for "+property, e);
			} 
		} else {
			// let's get the object value directly
			try {
				Method method = obj.getClass().getMethod(getGetterMethodName(property));
				return method.invoke(obj);
			} catch (Exception e) {
				throw new InvalidImplementationException("Failed to retrieve value for "+property, e);
			} 
		}
	}
	/**
	 * 
	 * This method retrieves the object value that corresponds to the property specified.
	 * This method supports nullable fields.
	 * 
	 * @see retrieveObjectValue
	 * @param obj
	 * @param property
	 * @return
	 */
	public static Object retrieveNullableObjectValue(Object obj, String property) {
		try {
			return retrieveObjectValue(obj, property);
		} catch (Exception e) {
			return null;
		}
	}


	/**
	 * This method retrieves the object type that corresponds to the property specified.
	 * This method can recurse inner classes until specified property is reached.
	 * 
	 * For example:
	 * obj.firstName
	 * obj.address.Zipcode
	 * 
	 * @param obj
	 * @param property
	 * @return
	 */
	public static Class<?> retrieveObjectType(Object obj, String property) {
		if (property.contains(".")) {
			// we need to recurse down to final object
			String props[] = property.split("\\.");
			try {
				Method method = obj.getClass().getMethod(getGetterMethodName(props[0]));
				Object ivalue = method.invoke(obj);
				return retrieveObjectType(ivalue,property.substring(props[0].length()+1));
			} catch (Exception e) {
				throw new InvalidImplementationException("Failed to retrieve value for "+property, e);
			} 
		} else {
			// let's get the object value directly
			try {
				Method method = obj.getClass().getMethod(getGetterMethodName(property));
				return method.getReturnType();
			} catch (Exception e) {
				throw new InvalidImplementationException("Failed to retrieve value for "+property, e);
			} 
		}
	}
	
	@SuppressWarnings("unchecked")
	public static String replaceSQLParameters(String sql, Object obj) {
		// let's get all sql parameter by expression
		Matcher sqlMatcher =  CrudUtil.SQL_PARAM_PATTERN.matcher(sql);
		while (sqlMatcher.find()) {
			String param = sqlMatcher.group(1);
			Object valueObject = CrudUtil.retrieveNullableObjectValue(obj, param); 
			if (valueObject==null) {
				sql = sql.replace(sqlMatcher.group(), "null");				
			} else if (String.class.isAssignableFrom(valueObject.getClass())) {
				sql = sql.replace(sqlMatcher.group(), "'"+valueObject.toString()+"'");
			} else if (Collection.class.isAssignableFrom(valueObject.getClass())) {
				Collection<Object> list = (Collection<Object>) valueObject;
				int ctr=0;
				String comma = "";
				for (Object item:list) {
					if (ctr++>0)
						comma += ", ";
					if (SystemCodes.class.isAssignableFrom(item.getClass())) {
						SystemCodes entity  = (SystemCodes) item;
						// use id 
						comma += "'" + entity.getKey() + "'";
					} else
					if (BaseEntity.class.isAssignableFrom(item.getClass())) {
						BaseEntity entity  = (BaseEntity) item;
						// use id 
						comma += entity.getId();
					} else
						comma += "'" + item.toString() + "'";
				}
				sql = sql.replace(sqlMatcher.group(), comma);				
			} else
				sql = sql.replace(sqlMatcher.group(), valueObject.toString());
		}
		return sql;
	}
	
	/**
	 * Helper method to retrieve a readable name for a given class.
	 * This method tries to access static method named readableName and returns its value if exist.
	 * Otherwise, the method tries to convert the class to a more readable form.
	 * (e.g. InboundDocument becomes Inbound Document);
	 * @param entityClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getReadableName(String entityClass) {
		try {
			Class clazz = Class.forName(entityClass);
			try {
				Method method = clazz.getMethod("readableName");
				return method.invoke(null).toString();
			} catch (Exception e) {
				String name = clazz.getSimpleName();
				return name.replaceAll("([A-Z])", " $1");
			}
		} catch (ClassNotFoundException e) {
			_log.warn("Attempt to get readableName for unknown class ["+entityClass+"]");
			return "";
		}
	}

	/**
	 * Helper method to retrieve all fields of a class including
	 * fields declared in its superclass.
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Field> getAllFields(Class clazz) {
		List<Field> fields = new ArrayList<Field>();
		if (BaseEntity.class.isAssignableFrom(clazz))
			fields.addAll(getAllFields(clazz.getSuperclass()));
		for (Field field:clazz.getDeclaredFields())
			fields.add(field);
		return fields;
	}
}