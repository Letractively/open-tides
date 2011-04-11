<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="idy" uri="http://www.ideyatech.com/tides"%>

<td colspan="4">
<form:form commandName="usergroup" id="usergroup-form-${usergroup.id}" action="" cssClass="user">
	<idy:form_title isNew="${usergroup.isNew}" formName="usergroup"/>
	<div class="content">	
		<spring:bind path="usergroup.*">
		<c:if test="${status.error}">
			<!-- this is a hack to crud.js to handle validation messages. -->
			<c:if test="${usergroup.isNew}">
			<!-- <tr id="usergroup-row-new"> -->
			</c:if>			
			<div class='errorBox'>
				<form:errors path="*" />
            </div>
		</c:if>
		</spring:bind>
    	<p class="name"> <label for="name"><spring:message code="label.name" /></label>
			<form:input path="name" size="40" maxlength="100" /> </p>
		<p class="description"> <label for="description"><spring:message code="label.description" /></label>
			<form:input path="description" size="40" maxlength="100" /> </p>
		<p  class="roles">	<label for="roles"><spring:message code="label.roles" /></label>		
		<c:forEach items="${roles}" var="role">
            <span class="role-checkbox">
			<form:checkbox path="roleNames" value="${role.key}"/>
			<c:out value="${role.value}"/>
            </span>
		</c:forEach>
		</p>
		<div class="button">
		  	<idy:submit_button id="${usergroup.id}" page="admin/usergroup.jspx" formName="usergroup-form-${usergroup.id}" prefix="usergroup"/>
  			<idy:cancel_button id="${usergroup.id}" page="admin/usergroup.jspx" prefix="usergroup"/>
		</div>
	</div>
</form:form>
</td>