<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>

<td colspan="4" class="remove-highlight">
<form:form commandName="systemCode" id="systemCode-form-${systemCode.id}" action="">
	<ot:form_title isNew="${systemCode.isNew}" formName="system-codes"/>
	
	<div class="system-codes-form">	
	<idy:form-instruction formName="systemCodesForm"/>
		<spring:bind path="systemCode.*">
		<c:if test="${status.error}">
			<div class='error-box'>
			<!-- this is a hack to crud.js to handle validation messages. -->
			<c:if test="${systemCode.isNew}">
			<!-- <tr id="system-row-new"> -->
			</c:if>
				<form:errors path="*" /> 
            </div>
		</c:if>
		</spring:bind>
		<div class="highlight-error-box R">
	        <span class="required">*</span>
	        <span class="bold">Required Field</span>			
		</div>
    	<div id="categorySelector" class="form-row">
        	<form:label path="category" cssErrorClass="highlight-error"><spring:message code="label.category" /></form:label>
    		<form:select id="categorySelect" path="category" onchange="javascript:checkNewCategory(true);" cssErrorClass="highlight-error">
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
            </form:select>
            <span class="required">*</span>
            <idy:tool-tip formName="systemCodesForm" attributeName="category"/>
        </div>
        
		<div class="form-row">  
            <form:label path="key" cssErrorClass="highlight-error"><spring:message code="label.key" /></form:label>
			<form:input path="key" maxlength="255" cssErrorClass="highlight-error"/>
			<span class="required">*</span>
			<idy:tool-tip formName="systemCodesForm" attributeName="key"/>
        </div>
        <div class="form-row"> 
		    <form:label path="value" cssErrorClass="highlight-error"><spring:message code="label.value" /></form:label>
			<form:input path="value" maxlength="128" cssErrorClass="highlight-error"/>
			<span class="required">*</span>
			<idy:tool-tip formName="systemCodesForm" attributeName="value"/>
        </div>
        <div class="form-row"> 
		    <form:label path="numberValue" cssErrorClass="highlight-error"><spring:message code="label.number-value" /></form:label>
			<form:input path="numberValue" cssErrorClass="highlight-error"/>
			<idy:tool-tip formName="systemCodesForm" attributeName="numberValue"/>
        </div>
		<div class="form-row"> 
            <form:label path="ownerOffice" cssErrorClass="highlight-error"><spring:message code="label.override-office" /> </form:label>
            <form:select path="ownerOffice" cssErrorClass="highlight-error">
                <form:option value=""></form:option>
	        	<form:options items="${officeList}" itemValue="key" itemLabel="value" />
			</form:select>
			<idy:tool-tip formName="systemCodesForm" attributeName="ownerOffice"/>
        </div>
		<div class="form-row">
			<label class="special">&nbsp;</label>
		  	<ot:submit_button id="${systemCode.id}" page="admin/system-codes.jspx" formName="systemCode-form-${systemCode.id}" prefix="system"/>
  			<ot:cancel_button id="${systemCode.id}" page="admin/system-codes.jspx" prefix="system"/>
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
</td>