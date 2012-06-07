<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>
<c:if test="${frameRedirect == true}">
    <script type="text/javascript">window.top.location="home.jspx";</script>
</c:if>

<script type="text/javascript">
    $(document).ready(function(){
    	disableMasterCheckbox();
        $("#master-checkbox").click(function(){
            var checked_status = this.checked;
            disableMasterCheckbox();
            $("input[name=checkboxes]").each(function(){
                this.checked = checked_status;
            });
        });			
    });
    function disableMasterCheckbox() {
   		var count = document.getElementsByName('checkboxes').length;
   		if(count == 0){
   			$("#master-checkbox").attr("disabled", true);
   		}
   	}
    
    $(window).bind("load", function() { 
		positionFooter();
		$(window).scroll(positionFooter).resize(positionFooter);
	});
	
	function positionFooter() {
		 var winHeight = $(window).height();
		 var headerHeight = $('#hd').height();
		 var footerHeight = $('#ft').height();
		 $('#bd').css("min-height",(winHeight - (headerHeight + footerHeight)));
	}
</script>
		
        
<div class="yui-b dashboard" id="widget">

    <div class="title-wrapper"><spring:message code="label.add-widget-title" /></div>
    
    <div class="content-wrapper">
    
        <div class="widget-list">
        <form:form commandName="userWidgets" id="userWidgets" action="widget-add.jspx">
        <table border="1">
        <thead>

            <tr>
                <th class="col-1"><input type="checkbox" id="master-checkbox" name="master-checkbox "/></th>
                <th class="col-2"><spring:message code="label.widget-name" /></th>
                <th class="col-3"><spring:message code="label.widget-thumbnail" /></th>
                <th class="col-4"><spring:message code="label.widget-description" /></th>
            </tr>
            
        </thead>
        
        <tbody>                     
        <c:forEach items="${widgets}" var="record" varStatus="status">
        
            <tr>
            
                <td class="col-1">
                    <c:if test="${record.installed}">
                        Already Installed
                    </c:if>
                    <c:if test="${!record.installed}">
                        <input type="checkbox" name="checkboxes" value="${record.id}"/>
                    </c:if>
                </td>
                
                <td class="col-2">
                    ${record.title}
                </td>
                
                <td class="col-3" align="center">
                    <c:choose>	
                        <c:when test="${not empty record.files}">
                            <c:forEach items = "${record.files}" var="fileInfo">
                            <img src="view-image.jspx?FileInfoId=${fileInfo.id}" width="150"/>
                            </c:forEach>	
                        </c:when>
                        <c:otherwise>
                                        
                        <c:if test="${fn:contains(record.name,'Column')}">
 								<img src="${url_context }<spring:theme code='column-graph'/>" width="128" height="128"/>						
						</c:if>
						<c:if test="${fn:contains(record.name,'Bar')}">
								 <img src="${url_context }<spring:theme code='bar-graph'/>" width="128" height="128"/>
						</c:if>
						<c:if test="${fn:contains(record.name,'Pie')}">
								 <img src="${url_context }<spring:theme code='pie-chart'/>" width="128" height="128"/>						
						</c:if>
						<c:if test="${fn:contains(record.name,'List')}">
								 <img src="${url_context }<spring:theme code='list-widget'/>" width="128" height="128"/>						
						</c:if>
						</c:otherwise>
                    </c:choose>
                </td>
                
                <td class="col-4"> 
                    ${record.description}
                </td>
                
            </tr>
        
        </c:forEach>
        </tbody>                     
        </table>
        
        <div id="save-button" class="button-wrapper">
            <input type="hidden" id="mergedCheckboxes" name="mergedCheckboxes" value="">
            <input class="button" type="button" value="Save" onclick="IDEYATECH.checkbox.mergeSubmit('userWidgets','mergedCheckboxes')" />
            <input class="button" type="button" value="Cancel" onclick="top.location='home.jspx'" />
        </div>
        
        </form:form>
        </div>
        
    </div>
    
</div><!-- END OF YUI-B -->