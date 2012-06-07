<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>

<idy:header title_webpage="label.widget">
</idy:header>

<!-- BODY -->
<div class="yui-b widget" id="widget">
	<div class="title-wrapper">
	    <spring:message code="label.widget" />
	</div>

    <div class="content-wrapper">
    	<idy:form-instruction formName="widgetSearchForm"/>
    	<div id="search-criteria">
			<form:form commandName="widget" id="widgetSearch" action="${url_context}/admin/widget.jspx">
				<div class="form-row"> 
                	<form:label path="name" cssErrorClass="highlight-error"><spring:message code="label.widget.name" />: </form:label>
                    <form:input path="name" />
                    <idy:tool-tip formName="widgetSearchForm" attributeName="name"/> 
               	</div>

				<div class="form-row"> 
                	<form:label path="title" cssErrorClass="highlight-error"><spring:message code="label.widget.title" />: </form:label>
                    <form:input path="title" cssErrorClass="highlight-error"/>
                    <idy:tool-tip formName="widgetSearchForm" attributeName="title"/> 
                </div>

				<div class="form-row"> 
                	<form:label path="url" cssErrorClass="highlight-error"><spring:message code="label.widget.url" />: </form:label>
                    <form:input path="url" cssErrorClass="highlight-error"/>
                    <idy:tool-tip formName="widgetSearchForm" attributeName="name"/> 
                </div>

				<div class="form-row">
                    <form:label path="accessCode" cssErrorClass="highlight-error"><spring:message code="label.widget.accessCode" />: </form:label>
                    <form:input path="accessCode" cssErrorClass="highlight-error"/>
                    <idy:tool-tip formName="widgetSearchForm" attributeName="accessCode"/> 
                </div>
				
				<div class="form-row">
					<ot:sort_input searchFormId="widgetSearch"/>
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
              		<div class="pagingSummary">
                   	   <ot:paging results="${results}" baseURL="/admin/widget.jspx" pageParamName="page" displaySummary="true" displayPageLinks="false" />
                  	</div>
              	</div>
              
              	<div class="R">
                  	<ot:add_button page="admin/widget.jspx" prefix="widget"/>
              	</div>
              
              	<div class="clear"></div>
              
          	</div>
			
			<div class="search-results-list">
	           	<table class="admin-table">
	           		<thead>
	           		<tr>
						<th class="col-1"><ot:header_sort headerField="name" headerLabel="label.widget.name" prefix="${ widget }" searchFormId="widgetSearch"/></th>
						<th class="col-2"><ot:header_sort headerField="title" headerLabel="label.widget.title" prefix="${ widget }" searchFormId="widgetSearch"/></th>
						<th class="col-3"><ot:header_sort headerField="isShown" headerLabel="label.widget.isShown" prefix="${ widget }" searchFormId="widgetSearch"/></th>
						<th class="col-4"><ot:header_sort headerField="lastCacheUpdate" headerLabel="label.widget.lastCacheUpdate" prefix="${ widget }" searchFormId="widgetSearch"/></th>
	           			<th class="col-5">&nbsp;</th>
	           		</tr>
	           		</thead>
	           		<tbody id="widget-table-results">
		           		<c:forEach items="${results.results}" var="record" varStatus="status">
			           		<tr id="widget-row-${ record.id }" class="row-style-${ status.count % 2 }">
								<td><c:out value="${ record.name }" /></td>
								<td><c:out value="${ record.title }" /></td>
								<td><c:out value="${ record.isShown == true ? 'Yes' : 'No' }" /></td>
								<td><fmt:formatDate value="${ record.lastCacheUpdate }" dateStyle="short" type="DATE" pattern="MMM dd, yyyy hh:mm:ss" /></td>
		                 		<td>
									<ot:update_button id="${record.id}" page="admin/widget.jspx" prefix="widget"/>
			                   		<ot:delete_button id="${record.id}" page="admin/widget.jspx" title="${ record.name }" prefix="widget"/>                  		
		                 		</td>	
			           		</tr>
		           		</c:forEach>
	           		</tbody>
	           		<tr id="widget-row-new">
						<td colspan="9">
	           			</td>
	           		</tr>
	           	</table>
          	</div>
        </div>
        <ot:paging results="${ results }" baseURL="widget.jspx" pageParamName="page" displaySummary="false" displayPageLinks="true" searchFormId="widgetSearch"/>	            	
	</div>
</div>
<!-- END OF BODY -->
<!-- FOOT -->
<idy:footer>
	<script type="text/javascript">
		function clearSearchPane(){
			var searchForm = document.getElementById('widgetSearch');
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