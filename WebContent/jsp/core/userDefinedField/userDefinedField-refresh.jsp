<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>

<c:if test="${newRow}">
<tr id="user-defined-field-row-${userDefinedField.id}" status="new">
</c:if>
    <td><c:out value="${ userDefinedField.order }" /></td>
	<td><c:out value="${ userDefinedField.clazz.simpleName }" /></td>
	<td><c:out value="${ userDefinedField.userField }" /></td>
	<td><c:out value="${ userDefinedField.label }" /></td>
	<td>
		<ot:update_button id="${userDefinedField.id}" page="admin/user-defined-field.jspx" prefix="user-defined-field"/>
		<ot:delete_button id="${userDefinedField.id}" page="admin/user-defined-field.jspx" title="${userDefinedField.label}" prefix="user-defined-field"/>                  		
	</td>
<c:if test="${newRow}">
</tr>
<script language="javascript">
IDEYATECH.crud.cancelNew('user-defined-field-');
IDEYATECH.crud.refreshTable('user-defined-field-table-results', {})
</script>
</c:if> 