<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>

<c:if test="${newRow}">
<tr id="user-row-${user.id}" status="new">
</c:if>
	<td><c:out value="${user.firstName}" /></td>
	<td><c:out value="${user.lastName}" /></td>
	<td><c:out value="${user.emailAddress}" /></td>
	<td>
		<c:forEach items="${user.groups}" var="group">
		<c:out value="${group.name}" /> 
		</c:forEach>
	</td>
	<td>
		<ot:update_button id="${user.id}" page="admin/users.jspx" prefix="user"/>
		<ot:delete_button id="${user.id}" page="admin/users.jspx" title="${user.emailAddress}" prefix="user"/>                  		
	</td>	
<c:if test="${newRow}">
</tr>
<script language="javascript">
IDEYATECH.crud.cancelNew('user-');
IDEYATECH.crud.refreshTable('user-table-results', {});
</script>
</c:if>