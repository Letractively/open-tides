<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>

<idy:header title_webpage="label.welcome">
	<script src="${url_yui}/yahoo-dom-event/yahoo-dom-event.js"></script>
    <script src="${url_yui}/animation/animation-min.js"></script>
	<script src="${url_yui}/container/container.js" type="text/javascript"></script>	
	<script src="${url_yui}/calendar/calendar-min.js"></script>
  	<script src="${url_context}/script/ideyatech-${ot_version}/crud.js"></script>
  	<!--CSS file (default YUI Sam Skin) -->
	<link rel="stylesheet" type="text/css" href="${url_yui}/calendar/assets/skins/sam/calendar.css">
	<script>
	YAHOO.namespace("report.calendar");
	
	/*
	 * Function to handle selectEvent on calendar.
	 * type - ??
	 * args - contains the selected date
	 * obj  - parameter passed on subscribe which are [calendarContainer, textField]
	 *
	 */
	function handleSelect(type, args, obj) {
		var dates = args[0]; 
	    var date = dates[0]; 
	    var year = date[0], month = date[1], day = date[2]; 
		var txtDate = document.getElementById(obj[1]);
		txtDate.value = month + "/" + day + "/" + year;
		obj[0].hide();
	}
	</script>
</idy:header>

<!-- BODY -->
<div id="bd" class="yui-skin-sam">
    <div class="yui-g">
    	<div class="main" id="report">
	        <div class="title-wrapper">
	        	<div class="title" id="report-title"><span><spring:message code="label.report" /></span></div>
	        </div>
	        <div class="contents">
	        	<p ><spring:message code="label.please-fill-up-the-following" /></p>
	        	<form>
	        	<c:forEach items="${requestParameters}" var="entry">
	        		<input type="hidden" name="${entry.key}" 
	        			value='<c:forEach items="${entry.value}" var="value" varStatus="status"><c:if test="${status.count>1}">,</c:if>${value}</c:forEach>'/>
	        	</c:forEach>
	        	
	        	<c:forEach items="${missingParameters}" var="parameter">
	        		<p><label for="${parameter.name}">${parameter.label} :</label>
	        		<c:choose>
	        			<c:when test="${parameter.clazz=='java.util.Date'}">
							<input type="text" id="${parameter.name}" name="${parameter.name}" class="calendarTextInput"/> 
							<img src="${url_context}<spring:theme code='datepicker'/>" id="${parameter.name}Image"/>
							<div id="${parameter.name}Container" class="containerDate"></div> 
							<script>
								YAHOO.report.calendar.init = function() {
									YAHOO.report.calendar.${parameter.name} = 
										new YAHOO.widget.Calendar("${parameter.name}","${parameter.name}Container", 
																	{ title:"Enter ${parameter.label}:", 
																	  close:true } );
									YAHOO.report.calendar.${parameter.name}.render();
							
									// Listener to show the 1-up Calendar when the button is clicked
									YAHOO.util.Event.addListener("${parameter.name}Image", "click", 
											YAHOO.report.calendar.${parameter.name}.show, YAHOO.report.calendar.${parameter.name}, true);
											
									YAHOO.report.calendar.${parameter.name}.selectEvent.subscribe(handleSelect, 
											[YAHOO.report.calendar.${parameter.name},'${parameter.name}'], true);
								}
								YAHOO.util.Event.onDOMReady(YAHOO.report.calendar.init);
								
							</script>
	        			</c:when>
	        			<c:when test="${parameter.type=='dropdown'}">
	        				<select name="${parameter.name}">
	        					<c:forEach items="${parameter.properties}" var="option">
		        					<option value="${option.key}">${option.value}
	        					</c:forEach>
	        				</select>
	        			</c:when>
	        			<c:when test="${parameter.type=='checkbox'}">
							<input type="hidden" name="${parameter.name}" value="" />
        					<c:forEach items="${parameter.properties}" var="option">
								<input type="checkbox" name="${parameter.name}" value="${option.key}" /> ${option.value}<br/>
        					</c:forEach>
	        			</c:when>
	        			<c:when test="${parameter.type=='prompt.command'}">
						  <c:forEach items="${parameter.properties}" var="option">
						       ${option.value.html}
						  </c:forEach>
						</c:when>
	        			<c:otherwise>
		        			<input type="text" name="${parameter.name}" /> 
	        			</c:otherwise>
	        		</c:choose></p>
	        	</c:forEach>
	        	<input type="submit" value="Submit" />
	        	</form>
	        </div>                                
		</div><!-- END OF main -->
	</div><!-- END OF yui-g -->
</div>
<idy:footer />


