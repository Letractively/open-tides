<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>
<idy:header title_webpage="label.users">
  <script src="${url_jquery}"></script>
  <script src="${url_context}/script/ideyatech-${ot_version}/crud.js"></script>
</idy:header>
    <!-- BODY -->
    <div id="bd">
        <div class="yui-g">
        <div class="main">

            <div class="title-wrapper">
            	<div class="title"><span><spring:message code="label.users" /></span></div>
            </div> 
            
            <div class="contents">
            	<table class="admin-table">
            		<thead>
            		<tr>
            			<th><spring:message code="label.firstname" /></th>
            			<th><spring:message code="label.lastname" /></th>
            			<th><spring:message code="label.email-address" /></th>
            			<th><spring:message code="label.usergroups" /></th>
            			<th></th>
            		</tr>
            		</thead>
            		<tbody id="user-table-results">
            		<c:forEach items="${results.results}" var="record" varStatus="status">
            		<tr id="user-row-${record.id}" class="row-style-${status.count%2}">
            			<td><c:out value="${record.firstName}" /></td>
            			<td><c:out value="${record.lastName}" /></td>
            			<td><c:out value="${record.emailAddress}" /></td>
            			<td>
            			<c:forEach items="${record.groups}" var="group">
                     		<c:out value="${group.name}" /> 
                  		</c:forEach>
                  		</td>
                  		<td>
							<ot:update_button id="${record.id}" page="admin/users.jspx" prefix="user"/>
                    		<ot:delete_button id="${record.id}" page="admin/users.jspx" title="${record.emailAddress}" prefix="user"/>                  		
                  		</td>	
            		</tr>
            		</c:forEach>
            		</tbody>
            		<tr id="user-row-new">
            			<td colspan="5">  	
            			</td>
            		</tr>
            	</table>
            	<ot:add_button page="admin/users.jspx" prefix="user"/>
            	<ot:paging results="${results}" baseURL="/admin/users.jspx" pageParamName="page" displaySummary="false" displayPageLinks="true" />	            	
        	</div>
        </div>
        </div>    
    </div>
    <!-- END OF BODY -->

<!-- FOOT -->
<idy:footer />
