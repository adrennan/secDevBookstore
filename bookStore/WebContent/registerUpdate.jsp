<%@page import="java.util.HashMap, java.sql.ResultSet" %>

<%
/*
<input type="email" name="email" placeholder="email address"><br>
		<input type="text" name="user" placeholder="username"><br>
		<input type="password" name ="password" placeholder="password"><br>
		<input type="number" name = "cc" placeholder="Credit Card Number"><br>
		<input type="text" name = "address" placeholder="Address" ><br>
		
*/

	asd.DbManager db = new asd.DbManager();
	String user = "";
	String password = "";
	String email = "";
	String address = "";
	int creditCard = 0;
	try{
		user = request.getParameter("user");
		password = request.getParameter("password");
		email = request.getParameter("email");
		address = request.getParameter("address");
		creditCard = Integer.parseInt(request.getParameter("cc"));	
	}
	catch(Exception ex){
		//If there is an int overflow issue will be caught here
	}
	
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