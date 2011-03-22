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

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.opentides.bean.user.SessionUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * This class is an ACEGI helper that retrieves the currently logged in user.
 * 
 * @author allantan
 */
public class AcegiUtil {
	
	private static Logger _log = Logger.getLogger(AcegiUtil.class);
		
	// flag to allow null user (user not logged-in) 
	// turn this flag to false when deploying to production
	private static Boolean debug = false;
		
	/**
	 * Static helper to retrieve currently logged user.
	 * @return
	 */
	public static SessionUser getSessionUser() {
		try {
			Object userObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (userObj instanceof SessionUser)
				return (SessionUser) userObj;
		} catch (NullPointerException npe) {
		    // SecurityContextholder.getContext() could return null or
		    // SecurityContextholder.getContext().getAuthentication could return null;
		    _log.warn("No security context found on Acegi.");
		} catch (Exception e) {
			_log.error(e,e);
		}
        if (debug) {
            // in debug mode, we provide temporary Acegi user
            Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
            auths.add(new GrantedAuthorityImpl("SUPER_USER"));
            SessionUser user = new SessionUser("debug","debug",true, auths);
            user.setId(999999l);
            user.setFirstName("Superuser");
            user.setLastName("Debugger");
            return user;
        }
        return null;

	}
	/**
	 * Non-static accessor for JSTL. 
	 * Calls getSessionUser.
	 * @return 
	 */
	public SessionUser getUser() {
		return AcegiUtil.getSessionUser();
	}
	/**
	 * Static helper to check if currently logged-in user has access
	 * to given permission.
	 * @param permission
	 * @return
	 */
	public static boolean currentUserHasPermission(String permission) {
		SessionUser user = AcegiUtil.getSessionUser();
		if (user!=null) {
		    for (GrantedAuthority auth: user.getAuthorities()) {
		        if (permission.equals(auth.getAuthority()))
		            return true;
		    }
		}
		return false;
	}
	/**
	 * @param debug the debug to set
	 */
	public static void setDebug(Boolean debug) {
		AcegiUtil.debug = debug;
	}
	
	/**
     * Getter method for debug.
     *
     * @return the debug
     */
    public static Boolean getDebug() {
        return debug;
    }

}
