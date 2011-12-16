<%--
    - view_udf
    - 
    - Displays the label of user defined fields.
    -
    - @param meta - the meta listing of udf 
    - @param object - the object containing udf
    - @param prefix - insert prefix before each result is displayed
    - @param postfix - append postfix after each  result is displayed
    -
    --%>
<%@ tag isELIgnored="false" body-content="empty"%>
<%@ attribute name="meta" required="true" type="java.util.List"%>
<%@ attribute name="object" required="true" type="org.opentides.bean.UserDefinable"%>
<%@ attribute name="prefix" required="false" type="java.lang.String" %>
<%@ attribute name="postfix" required="false" type="java.lang.String" %>

<jsp:useBean id="crud" class="org.opentides.util.CrudUtil" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach items="${meta}" var="field"> 
<c:if test="${empty field.condition || crud.evaluateExpression(object, field.condition)}">
<c:if test="${field.listed}">
    ${prefix}
    ${field.label}
    ${postfix}
</c:if>
</c:if>
</c:forEach>