<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  

<idy:header title_webpage="label.usergroups">
    <link rel="stylesheet" href="${url_context}/script/ideyatech-${ot_version}/jquery.treeview.css" type="text/css" />
    <script type="text/javascript" src="${url_jquery}"></script>
    <script type="text/javascript" src="${url_context}/script/ideyatech-${ot_version}/jquery.treeview.js"></script>
    <script type="text/javascript" src="${url_context}/script/ideyatech-${ot_version}/crud.js"></script>
</idy:header>


<div class="yui-b usergroup">
	<div class="title-wrapper"><spring:message code="label.usergroups" /></div>
            
    	<div class="content-wrapper">
        <span><a href="usergroup-matrix.jspx"> <spring:message code="label.view-permission-matrix" /></a></span>
        <div class="usergroup-table">
            	<table border="1">
            		<thead>
            		<tr>
            			<th><spring:message code="label.name" /></th>
            			<th><spring:message code="label.description" /></th>
            			<th><spring:message code="label.permissions-assigned" /></th>
            			<th></th>
            		</tr>
            		</thead>
            		
            		<tbody>
            			<c:forEach items="${results.results}" var="record" varStatus="status">
            				<tr id="usergroup-row-${record.id}" class="row-style-${status.count%2}">
            					<td><c:out value="${record.name}" /></td>
            					<td><c:out value="${record.description}" /></td>
            					<td> ${fn:length(record.roleNames)}</td>
                  				<td>
									<ot:update_button id="${record.id}" page="admin/usergroup.jspx" prefix="usergroup"/>
                    				<ot:delete_button id="${record.id}" page="admin/usergroup.jspx" title="${record.name}" prefix="usergroup"/>                  		
                  				</td>	
            				</tr>
            			</c:forEach>
            		</tbody>
            		<tr>
            			<td colspan="4"></td>
            		</tr>
            	</table>
            	<ot:add_button page="admin/usergroup.jspx" prefix="usergroup"/>
            	<ot:paging results="${results}" baseURL="/admin/usergroup.jspx" pageParamName="page" displaySummary="false" displayPageLinks="true" />	            	
			</div>
		</div>
	</div><!-- END OF USER GROUP -->


<idy:footer>
<script language="text/javascript">
	function checkParent(id) {
	}
</script>
</idy:footer>
