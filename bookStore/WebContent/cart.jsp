<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="support.functions.CartManager"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dining Philosophers Bookstore | Cart</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
	<header>
	<h1>Dining Philosophers Bookstore | Cart</h1>
	</header>
	<br>
	<div class="cart">
		<table>
			<tr>
			<% CartManager cart = new CartManager();
			out.print(cart.getCartInfo());
			%>
			</tr>
		</table>
		<form action="checkout.jsp">
		<input type="button" >
		</form>
	</div>
</body>
</html>