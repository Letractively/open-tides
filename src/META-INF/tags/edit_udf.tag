<%--
    - edit_udf
    - 
    - Displays the values of user defined fields.
    -
    - @param meta - the meta listing of udf 
    - @param object - the object containing udf
    - @param searchMode - if true, only searchable udf are displayed
    - @param prefix - insert prefix before each result is displayed
    - @param postfix - append postfix after each  result is displayed
    
    --%>
<%@ tag isELIgnored="false" body-content="empty"%>
<%@ attribute name="meta" required="true" type="java.util.List"%>
<%@ attribute name="object" required="true" type="org.opentides.bean.UserDefinable"%>

<%@ attribute name="searchMode" required="false" type="java.lang.Boolean" %>
<%@ attribute name="prefix" required="false" type="java.lang.String" %>
<%@ attribute name="postfix" required="false" type="java.lang.String" %>


<jsp:useBean id="crud" class="org.opentides.util.CrudUtil" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<c:forEach items="${meta}" var="field"> 
<c:if test="${(empty field.condition) || crud.evaluateExpression(object, field.condition)}">
<c:if test="${(not searchMode) || (searchMode && field.searchable)}">
    ${prefix} 
    <c:set var="fieldRef" value="udf.${field.userField}"/>
    <label>${field.label}</label>
    <c:choose>
        <c:when test="${fn:startsWith(field.userField, 'string')==true}">            
        <input type="text" name="${fieldRef}" id="${fieldRef}" 
            value="${crud.retrieveObjectValue(object, fieldRef)}"/> 
        </c:when>
        <c:when test="${fn:startsWith(field.userField, 'date')==true}">
        <div class="L">
            <a id="show${field.userField}" title="${field.label}" class="date-picker">
            	<fmt:formatDate value="${crud.retrieveObjectValue(object, fieldRef)}" pattern="MM/dd/yyyy" var="udfDate"/>
            	<input type="text" name="${fieldRef}" id="${field.userField}" value="${udfDate}" class="num-date" readonly="true" onclick="$(this).datepicker('show'); $(this).blur();"/>
            </a>
            <img src="${url_context}<spring:theme code="trash"/>" 
                onClick="javascript: document.getElementById('${field.userField}').value=''; document.getElementById('${field.userField}').focus();" 
                title="Clear" class="iconz"/>
        </div>
        <script type="text/javascript">
        $(document).ready(function() {
            $( "#${field.userField}" ).datepicker({
                showOn: "both",
                buttonImage: '${url_context}<spring:theme code="datepicker"/>',
                buttonImageOnly: true,
                minDate: new Date(1901, 1 - 1, 1),
        		maxDate: "+50y",
        		yearRange: "1901:+5", /*range is currently selected year-45 to current year+5*/
        		buttonText: "Choose",
        		changeMonth : true,
        		changeYear : true,
        		dateFormat: 'mm/dd/yy',
        		constrainInput: true
            });
        });
        </script>
        </c:when>
        <c:when test="${fn:startsWith(field.userField, 'number')==true}">
        <input type="text" name="${fieldRef}" id="${fieldRef}" class="num-date"
            value="${crud.retrieveObjectValue(object, fieldRef)}" onkeydown="return isNumeric(event);"/>
        <script type="text/javascript">
	        function isNumeric(e) {
	    	    var evt = (e) ? e : window.event;
	    	    var key = (evt.keyCode) ? evt.keyCode : evt.which;
	    		
	    	    if(key != null) {
	    	        key = parseInt(key, 10);
	    	        if(((key < 48 || key > 57) && 
    	        		(key != 8 && key != 9 && key != 13 && key != 46) && 
    	        		(key < 96 || key > 105) && 
    	        		(key < 35 || key > 40))){
	 	               return false;
	 	        	}
	    	        if(evt.shiftKey && key >= 48 && key <= 57) {
	    	        	return false;
	    	        }
	    	    }
	    	   	return true;
	    	}
        </script> 
        </c:when>
        <c:when test="${fn:startsWith(field.userField, 'boolean')==true}">
            <div class="radio">
                <input type="radio" id="${fieldRef}" name="${fieldRef}" value="true" 
                    <c:if test="${crud.retrieveObjectValue(object, fieldRef) == true}">selected</c:if> /> <span>Yes</span>
                <input type="radio" id="${fieldRef}" name="${fieldRef}" value="false"
                    <c:if test="${crud.retrieveObjectValue(object, fieldRef) != true}">selected</c:if> /> <span>No</span>
            </div>
        </c:when>
        <c:when test="${fn:startsWith(field.userField, 'dropdown')==true}">
        	<c:set var="fieldId" value="udf.${field.userField}.id"/>
            <select id="${fieldRef}" name="${fieldRef}">    
             	<option value=""></option>
            <c:forEach items="${crud.retrieveObjectValue(dropList, field.userField)}" var="item">
                <option value="${item.id}" <c:if test="${crud.retrieveObjectValue(object, fieldId) eq item.id}">selected</c:if>> ${item.value}</option>
            </c:forEach>
            </select>
        </c:when>
    </c:choose>    
    ${postfix}
</c:if>
</c:if>
</c:forEach>