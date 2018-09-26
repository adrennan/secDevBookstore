<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
</head>
<body>
	<header>
		<img src="http://placehold.it/280x80" alt="company logo">
		<h1>Register | Dining Philosophers Bookstore</h1>
	</header>
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