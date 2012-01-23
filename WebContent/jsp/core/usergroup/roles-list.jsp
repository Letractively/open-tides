<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="org.json.simple.JSONArray"%>  
<%@page import="org.json.simple.JSONObject"%>

<%
	JSONArray arr=new JSONArray();
	arr.addAll((List<String>)request.getAttribute("roleNames"));
	JSONObject json = new JSONObject();
	json.put("roles", arr);
	out.print(json);
    out.flush();
%>