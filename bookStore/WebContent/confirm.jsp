<%@page import="support.functions.SendEmail"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "java.util.Date, java.util.Calendar" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dining Philosophers Bookstore</title>
</head>
<body>
	<%
		//Shouldnt be able to get here without a cart
		bookStore.DbManager db = new bookStore.DbManager();
		support.functions.CartManager cart = (support.functions.CartManager) session.getAttribute("cart");
		
		//add transaction to DB
		int total = (int) cart.getCartTotal();		
		db.addTransaction((String)session.getAttribute("user"), total);
		
		
		
		//update book info
		int[] bookIDs = cart.getBooksIDList();
		ResultSet query = null;
		int currentQty = 0;
		for(int i = 0; i < bookIDs.length; i++){
			query = db.getBookInfo(bookIDs[i]);
			query.first();
			currentQty = query.getInt("Quantity");
			db.updateBookQty(bookIDs[i], (currentQty - cart.getQty(bookIDs[i])));
		}
		
		//Send Email
		support.functions.SendEmail.send((String)session.getAttribute("email"),"Receipt from Dining Philosophers Bookstore", cart.getCartEmailInfo());
		
		//If they go back, want to have a new cart
		session.removeAttribute("cart");
		
	%>
	<h2>TRANSACTION CONFIRMED</h2>
	<p>You can expect your books to arrive by 
	
	//gets current date and adds 3
	<% Date dt = new Date();
	Calendar c = Calendar.getInstance(); 
	c.setTime(dt); 
	c.add(Calendar.DATE, 3);	
	dt = c.getTime();
	out.println(dt.getMonth() + "-" + dt.getDate());
	%>
	</p>
	
	<a href = "bookPage.jsp"><button>Browse more Books</button></a><br>
	<a href = "index.jsp"><button>Log out</button></a>
	
</body>
</html>