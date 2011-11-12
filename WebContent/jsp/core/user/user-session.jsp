<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<idy:header title_webpage="label.welcome">
	<script src="${url_jquery}"></script>
	<script src="${url_jquery_ui}"></script>
	<script type="text/javascript" src="script/attache.js"></script>
	<script type="text/javascript" src="script/ideyatech-${ot_version}/crud.js"></script>
</idy:header>
<div class="yui-b user-session">
	<div class="title-wrapper"><spring:message code="label.user-session"/></div>
    	<div class="content-wrapper">
        <div class="user-session-table">
        	<table border="1">
            	<thead>
                	<tr>
                		<th class="col-1"><spring:message code="label.user-session.count"/></th>
                    	<th class="col-2"><spring:message code="label.user-session.user"/></th>
                    	<th class="col-3"><spring:message code="label.user-session.last-activity"/></th>
                    	<th class="col-4"></th>
                	</tr>
                </thead>
              	<tbody>       
                    <c:forEach items="${sessionList}" var="record" varStatus="status">
                    	<tr>
                        	<td class="col-1">${status.count}</td> 
                            <td class="col-2">
                            	<div class="L">
                            		<div class="image-usersession">
                            			<img src="<c:url value='/user-image.jspx?userId=${record.principal.id}&imageId=2' />">
                            		</div>
                            	</div>
                                <p><c:out value="${record.principal.completeName}"></c:out></p>
                                <p><c:out value="${record.principal.position}"></c:out></p>
                                <p><c:out value="${record.principal.office}"/></p>
                           	</td>
                            <td class="col-3"><c:out value="${record.lastRequest}"/></td>
                            <td class="col-4">
                            	<sec:authorize ifAnyGranted="LOGOUT_USER">
                                	<a href="javascript:void(0)" onclick="confirmLogout('${record.principal.completeName}', '${record.principal.username}');" id="peopleLinkEdit">LOGOUT</a>
                                </sec:authorize>
                            </td>
                       	</tr>
                     </c:forEach>
                    </tbody>        
                    </table>
                </div>
                </div>
      </div><!-- END OF USER SESSION -->
<idy:footer>
<script type="text/javascript">
    function confirmLogout(completeName, username) {
        var answer = confirm("Are you sure to logout " + completeName + " ?");
        if (answer)
            window.location = "${url_context}/view-session.jspx?logout="
                    + username;
    }
</script>
</idy:footer>