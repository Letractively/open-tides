package org.hightides.annotations.util;

import java.io.File;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class PackageUtilTest {

	@Before
	public void onSetUpInTransaction() throws Exception {
		PackageUtil.setBaseOutputPath("test/");
	}

	@Test
	public void testGetPackage() {
		PackageUtil packageUtil = new PackageUtil("resources/templates", "test");
		Assert.assertEquals("com.ideyatech", packageUtil.getPackage(new File("test/com/ideyatech/Test.xml")));
		try {
			Assert.assertEquals("com.ideyatech", packageUtil.getPackage(new File("src/com/ideyatech/Test.xml")));
			Assert.fail("Unexpected behavior on invalid path for PackageUtil.getPackage() method.");
		} catch (Exception e) {
			// do nothing
		} 
	}

}
