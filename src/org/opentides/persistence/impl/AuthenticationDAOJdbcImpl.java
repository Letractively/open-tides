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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opentides.bean.user.SessionUser;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

/**
 * This class is responsible in retrieving the user information.
 * TODO: Rewrite this to use JPA EntityManager... if at all, possible.
 * @author allantan
 *
 */
public class AuthenticationDAOJdbcImpl extends JdbcDaoImpl {

	private static Log _log = LogFactory.getLog(AuthenticationDAOJdbcImpl.class);	
	private static String loadUserByUsernameQuery = 
		"select U.USERID ID, FIRSTNAME, LASTNAME, EMAIL, P.OFFICE OFFICE, P.LASTLOGIN LASTLOGIN, '' POSITION,'' COMPANY,'' PICTUREURL" +
		" from USER_PROFILE P, USERS U where P.ID=U.USERID and U.USERNAME=?";
	
	@Override 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		try {
            UserDetails user = super.loadUserByUsername(username);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SessionUser sessUser = new SessionUser(user.getUsername(), user.getPassword(), user.isEnabled(), user.getAuthorities());
            Map<String,Object> result = getJdbcTemplate().queryForMap(loadUserByUsernameQuery.replace("?", "'"+username+"'"));
            sessUser.setFirstName(result.get("FIRSTNAME").toString());
            sessUser.setLastName(result.get("LASTNAME").toString());
            sessUser.setEmailAddress(result.get("EMAIL").toString());
			if (result.get("OFFICE") != null)
				sessUser.setOffice(result.get("OFFICE").toString());
            sessUser.setPosition(result.get("POSITION").toString());
            sessUser.setCompany(result.get("COMPANY").toString());
            sessUser.setPictureUrl(result.get("PICTUREURL").toString());
            if (result.get("LASTLOGIN") != null) {
				try {
					sessUser.setLastLogin(format.parse(result.get("LASTLOGIN").toString()));
				} catch (ParseException e) {
					sessUser.setLastLogin(Calendar.getInstance().getTime());
				}
			}            sessUser.setId((Long) result.get("ID"));
            return sessUser;
		} catch (UsernameNotFoundException ex1) {
			_log.error(ex1);
		    throw ex1;
		} catch (DataAccessException ex2) {
			_log.error(ex2);
		    throw ex2;
		}
	}
}
