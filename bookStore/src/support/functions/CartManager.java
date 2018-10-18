package support.functions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import bookStore.DbManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class will be used to handle the cart management, order processing
 * @author Alex
 *
 */

public class CartManager{

	// private String uname; 
	private HashMap<Integer, Integer> cart;
	//Cart will have keys as the ISBN as integer values, and the hashmap values will show the current quantity
	
	
	//Might need to add functionality for constructor to pass in the username for session
	public CartManager() {
		//Initialize cart
		cart = new HashMap<>();
		cart.put(1, 0);
		cart.put(2, 0);
		cart.put(3, 0);
		cart.put(4, 0);
		cart.put(5, 0);
	}
	
	
	public void addToCart(int bookID, int quantity) {
		//If logic to check if we have the requested quantity in the DB
		
		cart.put(bookID, quantity);
	}
	
	
	
	public boolean removeFromCart(int bookID, int rmvQuantity) {
		//Check if cart has the key
		if(cart.containsKey(bookID)) {
			
			//Determine quantity currently at the Key
			int currentQuantity = cart.get(bookID);
			
			if(rmvQuantity > currentQuantity) {		//case where too much is being asked for removal cancel
				System.out.println("error - cart didn't have the quantity requested to remove");
				return false;
			}
			else if(rmvQuantity - currentQuantity == 0){	//Case where remove all - can just delete the key
				cart.remove(bookID);
			}
			else {
				cart.put(bookID, currentQuantity - rmvQuantity);  //All other cases
			}
		}
		else {
			System.out.println("error - cart didn't have the String item requested");	//Item not contained
			return false;
		}
			return true;
	}
	
	public void emptyCart() {
		cart.clear();
	}
	
	public String getCartEmailInfo() {
		//Same as getCartInfo logically but removes the html tags for more email readable format	
		//Would use the DB for this to get info on pricing and available quantity
		//Currently just shows the info on isbn, price
		String cartInfo = "Cart - \n";
		
		Set<Integer> keys = cart.keySet();
		Iterator<Integer> keyIterator = keys.iterator();
		int tempKey = 0;
		
		while(keyIterator.hasNext()){
			tempKey = keyIterator.next();
			cartInfo += getItemName(tempKey) + " - $" + getItemPrice(tempKey) + "0   Quantity : " + cart.get(tempKey) + 
					"   Extended Price : $" + (getItemPrice(tempKey) * cart.get(tempKey)) + "0\n"; 
		}
		
		cartInfo += "\nTotal - $" + getCartTotal();
		return cartInfo;
	}
	
	public String getCartInfo() {
		
		
		//Would use the DB for this to get info on pricing and available quantity
		//Currently just shows the info on isbn, price
		String cartInfo = "Cart - <br>";
		
		Set<Integer> keys = cart.keySet();
		Iterator<Integer> keyIterator = keys.iterator();
		int tempKey = 0;
		
		while(keyIterator.hasNext()){
			tempKey = keyIterator.next();
			cartInfo += getItemName(tempKey) + " - $" + getItemPrice(tempKey) + "0   Quantity : " + cart.get(tempKey) + 
					"   Extended Price : $" + (getItemPrice(tempKey) * cart.get(tempKey)) + "0<br>"; 
		}
		
		cartInfo += "\nTotal - $" + getCartTotal();
		return cartInfo;
	}
	
	public int[] getBooksIDList() {
		int[] books = new int[cart.size()];
		Set<Integer> keys = cart.keySet();
		Iterator<Integer> keyIterator = keys.iterator();
		int tempKey = 0;
		int i = 0;
		while(keyIterator.hasNext()){
			tempKey = keyIterator.next();
			books[i] = tempKey;
		}
		
		return books;
	}
	
	public int getQty(int bookID) {
		return cart.get(bookID);
	}
	
	public double getCartTotal() {
		//Need to use DB for this - can get the pricing
		

		Set<Integer> keys = cart.keySet();
		Iterator<Integer> keyIterator = keys.iterator();
		double total = 0;
		int tempKey = 0;
		
		while(keyIterator.hasNext()){
			tempKey = keyIterator.next();
			total += cart.get(tempKey) * getItemPrice(tempKey);
		}
				
		return total; 
	}
	
	public int getCartSize() {
		int size = 0;
		
		Set<Integer> keys = cart.keySet();
		Iterator<Integer> keyIterator = keys.iterator();
		
		while(keyIterator.hasNext()){
			size += cart.get(keyIterator.next());
		}
		
		return size;
	}
	
	
	private static String getItemName(int bookID) {
		//Method to get the price from the DB for a particular item, will make a call to the DbManager class when it is 
		//completed.
		try {
			DbManager db = new DbManager();
			ResultSet query = db.getBookInfo(bookID);
			
			query.first();
			return query.getString("Name");	
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		System.out.println("Fail");
		return "";
		
	}
	
	private static double getItemPrice(int bookID) {
		//Method to get the price from the DB for a particular item, will make a call to the DbManager class when it is 
		//completed.
		try {
			System.out.println("1");
			DbManager db = new DbManager();
			System.out.println("2");
			ResultSet query = db.getBookInfo(bookID);
			System.out.println("3");
			
			query.first();
			return query.getInt("Price");	
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		System.out.println("Fail");
		return 0;
		
	}
	
	//Convert dangerous strings char by char into safe strings with escaped chars
	//Code from https://www.ibm.com/developerworks/library/se-prevent-cross-site-scripting-attacks/index.html
	 public static String escapeHtml(String inputString) {
		    StringBuilder builder = new StringBuilder();
		    char[] charArray = inputString.toCharArray();
		    for (char nextChar: charArray) {
		      String entityName = charMap.get((int) nextChar);
		      if (entityName == null) {
		        if (nextChar > 0x7F)
		          builder.append("&#")
		            .append(Integer.toString(nextChar, 10))
		            .append(";");
		        else
		          builder.append(nextChar);
		      }
		      else
		        builder.append(entityName);
		    }
		    return builder.toString();
		  }

		  public static final HashMap<Integer, String> charMap =
		    new HashMap<Integer, String>();

		  static {
		    charMap.put(34, "&quot;");    // double quote
		    charMap.put(35, "&#35;");     // hash mark (no HTML named entity)
		    charMap.put(38, "&amp;");     // ampersand
		    charMap.put(39, "&apos;");    // apostrophe, aka single quote
		    charMap.put(60, "&lt;");      // less than
		    charMap.put(62, "&gt;");      // greater than
		  }
	
	
}
