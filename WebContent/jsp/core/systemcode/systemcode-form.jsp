<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>

<td colspan="4">
<form:form commandName="systemCode" id="systemCode-form-${systemCode.id}" action="">
	<ot:form_title isNew="${systemCode.isNew}" formName="system-codes"/>
	
	<div class="content">	
		<spring:bind path="systemCode.*">
		<c:if test="${status.error}">
			<div class='errorBox'>
			<!-- this is a hack to crud.js to handle validation messages. -->
			<c:if test="${systemCode.isNew}">
			<!-- <tr id="system-row-new"> -->
			</c:if>
				<form:errors path="*" />
            </div>
		</c:if>
		</spring:bind>
		<div class="fieldsBox">
    	<div id="categorySelector" class="form-row"> <label for="category"><spring:message code="label.category" /></label>
    		<select id="categorySelect" name="category" onchange="javascript:checkNewCategory(true);">
    			<option value="0">Select a Category</option>
            	<option value="0">-- New Category --</option>
			<c:forEach items="${categories}" var="record" varStatus="status">
				<c:choose><c:when test="${systemCode.category==record.category}">
					<option selected="selected">${record.category}</option>
				</c:when>
				<c:otherwise>
					<option>${record.category}</option>
				</c:otherwise>
				</c:choose>
            </c:forEach>
            </select></div>
		<div class="form-row">  
            <label for="key"><spring:message code="label.key" /></label>
			<form:input path="key" maxlength="20" />
        </div>
        <div class="form-row"> 
		    <label for="value"><spring:message code="label.value" /></label>
			<form:input path="value" maxlength="50" />
        </div>
		<div class="form-row"> 
            <label for="office"><spring:message code="label.override-office" /> </label>
            <form:select path="ownerOffice">
                <form:option value=""></form:option>
	        	<form:options items="${officeList}" itemValue="key" itemLabel="value" />
			</form:select>
        </div>
		</div>
		<div class="button">
		  	<ot:submit_button id="${systemCode.id}" page="admin/system-codes.jspx" formName="systemCode-form-${systemCode.id}" prefix="system"/>
  			<ot:cancel_button id="${systemCode.id}" page="admin/system-codes.jspx" prefix="system"/>
		</div>
	</div>
</form:form>
</td>