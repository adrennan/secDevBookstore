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
<title>Dining Philosophers Bookstore</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>

	<header>
		<img src="https://i.imgur.com/EfK0Hjp.jpg" alt="company logo" style="width:500px;height:300px;">
		<h1>The Dining Philosophers Bookstore</h1>
	</header>
	<br>
	
	<section>
		
		<%
		//clean out session info
		DbManager db = new DbManager();
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
		</section>
</body>
</html>