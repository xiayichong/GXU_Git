<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
%>

<!DOCTYPE HTML>
<html>
  <head>
    <%@include file="/common/header.jsp"%>
    <title>年度投诉统计图</title>
  </head>
  
  <body>
  	<br>
    <s:select id="year" list="{2015}" onchange="doAnnualStatistic()"></s:select>
    <br>
    <div id="chartContainer"></div>
  </body>
</html>
