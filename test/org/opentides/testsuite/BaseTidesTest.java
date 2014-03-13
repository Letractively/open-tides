/**
 * 
 */
package org.opentides.testsuite;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.jpa.AbstractJpaTests;

/**
 * This is the base class for all service level test class.
 * 
 * @author allantan
 *
 */
@SuppressWarnings("deprecation")
public class BaseTidesTest extends AbstractJpaTests {

    private String datasetPath = "test/xml/";
    protected String datasetName;
    /**
     * Default constructor.
     * Initializes autowiring by name.
     */
    public BaseTidesTest() {
        super();
        datasetName = datasetPath + getClass().getSimpleName() + ".xml";
        setAutowireMode(AUTOWIRE_BY_NAME);
    }
    
    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[] { 
                "file:WebContent/WEB-INF/tides/tidesContext-*.xml",
                "file:WebContent/WEB-INF/tides/testConfig.xml" 
              };
    }
    
    @Override
    @Before
    protected void onSetUpInTransaction() throws Exception {
        super.onSetUpInTransaction();
        executeDatabaseOperation(DatabaseOperation.CLEAN_INSERT, datasetName);
    }

    @Override
    @After
    protected void onTearDown() throws Exception {
        super.onTearDown();
        executeDatabaseOperation(DatabaseOperation.DELETE_ALL, datasetName);
    }
    
    /**
     * for deleting/loading dbunit datasetXML
     * 
     * @param databaseOperation
     * @param dataSetXML
     * @throws DatabaseUnitException
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException
     */
    protected void executeDatabaseOperation(DatabaseOperation databaseOperation,
            String dataSetXML) throws DatabaseUnitException, SQLException,
            FileNotFoundException, IOException {
        File datasetFile = new File(dataSetXML);
        if (datasetFile.exists()) {
            DataSource ds = jdbcTemplate.getDataSource();
            Connection con = DataSourceUtils.getConnection(ds);
            IDatabaseConnection dbUnitCon = new DatabaseConnection(con);
            DatabaseConfig config = dbUnitCon.getConfig();
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
            IDataSet dataSet = new FlatXmlDataSetBuilder().build(datasetFile);
            databaseOperation.execute(dbUnitCon, dataSet);
            DataSourceUtils.releaseConnection(con, ds);
        } else {
            System.out.println("No initial test data loaded.");
        }
    }
    
    @Test
    public void testEmpty() {
    	// this is just to avoid JUnit warning
    }
}
