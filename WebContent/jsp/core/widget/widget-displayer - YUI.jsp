
<!-- ATTENTION! This file is not being used. This is part of the YUI implementation of -->
<!-- the dashboard widgets. This is kept for back-up purposes only. -->
 
 

<%@page import="org.opentides.service.WidgetService"%>
<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%  
 	 response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
     response.setHeader("Pragma","no-cache"); //HTTP 1.0 
     response.setDateHeader ("Expires", 0); //prevents caching 
     response.setHeader("Cache-Control","no-store"); //HTTP 1.1 
%>
<%
int widgetConfigMinimize = WidgetService.WIDGET_STATUS_MINIMIZE;
pageContext.setAttribute("widgetConfigMinimize",widgetConfigMinimize);
int widgetConfigShow = WidgetService.WIDGET_STATUS_SHOW;
pageContext.setAttribute("widgetConfigShow",widgetConfigShow);
%>

<%@page import="org.opentides.util.UrlUtil"%>
<%@page import="org.opentides.util.StringUtil"%>
<c:set var="columnClassName" value="dashboard-column" scope="page" />
<c:set var="widgetClassName" value="widget-box" scope="page" />
<c:set var="handleClassName" value="tides-dd-handle" scope="page" />

		<div id="dashboard-main">	
			 <c:forEach var="i" begin="1" end="${columnNumber}" varStatus ="status">
				  	<div id="Column${i}" class="${columnClassName}">
						<c:forEach items="${userWidgets}" var="userWidget" varStatus ="widgetStatus">
							<c:set var="urlSource" value="widget-load.jspx?name=${userWidget.widget.name}" scope="page" />
							<c:set var="urlNoCache" value="${userWidget.widget.url}" scope="page" />
							<%
							String urlSrc = (String)pageContext.getAttribute("urlNoCache");
							String widgetCSS = UrlUtil.getURLParam(urlSrc,"widgetCSSName");
							pageContext.setAttribute("widgetCSS",widgetCSS);
							String hasToEval = UrlUtil.getURLParam(urlSrc,"hasToEval");
							if (StringUtil.isEmpty(hasToEval)) {
								hasToEval="0";
							}
							pageContext.setAttribute("hasToEval",hasToEval);
							%>
							<c:choose>
								<c:when test="${(fn:contains(urlNoCache,'?'))}">
									<c:set var="urlNoCache" value="${userWidget.widget.url}&${params}" scope="page" />
								</c:when>
								<c:when test="${!(fn:contains(urlNoCache,'?'))}">
									<c:set var="urlNoCache" value="${userWidget.widget.url}?${params}" scope="page" />
								</c:when>
							</c:choose>
							<c:set var="widgetState" value="widget-collapse" scope="page" />
							<c:set var="widgetState2" value="${widgetConfigMinimize}" scope="page" />
							<c:if test="${userWidget.status == widgetConfigMinimize}">
								<c:set var="widgetState" value="widget-expand" scope="page" />
								<c:set var="widgetState2" value="${widgetConfigShow}" scope="page" />
							</c:if>
							<c:set var="customWidgetCss" value="${widgetCSS=='' ? '' : widgetCSS}" scope="page" />
							<c:set var="customWidgetCssHandle" value="" scope="page" />
							<c:if test="${customWidgetCss != ''}">
								<c:set var="customWidgetCssHandle" value="${customWidgetCss}-custom-handle" scope="page" />
							</c:if>
							<c:if test="${userWidget.column == i}">
								<div id="${userWidget.widget.name}" class="${widgetClassName} ${customWidgetCss}">					
									<div id="${userWidget.widget.name}Handle" class="${handleClassName} ${customWidgetCssHandle}">
										<a class="widget-close" title="Remove widget" onclick="IDEYATECH.widgets.executeSaveStatus(this,'0')"></a>
	                                    <a class="${widgetState}" title="Expand/Collapse widget" onclick="IDEYATECH.widgets.executeSaveStatus(this,'${widgetState2}')"></a>
	                                	<h4>${userWidget.widget.title}</h4>
									</div>
									<div id="ajaxContainer${widgetStatus.count}" class="widgetAjaxContainer" style="${widgetState=='widget-collapse' ? '' : 'display:none;'}"><center><img src="${url_context}/images/widgets/ajax-loader.gif" /></center></div>
									<script type="text/javascript">ajaxinclude("${userWidget.widget.cacheDuration < 0 ? urlNoCache : urlSource}","ajaxContainer${widgetStatus.count}", "${hasToEval}")</script>
								</div>
							</c:if>
						</c:forEach>
					
					</div>  
				</c:forEach>
				<div class="clear not-dd-target"></div>
		</div>