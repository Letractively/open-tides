package org.opentides.service;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.opentides.bean.PasswordReset;
import org.opentides.persistence.PasswordResetDAO;
import org.opentides.persistence.UserDAO;
import org.opentides.testsuite.BaseTidesTest;

public class UserServiceTest extends BaseTidesTest {
	
	private UserService userService;
	private UserDAO 	userDAO;
	private PasswordResetDAO passwordResetDAO;
		
	@Test
	public final void testGetRoles() {
		Map<String,String> roles = userService.getRoles();
		assertEquals(2,roles.size());
		assertTrue(roles.containsValue("Administrator"));
		assertTrue(roles.containsValue("Guest"));
	}
	
	
	@Test 
	public void testRequestPasswordReset() {
		String email = "admin@ideyatech.com";
		userService.requestPasswordReset(email);
		PasswordReset example = new PasswordReset();
		example.setEmailAddress(email);
		example.setStatus(PasswordReset.STATUS_ACTIVE);
		assertNotSame(0, passwordResetDAO.countByExample(example,true));
		try {
			userService.requestPasswordReset("garbage@bin.com");
			fail("No exception thrown on missing email address");
		} catch (RuntimeException re) {	
		}
	}
	
	@Test
	public void testConfirmPasswordResetByEmailToken() {
        String email = "admin@ideyatech.com";
        userService.requestPasswordReset(email);
		PasswordReset example = new PasswordReset();
		example.setEmailAddress(email);
		example.setStatus(PasswordReset.STATUS_ACTIVE);
		List<PasswordReset> actuals = passwordResetDAO.findByExample(example, true);
		PasswordReset actual = actuals.get(0);
		assertFalse(userService.confirmPasswordReset(email, "dummy"));
		assertTrue(userService.confirmPasswordReset(email, actual.getToken()));
	}

	@Test
	public void testConfirmPasswordResetByCipher() {
		String email = "admin@ideyatech.com";
        userService.requestPasswordReset(email);
		PasswordReset example = new PasswordReset();
		example.setEmailAddress(email);
		example.setStatus(PasswordReset.STATUS_ACTIVE);
		List<PasswordReset> actuals = passwordResetDAO.findByExample(example, true);
		PasswordReset actual = actuals.get(0);
		PasswordReset dummy = new PasswordReset();
		dummy.setCipher("dummy");
		assertFalse(userService.confirmPasswordResetByCipher(dummy));
		assertTrue(userService.confirmPasswordResetByCipher(actual));
	}
	
	public void testResetPasswordFail() {
		String email = "admin@ideyatech.com";
		PasswordReset dummy = new PasswordReset();
		dummy.setEmailAddress(email);
		dummy.setToken("dummy");
		assertFalse(userService.resetPassword(dummy));
	}
	
	@Test
	public void testResetPassword() {
		String email = "admin@ideyatech.com";
        userService.requestPasswordReset(email);
		PasswordReset example = new PasswordReset();
		example.setEmailAddress(email);
		example.setStatus(PasswordReset.STATUS_ACTIVE);
		List<PasswordReset> actuals = passwordResetDAO.findByExample(example, true);
		PasswordReset actual = actuals.get(0);
		long actualId = actual.getId();		
		actual.setPassword("tennis");
		assertTrue(userService.resetPassword(actual));
		// password reset must be updated to used
		PasswordReset newActual = passwordResetDAO.loadEntityModel(actualId);
		assertSame(PasswordReset.STATUS_USED, newActual.getStatus());
		// and password must change 
		// but there is no way to get password anymore.
//		BaseUser user = userDAO.loadByEmailAddress(email);
//		assertEquals("", user.getCredential().getPassword());
	}

	/**
	 * @param passwordResetDAO the passwordResetDAO to set
	 */
	public void setPasswordResetDAO(PasswordResetDAO passwordResetDAO) {
		this.passwordResetDAO = passwordResetDAO;
	}
	
	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @param userDAO the userDAO to set
	 */
	public void setCoreUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
}
