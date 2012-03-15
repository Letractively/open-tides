
<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>

<idy:header title_webpage="label.report">
  <script src="${url_jquery}"></script>
  <script src="${url_context}/script/ideyatech-${ot_version}/jquery.form.js"></script>
  <script src="${url_context}/script/ideyatech-${ot_version}/crud.js"></script>
</idy:header>
    <!-- BODY -->
    <div id="bd">
        <div class="yui-g">
        <div class="main" id="report">
            <div class="title-wrapper">
                <div class="title" id="report-title"><span><spring:message code="label.report" /></span></div>
            </div> 
            
            <div class="contents">
    	            <div id="search-criteria">
					<form:form commandName="report" id="reportSearch" action="${url_context}/admin/report.jspx">
						<p><label for="name"><spring:message code="label.report.name" />: </label>
	<form:input path="name" /> </p>

						<p><label for="accessCode"><spring:message code="label.report.accessCode" />: </label>
	<form:input path="accessCode" /> </p>

					<ot:sort_input searchFormId="reportSearch"/>
					<input type="submit" name="Submit_" value="<spring:message code="label.submit" />" />	
					<input type="button" name="clear" value="Clear" onclick="clearSearchPane()"/>				
					</form:form>
				</div>
				<hr class="broad"/>
            	<table class="admin-table">
            		<thead>
            		<tr>
						<th><ot:header_sort headerField="name" headerLabel="label.report.name" prefix="${ report }" searchFormId="reportSearch"/></th>
						<th><ot:header_sort headerField="description" headerLabel="label.report.description" prefix="${ report }" searchFormId="reportSearch"/></th>
						<th><ot:header_sort headerField="accessCode" headerLabel="label.report.accessCode" prefix="${ report }" searchFormId="reportSearch"/></th>
            			<th>&nbsp;</th>
            		</tr>
            		</thead>
            		<tbody id="report-table-results">
            		<c:forEach items="${results.results}" var="record" varStatus="status">
            		<tr id="report-row-${ record.id }" class="row-style-${ status.count % 2 }">
						<td><c:out value="${ record.name }" /></td>
						<td><c:out value="${ record.description }" /></td>
						<td><c:out value="${ record.accessCode }" /></td>
                  		<td>
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
	            <ot:add_button page="admin/report.jspx" prefix="report"/>
	            <ot:paging results="${ results }" baseURL="admin/report.jspx" pageParamName="page" displaySummary="false" displayPageLinks="true" searchFormId="reportSearch"/>	            	
        	</div>
        </div>
        </div>    
    </div>
    <!-- END OF BODY -->
<!-- FOOT -->
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