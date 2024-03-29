<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>
<idy:header title_webpage="label.system-codes">
  <script type="text/javascript">
  	function checkNewCategory(fromDropdown) {
 		var dropDownHTML  ='<label for="category"><spring:message code="label.category" /></label><select name="category" onclick="javascript:checkNewCategory(true);"><option value="0">Select a Category</option><option value="0">-- New Category --</option><c:forEach items="${categories}" var="record" varStatus="status"><option>${record.category}</option></c:forEach></select>';
 		var textFieldHTML ='<label for="category"><spring:message code="label.category" /></label><input type="text" name="category" id="categoryText"/> <a href="javascript:void(0)" onclick="javascript:checkNewCategory(false)">Cancel</a>';
 		var ni = document.getElementById('categorySelector');
  		if (fromDropdown) {
  			// check if "New Category" is selected
  			var select = document.getElementById('categorySelect');
	  		if (select.selectedIndex==1)
	  			ni.innerHTML = textFieldHTML;
  		} else
  			ni.innerHTML = dropDownHTML;
	}
  	function clearSearchPane(){
		var searchForm = document.getElementById('systemCodeSearch');
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
</idy:header>

<div class="yui-b system-codes" id="systemcodes">

    <div class="title-wrapper" id="systemcodes-title"><spring:message code="label.system-codes" /></div> 
    
    <div class="content-wrapper">
    	<idy:form-instruction formName="systemCodesSearchForm"/>
        <div id="search-criteria" class="search-criteria">
        <form:form commandName="systemCode" id="systemCodeSearch" action="${url_context}/admin/system-codes.jspx">
            
            <div class="form-row"> 
                <form:label path="category" cssErrorClass="highlight-error"><spring:message code="label.category" /></form:label>
                <form:select path="category" cssErrorClass="highlight-error">
                    <form:option value="">Select a Category</form:option>
                    <form:options items="${categories}"/>
                </form:select>
                <idy:tool-tip formName="systemCodesSearchForm" attributeName="name"/>
            </div>
            
            <div class="form-row"> 
                <form:label path="key" cssErrorClass="highlight-error"><spring:message code="label.key" /></form:label>
                <form:input path="key" maxlength="50" cssErrorClass="highlight-error"/>
                <idy:tool-tip formName="systemCodesSearchForm" attributeName="key"/>
            </div>
            
            <div class="form-row"> 
                <form:label path="value" cssErrorClass="highlight-error"><spring:message code="label.value" /></form:label>
                <form:input path="value" maxlength="128" cssErrorClass="highlight-error"/>
                <idy:tool-tip formName="systemCodesSearchForm" attributeName="value"/>
            </div>
            
	        <div class="form-row"> 
	            <form:label path="ownerOffice" cssErrorClass="highlight-error"><spring:message code="label.override-office" /> </form:label>
	            <form:select path="ownerOffice" cssErrorClass="highlight-error">
	                <form:option value=""></form:option>
		        	<form:options items="${officeList}" itemValue="key" itemLabel="value" />
				</form:select>
				<idy:tool-tip formName="systemCodesSearchForm" attributeName="ownerOffice"/>
	        </div>						
            
            <ot:sort_input searchFormId="systemCodeSearch"/>
            
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
                	<div class="pagingSummary">
                        <ot:paging results="${results}" baseURL="/admin/system-codes.jspx" pageParamName="page" displaySummary="true" displayPageLinks="false" />
                    </div>
                </div>
                
                <div class="R">
                    <ot:add_button page="admin/system-codes.jspx" prefix="system"/>
                </div>
                
                <div class="clear"></div>
                
            </div>
            
            <div class="search-results-list">
            <table border="1">
                <thead>
                <tr>
                    <th class="col-1"><ot:header_sort headerField="category" headerLabel="label.category" prefix="${systemCode}" searchFormId="systemCodeSearch"/></th>
                    <th class="col-2"><ot:header_sort headerField="key" headerLabel="label.key" prefix="${systemCode}" searchFormId="systemCodeSearch"/></th>
                    <th class="col-3"><ot:header_sort headerField="value" headerLabel="label.value" prefix="${systemCode}" searchFormId="systemCodeSearch"/></th>
                    <th class="col-4">&nbsp;</th>
                </tr>
                </thead>
                <tbody id="system-table-results">
                <c:forEach items="${results.results}" var="record" varStatus="status">
                <tr id="system-row-${record.id}" class="row-style-${status.count%2}">
                    <td class="col-1"><c:out value="${record.category}" /></td>
                    <td class="col-2"><c:out value="${record.key}" /></td>
                    <td class="col-3"><c:out value="${record.value}" /></td>
                    <td class="col-4">
                        <ot:update_button id="${record.id}" page="admin/system-codes.jspx" prefix="system"/>
                        <ot:delete_button id="${record.id}" page="admin/system-codes.jspx" title="${record.key}" prefix="system"/>                  		
                    </td>	
                </tr>
                </c:forEach>
                </tbody>
                <tr id="system-row-new">
                    <td colspan="4"></td>
                </tr>
                
            </table>
            </div>
            
            <div class="search-results-footer">
        		<div class="numbers">
                    <ot:paging results="${results}" baseURL="${url_context}/admin/system-codes.jspx" pageParamName="page" displaySummary="false" displayPageLinks="true" searchFormId="systemCodeSearch"/>
                </div>
            </div>

        </div>           	
    </div>    
</div>

<idy:footer>
</idy:footer>