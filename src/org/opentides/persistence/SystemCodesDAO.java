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


package org.opentides.persistence;

import java.util.List;

import org.opentides.bean.SystemCodes;


public interface SystemCodesDAO extends BaseEntityDAO<SystemCodes, Long> {
	
	/**
	 * Return List of SystemCodes by Category.
	 * @param category
	 * @return
	 */
	public List<SystemCodes> findSystemCodesByCategory(String category);
	
	/**
	 * Returns all the available categories.
	 * @return
	 */
	public List<SystemCodes> getAllCategories();
	
	/**
	 * 
	 * @return
	 */
	public List<String> getAllCategoryValues();
	
	/**
	 * Return SystemCode entity by key.
	 * @param key
	 * @return
	 */
	public SystemCodes loadBySystemCodesByKey(String key);
	
	/**
	 * Increment counter - useful for ID generation
	 */
	public Long incrementValue(String key);
	
	/** 
     * Selects all available categories except for the
     * specified ones 
     */
	public List<SystemCodes> getAllCategoriesExcept(String ... categories);
	
	/**
	 * 
	 * @param categories
	 * @return
	 */
	public List<String> getAllCategoryValuesExcept(String ... categories);
	
}
