<%--
    - view_udf
    - 
    - Displays the values of user defined fields.
    -
    - @param meta - the meta listing of udf 
    - @param object - the object containing udf
    - @param searchMode - if true, only isListed udf are displayed
    - @param prefix - insert prefix before each result is displayed
    - @param postfix - append postfix after each  result is displayed
    -
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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="tides_fn" uri="http://www.ideyatech.com/tides_fn"%>

<c:forEach items="${meta}" var="field"> 
<c:if test="${(empty field.condition) || tides_fn:evaluateExpression(object, field.condition)}">
<c:if test="${(not searchMode) || (searchMode && field.listed)}">
    ${prefix}
    <c:set var="fieldRef" value="udf.${field.userField}"/>
    <c:set var="value" value="${tides_fn:retrieveNullableObjectValue(object, fieldRef)}"/>   
    <c:if test="${ not (searchMode && field.listed)}"> 
    <label>${field.label}</label>
    </c:if>
    <c:choose>
        <c:when test="${fn:startsWith(field.userField, 'string')==true}">
            <span>${value}</span>
        </c:when>
        <c:when test="${fn:startsWith(field.userField, 'date')==true}">            
            <span><fmt:formatDate value="${value}" pattern="${field.datePattern}"/></span>
        </c:when>
        <c:when test="${fn:startsWith(field.userField, 'number')==true}">
            <span><fmt:formatNumber type="number" value="${value}" minFractionDigits="${field.fraction}" maxFractionDigits="${field.fraction}"/></span>
        </c:when>
        <c:when test="${fn:startsWith(field.userField, 'boolean')==true}">
            <span><c:if test="${value==true}">Yes</c:if><c:if test="${value!=true}">No</c:if></span>
        </c:when>        
        <c:when test="${fn:startsWith(field.userField, 'dropdown')==true}">
            <span>${tides_fn:retrieveNullableObjectValue(object, fieldRef).value}</span>
        </c:when>
    </c:choose>
    ${postfix}
</c:if>
</c:if>
</c:forEach>