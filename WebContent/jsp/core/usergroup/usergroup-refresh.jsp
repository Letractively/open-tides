<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  

<c:if test="${newRow}">
<tr id="usergroup-row-${usergroup.id}" status="new">
</c:if>
	<td><c:out value="${usergroup.name}" /></td>
	<td><c:out value="${usergroup.description}" /></td>
	<td>
		 ${fn:length(usergroup.roleNames)}
	</td>
	<td>
		<ot:update_button id="${usergroup.id}" page="admin/usergroup.jspx" prefix="usergroup"/>
		<ot:delete_button id="${usergroup.id}" page="admin/usergroup.jspx" title="${usergroup.name}" prefix="usergroup"/>                  		
	</td>	
<c:if test="${newRow}">
</tr>
<script language="javascript">
IDEYATECH.crud.cancelNew('usergroup-');
IDEYATECH.crud.refreshTable('usergroup-table-results', {})
</script>
</c:if>
