
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
[multipart_td colspan="4"]
<form:form commandName="report" id="report-form-${report.id}" action="" enctype="multipart/form-data">
	<ot:form_title isNew="${report.isNew}" formName="report"/>
	<div class="content">	
		<spring:bind path="report.*">
		<c:if test="${status.error}">
			<div class='errorBox'>
				<!-- this is a hack to crud.js to handle validation messages. -->
				<c:if test="${report.isNew}">
				<!-- <tr id="report-row-new" -->
				</c:if>
				<form:errors path="*" />
            </div>
		</c:if>
		</spring:bind>
		<div class='fieldsBox'>
			<p><label for="name"><spring:message code="label.report.name" />: </label>
	<form:input path="name" /> <sup class="required">*</sup></p>

			<p><label for="description"><spring:message code="label.report.description" />: </label>
	<form:textarea path="description" rows="3" cols="20"  /> </p>

			<p><label for="reportGroup"><spring:message code="label.report.reportGroup" />: </label>
	<form:select path="reportGroup">
            	<form:option value="" label="" />
            	<form:options items="${reportGroupList}" itemValue="id" itemLabel="value"/>
            </form:select>  <sup class="required">*</sup></p>
	
			<p><label for="accessCode"><spring:message code="label.report.accessCode" />: </label>
	<form:input path="accessCode" /> </p>

			<p><label for="screenshot"><spring:message code="label.report.screenshot" />: </label>
	<spring:bind path="screenshot">
		<input type="file" name="attachment"/>
	</spring:bind> (150 x 100)<br/>
	<c:forEach items = "${report.files}" var="fileInfo">
		<img src="view-image.jspx?FileInfoId=${fileInfo.id}" width="150"/>
	</c:forEach>
			</p>
			<p>
		<label for="jasperFile"><spring:message code="label.report.jasper-file" />: </label>
		<input type="file" name="jasperFile"/>
			</p>
			<p>
		<c:if test="${not empty report.reportFile}">
			<label for="jasperFile"><spring:message code="label.report.current-file" />: </label>
			${report.reportFile}.jasper
		</c:if>
			</p>
			<p><label for="jrxmlFile"><spring:message code="label.report.jrxml-file" />: </label>
		<input type="file" name="jrxmlFile"/>
		<spring:message code="label.must-be-same-name-with-jasper-file" />
			</p>
			<p>
		<c:if test="${not empty report.reportFile}">
			<label for="jrxmlFile"><spring:message code="label.report.current-file" />: </label>
			${report.reportFile}.jrxml
		</c:if>
			</p>

		</div>
		<div class="requiredInfo">
			<sup class="required">*</sup> Indicates required field.
		</div>
				<div class="button">
		  	<ot:submit_button id="${report.id}" page="admin/report.jspx" formName="report-form-${report.id}" prefix="report" multipart="true"/>
  			<ot:cancel_button id="${report.id}" page="admin/report.jspx" prefix="report"/>
		</div>
	</div>
</form:form>
[/multipart_td] 