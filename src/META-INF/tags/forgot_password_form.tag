<%--
	- forgot_password_form.tag
	- 
	- Displays the different forms used in forgot password process.
	- 
	- @param type - can be 'request', 'confirm', 'change'
	-
	--%>	
	

<%@ tag isELIgnored="false" body-content="empty" %>
<%@ attribute name="action" required="true" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${action == 'request'}">
    <form:form commandName="passwordReset">
		<spring:bind path="passwordReset.*">
		<c:if test="${status.error}">
            <div class='errorBox'>
                <form:errors path="*"/>
            </div>
        </c:if>
		</spring:bind>
        
        <div class="form-row">  
            <spring:message code="msg.forgot-password-instructions"/>
        </div>
	    <div class="form-row">  
            <label for="emailAddress"><spring:message code="label.email-address"/></label> &nbsp;
	    		<form:input path="emailAddress" size="40" maxlength="100" cssStyle="text"/>
	    		 <span class="required">*</span>
        </div>
	    <div class="form-row">  
            <label class="special" for="submit">&nbsp;</label><input type="submit" value="Submit"> &nbsp; <input type="button" value="Cancel" onclick="window.location='<c:url value='/home.jspx'/>'">
        </div>
    </form:form>
</c:if> 

<c:if test="${action == 'confirm'}">
    <form:form commandName="passwordReset">
		<spring:bind path="passwordReset.*">
		<c:if test="${status.error}">
            <div class='errorBox'>
                <form:errors path="*"/>
            </div>
        </c:if>
		</spring:bind>
        <div class="form-row">  
            <spring:message code="msg.confirm-password-reset-instructions"/>
        </div>
	    <div class="form-row">  
            <label for="emailAddress"><spring:message code="label.email-address"/></label><form:input path="emailAddress" size="40" maxlength="100" cssStyle="text"/>
        </div>
	    <div class="form-row">  
            <label for="token"><spring:message code="label.confirmation-code"/></label><form:input path="token" size="40" maxlength="100" cssStyle="text" />
        </div> 
	    <div class="form-row">  
            <label for="submit">&nbsp;</label><input type="submit" value="Submit">
        </div> 
    </form:form>
</c:if>

<c:if test="${action == 'change'}">
    <form:form commandName="passwordReset" action="change-password.jspx">
		<spring:bind path="passwordReset.*">
		<c:if test="${status.error}">
            <div class='errorBox'>
                <form:errors path="*"/>
            </div>
        </c:if>
		</spring:bind>
    	<form:hidden path="emailAddress"/>
        <div class="form-row">
            <spring:message code="msg.change-password-instructions"/>
        </div>
	    <div class="form-row">  <label for="emailAddress"><spring:message code="label.email-address"/></label> ${passwordReset.emailAddress}</div>
	    <div class="form-row">  <label for="password"><spring:message code="label.password"/></label><form:password path="password" size="40" maxlength="100" cssStyle="text"/></div>
	    <div class="form-row">  <label for="confirmPassword"><spring:message code="label.confirm-password"/></label><form:password path="confirmPassword" size="40" maxlength="100"  cssStyle="text"/></div>
	    <div class="form-row">  <label for="submit">&nbsp;</label><input type="submit" value="Submit"></div>
    </form:form>
</c:if>