<%--
	- update_button
	- 
	- Creates the update button for Ajaxified action
	-
	- @param id - the primary key reference
	- @param page - the page to request
	--%>
	
<%@ tag isELIgnored="false" body-content="empty" %>
<%@ attribute name="id" required="true" type="java.lang.String" %>
<%@ attribute name="page" required="true" type="java.lang.String" %>
<%@ attribute name="prefix" type="java.lang.String" %>
<%@ attribute name="urlParam" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${ot_version == '1.0'}"> 
<input type="image" src="${url_context}<spring:theme code="db_edit"/>"" title="<spring:message code="label.edit"/>" alt="Edit"
	onclick="IDEYATECH.crud.editRecord('${prefix}-row-${id}',
			'${url_context}/${page}?action=update&codeId=${id}${urlParam}');"/>
</c:if>