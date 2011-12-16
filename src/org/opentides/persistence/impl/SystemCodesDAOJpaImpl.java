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
package org.opentides.persistence.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opentides.bean.SystemCodes;
import org.opentides.persistence.SystemCodesDAO;

public class SystemCodesDAOJpaImpl extends BaseEntityDAOJpaImpl<SystemCodes, Long> implements
		SystemCodesDAO {

	/**
	 * Retrieves SystemCodes for the given category
	 */
	public List<SystemCodes> findSystemCodesByCategory(String category) {		
        SystemCodes example = new SystemCodes();
        example.setCategory(category);
        return findByExample(example, true);        
	}

	/**
	 * Retrieves system code based on key.
	 */
	public SystemCodes loadBySystemCodesByKey(String key) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyName", key);
		return findSingleResultByNamedQuery("jpql.systemcodes.findByKey", map);
	}

	/**
	 * Increments value of numberValue by used. 
	 * Useful for custom generation of id.
	 */
	public Long incrementValue(String key) {
	    synchronized(SystemCodes.class) {
    		SystemCodes code = loadBySystemCodesByKey(key);
    		if (code==null) code = new SystemCodes(key);
            code.setSkipAudit(true);    // no need to audit auto-generated keys
    		code.incrementNumberValue();
    		code.setCategory("KEYGEN");
    		this.saveEntityModel(code);
    		return code.getNumberValue();
	    }
	}

	public List<SystemCodes> getAllCategories() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SystemCodes> list = findByNamedQuery("jpql.systemcodes.findAllCategories", map);
		if (list == null || list.size() == 0)
			return null;
		return list;
	}

	public List<SystemCodes> getAllCategoriesExcept(String... categories) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> categoryList = new ArrayList<String>();
		Collections.addAll(categoryList, categories);
		map.put("categories", categoryList);
		List<SystemCodes> list = findByNamedQuery("jpql.systemcodes.findAllCategoriesExcept", map);
		if (list == null || list.size() == 0)
			return new ArrayList<SystemCodes>();
		return list;
	}

	/* (non-Javadoc)
	 * @see org.opentides.persistence.impl.BaseEntityDAOJpaImpl#appendClauseToExample(org.opentides.bean.BaseEntity, boolean)
	 */
	@Override
	protected String appendClauseToExample(SystemCodes example,
			boolean exactMatch) {
		// exclude all system code with category KEYGEN
		return "category != 'KEYGEN'";
	}
	
}