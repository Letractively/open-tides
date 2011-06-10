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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opentides.bean.Auditable;
import org.opentides.bean.AuditableField;
import org.opentides.bean.Searchable;

/**
 * Helper class to keep a cache of reusable attributes.
 * 
 * @author allantan
 *
 */
public class CacheUtil {

	@SuppressWarnings("rawtypes")
	public static final Map<Class, List<AuditableField>> auditable  = new HashMap<Class, List<AuditableField> >();

	@SuppressWarnings("rawtypes")
	public static final Map<Class, List<String>> searchable = new HashMap<Class, List<String>>();
	
	/**
	 * Retrieves auditable settings from the cache, if available.
	 * Otherwise, adds settings to cache.
	 * 
	 * @param obj
	 * @return
	 */
	public static List<AuditableField> getAuditable(Auditable obj) {
		List<AuditableField> ret = auditable.get(obj.getClass());
		if (ret == null) {
			auditable.put(obj.getClass(), obj.getAuditableFields());
			ret =  auditable.get(obj.getClass());
		}
		return ret;		
	}
	
	/**
	 * Retrieves the auditable field of the given object and field reference.
	 * This is used to get the proper label of the given field.
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static AuditableField getAuditableField(Auditable obj, String fieldName) {
		List<AuditableField> fields = CacheUtil.getAuditable(obj);
		for (AuditableField field:fields) {
			if (fieldName.equals(field.getFieldName()))
				return field;
		}
		return null;
	}
	/**
	 * Retrieves searchable settings from the cache, if available.
	 * Otherwise, adds settings to cache.
	 * 
	 * @param obj
	 * @return
	 */
	public static List<String> getSearchable(Searchable obj) {
		List<String> ret = searchable.get(obj.getClass());
		if (ret == null) {
			searchable.put(obj.getClass(), obj.getSearchProperties());
			ret =  searchable.get(obj.getClass());
		}
		return ret;				
	}
}
