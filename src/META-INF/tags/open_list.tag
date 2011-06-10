<%--
    - open_list
    - 
    - Checks if an opening <ul> should be created.
    -
    - The notation is as follows:
    -
    - 01.00.00
    -     01.01.00 
    -         01.01.01  
    -         01.01.02 
    -     01.02.00
    -         01.02.01
    -         01.02.02
    -         01.02.03
    - 02.00.00
    -     02.01.00 
    -   
    - @param prev - previous stage
    - @param curr - current stage
    --%>
    
<%@ tag isELIgnored="false" body-content="empty" %>
<%@ attribute name="prev" required="true" type="java.lang.String" %>
<%@ attribute name="curr" required="true" type="java.lang.String" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pStage" value="${fn:substring(prev,0,8)}"/>
<c:set var="pList"  value="${fn:split(pStage, '.')}"/>
<c:set var="cStage" value="${fn:substring(curr,0,8)}"/>
<c:set var="cList"  value="${fn:split(cStage, '.')}"/>
<c:set var="ctr"    value="0"/>
<c:forEach begin="0" end="2" step="1" var="i">
    <c:if test="${not ('00' eq cList[i]) && ('00' eq pList[i] || empty pList[i]) }">
        <ul>
    </c:if>
</c:forEach>