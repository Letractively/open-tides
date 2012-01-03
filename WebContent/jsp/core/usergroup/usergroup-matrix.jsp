<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>

<idy:header title_webpage="label.usergroups">
  <script src="${url_yui}/yahoo-dom-event/yahoo-dom-event.js"></script>
</idy:header>
    <!-- BODY -->
    <div id="bd">
        <div class="yui-g">
        <div class="main">

            <div class="title-wrapper">
            	<div class="title"><span><spring:message code="label.usergroups-matrix" /></span></div>
            </div> 
            
            <div class="contents">
                <p><a href="usergroup.jspx"> Back </a> </p>            
            	<table class="admin-table">
            		<thead>
            		<tr>
            			<th>&nbsp;</th>
            			<c:forEach items="${userGroups}" var="usergroup">
            			<th>${usergroup.verticalTitle}</th>
            			</c:forEach>
            		</tr>
            		</thead>
            		<tbody id="usergroup-table-results">
            		<c:forEach items="${roles}" var="role" varStatus="status">
            		<tr class="row-style-${status.count%2}">
						<td>${role.value}</td>
            			<c:forEach items="${userGroups}" var="usergroup">
            				<td>
            				<c:if test="${not empty usergroup.userRoleMap[role.key]}">
								<a title="${usergroup.name}">X</a>
            				</c:if>
            				</td>
            			</c:forEach>
            		</tr>
            		</c:forEach>
            		</tbody>
            	</table>
        	</div>
        </div>
        </div>    
    </div>
    <!-- END OF BODY -->
    
<!-- FOOT -->
<idy:footer />
