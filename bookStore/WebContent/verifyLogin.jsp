<%@page import="java.util.HashMap, java.sql.ResultSet" %>


You came to the wrong neighborhood, kid
<%

	bookStore.DbManager db = new bookStore.DbManager();
	
	
	String recUser = request.getParameter("user");
	String recRealPass = request.getParameter("password");
	String hashPass = db.hash(recRealPass);
	
	
	ResultSet dbUser = db.getUserInfo(recUser);
	String user;
	String pass;
	
	if(!dbUser.first()){	//see if valid user
		response.sendRedirect("index.jsp");
	}
	else{ //valid - check password
		dbUser.first();
		user = dbUser.getString("Username");
		pass = dbUser.getString("Password");
		
		if((user.equals(recUser)) && (pass.equals(hashPass))){	//check password vs DB
			response.sendRedirect("bookPage.jsp");
			String[] sessAttributes = {"user", "email", "address", "cc"};
			
			//Set user info to a session attribute
			session.setAttribute("user", user);
			session.setAttribute("email", dbUser.getString("Email"));
			session.setAttribute("address", dbUser.getString("Address"));
			session.setAttribute("cc", dbUser.getString("CreditCard"));
		}
		else{		//on wrong password return to login
			response.sendRedirect("index.jsp");
		}
	}
%>

	