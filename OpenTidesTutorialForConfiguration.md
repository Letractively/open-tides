

# 1 Introduction #

> The goal of this tutorial is to describe and demonstrate to software developers and aspiring programmers what configurations must be done in order for an application to run smoothly.

> This tutorial uses the Apache Ant build tool within the Eclipse IDE. If another build tool or IDE is to be used, adjust accordingly.


# 2 Configuration #

## 2.1 Import the Project Framework ##
> To set up the IDE, import the project framework first. To do so, do the following:
    1. Go to the FILE menu and click on IMPORT.
    1. Click on the GENERAL folder and then EXISTING PROJECTS INTO WORKSPACE.
    1. Click NEXT
    1. Click on SELECT ARCHIVE FILE and browse for the zipped file previously acquired.
    1. Once done, press FINISH.

## 2.2 Setup Apache Tomcat ##
> After importing the project framework, setup the Apache Tomcat server by adding it through . To do so, do the following:
    1. Click on the FILE menu.
    1. Choose NEW, then OTHER….
    1. In the Wizard, choose SERVER under the SERVER option.
      * Or you can go to the SERVER tab on the lower right section of your window, right click on the white space and choose NEW, then SERVER.
    1. Click NEXT.
    1. Define a new server.
      * Under SELECT THE SERVER TYPE, browse for Apache Tomcat 5.5 Server or later.
      * Leave the rest of the other values and click NEXT.
      * Click on BROWSE… to search for the Tomcat installation directory.
      * Once you’re done, click FINISH.
    1. Click Next to proceed to ADD OR REMOVE PROJECTS.
    1. Under AVAILABLE PROJECTS on the left, locate the ot-default project and click ADD to add the project to the list of CONFIGURED PROJECTS to the right.
    1. Click FINISH when done.
    1. The Tomcat Server will be configured and will be shown under the SERVERS tab.
> If the Servers tab is not displayed, enable it by going to WINDOW, SHOW VIEW, and then SERVERS.

## 2.3 Setup the SQL Schema ##
> After setting up the project and server configuration, the next step is to create the mySQL schema for your project.
    1. Open MySQL Query Browser.
    1. On the SCHEMATA pane on the right, right-click on the white space and choose CREATE NEW SCHEMA.
      * or Click on the white space under the SCHEMATA tab and press CTRL+N.
    1. Type the name of your schema. Take note of your schema name for later configurations.

## 2.4 Configure the Database Connection ##
> Configure your project by setting the datasource to obtain a connection to the database. There are two ways to configure the database connection—either by using the basic JDBC datasource configuration or by using the JNDI datasource configuration. By default, the datasource uses JNDI for its configuration.

### 2.4.1 Choose the datasource configuration ###
#### 2.4.1.1 Using the JNDI datasource configuration ####
> Since many Java EE containers typically supply DataSource instances via the Java Naming and Directory Interface (JNDI). Sometimes you are required to look-up a DataSource via JNDI.
> To configure the datasource as JNDI, do the following:
    1. Expand the necessary folders in the package explorer, to open the CONTEXT folder.
      * OT-DEFAULT > WEBCONTENT > WEB-INF > CONTEXT
    1. Open productionConfig.xml.
    1. Type the following:
```
  <bean id="dataSource" class="org.springframework.jndi.JndiObjectFactoryBean">
    <property name="jndiName">
      <value>${database.jndi}</value>
    </property>
  </bean>
```
    1. Go to the SERVERS folder in the root directory of the package explorer.
    1. Expand Tomcat v5.5 Server at localhost-config.
    1. Open server.xml.
    1. Proceed to the bottom of the page and search for the context tag that looks like this:
```
<Context docBase="ot-default" path="/ot-default" reloadable="true" source="org.eclipse.jst.j2ee.server:ot-default"/>
```
    1. Replace the context tag with the following:
```
<Context docBase="ot-default" path="/ot-default" reloadable="true" source="org.eclipse.jst.j2ee.server:ot-default">
  <Resource type="javax.sql.DataSource" auth="Container"
            maxActive="100" maxIdle="30" maxWait="10000"
            name="jdbc/ideyatech" driverClassName="com.mysql.jdbc.Driver"
            url="jdbc:mysql://localhost:3306/ot-default?autoReconnect=true"
            username="root" password="root"/>
</Context>
```
#### 2.4.1.2 Using the Basic JDBC datasource configuration ####
> To configure the datasource using the basic JDBC configuration, do the following:
    1. Expand the necessary folders in the package explorer, to open the CONTEXT folder.
      * OT-DEFAULT > WEBCONTENT > WEB-INF > CONTEXT
    1. Open productionConfig.xml.
    1. Type the following:
```
<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
  <property name="driverClassName" value="${database.driver}"/>
  <property name="url" value="${database.url}"/>
  <property name="username" value="${database.username}"/>
  <property name="password" value="${database.password}"/>
</bean>
```
### 2.4.2 Change the Default Values ###
> The Basic JDBC datasource connection configuration above uses EL expressions to populate its values. The Open-tides framework sets default values for these properties, as follows:
```
database.driver=com.mysql.jdbc.Driver
database.url=jdbc:mysql://localhost/ot-default
database.username=admin
database.password=admin
database.jndi=java:comp/env/jdbc/ideyatech
```
> These values are found in ideyatech.properties and can be customized for the project. This file is found under the resources package of the JAVA RESOURCES:SRC folder.

> In the ideyatech.properties file, scroll down to view the FOR DATABASE CONNECTION section and do the necessary modifications.

> Do not forget to modify the JDBC URL of the database under database-url to account for your schema by replacing the value of ot-default with your schema name.

## 2.5 Configure the Java Build Path ##
> Add the Java Virtual Machine (JVM) library on the build path. To do so,
    1. Go the PROJECT menu and then PROPERTIES.
    1. Click on the JAVA BUILD PATH selection on the left pane.
    1. Click on the LIBRARIES tab.
    1. Click on ADD LIBRARY…. If an existing JRE System Library has already been added prior to your configuration, but does not work properly, remove it and proceed to add the proper library.
    1. When asked what library type to add, choose JRE System Library.
    1. Click FINISH.

## 2.6 Configure the Server Launch Configuration ##
> To successfully connect to the database, the classpath of the server must be configured. To do so, do the following:
    1. On the SERVER tab, double click on the server used by your project to open up the server’s OVERVIEW.
    1. As an additional step, we modify the minimum time to complete server operations so as to avoid server timeouts later. To do so, click on the TIMEOUTS tab and increase the start value.
    1. Save the new settings before moving on.
    1. Next, click on the OPEN LAUNCH CONFIGURATION hyperlink under the GENERAL INFORMATION tab.
    1. Under the CLASSPATH tab, click on USER ENTRIES, and then on ADD JARS….
    1. Expand down the folders to open the LIB folder.
      * OT-DEFAULT > WEBCONTENT > WEB-INF > LIB
    1. Add the MySQL-java connector library.
      * In this example, choose mysql-connector-java-5.0.8-bin.jar and press OK.

# 3 Summary #
> In summary, here are the things that need to be configured for the project to successfully run.
    1. Import the Project.
    1. Add the Server and add the project to the server’s configuration.
    1. Create a new MySQL schema.
    1. Modify productionConfig.xml and server.xml to configure the datasource.
    1. Modify ideyatech.properties for the default username and password.
    1. Add the JRE System Library .jar file to the Java Build Path.
    1. Add the MySQL connector .jar file to the Classpath of the Server.