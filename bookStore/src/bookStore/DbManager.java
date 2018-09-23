package bookStore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbManager {
	private Connection conn;
	public DbManager() {
		this.conn = getConnection();
		try {
			initializeDB(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "test");
			return conn;			
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;			
		}
	}
	
	private void initializeDB(Connection conn) throws SQLException {
		String createAccount = "CREATE TABLE Account IF NOT EXISTS\r\n" + 
				"(\r\n" + 
				"    UserID int PRIMARY KEY NOT NULL AUTO_INCREMENT,\r\n" + 
				"    Username VARCHAR(50) NOT NULL,\r\n" + 
				"    Password VARCHAR(50) NOT NULL,\r\n" + 
				"    Email VARCHAR(50) NOT NULL,\r\n" + 
				"    Address VARCHAR(200) NOT NULL,\r\n" + 
				"    CreditCard int NOT NULL\r\n" + 
				");";
		String createTransactions = "CREATE TABLE Transactions IF NOT EXISTS\r\n" + 
				"(\r\n" + 
				"    TransactionID int PRIMARY KEY NOT NULL AUTO_INCREMENT,\r\n" + 
				"    UserID int NOT NULL,\r\n" + 
				"    Amount int NOT NULL,\r\n" + 
				"  FOREIGN KEY (UserID) REFERENCES Account(UserID)\r\n" + 
				");";
		String createBooks = "create table Book if not exists\r\n" + 
				"(\r\n" + 
				"  BookID     int	auto_increment      primary key       not null,\r\n" + 
				"  ISBN     int	      not null,\r\n" + 
				"  Name     varchar(100)    not null,\r\n" + 
				"  Price    int             not null,\r\n" + 
				"  Quantity int default '0' null,\r\n" + 
				"  constraint Book_ISBN_uindex\r\n" + 
				"  unique (ISBN)\r\n" + 
				");";
		Statement stmt1 = conn.createStatement();
		Statement stmt2 = conn.createStatement();
		Statement stmt3 = conn.createStatement();
		stmt1.execute(createBooks);
		stmt2.execute(createAccount);
		stmt3.execute(createTransactions);
	}
	
	public int getISBN(int bookID) {
		String sql = "SELECT ISBN FROM Book WHERE BookID = " + bookID + ";";
		Statement stmt;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ResultSet rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int isbn = rs.getInt("ISBN");
		
	}
	
}
