<%--
    - view_udf
    - 
    - Displays the values of user defined fields.
    -
    - @param meta - the meta listing of udf 
    - @param object - the object containing udf
    --%>
<%@ tag isELIgnored="false" body-content="empty"%>
<%@ attribute name="meta" required="true" type="java.util.List"%>
<%@ attribute name="object" required="true" type="org.opentides.bean.UserDefinable"%>
<jsp:useBean id="crud" class="org.opentides.util.CrudUtil" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<c:forEach items="${meta}" var="field"> 
    <div class="form-row">
    <c:set var="fieldRef" value="udf.${field.userField}"/>
    <c:set var="value" value="${crud.retrieveObjectValue(object, fieldRef)}"/>
    <label>${field.label}</label>
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
            <span>${crud.retrieveObjectValue(object, fieldRef).value}</span>
        </c:when>
    </c:choose>
    </div>
</c:forEach>