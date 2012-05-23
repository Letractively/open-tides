<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>

<!--using  [multipart_td] [/multipart_td] tags for multipart form -->
[multipart_td colspan="8"]
<form:form commandName="widget" id="widget-form-${widget.id}" action="" enctype="multipart/form-data">
	<ot:form_title isNew="${widget.isNew}" formName="widget"/>
	<div class="content">	
		<spring:bind path="widget.*">
		<c:if test="${status.error}">
			<div class='errorBox'>
				<!-- this is a hack to crud.js to handle validation messages. -->
				<c:if test="${widget.isNew}">
				<!-- <tr id="widget-row-new" -->
				</c:if>
				<form:errors path="*" />
            </div>
		</c:if>
		</spring:bind>
		<div class='fieldsBox'>
			<div class="form-row"> 
                <label for="name"><spring:message code="label.widget.name" />: </label>
	            <form:input onblur="this.value=IDEYATECH.util.removeSpaces(this.value);" path="name" /> <sup class="required">*</sup>
            </div>
			
			<div class="form-row"> 
                <label for="title"><spring:message code="label.widget.title" />: </label>
	            <form:input path="title" /> <sup class="required">*</sup>
            </div>
            
            <div class="form-row"> 				
                <form:checkbox path="isShown" /><label for="isShown"><spring:message code="label.widget.isShown" /></label>
			</div>
			
			<div class="form-row"> 
                <label for="description"><spring:message code="label.widget.description" />: </label>
	            <form:textarea path="description" rows="3" cols="20"  /> 
            </div>

			<div class="form-row"> 
                <label for="url"><spring:message code="label.widget.url" />: </label>
	            <form:input path="url" /> <sup class="required">*</sup>
            </div>

			<div class="form-row"> 
                <label for="accessCode"><spring:message code="label.widget.accessCode" />: </label>
	            <form:input path="accessCode" />
            </div>

			<div class="form-row"> 
                <label for="cacheDuration"><spring:message code="label.widget.cacheDuration" />: </label>
	            <form:input path="cacheDuration" /> 
            </div>
	
			<div class="form-row"> 
                <label for="screenshot"><spring:message code="label.widget.screenshot" />: </label>
	            <spring:bind path="screenshot">
		            <input type="file" name="attachment"/>
	            </spring:bind> (150 x 100)<br/>
            	<c:forEach items = "${widget.files}" var="fileInfo">
            		<img src="view-image.jspx?FileInfoId=${fileInfo.id}" width="150"/>
            	</c:forEach>
			</div>

			<div class="form-row"> 
                <label for="lastCacheUpdate"><spring:message code="label.widget.lastCacheUpdate" />: </label>
	            <form:input path="lastCacheUpdate" /> 
            </div>

			<form:hidden path="isUserDefined"  />
		</div>
		<div class="requiredInfo">
			<sup class="required">*</sup> Indicates required field.
		</div>
		<div class="button">
		  	<ot:submit_button id="${widget.id}" page="admin/widget.jspx" formName="widget-form-${widget.id}" prefix="widget" multipart="true"/>
  			<ot:cancel_button id="${widget.id}" page="admin/widget.jspx" prefix="widget"/>
		</div>
	</div>
</form:form>
</multipart_td> 