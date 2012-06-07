


<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>

<idy:header title_webpage="label.user-defined-field">
</idy:header>
    
        <div class="yui-b user-defined-field" id="user-defined-field">
            <div class="title-wrapper" id="user-defined-field-title">
                <span><spring:message code="label.user-defined-field" /></span>
            </div> 
            
            <div class="content-wrapper">
            	<idy:form-instruction formName="userDefinedFieldSearchForm"/>
      	            <div id="search-criteria">
					<form:form commandName="userDefinedField" id="userDefinedFieldSearch" action="${url_context}/admin/user-defined-field.jspx">
						<div class="form-row">
                            <form:label path="clazz" cssErrorClass="highlight-error"><spring:message code="label.user-defined-field.clazz" />: </form:label>
	                        <form:select path="clazz" multiple="false" cssErrorClass="highlight-error">
	                        	<form:option value="" />
	                        	<form:options items="${clazzList}" />
	                        </form:select>
	                        <idy:tool-tip formName="userDefinedFieldSearchForm" attributeName="clazz"/>
                        </div>
						<div class="form-row">
                            <form:label path="userField" cssErrorClass="highlight-error"><spring:message code="label.user-defined-field.userField" />: </form:label>
	                        <form:select path="userField" multiple="false" cssErrorClass="highlight-error">
	                        	<form:option value="" />
	                        	<form:options items="${userFieldList}" />
	                        </form:select> 
	                        <idy:tool-tip formName="userDefinedFieldSearchForm" attributeName="userField"/>
                        </div>
						<div class="form-row">
                            <form:label path="label" cssErrorClass="highlight-error"><spring:message code="label.user-defined-field.label" />: </form:label>
	                        <form:input path="label" cssErrorClass="highlight-error"/>
	                        <idy:tool-tip formName="userDefinedFieldSearchForm" attributeName="label"/>
                        </div>
                        <div class="form-row">
    						<ot:sort_input searchFormId="userDefinedFieldSearch"/>
    						<label class="special">&nbsp;</label>
    						<input type="submit" name="Submit_" value="<spring:message code="label.submit" />" id="userDefinedFieldSearchFormSubmitButton"/>
    						<input type="button" name="clear" value="Clear" onclick="clearSearchPane()" id="userDefinedFieldSearchFormCancelButton"/>
    					</div>									
					</form:form>
				</div>

				<div class="separator"></div>
        		<div class="search-results">
					<div class="search-results-header">
                    	<div class="L">
		                    <ot:paging results="${results}" baseURL="admin/user-defined-field.jspx" pageParamName="page" displaySummary="true" displayPageLinks="false" />
		                </div>
		            
		            	<div class="R">
		                	<ot:add_button page="admin/user-defined-field.jspx" prefix="user-defined-field"/>
		                </div>
		                
		                <div class="clear"></div>
		            
		            </div>
					<div class="search-results-list">
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
							<td colspan="5">
	            			</td>
	            		</tr>
	            	</table>
	            	</div>
	            	<div class="search-results-footer">
	            	<div class="numbers">
		            <ot:paging results="${ results }" baseURL="/admin/user-defined-field.jspx" pageParamName="page" displaySummary="false" displayPageLinks="true" searchFormId="userDefinedFieldSearch"/>
	            	</div>
	            	</div>
	            </div>	            	
        	</div>
        </div>
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