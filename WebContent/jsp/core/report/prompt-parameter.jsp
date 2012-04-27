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
               <c:choose>
               		<c:when test="${not empty param.reportName}">
               	   		<c:out value="${param.reportName}" />
               		</c:when>
               		<c:otherwise>
               			<spring:message code="label.report" />
               		</c:otherwise>
               </c:choose>
        	</div>
	        <div class="contents">
	        	<p ><spring:message code="label.please-fill-up-the-following" /></p>
	        	<form>
	        		        <c:forEach items="${requestParameters}" var="entry">
	        						<input type="hidden" name="${entry.key}"  value='<c:forEach items="${entry.value}" var="value" varStatus="status"><c:if test="${status.count>1}">,</c:if>${value}</c:forEach>'/>
	        				        <input type="hidden" name="requestParameters" value="${entry.key}"/>
	        				</c:forEach>
	        	
	        				<c:forEach items="${missingParameters}" var="parameter">
	        				             <c:choose>
	        				               <c:when test="${parameter.name == 'validator'}">
	        				               		<div class="form-row" style="display:none;"><label for="${parameter.name}" class="col-1">${parameter.label}</label>
	        				               </c:when>
	        				               <c:otherwise>
	        				               		<div class="form-row"><label for="${parameter.name}" class="col-1">${parameter.label}</label>
	        				               </c:otherwise>
	        				             </c:choose>
	        				            <c:choose>
	        							        <c:when test="${parameter.clazz=='java.util.Date'}">
														<div class="L">
																	<input type="text" id="${parameter.name}"  name="${parameter.name}" class="num-date" />
    																<img src="${url_context}<spring:theme code="trash"/>" onClick="javascript: document.getElementById('${parameter.name}').value='';" title="Cancel" class="iconz"/>
   																	<span class="required">*</span>
   														</div>
   														
   														<div class="clear"></div>
   														
														<script type="text/javascript">
																	var dateId = "${parameter.name}";
																	dateId = dateId.replace(/\./g, "\\.");
																	$("#"+dateId).datepicker({
																			showOn : "button",
																			buttonImage : '${url_context}<spring:theme code="datepicker"/>',
																			buttonImageOnly : true,
																	});
														</script>
											</c:when>
	        								<c:when test="${parameter.type=='dropdown'}">
								        				<select name="${parameter.name}">
								        							<c:forEach items="${parameter.properties}" var="option">
									        									<option value="${option.key}">${option.value}
								        							</c:forEach>
								        				</select><span class="required">*</span>
	        								</c:when>
						        			<c:when test="${parameter.type=='checkbox'}">
														<input type="hidden" name="${parameter.name}" value="" />
							        					<c:forEach items="${parameter.properties}" var="option">
																	<input type="checkbox" name="${parameter.name}" value="${option.key}" /> ${option.value}<br/>
							        					</c:forEach>
							        					<span class="required">*</span>
						        			</c:when>
						        				<c:when test="${parameter.type=='prompt.command'}">
						        				 <c:forEach items="${parameter.properties}" var="option">
						        				        		${option.value.html}
						        				    </c:forEach>
						        				    <span class="required">*</span>
						        				</c:when>
						        			<c:when test="${parameter.name eq 'validator'}">
						        				<c:forEach items="${parameter.properties}" var="option">
						        				        	<input type="hidden" value="${option.value}" name="validator" />
						        				 </c:forEach>
						        			</c:when>		
						        			<c:otherwise>
							        					<input type="text" name="${parameter.name}" /><span class="required">*</span> 
						        			</c:otherwise>
	        						</c:choose>
	        						  
	        					</div>
	        			</c:forEach>
	        			
			        	<div class="form-row">
			        			<label class="special">&nbsp;</label>
			        			<input type="submit" value="Submit" />
			        			<input type="button" value="<spring:message code="label.back" text="Back"/>" onclick="window.location='reports-view.jspx'" />
			        	</div>
	        	
	        	</form>
	        </div>                                
		</div><!-- END OF main -->
	</div><!-- END OF yui-g -->
</div>
<idy:footer />


