


<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>

	<td colspan="4">

	<ot:form_title isNew="${userDefinedField.isNew}" formName="user-defined-field"/>
	<div class="content">	
		<form:form commandName="userDefinedField" id="user-defined-field-form-${userDefinedField.id}" action="">
		<spring:bind path="userDefinedField.*">
		<c:if test="${status.error}">
			<div class='errorBox'>
				<!-- this is a hack to crud.js to handle validation messages. -->
				<c:if test="${userDefinedField.isNew}">
				<!-- <tr id="user-defined-field-row-new" -->
				</c:if>
				<form:errors path="*" />
            </div>
		</c:if>
		</spring:bind>
		<div class='fieldsBox'>
			<div class="form-row">
                <label for="clazz"><spring:message code="label.user-defined-field.clazz" />: </label>
            	<form:select path="clazz" multiple="false"  ><form:option value="" /><form:options items="${clazzList}"/></form:select> <sup class="required">*</sup>
            </div>
			<div class="form-row">
                <label for="userField"><spring:message code="label.user-defined-field.userField" />: </label>
	            <form:select path="userField" multiple="false"  ><form:option value="" /><form:options items="${userFieldList}" /></form:select> <sup class="required">*</sup>
            </div>
	        <div class="form-row">
                <label for="label"><spring:message code="label.user-defined-field.label" />: </label>
	            <form:input path="label" /> <sup class="required">*</sup>
            </div>
            <div class="form-row">
                <label for="label"><spring:message code="label.user-defined-field.order" />: </label>
                <form:select path="order">
                    <c:forEach begin="1" end="50" step="1" var="count">
                      <form:option value="${count}" label="${count}" />
                    </c:forEach>
                </form:select>
            </div>
            <div class="form-row">
                <label for="label"><spring:message code="label.user-defined-field.reference" />: </label>
                <form:input path="reference" /> For date, specify date pattern. For number, specify fraction. For dropdown, specify SystemCodes Category.
            </div>            
            <div class="form-row">
                <label for="label"><spring:message code="label.user-defined-field.condition" />: </label>
                <form:input path="condition" /> (e.g. obj.caseCategory.value="Adjudication")
            </div>
            <div class="form-row" >
                <label for="label" class="special">&nbsp;</label>
                <form:checkbox path="searchable" /> <spring:message code="label.user-defined-field.is-searchable" />
            </div>
            <div class="form-row" >
                <label for="label" class="special">&nbsp;</label>
                <form:checkbox path="listed" /> <spring:message code="label.user-defined-field.is-listed-in-search-results" />
            </div>

		</div>
	</form:form>
		<div class="button">
	  		<ot:submit_button id="${userDefinedField.id}" page="admin/user-defined-field.jspx" formName="user-defined-field-form-${userDefinedField.id}" prefix="user-defined-field" />
  			<ot:cancel_button id="${userDefinedField.id}" page="admin/user-defined-field.jspx" prefix="user-defined-field" />
		</div>
		
	</div>

	</td> 

