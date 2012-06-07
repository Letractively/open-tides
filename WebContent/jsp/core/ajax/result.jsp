<%@page contentType="text/html; charset=UTF-8"%>

<%
	Object result = request.getAttribute("result");
	out.print(result);
    out.flush();
%>