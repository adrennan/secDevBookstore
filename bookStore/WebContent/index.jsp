<%@ page import="java.sql.*" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="bookStore.DbManager" %>
<%@ page import="java.sql.Connection" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Test Index</title>
</head>
<body>

	<header>
		<img src="http://placehold.it/280x80" alt="company logo">
		<h1>Change this shit later Bookstore</h1>
	</header>
	
	<section>
		
		<p>Please Login or Register</p>
		<form action = "bookPage.jsp" method = "POST">
			<input type="text" placeholder="username"><br>
			<input type="password" placeholder="password"><br>
			<br>
			<input type="submit" value="login">
		</form>
		
		<a href="register.jsp">Register</a>
		
	</section>
	<%
		//out.println("Hello World");
		
	%>
	
	<%	
		//Shouldnt do this in the jsp - change later
		DbManager db = new DbManager();
		Connection conn = db.getConnection();
		if(conn == null){
			out.print("Connection failed");
		}
		else{
			out.print("Connection successful");
		}
				
		
		//STEP 4: Execute a query
		Statement stmt = null;	  
	    stmt = conn.createStatement();
	    String sql;
	    sql = "SELECT * FROM Book";
	    ResultSet rs = stmt.executeQuery(sql);
	%>
	<div>
	<table>
	<%
		//STEP 5: Extract data from result set
		int rows = 0;
		while(rs.next()){
			if(rows == 3){
				out.print("<tr>");
			}
			
			
		   //Retrieve by column name
		   int id  = rs.getInt("BookId");
		   String name = rs.getString("Name");
		   int Isbn = rs.getInt("ISBN");
		   double price = rs.getDouble("Price");
		   out.print("<td>");		   
	   //Display values
		   out.print("ID: " + id);
		   out.print("<br> Book Name: " + name);
		   out.print("<br> ISBN: " + Isbn);
		   out.println("<br>, price: " + price);
		   out.print("</td>");
		   
			if(rows == 3){
				out.print("</tr>");
				rows = 0;
			}
		
		}
	    //STEP 6: Clean-up environment
		rs.close();
		stmt.close();
		conn.close();
	    
	%>
	</table>
	</div>
</body>
</html>