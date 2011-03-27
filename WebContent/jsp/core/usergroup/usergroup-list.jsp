<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>

<idy:header title_webpage="label.usergroups">
  <script src="${url_jquery}"></script>
  <script src="${url_context}/script/ideyatech-${ot_version}/crud.js"></script>
</idy:header>
    <!-- BODY -->
    <div id="bd">
        <div class="yui-g">
        <div class="main">

            <div class="title-wrapper">
            	<div class="title"><span><spring:message code="label.usergroups" /></span></div>
            </div> 
            
            <div class="contents">
            	<p><a href="usergroup-matrix.jspx"> View Permission Matrix </a> </p>
            	<table class="admin-table">
            		<thead>
            		<tr>
            			<th><spring:message code="label.name" /></th>
            			<th><spring:message code="label.description" /></th>
            			<th><spring:message code="label.role" /></th>
            			<th></th>
            		</tr>
            		</thead>
            		<tbody id="usergroup-table-results">
            		<c:forEach items="${results.results}" var="record" varStatus="status">
            		<tr id="usergroup-row-${record.id}" class="row-style-${status.count%2}">
            			<td><c:out value="${record.name}" /></td>
            			<td><c:out value="${record.description}" /></td>
            			<td>
            			<c:forEach items="${record.roleNames}" var="role" varStatus="status">
            				<c:if test="${status.index > 0}"> | </c:if>
                      		<c:out value="${role}" />
                    	</c:forEach>
                  		</td>
                  		<td>
							<ot:update_button id="${record.id}" page="admin/usergroup.jspx" prefix="usergroup"/>
                    		<ot:delete_button id="${record.id}" page="admin/usergroup.jspx" title="${record.name}" prefix="usergroup"/>                  		
                  		</td>	
            		</tr>
            		</c:forEach>
            		</tbody>
            		<tr id="usergroup-row-new">
            			<td colspan="4">  	
            			</td>
            		</tr>
            	</table>
            	<ot:add_button page="admin/usergroup.jspx" prefix="usergroup"/>
            	<ot:paging results="${results}" baseURL="/admin/usergroup.jspx" pageParamName="page" displaySummary="false" displayPageLinks="true" />	            	
        	</div>
        </div>
        </div>    
    </div>
    <!-- END OF BODY -->
    
<!-- FOOT -->
<idy:footer />
