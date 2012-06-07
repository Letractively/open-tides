<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<!-- [multipart_td] [multipart_tr] : temporary fix for stripping <TD> tags due to multipart form -->
<c:if test="${newRow}">
[multipart_tr id="widget-row-${widget.id}" status="new"]
</c:if>
	[multipart_td]<c:out value="${ widget.name }" />[/multipart_td]
	[multipart_td]<c:out value="${ widget.title }" />[/multipart_td]
	[multipart_td]<c:out value="${ widget.isShown == true ? 'Yes' : 'No' }" />[/multipart_td]
	[multipart_td]<fmt:formatDate value="${ widget.lastCacheUpdate }" dateStyle="short" type="DATE" pattern="MMM dd, yyyy hh:mm:ss" />[/multipart_td]
	[multipart_td]
		<ot:update_button id="${widget.id}" page="admin/widget.jspx" prefix="widget"/>
		<ot:delete_button id="${widget.id}" page="admin/widget.jspx" title="${widget.name}" prefix="widget"/>                  		
	[/multipart_td]
<c:if test="${newRow}">
[/multipart_tr] 
<script language="javascript">
IDEYATECH.crud.cancelNew('widget-');
IDEYATECH.crud.refreshTable('widget-table-results', {})
</script>
</c:if> 