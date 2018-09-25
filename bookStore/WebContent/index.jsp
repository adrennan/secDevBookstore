<%@ page import="java.sql.*" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="bookStore.DbManager" %>
<%@ page import="java.sql.Connection" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Test Index</title>
</head>
<body>

	<header>
		<img src="http://placehold.it/280x80" alt="company logo">
		<h1>Change this shit later Bookstore</h1>
	</header>
	
	<section>
		
		<%
		//clean out session info
		String[] sessAttributes = {"user", "email", "address", "cc", "cart"};
		for(int i = 0; i < sessAttributes.length; i++){
			if(session.getAttribute(sessAttributes[i]) != null){
				session.removeAttribute(sessAttributes[i]);	
			}	
		}
		
		%>
		<p>Please Login or Register</p>
		<form action = "verifyLogin.jsp" method = "POST">
			<input type="text" name="user" placeholder="username"><br>
			<input type="password" name="password" placeholder="password"><br>
			<br>
			<input type="submit" value="login">
		</form>
		
		<a href="register.jsp">Register</a>
</body>
</html>