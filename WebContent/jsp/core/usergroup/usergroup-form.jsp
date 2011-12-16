<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="idy" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<td colspan="4" class="remove-highlight">
<form:form commandName="usergroup" id="usergroup-form-${usergroup.id}" action="" cssClass="user">
	<idy:form_title isNew="${usergroup.isNew}" formName="usergroup"/>
	<div class="content">	
		<spring:bind path="usergroup.*">
		<c:if test="${status.error}">
			<!-- this is a hack to crud.js to handle validation messages. -->
			<c:if test="${usergroup.isNew}">
			<!-- <tr id="usergroup-row-new"> -->
			</c:if>
			<div class='error-box'>
				<form:errors path="*" />
            </div>
		</c:if>
		</spring:bind>
    	<div class="form-row"> 
            <label for="name"><spring:message code="label.name" /></label>
			<form:input path="name" size="40" maxlength="100" /><span class="required">*</span>
        </div>
		<div class="form-row">
            <label for="description"><spring:message code="label.description" /></label>
			<form:input path="description" size="40" maxlength="100" /><span class="required">*</span>
        </div>
        <div class="form-row">
		<label for="roles"><spring:message code="label.roles" /></label>	        
        </div>
        <div class="form-row">
            <c:set var="prevRole" value=""/>	
            <ul id="roles-${usergroup.id}">
       		<c:forEach items="${roles}" var="role">
                <idy:close_list prev="${prevRole}" curr="${role.value}" />
                <idy:open_list prev="${prevRole}" curr="${role.value}" />
                <li class="closed"><form:checkbox path="roleNames" value="${role.key}" class="check"/> <c:out value="${fn:substring(role.value,8,-1)}"/>
                <c:set var="prevRole" value="${role.value}"/>
    		</c:forEach>
            </ul>
		</div>
		<div class="button">
		  	<idy:submit_button id="${usergroup.id}" page="admin/usergroup.jspx" formName="usergroup-form-${usergroup.id}" prefix="usergroup"/>
  			<idy:cancel_button id="${usergroup.id}" page="admin/usergroup.jspx" prefix="usergroup"/>
		</div>
	</div>
</form:form>
</td> 
<script language="javascript">
	$(document).ready(function() {
    	$("#roles-${usergroup.id}").treeview();
    	$("input.check").click(function() {
    	    if ($(this).is(":checked")) {
    	        // if checked, check all parents
    	        $(this).parents("li").children(".check").attr("checked","true");
    	    } else {
    	        // if unchecked
    	        var children = $(this).parent("li").find(".check");
    	        if (children.size() > 1) {
    				if (confirm('<spring:message code="msg.this-will-deselect-all-child-permissions-are-you-sure" />')) {
    					children.removeAttr("checked");    
    				}    	            
    	        }
    	    }
    	});
	});
	
</script>