
<%@page import="java.util.List"%>
<%@page import="in.co.raystech.controller.ChangePasswordCtl"%>
<%@page import="in.co.raystech.utility.DataUtility"%>
<%@page import="in.co.raystech.utility.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Change password</title>

<style>
.p1 {
	font-size: 18px;
}

.p2 {
	padding: 5px;
	margin: 3px;
}
</style>
</head>
<body style="background-color: rgb(241, 19, 149);">
	<center>
		<form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="POST">
			<%@include file="Header.jsp"%>
			<jsp:useBean id="bean" class="in.co.raystech.dto.UserDTO"
				scope="request"></jsp:useBean>
			<br>
			<br>
			<br>
			<h1>Change Password</h1>
			<div>
				<%
					List list = (List) request.getAttribute("roleList");
				%>

				<H4 align="center">
					<%
						if (!ServletUtility.getSuccessMessage(request).equals("")) {
					%>
					<div class="alert alert-success alert-dismissible">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<%=ServletUtility.getSuccessMessage(request)%>
					</div>
					<%
						}
					%>
				</H4>

				<H4 align="center">
					<%
						if (!ServletUtility.getErrorMessage(request).equals("")) {
					%>
					<div class="alert alert-danger alert-dismissible">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<%=ServletUtility.getErrorMessage(request)%>
					</div>
					<%
						}
					%>

				</H4>
				<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
					type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
				<input type="hidden" name="modifiedBy"
					value="<%=bean.getModifiedBy()%>"> <input type="hidden"
					name="createdDatetime" value="<%=bean.getCreatedDatetime()%>">
				<input type="hidden" name="modifiedDatetime"
					value="<%=bean.getModifiedDatetime()%>"> <br>
				<br>
				<br>
				<br>
				<table>
					<tr>
						<th>Old Password<span style="color: red;">*</span></th>
						<th><input type="password" name="oldpassword" class="p2"
							size="30" placeholder="Enter Old Password"
							value=<%=DataUtility.getString(request.getParameter("oldpassword") == null ? ""
					: DataUtility.getString(request.getParameter("oldpassword")))%>></th>
						<th style="position: fixed;"><font color="yellow"><%=ServletUtility.getErrorMessage("oldpassword", request)%></font>
						</th>
					</tr>
					<tr>
						<th>New Password<span style="color: red;">*</span></th>
						<td><input type="password" name="newpassword" class="p2"
							size="30" placeholder="Enter New Password"
							value=<%=DataUtility.getString(request.getParameter("newpassword") == null ? ""
					: DataUtility.getString(request.getParameter("newpassword")))%>></td>
						<td style="position: fixed;"><font color="yellow"><%=ServletUtility.getErrorMessage("newpassword", request)%></font>
						</td>
					</tr>
					<tr>
						<th>Confirm Password<span style="color: red;">*</span></th>
						<td><input type="password" name="confirmpassword" class="p2"
							size="30" placeholder="Enter Confirm password"
							value=<%=DataUtility.getString(request.getParameter("confirmpassword") == null ? ""
					: DataUtility.getString(request.getParameter("confirmpassword")))%>></td>
						<td style="position: fixed;"><font color="yellow"><%=ServletUtility.getErrorMessage("confirmpassword", request)%></font>
						</td>
					</tr>



					<tr>
						<th></th>
						<td colspan="2" align="center"><input type="submit"
							name="operation" value="<%=ChangePasswordCtl.OP_SAVE%>"
							style="padding: 5px;"> &nbsp; <input type="submit"
							name="operation"
							value="<%=ChangePasswordCtl.OP_CHANGE_MY_PROFILE%>"
							style="padding: 5px;"> &nbsp; &nbsp;<input type="submit"
							name="operation" value="<%=ChangePasswordCtl.OP_RESET%>"
							style="padding: 5px;""></td>
					</tr>
				</table>

				<%@include file="FooterView.jsp"%>
		</form>
	</center>
</body>
</html>