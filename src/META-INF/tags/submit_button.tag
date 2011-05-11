<%--
	- submit_button
	- 
	- Creates the submit button for Ajaxified action
	-
	- @param id - the primary key reference
	- @param page - the page to request
	- @param formName - name of form
	--%>
<%@ tag isELIgnored="false" body-content="empty"%>
<%@ attribute name="id" required="true" type="java.lang.Long"%>
<%@ attribute name="page" required="true" type="java.lang.String"%>
<%@ attribute name="prefix" type="java.lang.String"%>
<%@ attribute name="multipart" type="java.lang.String"%>
<%@ attribute name="urlParam" type="java.lang.String"%>
<%@ attribute name="formName" required="true" type="java.lang.String"%>
<%@ attribute name="newPage" required="false" type="java.lang.Boolean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${!newPage}">
    <c:set var='varName' value='${fn:replace(prefix, "-", "_")}' />
    <c:if test="${id>0}">
        <c:if test="${ot_version == '1.0'}">
            <input type="button" value="<spring:message code="label.submit" />"
                onclick="
	    var args_${varName}_row = { 
	    	divId: '${prefix}-row-${id}', 
	    	postdata: 'action=update&codeId=${id}${urlParam}', 
	    	append: 'false', 
	    	formName: '${formName}',
	    	multipart: '<c:out value="${multipart}" default="false"/>'
	    }
	    IDEYATECH.util.submitForm('${url_context}/${page}', args_${varName}_row);"
            />
        </c:if>
    </c:if>
    <c:if test="${id==0}">
        <c:if test="${ot_version == '1.0'}">
            <input type="button" value="<spring:message code="label.submit" />"
                onclick="
	    var args_${varName}_rownew = { 
	    	divId: '${prefix}-table-results',
	    	postdata: 'action=create${urlParam}', 
	    	append: 'true', 
	    	formName: '${formName}',
	    	multipart: '<c:out value="${multipart}" default="false"/>'
	    }
	    IDEYATECH.util.submitForm('${url_context}/${page}', args_${varName}_rownew);"
            />
        </c:if>
    </c:if>
</c:if>

<c:if test="${newPage}">
    <input type="button" value="<spring:message code="label.submit" />"
                onclick="$('#${formName}').submit();"/>
</c:if>