package org.opentides.persistence;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.opentides.bean.user.BaseUser;
import org.opentides.bean.user.UserCredential;
import org.opentides.bean.user.UserGroup;
import org.opentides.testsuite.BaseTidesTest;


/**
 * This unit test checks BaseUserDAO functions.
 * @author allanctan
 *
 */
public class UserDAOTest extends BaseTidesTest {

	private UserDAO coreUserDAO;
	private UserGroupDAO userGroupDAO;
    private UserGroup group1;
	private UserGroup group2;
	private BaseUser user;

    @Override
    @Before
    protected void onSetUpInTransaction() throws Exception {
        super.onSetUpInTransaction();
        group1 = new UserGroup();
        group1.setId(981L);
        group1.setName("group1");
        group1.setDescription("Group 1");

        group2 = new UserGroup();
        group2.setId(982L);
        group2.setName("group2");
        group2.setDescription("Group 2");
        
        userGroupDAO.saveEntityModel(group1);
        userGroupDAO.saveEntityModel(group2);

    }
	
	@Test
	public void testAddUser() {
		BaseUser user = new BaseUser();
		user.addGroup(group1);
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setEmailAddress("johndoe@unittest.com");
		UserCredential userAccount = new UserCredential();
		userAccount.setPassword("unittest");
		userAccount.setUsername("jdoe");
		userAccount.setUser(user);
		user.setCredential(userAccount);
		coreUserDAO.saveEntityModel(user);
		// ensure user is saved and has id
		assertNotNull(user.getId());
		assertEquals( 1, jdbcTemplate.queryForInt("select count(*) from USER_PROFILE where EMAIL='johndoe@unittest.com'"));
		long userId = user.getId();
		
		Map result = jdbcTemplate.queryForMap("select * from USER_PROFILE where ID=?", new Object[]{userId});
		assertEquals("John",result.get("FIRSTNAME"));
		assertEquals("Doe",result.get("LASTNAME"));
		assertEquals("johndoe@unittest.com",result.get("EMAIL"));

		Map cred = jdbcTemplate.queryForMap("select * from USERS where USERID=?", new Object[]{userId});
		assertEquals("jdoe",cred.get("USERNAME"));
		assertEquals("unittest",cred.get("PASSWORD"));	
		
		// add user group
		BaseUser testUser = coreUserDAO.loadEntityModel(user.getId());
		Set<UserGroup> groups = testUser.getGroups();
		assertNotNull(groups);
		assertTrue(groups.size() == 1);
		assertTrue(groups.iterator().next().getId().equals(group1.getId()));
		
		// remove user group
		testUser.removeGroup(group1);
		coreUserDAO.saveEntityModel(testUser);
		BaseUser testUser2 = coreUserDAO.loadEntityModel(testUser.getId());
		assertTrue(testUser2.getGroups().size() == 0);
		
		// remove user
		int id = jdbcTemplate.queryForInt("select id from USER_PROFILE where EMAIL='johndoe@unittest.com'");
		coreUserDAO.deleteEntityModel(user.getId());
		assertEquals(0, jdbcTemplate.queryForInt("select count(*) from USER_PROFILE where ID="+user.getId()));
		assertEquals(0, jdbcTemplate.queryForInt("select count(*) from USERS where USERID="+user.getId()));

	}
	
	@Test
	public void testIsRegisteredByEmail() {
	    assertEquals(true, coreUserDAO.isRegisteredByEmail("admin@ideyatech.com"));
        assertEquals(false, coreUserDAO.isRegisteredByEmail("test@missing.com"));
	}
	
	@Test
    public void testLoadByUsername() {
	    BaseUser user = coreUserDAO.loadByUsername("admin");
        assertEquals("SuperAdmin", user.getFirstName());
        assertEquals("User", user.getLastName());        
        assertEquals(null, coreUserDAO.loadByUsername("missing"));
		assertNull(coreUserDAO.loadByUsername(""));
		assertNull(coreUserDAO.loadByUsername(null));
        
    }
	
	@Test
    public void testLoadByEmailAddress() {
	    BaseUser user = coreUserDAO.loadByEmailAddress("admin@ideyatech.com");
	    assertEquals("SuperAdmin", user.getFirstName());
        assertEquals("User", user.getLastName());	    
    }
	
    @Test
	public void testFindByUsergroupName() {
    
        // a user can be member of multiple user group
        user = new BaseUser();
        user.addGroup(group1);
        user.addGroup(group2);
        user.setEmailAddress("multiple@unittest.com");
        UserCredential userAccount = new UserCredential();
        userAccount.setPassword("test");
        userAccount.setUsername("multiple");
        userAccount.setUser(user);
        user.setCredential(userAccount);
        coreUserDAO.saveEntityModel(user);
        
        List<BaseUser> users1 = coreUserDAO.findByUsergroupName("group1");
        assertEquals(1, users1.size());
        assertEquals(user, users1.get(0));
        
        List<BaseUser> users2 = coreUserDAO.findByUsergroupName("group2");
        assertEquals(1, users2.size());
        assertEquals(user, users2.get(0));
    }
	
	@Test
	public void testUniqueUsernameConstraints() {
		try {
			BaseUser user = new BaseUser();
			user.addGroup(group1);
			user.setFirstName("SuperUser");
			user.setLastName("Duplicate");
			user.setEmailAddress("duplicate@unittest.com");
			UserCredential userAccount = new UserCredential();
			userAccount.setPassword("unittest");
			userAccount.setUsername("admin");
			userAccount.setUser(user);
			user.setCredential(userAccount);
			coreUserDAO.saveEntityModel(user);
			fail("Failed to throw exception on duplicate username");
		} catch (Exception e) {
			// success
		}
	}
	
	@Test
	public void testUniqueEmailConstraints() {
		try {
			BaseUser user = new BaseUser();
			user.addGroup(group1);
			user.setFirstName("SuperUser");
			user.setLastName("Duplicate");
			user.setEmailAddress("admin@ideyatech.com");
			UserCredential userAccount = new UserCredential();
			userAccount.setPassword("unittest");
			userAccount.setUsername("admin");
			userAccount.setUser(user);
			user.setCredential(userAccount);
			coreUserDAO.saveEntityModel(user);
			fail("Failed to throw exception on duplicate email");
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void testCountAll() {
		long cnt = coreUserDAO.countAll();
		int cnt2 = jdbcTemplate.queryForInt("select count(*) from USER_PROFILE");
		assertEquals("Mismatch on count all.", cnt2, (int) cnt);			
	}
	
	@Test
	public void testIsRegistered() {
		assertTrue(coreUserDAO.isRegisteredByEmail("admin@ideyatech.com"));
		assertFalse(coreUserDAO.isRegisteredByEmail("none@nothing.com"));
		assertFalse(coreUserDAO.isRegisteredByEmail(""));
		assertFalse(coreUserDAO.isRegisteredByEmail(null));
	}

	/**
	 * Setter method for coreUserDAO.
	 *
	 * @param coreUserDAO the coreUserDAO to set
	 */
	public final void setCoreUserDAO(UserDAO coreUserDAO) {
		this.coreUserDAO = coreUserDAO;
	}

	/**
	 * Setter method for userGroupDAO.
	 *
	 * @param userGroupDAO the userGroupDAO to set
	 */
	public final void setUserGroupDAO(UserGroupDAO userGroupDAO) {
		this.userGroupDAO = userGroupDAO;
	}
}
