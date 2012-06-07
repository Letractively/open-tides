<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>

<c:if test="${newRow}">
[multipart_tr id="report-row-${report.id}" status="new"]
</c:if>
	[multipart_td]<c:out value="${ report.name }" />[/multipart_td]
	[multipart_td]<c:out value="${ report.title }" />[/multipart_td]
	[multipart_td]
		<ot:update_button id="${report.id}" page="admin/report.jspx" prefix="report"/>
		<ot:delete_button id="${report.id}" page="admin/report.jspx" title="${report.name}" prefix="report"/>                  		
	[/multipart_td]
<c:if test="${newRow}">
[/multipart_tr]
<script language="javascript">
try{
	IDEYATECH.crud.cancelNew('report-');
	IDEYATECH.crud.refreshTable('report-table-results', {})
}catch(e){}
</script>
</c:if> 