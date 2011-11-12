<%@ page contentType="text/html;utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>

<idy:header title_webpage="${title}" />
    <!-- BODY -->
    <div id="bd">
        <div class="yui-main">
        <div class="yui-b">
            <div class="title-wrapper">
                <span><spring:message code="${title}" /></span>
            </div>            
            <div class="content-wrapper">
                <spring:message code="${message}"/>
            </div>
        </div>
        </div>
    </div>
    <!-- END OF BODY -->
<idy:footer/>

