<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>

[multipart_td colspan="4" class="remove-highlight"]
<form:form commandName="usergroup" id="usergroup-form-${usergroup.id}" action="" cssClass="user">
	<ot:form_title isNew="${usergroup.isNew}" formName="usergroup"/>	
	<div class="usergroup-form">
	
		<idy:form-instruction formName="userGroupForm"/> 
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
		<div class="highlight-error-box R">
	        <span class="required">*</span>
	        <span class="bold">Required Field</span>			
		</div>
    	<div class="form-row"> 
            <form:label path="name" cssErrorClass="highlight-error"><spring:message code="label.name" /></form:label>
			<form:input path="name" size="40" maxlength="100" cssErrorClass="highlight-error"/><span class="required">*</span>
			<idy:tool-tip formName="userGroupForm" attributeName="name"/>
        </div>
		<div class="form-row">
            <form:label path="description" cssErrorClass="highlight-error"><spring:message code="label.description" /></form:label>
			<form:input path="description" size="40" maxlength="100" cssErrorClass="highlight-error"/><span class="required">*</span>
			<idy:tool-tip formName="userGroupForm" attributeName="description"/>
        </div>
		<div class="form-row">
            <label for="description"><spring:message code="label.copy-from" /></label>
            <input type="checkbox" id="enable-copy-${usergroup.id}"/>
            <select id="copy-roles-${usergroup.id}">
            	<option id=""> </option>
            	<c:forEach var="groupOptions" items="${userGroupList}">
            		<option value="${groupOptions.id}">${groupOptions.name}</option>
            	</c:forEach>
            </select>
        </div>
        <div id="roles-tree" class="form-row">
		<label for="roles"><spring:message code="label.roles" /></label>	        
        </div>
        <div class="form-row">
            <c:set var="prevRole" value=""/>	
            <ul id="roles-${usergroup.id}">
       		<c:forEach items="${roles}" var="role">
                <ot:close_list prev="${prevRole}" curr="${role.value}" />
                <ot:open_list prev="${prevRole}" curr="${role.value}" />
                <li class="closed"><form:checkbox path="roleNames" value="${role.key}" class="check ${role.key}"/> <c:out value="${fn:substring(role.value,11,-1)}"/>
                <c:set var="prevRole" value="${role.value}"/>
    		</c:forEach>
            </ul>
		</div>
		<div class="button">
		  	<ot:submit_button id="${usergroup.id}" page="admin/usergroup.jspx" formName="usergroup-form-${usergroup.id}" prefix="usergroup"/>
  			<ot:cancel_button id="${usergroup.id}" page="admin/usergroup.jspx" prefix="usergroup"/>
		</div>
	</div>
</form:form>
<script type="text/javascript">
	$(document).ready(function() {
		$('#copy-roles-${usergroup.id}').hide();
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
    	$('#enable-copy-${usergroup.id}').click(function() {
    		if($(this).attr('checked')) {
    			$('#copy-roles-${usergroup.id}').show();
    		} else {
    			$('#copy-roles-${usergroup.id}').hide();
    		}
    	});    	
    	$('#copy-roles-${usergroup.id}').change(function(){
    		// uncheck all checkboxes in this role
    		$('#roles-${usergroup.id} .check').removeAttr("checked");    
     		$.ajax({
				url : "roles-list.jspx?userGroupId="+$('#copy-roles-${usergroup.id}').val(),
				type : 'GET',
				dataType : "json",
				success : function(data) {
					$.each(data.roles, function(index, role) {
						$('#roles-${usergroup.id} .'+role).attr("checked","true");						
					});
				}
			});
     	});
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
[/multipart_td]