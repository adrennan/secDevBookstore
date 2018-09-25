<%@page import="java.util.HashMap" %>



<%

	bookStore.DbManager db = new bookStore.DbManager();
	bookStore.CartUpdate test = new bookStore.CartUpdate();
	
	String recBookID = request.getParameter("bookID");
	int bookID = Integer.parseInt(recBookID);
	String recQuantity = request.getParameter("Quantity");
	int quantity = Integer.parseInt(recQuantity);
	out.println(bookID + "   " + quantity);
	
	support.functions.CartManager cart = (support.functions.CartManager) session.getAttribute("cart");
	if(cart == null){
		cart = new support.functions.CartManager();
	}
	
	
	cart.addToCart(bookID, quantity);
	session.setAttribute("cart", cart);
	
	String redirectURL = "bookPage.jsp";
    response.sendRedirect(redirectURL);
%>
	