

# 1 Introduction #
> The goal of this tutorial is to demonstrate to software developers and aspiring programmers how quick and easy it is to create a simple java application using the Open-Tides framework.

> This tutorial uses the Apache Ant build tool within the Eclipse IDE. If another build tool or IDE is to be used, adjust accordingly.

# 2 Creating A Simple Application #
> This section describes the procedures for creating a simple application. The Open-tides framework makes creating an application a three-step process: create the java bean, add the annotations, and generate the CRUD pages.

> As an example, let us create a simple application that handles employee management—creating, reading, updating and deleting employees.
## 2.1 Creating the Beans ##
> Create the java bean packages, if needed. Create the beans.

> In the employee management example, we create the employee bean.
    1. Create the java bean that extends the BaseEntity superclass and implements the BaseCriteria interface. Your bean should generally look like this:
```
package com.otdefault.bean;

import org.opentides.bean.BaseCriteria;
import org.opentides.bean.BaseEntity;

public class Employee extends BaseEntity implements BaseCriteria {

}
```
    1. Define the fields of your bean and create getters and setters for each one.
```
private String employeeId;
private String fname;
private String lname;
private String gender;
private String civilStatus;

// getters and setters of the fields go here
```

## 2.2 Adding annotations ##
> The Open-tides framework provides a set of annotations to easily and quickly apply the appropriate behaviors to particular bean field types. Refer to the javadoc API documentation for more details.

> In our example, we distinguish the fields as follows:
    * employeeId, fname and lname are all of TextField types,
    * gender is a RadioButton type, and
    * civilStatus is a DropDown type.
> To properly annotate the bean, we do the following:
    1. Add the standard JPA annotations needed by your bean.
    1. Add the Open-Tides annotations below to set the bean to automatically create the DAO, service, controller and JSP page of the bean.
      * @Dao
        * This creates the dao and dao implementation classes for the bean. In the example above, the EmployeeDao.class and EmployeeJpaDaoImpl.class are created.
      * @Service
        * This creates the service and service implementation classes for the bean. In the example above, the EmployeeService.class and EmployeeServiceImpl.class are created.
      * @Controller
        * This creates the controller classes for the bean. In the example above, the EmployeeController.class is created.
      * @Page
        * This creates the form, list and refresh pages for the bean. In the example above, the employee-form.jsp, employee-list.jsp and employee-refresh.jsp pages are created.
```
...
...
import javax.persistence.ManyToOne;
import org.hightides.annotations.DropDown;
import org.hightides.annotations.RadioButton;
import org.hightides.annotations.TextField;
...
...
@Dao
@Page
@Service
@Controller
@SuppressWarnings("serial")
public class Employee extends BaseEntity implements BaseCriteria{

@TextField
private String employeeId;

@TextField (requiredField=true, label="First Name")
private String fname;

@TextField (requiredField=true, label="Last Name")
private String lname;

@ManyToOne
@RadioButton (options={"male", "female"})
private String gender;

@ManyToOne
@DropDown (category="SYSTEMCODES_STATUS")
private String civilStatus;
...
```
## 2.3 Generating CRUD pages ##
> Once the bean is created and the proper annotations are in place, the final step is to generate the CRUD pages. To do so, you only need to do two major tasks:
    1. Include the servlet-api.jar library in the Java Build Path of the project, if it has not yet been included. Ot-default requires HttpServletRequest.class, which is located within that library. Alternatively, the Apache Tomcat Server Runtime library contains that necessary file, and that whole .jar file can be included instead.
    1. Open build.xml using the APACHE ANT build tool and run generate.
      * or Open the APACHE ANT VIEW, click on ADD BUILDFILES and choose build.xml under OT-DEFAULT.
> After the code generation is finished, your simple application is ready to run.