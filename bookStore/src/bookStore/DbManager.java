package bookStore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



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
				"    Password VARCHAR(64) NOT NULL,\r\n" + 
				"    Email VARCHAR(50) NOT NULL,\r\n" + 
				"    Address VARCHAR(200) NOT NULL,\r\n" + 
				"    CreditCard VARCHAR(64) NOT NULL\r\n" + 
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
				"  BookID     int      primary key       not null,\r\n" + 
				"  ISBN     int	      not null,\r\n" + 
				"  Name     varchar(100)    not null,\r\n" + 
				"  Price    int             not null,\r\n" + 
				"  Quantity int default '0' null,\r\n" + 
				"  constraint Book_ISBN_uindex\r\n" + 
				"  unique (ISBN)\r\n" + 
				");";
		String addbook1 = "INSERT IGNORE INTO Book (BookID, ISBN, Name, Price, Quantity) VALUES (1, 12456, 'Difference & Repetion', 15, 5);";
		String addbook2 = "INSERT IGNORE INTO Book (BookID, ISBN, Name, Price, Quantity) VALUES (2, 12345, 'The Order Of Things', 20, 5);";
		String addbook3 = "INSERT IGNORE INTO Book (BookID, ISBN, Name, Price, Quantity) VALUES (3, 12856, 'Infinite Jest', 25, 5);";
		String addbook4 = "INSERT IGNORE INTO Book (BookID, ISBN, Name, Price, Quantity) VALUES (4, 23456, 'The Trial', 10, 5);";
		String addbook5 = "INSERT IGNORE INTO Book (BookID, ISBN, Name, Price, Quantity) VALUES (5, 12346, 'Psychopolitics', 5, 5);";
		Statement stmt1 = conn.createStatement();
		Statement stmt2 = conn.createStatement();
		Statement stmt3 = conn.createStatement();
		
		stmt1.execute(createBooks);	
		stmt2.execute(createAccount);
		stmt3.execute(createTransactions);
		stmt1.execute(addbook1);
		stmt1.execute(addbook2);
		stmt1.execute(addbook3);
		stmt1.execute(addbook4);
		stmt1.execute(addbook5);
	}
	
	public ResultSet getBookInfo (int bookID) {	// returns the specified row, keyed by BookID, (with ISBN, Price, Quantity) in Book as a resultset
		// String sql = "SELECT * FROM Book WHERE BookID =" + bookID +";";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM Book WHERE BookID = ?;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			stmt.setInt(1, bookID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet rs = null;
		try {
		rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getBookList () {	// returns all books, (with ISBN, Price, Quantity) in Book as a resultset
		String sql = "SELECT * FROM Book;";
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
		// String sql = "SELECT * FROM Book WHERE Name LIKE \'" + title +"\';";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM Book WHERE Name LIKE ?;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			stmt.setString(1, title);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet rs = null;
		try {
		rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public void updateBookQty(int bookID, int newQuantity) {
		// String sql = "UPDATE `bookstore`.`Book` SET `Quantity` = '" + newQuantity + "' WHERE (`BookID` = '" + bookID +"')";
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("UPDATE bookstore.Book SET Quantity = ? WHERE (BookID = ?);");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			stmt.setInt(1, newQuantity);
			stmt.setInt(2, bookID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ResultSet getTransactionInfo(int transactionID) { // returns as a ResultSet the specified row, keyed by TransactionID, (with Username and Amount) in Transactions
		// String sql = "SELECT * FROM Transactions WHERE TransactionID =" + transactionID +";";
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM Transactions WHERE TransactionID = ?;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			stmt.setInt(1, transactionID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet rs = null;
		try {
		rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getTransactionInfo(String user) { // returns as a ResultSet the specified row, keyed by Username, (with TransactionID and Amount) in Transactions
		// String sql = "SELECT * FROM Transactions WHERE Username = \'" + user +"\';";
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM Transactions WHERE Username = ?;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			stmt.setString(1, user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet rs = null;
		try {
		rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getUserInfo(String user) { // returns as a ResultSet the specified row, keyed by username, in Account
		// String sql = "SELECT * FROM Account WHERE Username = \'" + user +"\';";
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM Account WHERE Username = ?;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			stmt.setString(1, user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet rs = null;
		try {
		rs = stmt.executeQuery();
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
	
	public void addUser(String uname, String pass, String email, String addr, String cc) throws SQLException, NoSuchAlgorithmException { // Adds a user, taking parameters for username, password, email, address, and credit card #
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO Account (Username, Password, Email, Address, CreditCard) VALUES (?,?,?,?,?);");
		stmt.setString(1, uname);
		stmt.setString(2, hash(pass));
		stmt.setString(3, email);
		stmt.setString(4, addr);
		stmt.setString(5, hash(cc));
		
		stmt.executeUpdate();
		System.out.println("New record inserted into Account.");
	}
	public String hash(String plaintext) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(plaintext.getBytes());
		byte[] digest = md.digest();
		String out = String.format("%064x", new BigInteger(1, digest));
		return out;
	}
	public void addTransaction(String uname, int amt) throws SQLException { // Adds a transaction, taking username and amount as parameters
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO Transactions (Username, Amount) VALUES (?,?);");
		stmt.setString(1, uname);
		stmt.setInt(2, amt);
		
		stmt.executeUpdate();
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
