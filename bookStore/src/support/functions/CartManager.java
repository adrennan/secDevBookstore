package support.functions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


/**
 * This class will be used to handle the cart management, order processing
 * @author Alex
 *
 */

public class CartManager {

	private HashMap<String, Integer> cart;
	//Cart will contain a list of ISBN strings, which can be compared to the DB to get the current pricing info
	
	
	
	public CartManager() {
		cart = new HashMap<>();
	}
	
	
	public void addToCart(String item, int quantity) {
		//If logic to check if we have the requested quantity in the DB
		
		cart.put(item, quantity);
	}
	
	
	
	public boolean removeFromCart(String item, int rmvQuantity) {
		//Check if cart has the key
		if(cart.containsKey(item)) {
			
			//Determine quantity currently at the Key
			int currentQuantity = cart.get(item);
			
			if(rmvQuantity > currentQuantity) {		//case where too much is being asked for removal cancel
				System.out.println("error - cart didn't have the quantity requested to remove");
				return false;
			}
			else if(rmvQuantity - currentQuantity == 0){	//Case where remove all - can just delete the key
				cart.remove(item);
			}
			else {
				cart.put(item, currentQuantity - rmvQuantity);  //All other cases
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
	
	public String getCartInfo() {
		
		
		//Would use the DB for this to get info on pricing and available quantity
		//Currently just shows the info on isbn, price
		String cartInfo = "Cart - \n";
		
		Set<String> keys = cart.keySet();
		Iterator<String> keyIterator = keys.iterator();
		String tempKey = "";
		
		while(keyIterator.hasNext()){
			tempKey = keyIterator.next();
			cartInfo += tempKey + " - $" + getItemPrice(tempKey) + "   Quantity : " + cart.get(tempKey) + 
					"   Extended Price : $" + (getItemPrice(tempKey) * cart.get(tempKey)) + "\n"; 
		}
		
		cartInfo += "\nTotal - $" + getCartTotal();
		return cartInfo;
	}
	
	public double getCartTotal() {
		//Need to use DB for this - can get the pricing
		

		Set<String> keys = cart.keySet();
		Iterator<String> keyIterator = keys.iterator();
		double total = 0;
		String tempKey = "";
		
		while(keyIterator.hasNext()){
			tempKey = keyIterator.next();
			total += cart.get(tempKey) * getItemPrice(tempKey);
		}
				
		return total; 
	}
	
	public int getCartSize() {
		int size = 0;
		
		Set<String> keys = cart.keySet();
		Iterator<String> keyIterator = keys.iterator();
		
		while(keyIterator.hasNext()){
			size += cart.get(keyIterator.next());
		}
		
		return size;
	}
	
	
	private static double getItemPrice(String item) {
		//Method to get the price from the DB for a particular item, will make a call to the DbManager class when it is 
		//completed.
		return 5;
	}
	
}
