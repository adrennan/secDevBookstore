<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dining Philosophers Bookstore | Checkout</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
	<header>
	<h1>Dining Philosophers Bookstore | Checkout</h1>
	</header>
	<br>
	<h2>Please verify your order</h2>
	
	<%
		if(session.getAttribute("user") == null){	//go back to Login if null
				response.sendRedirect("index.jsp");
		}
		
		out.println("<p>Account - " + session.getAttribute("user") + "</p>");
		support.functions.CartManager cart = (support.functions.CartManager) session.getAttribute("cart");
		if(cart != null){
			out.println("<form action=\"confirm.jsp\"");
			
			out.println(cart.getCartInfo());
			
			out.println("<br><input type=\"submit\" value=\"Confirm Order\">");
			out.println("<p>You will be sent an emailed copy of your reciept at " + session.getAttribute("email") + "</p>");
		}
		else{
			out.println("<p>Cart is Empty</p>");
			out.println("<p> You need to Order something still!</p>");
		}
			
		
	%>
		<a href = "bookPage.jsp"><button>Back to Books</button></a><br>
		<a href = "index.jsp"><button>Log out</button></a>
</form>
</body>
</html>