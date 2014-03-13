package org.opentides.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import org.opentides.bean.user.UserGroup;
import org.opentides.bean.user.UserRole;
import org.opentides.testsuite.BaseTidesTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class UserGroupServiceTest extends BaseTidesTest {
	private UserGroupService userGroupService;
		
	public void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}	
	
	@SuppressWarnings("rawtypes")
	private static final class UserGroupsExtractor implements ResultSetExtractor{
		public Object extractData(ResultSet rs) throws SQLException,
				DataAccessException {
			rs.next();
			UserGroup ug = new UserGroup();
			ug.setDescription(rs.getString("description"));
			ug.setName(rs.getString("name"));
			return ug;
		}
	}
	
	/*@SuppressWarnings({ "unchecked", "deprecation" })
	public void testLoadUserGroupByName(){
		UserGroup expected = (UserGroup) jdbcTemplate.query("SELECT * FROM USERGROUP WHERE NAME='Super User'",new UserGroupsExtractor());
		UserGroup actual = userGroupService.loadUserGroupByName("Super User");
		
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getName(), actual.getName());
	}*/
	
	public void testSetAuthoritiesFromCsvFile() throws Exception {
		String file = this.getClass().getClassLoader().getResource("resources/UserGroup.csv").getPath();
		userGroupService.setAuthoritiesFromCsvFile(file);
		UserGroup userGroup = userGroupService.loadUserGroupByName("MIS");
		Set<UserRole> roles = userGroup.getRoles();
		assertEquals(4, roles.size());
	}
	
	
}
