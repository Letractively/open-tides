


<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>

	[multipart_td colspan="5" class="remove-highlight"]
	<form:form commandName="userDefinedField" id="user-defined-field-form-${userDefinedField.id}" action="">
		<ot:form_title isNew="${userDefinedField.isNew}" formName="user-defined-field"/>
		<div class='user-defined-field-form'>
		<idy:form-instruction formName="userDefinedFieldForm"/>
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
		<div class="highlight-error-box R">
	        <span class="required">*</span>
	        <span class="bold">Required Field</span>			
		</div>
		<div class="form-row">
           	<form:label path="clazz" cssErrorClass="highlight-error"><spring:message code="label.user-defined-field.clazz" />: </form:label>
           	<form:select path="clazz" multiple="false" cssErrorClass="highlight-error">
           		<form:option value="" />
           		<form:options items="${clazzList}"/>
           	</form:select> 
           	<sup class="required">*</sup>
           	<idy:tool-tip formName="userDefinedFieldForm" attributeName="clazz"/>
		</div>
		<div class="form-row">
			<form:label path="userField" cssErrorClass="highlight-error"><spring:message code="label.user-defined-field.userField" />: </form:label>
            <form:select path="userField" multiple="false" cssErrorClass="highlight-error">
            	<form:option value="" />
            	<form:options items="${userFieldList}" />
            </form:select> 
            <sup class="required">*</sup>
            <idy:tool-tip formName="userDefinedFieldForm" attributeName="userField"/>
		</div>
        <div class="form-row">
            <form:label path="label" cssErrorClass="highlight-error"><spring:message code="label.user-defined-field.label" />: </form:label>
         	<form:input path="label" cssErrorClass="highlight-error"/> 
         	<sup class="required">*</sup>
         	<idy:tool-tip formName="userDefinedFieldForm" attributeName="label"/>
        </div>
        <div class="form-row">
            <form:label path="order" cssErrorClass="highlight-error"><spring:message code="label.user-defined-field.order" />: </form:label>
            <form:select path="order" cssErrorClass="highlight-error">
                <c:forEach begin="1" end="50" step="1" var="count">
                  <form:option value="${count}" label="${count}" />
                </c:forEach>
            </form:select>
            <idy:tool-tip formName="userDefinedFieldForm" attributeName="order"/>
        </div>
        <div class="form-row">
            <form:label path="reference" cssErrorClass="highlight-error"><spring:message code="label.user-defined-field.reference" />: </form:label>
            <form:input path="reference" cssErrorClass="highlight-error"/> 
            <idy:tool-tip formName="userDefinedFieldForm" attributeName="reference"/>
            For date, specify date pattern. For number, specify fraction. For dropdown, specify SystemCodes Category.
        </div>            
        <div class="form-row">
            <form:label path="condition" cssErrorClass="highlight-error"><spring:message code="label.user-defined-field.condition" />: </form:label>
            <form:input path="condition" cssErrorClass="highlight-error"/> 
            <idy:tool-tip formName="userDefinedFieldForm" attributeName="condition"/>
            (e.g. caseCategory.title=="DARAB")
        </div>
        <div class="form-row" >
            <form:label path="searchable" class="special" cssErrorClass="highlight-error">&nbsp;</form:label>
            <form:checkbox path="searchable" cssErrorClass="highlight-error"/> <spring:message code="label.user-defined-field.is-searchable" />
            <idy:tool-tip formName="userDefinedFieldForm" attributeName="searchable"/>
        </div>
        <div class="form-row" >
            <form:label path="listed" class="special" cssErrorClass="highlight-error">&nbsp;</form:label>
            <form:checkbox path="listed" cssErrorClass="highlight-error"/> <spring:message code="label.user-defined-field.is-listed-in-search-results" />
            <idy:tool-tip formName="userDefinedFieldForm" attributeName="listed"/>
        </div>
        <div class="form-row" >
        	<form:label path="required" class="special" cssErrorClass="highlight-error">&nbsp;</form:label>
        	<form:checkbox path="required" cssErrorClass="highlight-error"/> <spring:message code="label.user-defined-field.is-required" />
            <idy:tool-tip formName="userDefinedFieldForm" attributeName="required"/>
        </div>
		<div class="form-row">
			<label class="special">&nbsp;</label>
		  		<ot:submit_button id="${userDefinedField.id}" page="admin/user-defined-field.jspx" formName="user-defined-field-form-${userDefinedField.id}" prefix="user-defined-field" />
	  			<ot:cancel_button id="${userDefinedField.id}" page="admin/user-defined-field.jspx" prefix="user-defined-field" />
			</div>
		</div>
		<script type="text/javascript">
			$(document).ready(function(){
			 $('.icon-information').jtooltip({
		  	     radius       : 5,                   // radius for rounded corners 
		  	     shadow       : "0 0 0 0 #666",      // tooltip shadow
		  	     delay        : 200,                 // delay time for tooltip to appear in ms
		  	     offset       : 2,                   // distance in pixel from element
		  	     p_offset     : 10,                  // pointer offset from border 
		  	     c_offset     : 10,                  // pointer offset from element corner
		  	     pointer      : 8,                   // pointer length 
		  	     border       : "1px solid #dadada", // border style
		  	     bgcolor      : "#dadada",           // background color
		  	     font         : "12px tahoma",       // font style
		  	     color        : "#464646",           // text colors
		  	     padding      : "10px",              // padding
		  	     mode		  : "tooltip"
		  	  });
			});
		</script>
	</form:form>
	[/multipart_td]

