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
package org.opentides.bean;

/**
 * Container of request by requesting for results of 
 * http. Used by UrlUtil.getPage(...)
 * 
 * @author allantan
 *
 */
public class UrlResponseObject {
	
	private String responseType;
	
	private byte[] responseBody;

	/**
	 * Getter method for responseType.
	 *
	 * @return the responseType
	 */
	public String getResponseType() {
		return responseType;
	}

	/**
	 * Setter method for responseType.
	 *
	 * @param responseType the responseType to set
	 */
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	/**
	 * Getter method for responseBody.
	 *
	 * @return the responseBody
	 */
	public byte[] getResponseBody() {
		return responseBody;
	}

	/**
	 * Setter method for responseBody.
	 *
	 * @param responseBody the responseBody to set
	 */
	public void setResponseBody(byte[] responseBody) {
		this.responseBody = responseBody;
	}
	
	
}
