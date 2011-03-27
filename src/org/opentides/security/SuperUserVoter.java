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

package org.opentides.security;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * This class provides a positive vote for user with role "SUPER_USER".
 * 
 * @author allantan
 * 
 */
public class SuperUserVoter implements AccessDecisionVoter {

	public static final String SUPER_USER = "SUPER_USER";

	public boolean supports(ConfigAttribute attrib) {
		return true;
	}

    /* (non-Javadoc)
     * @see org.springframework.security.access.AccessDecisionVoter#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }
	/**
	 * Grant access if user has authentication of "SUPER_USER"
	 */
	public int vote(Authentication authentication, 
	        Object arg1,
	        Collection<ConfigAttribute> attributes) {
	    for (GrantedAuthority auth:authentication.getAuthorities()) {
            if (SUPER_USER.equals(auth.getAuthority())) {
                return ACCESS_GRANTED;
            }
	    }
		return ACCESS_ABSTAIN;
	}
}