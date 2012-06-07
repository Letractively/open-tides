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
  	<script type="text/javascript">
  		$(document).ready(function() {
  			$('#report-form').submit(function() {
  				$('.error-box').css('display', 'none');
  			});
  		});
  	</script>
</idy:header>

<!-- BODY -->

<div class="yui-b prompt-parameter">

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
	        	
	    <div class="content-wrapper"> 
                    <div class="search-criteria report-parameter">
	        		<div class="form-row"><spring:message code="label.please-fill-up-the-following" /></div>
	        		<c:if test="${not empty errorMessages}">
	        		       <div class="error-box">
	        		           <c:forEach items="${errorMessages}" var="message">
	        		           			<c:out value="${message}"/><br>
	        		           </c:forEach>
	        		       </div>
	        		</c:if>
	        		<form id="report-form">
	        		        <c:forEach items="${requestParameters}" var="entry">
	        						<input type="hidden" name="${entry.key}"  value='<c:forEach items="${entry.value}" var="value" varStatus="status"><c:if test="${status.count>1}">,</c:if>${value}</c:forEach>'/>
	        				        <input type="hidden" name="requestParameters" value="${entry.key}"/>
	        				</c:forEach>
	        	
	        				<c:forEach items="${missingParameters}" var="parameter">
	        				            <c:choose>
		        				               <c:when test="${parameter.name == 'validator'}">
		        				               		<div class="form-row" style="display:none;"><label for="${parameter.name}">${parameter.label}</label>
		        				               </c:when>
		        				               <c:when test="${parameter.name == 'userSections'}">
		        				               		<div class="form-row" style="display:none;"><label for="${parameter.name}">${parameter.label}</label>
		        				               </c:when>
		        				               <c:otherwise>
		        				               		<div class="form-row"><label for="${parameter.name}">${parameter.label}</label>
		        				               </c:otherwise>
	        				            </c:choose>
	        				            <c:choose>
        							       <c:when test="${parameter.clazz=='java.util.Date'}">
													<div class="L">
																<input type="text" id="${parameter.name}"  name="${parameter.name}" class="num-date" onclick="$(this).datepicker('show'); $(this).blur();"/>
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
																		yearRange: "c-45:+5",
																		changeMonth : true,
																		changeYear : true
																});
													</script>
											</c:when>
	        								<c:when test="${parameter.type=='dropdown'}">
								        				<select name="${parameter.name}" id="${parameter.name}">
								        							<c:forEach items="${parameter.properties}" var="option">
									        									<option value="${option.key}">${option.value}
								        							</c:forEach>
								        				</select><span class="required">*</span>
	        								</c:when>
						        			<c:when test="${parameter.type=='checkbox'}">
														<input type="hidden" name="${parameter.name}" value="-1" />
							        					<div class="L">
							        					<c:forEach items="${parameter.properties}" var="option">
																	<input type="checkbox" name="${parameter.name}" value="${option.key}" checked/> ${option.value}<br/>
							        					</c:forEach>
							        					</div>
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
			        			<input type="reset" value="Clear" />
			        			<input type="button" value="<spring:message code="label.back" />" onclick="window.location='reports-view.jspx'" />
			        	</div>
			        	
	        	</form>
	        </div>                                	
		</div><!-- END OF main -->
</div>
<idy:footer />
