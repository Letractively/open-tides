


<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>

<idy:header title_webpage="label.user-defined-field">
  <script src="${url_jquery}"></script>
  <script src="${url_context}/script/ideyatech-${ot_version}/crud.js"></script>
</idy:header>
    <!-- BODY -->
    <div id="bd">
        <div class="yui-g">
        <div class="main" id="user-defined-field">
            <div class="title-wrapper">
                <div class="title" id="user-defined-field-title"><span><spring:message code="label.user-defined-field" /></span></div>
            </div> 
            
            <div class="contents">
      	            <div id="search-criteria">
					<form:form commandName="userDefinedField" id="userDefinedFieldSearch" action="${url_context}/admin/user-defined-field.jspx">
						<div class="form-row">
                            <label for="clazz"><spring:message code="label.user-defined-field.clazz" />: </label>
	                        <form:select path="clazz" multiple="false"  ><form:option value="" /><form:options items="${clazzList}" /></form:select>
                        </div>
						<div class="form-row">
                            <label for="userField"><spring:message code="label.user-defined-field.userField" />: </label>
	                        <form:select path="userField" multiple="false"  ><form:option value="" /><form:options items="${userFieldList}" /></form:select> 
                        </div>
						<div class="form-row">
                            <label for="label"><spring:message code="label.user-defined-field.label" />: </label>
	                        <form:input path="label" />
                        </div>
    					<ot:sort_input searchFormId="userDefinedFieldSearch"/>
    					<input type="submit" name="Submit_" value="<spring:message code="label.submit" />" />
    					<input type="button" name="clear" value="Clear" onclick="clearSearchPane()"/>									
					</form:form>
				</div>

				<hr class="broad"/>
            	<table class="admin-table">
            		<thead>
            		<tr>
                        <th><ot:header_sort headerField="order" headerLabel="label.user-defined-field.order" prefix="${ userDefinedField }" searchFormId="userDefinedFieldSearch"/></th>
						<th><ot:header_sort headerField="clazz" headerLabel="label.user-defined-field.clazz" prefix="${ userDefinedField }" searchFormId="userDefinedFieldSearch"/></th>
						<th><ot:header_sort headerField="userField" headerLabel="label.user-defined-field.userField" prefix="${ userDefinedField }" searchFormId="userDefinedFieldSearch"/></th>
						<th><ot:header_sort headerField="label" headerLabel="label.user-defined-field.label" prefix="${ userDefinedField }" searchFormId="userDefinedFieldSearch"/></th>
            			<th>&nbsp;</th>
            		</tr>
            		</thead>
            		<tbody id="user-defined-field-table-results">
            		<c:forEach items="${ results.results }" var="record" varStatus="status">
            		<tr id="user-defined-field-row-${ record.id }" class="row-style-${ status.count % 2 }">
                        <td><c:out value="${ record.order }" /></td>
						<td><c:out value="${ record.clazz.simpleName }" /></td>
						<td><c:out value="${ record.userField }" /></td>
						<td><c:out value="${ record.label }" /></td>
                  		<td>
							<ot:update_button id="${record.id}" page="admin/user-defined-field.jspx" prefix="user-defined-field" />
                    		<ot:delete_button id="${record.id}" page="admin/user-defined-field.jspx" title="${ record.label }" prefix="user-defined-field"/>                  		
                  		</td>	
            		</tr>

            		</c:forEach>
            		</tbody>
            		<tr id="user-defined-field-row-new">
						<td colspan="4">
            			</td>
            		</tr>
            	</table>
	            <ot:add_button page="admin/user-defined-field.jspx" prefix="user-defined-field" />
            	
	            <ot:paging results="${ results }" baseURL="/admin/user-defined-field.jspx" pageParamName="page" displaySummary="false" displayPageLinks="true" searchFormId="userDefinedFieldSearch"/>	            	
        	</div>
        </div>
        </div>    
    </div>
    <!-- END OF BODY -->
<!-- FOOT -->
<idy:footer>
	<script type="text/javascript">
		function clearSearchPane(){
			var searchForm = document.getElementById('userDefinedFieldSearch');
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