<%--
	- add_button
	- 
	- Creates the add button for Ajaxified action
	-
	- @param page - the page to request
	--%>
	
<%@ tag isELIgnored="false" body-content="empty" %>
<%@ attribute name="page" required="true" type="java.lang.String" %>
<%@ attribute name="prefix" type="java.lang.String" %>
<%@ attribute name="urlParam" type="java.lang.String" %>
<%@ attribute name="newPage"  required="false" type="java.lang.Boolean" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${ot_version == '1.0'}"> 
<c:url value="/${page}?action=create${urlParam}" var="addUrl" />
<div class="addLink" >
<c:if test="${!newPage}">
    <input type="image" src="${url_context}<spring:theme code="db_add"/>"" title="<spring:message code="label.add"/>"
	onclick="IDEYATECH.crud.addRecord('${prefix}-',
			'${addUrl}');"/> <a href="javascript:void(0);" onclick="IDEYATECH.crud.addRecord('${prefix}-',
			'${addUrl}');"> <spring:message code="label.add"/></a>
</c:if>
<c:if test="${newPage}">
    <input type="image" src="${url_context}<spring:theme code="db_add"/>"" title="<spring:message code="label.add"/>"
    onclick="window.location='${addUrl}'"/> <a href="${addUrl}"><spring:message code="label.add"/></a>
</c:if>

</div>
</c:if>