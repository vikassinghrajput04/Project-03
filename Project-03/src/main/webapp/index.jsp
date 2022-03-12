<%@page import="in.co.raystech.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Index Page</title>
<style>
p{
font-size: 35px;
}
body {
	background-image: url('img/edd.jpg');
	background-size:100%;
}
</style>
</head>
<body>
	<h1 align="center">
		<br> <br><br><br><br> <img src="<%=ORSView.APP_CONTEXT%>/img/logo.png"
			width="400" height="80"> <br> <br> 
		<div class="index"><font color="white"><i> <u>Welcome</u> <u>to</u> <u>Online</u> <u>Result</u> <u>System(ORS)</u>
		<br><br> <u> Please</u> <a href="<%=ORSView.INDEX_LOGIN_VIEW%>"><font color="white">Click
				Here</a></i></font></div>
	</h1>
</body>
</html>