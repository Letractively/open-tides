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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${ot_version == '0.1'}"> 
<input type="image" src="${url_context}/images/db_edit.ico" title="<spring:message code="label.edit"/>"
	onclick="IDEYATECH.util.loadPage('row-${id}',
			'${url_context}/${page}?action=update&codeId=${id}');"/>
</c:if>

<c:if test="${ot_version == '0.2'}"> 
<input type="image" src="${url_context}/images/db_edit.gif" title="<spring:message code="label.edit"/>" alt="Edit"
	onclick="IDEYATECH.crud.editRecord('${prefix}-row-${id}',
			'${url_context}/${page}?action=update&codeId=${id}');"/>
</c:if>

<c:if test="${ot_version == '0.3'}"> 
<input type="image" src="${url_context}/images/db_edit.gif" title="<spring:message code="label.edit"/>" alt="Edit"
	onclick="IDEYATECH.crud.editRecord('${prefix}-row-${id}',
			'${url_context}/${page}?action=update&codeId=${id}');"/>
</c:if>