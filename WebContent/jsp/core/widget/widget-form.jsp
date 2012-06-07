<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>

<!--using  [multipart_td] [/multipart_td] tags for multipart form -->
[multipart_td colspan="8" class="remove-highlight"]
<form:form commandName="widget" id="widget-form-${widget.id}" action="" enctype="multipart/form-data">
	<ot:form_title isNew="${widget.isNew}" formName="widget"/>
	<div class="widget-form">	
		<br/>
		<idy:form-instruction formName="widgetForm"/>
		<spring:bind path="widget.*">
		<c:if test="${status.error}">
			<div class='error-box'>
				<!-- this is a hack to crud.js to handle validation messages. -->
				<c:if test="${widget.isNew}">
				<!-- <tr id="widget-row-new" -->
				</c:if>
				<form:errors path="*" />
            </div>
		</c:if>
		</spring:bind>
		<div class="highlight-error-box R">
	        <span class="required">*</span>
	        <span class="bold">Required Field</span>			
		</div>
		<div class='fieldsBox'>
			<div class="form-row"> 
                <form:label path="name" cssErrorClass="highlight-error"><spring:message code="label.widget.name" />: </form:label>
	            <form:input onblur="this.value=IDEYATECH.util.removeSpaces(this.value);" path="name" cssErrorClass="highlight-error"/> 
	            <sup class="required">*</sup>
	            <idy:tool-tip formName="widgetForm" attributeName="name"/>
            </div>
			
			<div class="form-row"> 
                <form:label path="title" cssErrorClass="highlight-error"><spring:message code="label.widget.title" />: </form:label>
	            <form:input path="title" cssErrorClass="highlight-error"/> 
	            <sup class="required">*</sup>
	            <idy:tool-tip formName="widgetForm" attributeName="title"/>
            </div>
            
            <div class="form-row"> 				
                <form:checkbox path="isShown" cssErrorClass="highlight-error"/>
                <form:label path="isShown" cssErrorClass="highlight-error"><spring:message code="label.widget.isShown" /></form:label>
                <idy:tool-tip formName="widgetForm" attributeName="isShown"/>
			</div>
			
			<div class="form-row"> 
                <form:label path="description" cssErrorClass="highlight-error"><spring:message code="label.widget.description" />: </form:label>
	            <form:textarea path="description" rows="3" cols="20" cssErrorClass="highlight-error"/>
	            <idy:tool-tip formName="widgetForm" attributeName="description"/> 
            </div>

			<div class="form-row"> 
                <form:label path="url" cssErrorClass="highlight-error"><spring:message code="label.widget.url" />: </form:label>
	            <form:input path="url" cssErrorClass="highlight-error"/> 
	            <sup class="required">*</sup>
	            <idy:tool-tip formName="widgetForm" attributeName="url"/>
            </div>

			<div class="form-row"> 
                <form:label path="accessCode" cssErrorClass="highlight-error"><spring:message code="label.widget.accessCode" />: </form:label>
	            <form:input path="accessCode" cssErrorClass="highlight-error"/>
	            <idy:tool-tip formName="widgetForm" attributeName="accessCode"/>
            </div>

			<div class="form-row"> 
                <form:label path="cacheDuration" cssErrorClass="highlight-error"><spring:message code="label.widget.cacheDuration" />: </form:label>
	            <form:input path="cacheDuration" cssErrorClass="highlight-error"/>
	            <idy:tool-tip formName="widgetForm" attributeName="cacheDuration"/> 
            </div>
	
			<div class="form-row"> 
                <form:label path="screenshot" cssErrorClass="highlight-error"><spring:message code="label.widget.screenshot" />: </form:label>
	            <spring:bind path="screenshot">
		            <input type="file" name="attachment"/>
	            </spring:bind> (150 x 100)<br/>
            	<c:forEach items = "${widget.files}" var="fileInfo">
            		<img src="view-image.jspx?FileInfoId=${fileInfo.id}" width="150"/>
            	</c:forEach>
            	<idy:tool-tip formName="widgetForm" attributeName="screenshot"/>
			</div>

			<div class="form-row"> 
                <form:label path="lastCacheUpdate" cssErrorClass="highlight-error"><spring:message code="label.widget.lastCacheUpdate" />: </form:label>
	            <form:input path="lastCacheUpdate" cssErrorClass="highlight-error"/>
	            <idy:tool-tip formName="widgetForm" attributeName="lastCacheUpdate"/> 
            </div>
            
			<form:hidden path="isUserDefined"  />
			
		</div>
		
		<div class="button">
		  	<ot:submit_button id="${widget.id}" page="admin/widget.jspx" formName="widget-form-${widget.id}" prefix="widget" multipart="true"/>
  			<ot:cancel_button id="${widget.id}" page="admin/widget.jspx" prefix="widget"/>
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
</multipart_td> 