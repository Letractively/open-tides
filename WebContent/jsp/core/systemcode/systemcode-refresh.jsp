<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>

<c:if test="${newRow}">
<tr id="system-row-${systemCode.id}" status="new">
</c:if>
	<td><c:out value="${systemCode.category}" /></td>
	<td><c:out value="${systemCode.key}" /></td>
	<td><c:out value="${systemCode.value}" /></td>
	<td>
		<ot:update_button id="${systemCode.id}" page="admin/system-codes.jspx" prefix="system"/>
		<ot:delete_button id="${systemCode.id}" page="admin/system-codes.jspx" title="${systemCode.key}" prefix="system"/>                  		
	</td>
<c:if test="${newRow}">
</tr>
<script language="javascript">
IDEYATECH.crud.cancelNew('system-');
IDEYATECH.crud.refreshTable('system-table-results', {})
</script>
</c:if>