<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
	<header>
		<img src="https://i.imgur.com/EfK0Hjp.jpg" alt="company logo" style="width:500px;height:300px;">
		<h1>Register | Dining Philosophers Bookstore</h1>
	</header>
<br>
	<form action="registerUpdate.jsp" method="POST">
		<input type="email" name="email" placeholder="email address"><br>
		<input type="text" name="user" placeholder="username"><br>
		<input type="password" name ="password" placeholder="password"><br>
		<input type="number" name = "cc" placeholder="Credit Card Number"><br>
		<input type="text" name = "address" placeholder="Address" ><br>
		
		<br>
		<input type="submit" value="Register">
		<a href = "index.jsp"><button>Back</button></a>
	</form>
</body>
</html>