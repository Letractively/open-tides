<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>

<idy:header title_webpage="label.widget">
  	<script src="${url_jquery}"></script>
  	<script src="${url_context}/script/ideyatech-${ot_version}/jquery.form.js"></script> 
  	<script src="${url_script}/crud.js"></script>
</idy:header>
    <!-- BODY -->
    <div id="bd">
        <div class="yui-g">
        <div class="main" id="widget">
            <div class="title-wrapper">
                <div class="title" id="widget-title"><span><spring:message code="label.widget" /></span></div>
            </div> 
            
            <div class="contents">
          	            <div id="search-criteria">
					<form:form commandName="widget" id="widgetSearch" action="${url_context}/admin/widget.jspx">
						<p><label for="name"><spring:message code="label.widget.name" />: </label>
	<form:input path="name" /> </p>

						<p><label for="title"><spring:message code="label.widget.title" />: </label>
	<form:input path="title" /> </p>
	
						<p><label for="url"><spring:message code="label.widget.url" />: </label>
	<form:input path="url" /> </p>

						<p><label for="accessCode"><spring:message code="label.widget.accessCode" />: </label>
	<form:input path="accessCode" /> </p>

					<ot:sort_input searchFormId="widgetSearch"/>
					<input type="submit" name="Submit_" value="<spring:message code="label.submit" />" />					
					</form:form>
				</div>
				<hr class="broad"/>
            	<table class="admin-table">
            		<thead>
            		<tr>
						<th><ot:header_sort headerField="name" headerLabel="label.widget.name" prefix="${ widget }" searchFormId="widgetSearch"/></th>
						<th><ot:header_sort headerField="title" headerLabel="label.widget.title" prefix="${ widget }" searchFormId="widgetSearch"/></th>
						<th><ot:header_sort headerField="isShown" headerLabel="label.widget.isShown" prefix="${ widget }" searchFormId="widgetSearch"/></th>
						<th><ot:header_sort headerField="url" headerLabel="label.widget.url" prefix="${ widget }" searchFormId="widgetSearch"/></th>
						<th><ot:header_sort headerField="accessCode" headerLabel="label.widget.accessCode" prefix="${ widget }" searchFormId="widgetSearch"/></th>
						<th><ot:header_sort headerField="cacheDuration" headerLabel="label.widget.cacheDuration" prefix="${ widget }" searchFormId="widgetSearch"/></th>
						<th><ot:header_sort headerField="lastCacheUpdate" headerLabel="label.widget.lastCacheUpdate" prefix="${ widget }" searchFormId="widgetSearch"/></th>
            			<th>&nbsp;</th>
            		</tr>
            		</thead>
            		<tbody id="widget-table-results">
            		<c:forEach items="${results.results}" var="record" varStatus="status">
            		<tr id="widget-row-${ record.id }" class="row-style-${ status.count % 2 }">
						<td><c:out value="${ record.name }" /></td>
						<td><c:out value="${ record.title }" /></td>
						<td><c:out value="${ record.isShown == true ? 'Yes' : 'No' }" /></td>
						<td><c:out value="${ record.url }" /></td>
						<td><c:out value="${ record.accessCode }" /></td>
						<td><c:out value="${ record.cacheDuration }" /></td>
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
	            <ot:add_button page="admin/widget.jspx" prefix="widget"/>
	            <ot:paging results="${ results }" baseURL="admin/widget.jspx" pageParamName="page" displaySummary="false" displayPageLinks="true" searchFormId="widgetSearch"/>	            	
        	</div>
        </div>
        </div>    
    </div>
    <!-- END OF BODY -->
<!-- FOOT -->
<idy:footer /> 