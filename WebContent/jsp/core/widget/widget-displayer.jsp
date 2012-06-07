<%@page import="org.opentides.service.WidgetService"%>
<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script src="${url_context}/script/ideyatech-${ot_version}/crud.js"></script>
<script src="${url_context}/script/ideyatech-${ot_version}/widgets.js"></script>
<script src="${url_context}/script/ideyatech-${ot_version}/jquery-ui-personalized-1.6rc2.min.js"></script>
<script type="text/javascript" src="${url_jquery}"></script>
<script type="text/javascript" src="${url_jquery_ui}"></script>

<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1 
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 
	response.setDateHeader("Expires", 0); //prevents caching 
	response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
%>
<%
	int widgetConfigMinimize = WidgetService.WIDGET_STATUS_MINIMIZE;
	pageContext.setAttribute("widgetConfigMinimize",
			widgetConfigMinimize);
	int widgetConfigShow = WidgetService.WIDGET_STATUS_SHOW;
	pageContext.setAttribute("widgetConfigShow", widgetConfigShow);
%>

<%@page import="org.opentides.util.UrlUtil"%>
<%@page import="org.opentides.util.StringUtil"%>
<c:set var="columnClassName" value="dashboard-column" scope="page" />
<c:set var="widgetClassName" value="widget-box" scope="page" />
<c:set var="handleClassName" value="tides-dd-handle" scope="page" />

<div id="columns">
	<c:forEach var="i" begin="1" end="${columnNumber}" varStatus="status">
		<ul id="column${i}" class="column">
			<c:if test="${i == 1}">
				<li class="widget" id="intro" style="display: none" />
			</c:if>

			<c:forEach items="${userWidgets}" var="userWidget"
				varStatus="widgetStatus">
				<c:if test="${userWidget.column == i}">
					<li class="widget ${(fn:contains(fn:toLowerCase(userWidget.widget.name), 'overdue')) ? 'color-overdue' : 'color-regular'}" id="${(userWidget.status == 2) ? 'collapse' : ''}">
						<div id="${userWidget.widget.name}" class="widget-head title-wrapper">
							<h3>
								<c:set var="currentSection" value="${currentSection}"/>
								<c:set var="widgetTitle" value="${userWidget.widget.title}"/>
								<c:out value="${fn:replace(widgetTitle, ':section', currentSection)}"/>
							</h3>
						</div>
						<div class="widget-content content-wrapper">
							<c:set var="urlSource"
								value="widget-load.jspx?name=${userWidget.widget.name}"
								scope="page" />
							<c:set var="urlNoCache" value="${userWidget.widget.url}"
								scope="page" />
							<%
								String urlSrc = (String) pageContext
													.getAttribute("urlNoCache");
											String widgetCSS = UrlUtil.getURLParam(urlSrc,
													"widgetCSSName");
											pageContext.setAttribute("widgetCSS", widgetCSS);
											String hasToEval = UrlUtil.getURLParam(urlSrc,
													"hasToEval");
											if (StringUtil.isEmpty(hasToEval)) {
												hasToEval = "0";
											}
											pageContext.setAttribute("hasToEval", hasToEval);
							%>
							<c:choose>
								<c:when test="${(fn:contains(urlNoCache,'?'))}">
									<c:set var="urlNoCache"
										value="${userWidget.widget.url}&${params}" scope="page" />
								</c:when>
								<c:when test="${!(fn:contains(urlNoCache,'?'))}">
									<c:set var="urlNoCache"
										value="${userWidget.widget.url}?${params}" scope="page" />
								</c:when>
							</c:choose>
							<div id="ajaxContainer${widgetStatus.count}" class="widgetAjaxContainer">
								<div id="loader"><img src="${url_context}<spring:theme code="loader"/>"/></div>
							</div>
							<script type="text/javascript">
                                ajaxinclude(
                                        "${url_context}/${urlNoCache}",
                                        "ajaxContainer${widgetStatus.count}",
                                        "${hasToEval}");
                                        
<%--								ajaxinclude(
										"${userWidget.widget.cacheDuration < 0 ? urlNoCache : urlSource}",
										"ajaxContainer${widgetStatus.count}",
										"${hasToEval}");
--%>                                        
							</script>
						</div></li>
				</c:if>
			</c:forEach>
           
		</ul>
	</c:forEach>

	<div class="clear not-dd-target"></div>
</div>

<script type="text/javascript">
	iNettuts.init();
</script>
