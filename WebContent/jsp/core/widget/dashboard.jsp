
<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>
<c:set var="url_yui" value="http://yui.yahooapis.com/2.5.1/build" scope="application"/>

<idy:header title_webpage="label.widget">
  <script src="${url_yui}/yahoo-dom-event/yahoo-dom-event.js"></script>
  <script src="${url_yui}/animation/animation-min.js"></script>
  <script src="${url_yui}/connection/connection-min.js"></script>
  <script src="${url_jquery}"></script>
  <script src="${url_context}/script/ideyatech-${ot_version}/crud.js"></script>
</idy:header>
    <!-- BODY -->
    <div id="bd">
        <div class="yui-g">
        <div class="main" id="widget">
            <div class="title-wrapper">
                <div class="title" id="widget-title"><span><spring:message code="label.add-widget" /></span></div>
            </div> 
            <div class="contents">
				<form:form commandName="userWidgets" id="userWidgets" action="">
            	<table cellspacing="1" id="widget_table">
                    <th>
						<td>&nbsp;</td>
						<td><spring:message code="label.widget-name" /></td>
						<td><spring:message code="label.widget-description" /></td>
					</th>
					<c:forEach items="${widgets}" var="record" varStatus="status">
					<authz:authorize ifAnyGranted="${record.accessCode}">
					<tr>
						<td class="widget_checkbox">
							<input type="checkbox" name="checkboxes" value="${record.id}"/>
						</td>
						<td class="widget_name"><img src="${record.screenshot}"/> <br/>${record.name} </td>
						<td class="widget_description"> ${record.description} </td>
					</tr>
					</authz:authorize>
					</c:forEach>
				</table>
				<div id="save-button" class="button">
					<input type="hidden" id="mergedCheckboxes" name="mergedCheckboxes" value="">
					<input class="button" type="button" value="Save" onclick="IDEYATECH.checkbox.mergeSubmit('userWidgets','mergedCheckboxes')" />
				</div>
				</form:form>
        	</div><!-- end of contents -->
        </div>
        </div>    
    </div>
    <!-- END OF BODY -->
<!-- FOOT -->
<idy:footer /> 