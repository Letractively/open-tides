/**
 * 
 */
package org.opentides.persistence.evolve;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.opentides.InvalidImplementationException;
import org.opentides.testsuite.BaseTidesTest;
import org.springframework.test.context.transaction.BeforeTransaction;

/**
 * @author allantan
 *
 */
public class DBEvolveManagerTest extends BaseTidesTest {

    private DBEvolveManager manager;
    private List<DBEvolve> testList;
  
    
    @Override
    @BeforeTransaction
    protected void onSetUpInTransaction() throws Exception {
        super.onSetUpInTransaction();
        manager = (DBEvolveManager) applicationContext.getBean("evolveManager");
        testList = new ArrayList<DBEvolve>();
        testList.add(new TestEvolve(1));
        testList.add(new TestEvolve(2));
        testList.add(new TestEvolve(3));
        manager.setEvolveList(testList);
    }

    /**
     * Test method for {@link org.opentides.persistence.evolve.DBEvolveManager#evolve()}.
     */
    @Test
    public void testEvolveFromEmpty() {
       // delete the version
       jdbcTemplate.execute("DELETE FROM SYSTEM_CODES where KEY_='DB_VERSION'");
       // check for version 1
       manager.evolve();
       long dbVersion = jdbcTemplate.queryForLong("SELECT NUMBER_VALUE FROM SYSTEM_CODES WHERE KEY_='DB_VERSION'");
       assertEquals(3, dbVersion);
       for (DBEvolve evolve:testList) {
           assertTrue(((TestEvolve)evolve).isExecuted());
       }
       // execute another evolve, no change expected.
       manager.evolve();
       dbVersion = jdbcTemplate.queryForLong("SELECT NUMBER_VALUE FROM SYSTEM_CODES WHERE KEY_='DB_VERSION'");
       assertEquals(3, dbVersion);
    }
    
    /**
     * Test method for {@link org.opentides.persistence.evolve.DBEvolveManager#evolve()}.
     */
    @Test
    public void testEvolveDuplicateException() {
       // delete the version
       jdbcTemplate.execute("DELETE FROM SYSTEM_CODES where KEY_='DB_VERSION'");
       // add duplicate evolve version
       testList.add(new TestEvolve(3));
       try {
           manager.evolve();
           fail("No exception thrown on duplicate version.");
       } catch (InvalidImplementationException e) {
           // exception thrown... correct behavior
       }
    }

}
