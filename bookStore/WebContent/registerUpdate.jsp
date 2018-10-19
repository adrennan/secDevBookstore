<%@page import="java.util.HashMap, java.sql.ResultSet" %>

<%

	bookStore.DbManager db = new bookStore.DbManager();
	String user = "";
	String password = "";
	String email = "";
	String address = "";
	String creditCard = "";
	user = request.getParameter("user");
	password = request.getParameter("password");
	email = request.getParameter("email");
	address = request.getParameter("address");
	creditCard = request.getParameter("cc");	
	
	
	//Check if user already there
	ResultSet checkDb = db.getUserInfo(user);
	
	if(checkDb.first()){
		//if finds someone, do not add. return
		response.sendRedirect("register.jsp");
	}
	else{
		//Add the user
		db.addUser(user, password, email, address, creditCard);
		response.sendRedirect("index.jsp");
	}
	
	
	
	
%>