<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>

[multipart_td colspan="4" class="remove-highlight"]
<form:form commandName="report" id="report-form-${report.id}" action="" enctype="multipart/form-data">
<ot:form_title isNew="${report.isNew}" formName="report"/>
	
    <div class="reports-form">	
    	<idy:form-instruction formName="reportForm"/>
		<spring:bind path="report.*">
		<c:if test="${status.error}">
			<div class='error-box'>
				<!-- this is a hack to crud.js to handle validation messages. -->
				<c:if test="${report.isNew}">
				<!-- <tr id="report-row-new" -->
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
            <form:label path="name" cssErrorClass="highlight-error"><spring:message code="label.report.name" /></form:label>
           	<form:input path="name" cssClass="L" cssErrorClass="highlight-error"/>
           	<span class="required">*</span>
            <idy:tool-tip formName="reportForm" attributeName="name"/>
        </div>

        <div class="form-row"> 
            <form:label path="title" cssErrorClass="highlight-error"><spring:message code="label.report.title" /></form:label>
            <form:input path="title" cssClass="L" cssErrorClass="highlight-error"/>
            <span class="required">*</span>
            <idy:tool-tip formName="reportForm" attributeName="title"/>
        </div>
        
        <div class="form-row"> 
            <form:label path="description" cssErrorClass="highlight-error"><spring:message code="label.report.description" /></form:label>
            <form:textarea path="description" rows="3" cols="20" cssErrorClass="highlight-error"/>
            <idy:tool-tip formName="reportForm" attributeName="description"/> 
        </div>
        
        <div class="form-row"> 
            <form:label path="reportGroup" cssErrorClass="highlight-error"><spring:message code="label.report.reportGroup" /></form:label>
            <form:select path="reportGroup" cssErrorClass="highlight-error">
            	<form:option value="" label="" />
            	<form:options items="${reportGroupList}" itemValue="id" itemLabel="value"/>
            </form:select> 
            <span class="required">*</span>
            <idy:tool-tip formName="reportForm" attributeName="reportGroup"/>
        </div>

        <div class="form-row"> 
            <form:label path="accessCode" cssErrorClass="highlight-error"><spring:message code="label.report.accessCode" /></form:label>
            <form:input path="accessCode" cssErrorClass="highlight-error"/>
            <idy:tool-tip formName="reportForm" attributeName="accessCode"/> 
        </div>

        <div class="form-row"> 
            <form:label path="screenshot" cssErrorClass="highlight-error"><spring:message code="label.report.screenshot" /></form:label>
            <spring:bind path="screenshot">
            <input type="file" name="attachment" id="screenshot"/>
            </spring:bind><span class="text-screenshot">(150 x 100)</span><br/>
            <c:forEach items = "${report.files}" var="fileInfo">
                <img src="view-image.jspx?FileInfoId=${fileInfo.id}" width="150"/>
            </c:forEach>
            <idy:tool-tip formName="reportForm" attributeName="screenshot"/>
        </div>
        
        <div class="form-row"> 
            <form:label path="jasperFile" cssErrorClass="highlight-error"><spring:message code="label.report.jasper-file" /></form:label>
            <input type="file" id="jasperFile" name="jasperFile" class="L"/>
            <span class="required">*</span>
            <idy:tool-tip formName="reportForm" attributeName="jasperFile"/>
        </div>
         
        <c:if test="${not empty report.reportFile}">
            <div class="form-row">
                <label for="jasperFile"><spring:message code="label.report.current-file" /></label>
                ${report.reportFile}.jasper
            </div>
        </c:if>
        
        <div class="form-row"> 
            <form:label path="jrxmlFile" cssErrorClass="highlight-error"><spring:message code="label.report.jrxml-file" /></form:label>
            <input type="file" id="jrxmlFile" name="jrxmlFile" class="L" />
            <span class="required">*</span>
            <span class="text-jasper-file"><spring:message code="label.must-be-same-name-with-jasper-file" /></span>
            <idy:tool-tip formName="reportForm" attributeName="jrxmlFile"/>
        </div>
        
        <c:if test="${not empty report.reportFile}">
            <div class="form-row"> 
                <label for="jrxmlFile"><spring:message code="label.report.current-file" /></label>
                ${report.reportFile}.jrxml
            </div>
        </c:if>
		<div class="form-row"> 
            <form:label path="orderNumber" cssErrorClass="highlight-error"><spring:message code="label.report.orderNumber" /></form:label>
           	<form:input path="orderNumber" cssClass="numbers L" cssErrorClass="highlight-error" cssStyle="min-width: 1em;" onkeydown="return restrictKeys(event);" maxlength="5"/>
            <idy:tool-tip formName="reportForm" attributeName="orderNumber"/>
        </div>
        <div class="form-row">
            <label class="special">&nbsp;</label>
            <ot:submit_button id="${report.id}" page="admin/report.jspx" formName="report-form-${report.id}" prefix="report" multipart="true"/>
            <ot:cancel_button id="${report.id}" page="admin/report.jspx" prefix="report"/>
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
	</div>

</form:form>
[/multipart_td]
 