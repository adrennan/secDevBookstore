<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import= "java.util.ArrayList" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dining Philosophers Bookstore</title>
</head>
<body>
	<header>
		<img src="https://i.imgur.com/EfK0Hjp.jpg" alt="company logo" style="width:500px;height:300px;">
		<h1>Dining Philosophers Bookstore</h1>
		<h4>Hey <%out.println(session.getAttribute("user")); %>, Check out our books</h4>
		<a href = "index.jsp"><button>Log out</button></a>
	</header>
	<hr>
		<a href="checkout.jsp">
		<img src="http://www.clker.com/cliparts/1/2/x/X/a/Q/simple-gray-checkout-button-hi.png" height="80px">
		</a>
	<hr>
	<%
	
	bookStore.DbManager db = new bookStore.DbManager();
	
	ResultSet bookList = db.getBookList();	
	bookList.first();
	boolean breakLoop = true;
	
	
	/* old
	<form>
			<input type="text" placeholder="your login"><br>
			<input type="password" placeholder="your password"><br>
			<br>
			<input type="submit" value="login">
		</form>
	*/
	int qtyAvl = 0;
	int price = 0;
	int bookID = 0;
	while(breakLoop){
		qtyAvl = bookList.getInt("Quantity");
		price = bookList.getInt("Price");
		bookID = bookList.getInt("BookID");
		out.println("<h4>"+ bookList.getString("Name") + "</h4>");
		out.println("<p>Price - $" + price + ".00</p>");
		out.println("<p>Quantity Available - " + qtyAvl +" </p>");
		if(qtyAvl == 0){
			out.println("<p>Sold Out!</p>");
		}
		else{
			out.println("Add : ");
			out.println("<form action=\"CartUpdate.jsp\" method = \"POST\"><select name=\"Quantity\">");
			for(int i = 0; i < qtyAvl; i++){
				out.println("<option>"+ (i + 1) + "</option>");
			}
			out.println("       </select>");	
		}
		
		out.println("<input type=\"hidden\" name=\"bookID\" value =\"" + bookID + "\"/>");
		out.println("<input type=\"submit\" value=\"Add to Cart\"></form> <hr>");
		
				
		//add in logic to send form data to server				
		
		
		breakLoop = bookList.next();
			
	}
	bookList.close();
	
	  %>

</body>
</html>