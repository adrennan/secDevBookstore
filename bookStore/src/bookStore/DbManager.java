package bookStore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

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
	
	private void initializeDB(Connection conn) throws SQLException { // invoked as part of constructor, don't worry about this (unless it breaks...)
		String createAccount = "CREATE TABLE IF NOT EXISTS Account\r\n" + 
				"(\r\n" + 
				"    Username VARCHAR(50) PRIMARY KEY NOT NULL,\r\n" + 
				"    Password VARCHAR(50) NOT NULL,\r\n" + 
				"    Email VARCHAR(50) NOT NULL,\r\n" + 
				"    Address VARCHAR(200) NOT NULL,\r\n" + 
				"    CreditCard int NOT NULL\r\n" + 
				");";
		String createTransactions = "CREATE TABLE IF NOT EXISTS Transactions\r\n" + 
				"(\r\n" + 
				"    TransactionID int PRIMARY KEY NOT NULL AUTO_INCREMENT,\r\n" + 
				"    Username VARCHAR(50) NOT NULL,\r\n" + 
				"    Amount int NOT NULL,\r\n" + 
				"  FOREIGN KEY (Username) REFERENCES Account(Username)\r\n" + 
				");";
		String createBooks = "create table if not exists Book\r\n" + 
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
	
	public ResultSet getBookInfo (int bookID) {	// returns the specified row, keyed by BookID, (with ISBN, Price, Quantity) in Book as a resultset
		String sql = "SELECT * FROM Book WHERE BookID =" + bookID +";";
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getBookInfo (String title) {	// returns the specified row, selected by Title, (with ISBN, Price, Quantity) in Book as a resultset
		String sql = "SELECT * FROM Book WHERE Name LIKE \'" + title +"\';";
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getTransactionInfo(int transactionID) { // returns as a ResultSet the specified row, keyed by TransactionID, (with Username and Amount) in Transactions
		String sql = "SELECT * FROM Transactions WHERE TransactionID =" + transactionID +";";
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getTransactionInfo(String user) { // returns as a ResultSet the specified row, keyed by Username, (with TransactionID and Amount) in Transactions
		String sql = "SELECT * FROM Transactions WHERE Username = \'" + user +"\';";
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getUserInfo(String user) { // returns as a ResultSet the specified row, keyed by username, in Account
		String sql = "SELECT * FROM Account WHERE Username = \'" + user +"\';";
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public void addBook(int ISBN, String name, int price, int quant) throws SQLException { // Adds a book, taking parameters for ISBN, name, price, and quantity
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("INSERT INTO Book (ISBN, Name, Price, Quantity) VALUES (?,?,?,?);");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		stmt.setInt(1, ISBN);
		stmt.setString(2, name);
		stmt.setInt(3, price);
		stmt.setInt(4, quant);
		
		stmt.executeUpdate();
		System.out.println("New record inserted into Book.");
	}
	
	public void addUser(String uname, String pass, String email, String addr, int cc) throws SQLException { // Adds a user, taking parameters for username, password, email, address, and credit card #
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO Accounts (Username, Password, Email, Address, CreditCard) VALUES (?,?,?,?,?);");
		stmt.setString(1, uname);
		stmt.setString(2, pass);
		stmt.setString(3, email);
		stmt.setString(4, addr);
		stmt.setInt(5, cc);
		
		stmt.executeUpdate();
		System.out.println("New record inserted into Account.");
	}
	
	public void addTransaction(String uname, int amt) throws SQLException { // Adds a transaction, taking username and amount as parameters
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO Transaction (Username, Amount) VALUES (?,?);");
		stmt.setString(1, uname);
		stmt.setInt(2, amt);
		
		stmt.executeQuery();
		System.out.println("New record inserted into Transactions.");
	}
	
	/* public int getISBN(int bookID) {
		String sql = "SELECT ISBN FROM Book WHERE BookID = " + bookID + ";";
		Statement stmt;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int isbn = rs.getInt("ISBN");
		return isbn;
	}

	public String getName(int bookID) {
		String sql = "SELECT Name FROM Book WHERE BookID = " + bookID + ";";
		Statement stmt;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String name = rs.getString("Name");
		return name;
	}
	
	public int getPrice(int bookID) {
		String sql = "SELECT Price FROM Book WHERE BookID = " + bookID + ";";
		Statement stmt;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int price = rs.getInt("Price");
		return price;
	}
	
	public int getQuantity(int bookID) {
		String sql = "SELECT Quantity FROM Book WHERE BookID = " + bookID + ";";
		Statement stmt;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int quantity = rs.getInt("Quantity");
		return quantity;
	} */
}
