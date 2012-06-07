<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>

<%@page import="com.attache.bean.core.JSONKeyValue"%>
  
<%
	JSONArray Jsresults=new JSONArray();
	List<JSONKeyValue> results = (List<JSONKeyValue>) request.getAttribute("results");
	
	for (JSONKeyValue record:results) {
		Jsresults.add(record.toJSONObject());
	}	
	out.print(Jsresults);
    out.flush();
%>