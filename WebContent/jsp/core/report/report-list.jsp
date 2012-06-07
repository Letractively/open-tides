
<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>

<idy:header title_webpage="label.report">
</idy:header>

<div class="yui-b reports" id="report">

    <div class="title-wrapper" id="report-title">
        <span><spring:message code="label.report" /></span>
    </div> 
    
    <div class="content-wrapper">
    	<idy:form-instruction formName="reportSearchForm"/>
        <div id="search-criteria" class="search-criteria" >
        <form:form commandName="report" id="reportSearch" action="${url_context}/admin/report.jspx">
        
            <div class="form-row">
                <form:label path="name" cssErrorClass="highlight-error"><spring:message code="label.report.name" /></form:label>
                <form:input path="name" cssErrorClass="highlight-error"/> 
                <idy:tool-tip formName="reportSearchForm" attributeName="name"/>
            </div>

            <div class="form-row">
            	<form:label path="accessCode" cssErrorClass="highlight-error"><spring:message code="label.report.accessCode" /></form:label>
            	<form:input path="accessCode" cssErrorClass="highlight-error"/> 
            	<idy:tool-tip formName="reportSearchForm" attributeName="accessCode"/>
            </div>

            <ot:sort_input searchFormId="reportSearch"/>
            <div class="form-row"> 
                <label class="special">&nbsp;</label>
                <input type="submit" name="Submit_" value="<spring:message code="label.submit" />" />	
                <input type="button" name="clear" value="Clear" onclick="clearSearchPane()"/>				
            </div>
        
        </form:form>
        </div>
        
        <div class="separator"></div>
        
        <div class="search-results">
        
            <div class="search-results-header">
            
            	<div class="L">
                    <ot:paging results="${results}" baseURL="admin/report.jspx" pageParamName="page" displaySummary="true" displayPageLinks="false" />
                </div>
            
            	<div class="R">
                	<ot:add_button page="admin/report.jspx" prefix="report"/>
                </div>
                
                <div class="clear"></div>
            
            </div>
            
            <div class="search-results-list">
            <table border="1">
                <thead>
                <tr>
                    <th class="col-1"><ot:header_sort headerField="name" headerLabel="label.report.name" prefix="${ report }" searchFormId="reportSearch"/></th>
                    <th class="col-2"><ot:header_sort headerField="title" headerLabel="label.report.title" prefix="${ report }" searchFormId="reportSearch"/></th>
                    <th class="col-3">&nbsp;</th>
                </tr>
                </thead>
                <tbody id="report-table-results">
                <c:forEach items="${results.results}" var="record" varStatus="status">
                <tr id="report-row-${ record.id }">
                    <td class="col-1"><c:out value="${ record.name }" /></td>
                    <td class="col-2"><c:out value="${ record.title }" /></td>
                    <td class="col-3">
                        <ot:update_button id="${record.id}" page="admin/report.jspx" prefix="report"/>
                        <ot:delete_button id="${record.id}" page="admin/report.jspx" title="${ record.name }" prefix="report"/>                  		
                    </td>	
                </tr>

                </c:forEach>
                </tbody>
                <tr id="report-row-new">
                    <td colspan="4">
                    </td>
                </tr>
            </table>
            </div>
            
            <div class="search-results-footer">
            
            	<div class="numbers">
                    <ot:paging results="${ results }" baseURL="report.jspx" pageParamName="page" displaySummary="false" displayPageLinks="true" searchFormId="reportSearch"/>	            	
            	</div>
            
            </div>
            
        </div>
    
    </div>
    
</div>

<idy:footer>
	<script type="text/javascript">
		function clearSearchPane(){
			var searchForm = document.getElementById('reportSearch');
			var formElements = searchForm.elements;
			for (var i = 0; i < formElements.length; i++){
				if (formElements[i].type.toLowerCase() == "text"){
					//for text field element
					formElements[i].value = "";
				}else if (formElements[i].type.toLowerCase() == "select-one"){
					//for select element
					formElements[i].selectedIndex = 0;
				}
			}	
		}
	</script>
</idy:footer> 