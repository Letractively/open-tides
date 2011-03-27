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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${ot_version == '1.0'}"> 
<div class="addLink" >
<input type="image" src="${url_context}<spring:theme code="db_add"/>"" title="<spring:message code="label.add"/>"
	onclick="IDEYATECH.crud.addRecord('${prefix}-',
			'${url_context}/${page}?action=create');"/> <a href="javascript:void(0);" onclick="IDEYATECH.crud.addRecord('${prefix}-',
			'${url_context}/${page}?action=create');"> <spring:message code="label.add"/></a>
</div>
</c:if>