package production_unitTesting;

import java.util.HashMap;

import production_unitTesting.Order;
import production_unitTesting.Report;


/**
 * @author wangyang xu 
 * @author Charles Carlson
 * 		-modified this to work with OrderItems and ItemController 
 * it is OrderItem control system, including checking item available, adding item, reduce item quantity.
 */
 
public class ItemController {
	/**
	 * This works as the inventory subsystem 
	 * 	-just for unit testing Order Control @author Charles Carlson
	 */
	private static HashMap<String, OrderItem> inventory;

	public ItemController() {
		inventory = new HashMap<String, OrderItem>();
	}

	public ItemController(HashMap<String, OrderItem> database) {
		ItemController.inventory = database;
	}
	
	public void addItem(OrderItem i){
		inventory.put(i.getOrderItemName(), i);
		// if the quantity of the item lower then the minimum (set by administrator), we should additem
	}
	
	public static boolean itemAvailable(OrderItem i) {
		if (!inventory.containsKey(i.getOrderItemName())) {
			return false; }
		return inventory.get(i.getOrderItemName()).getOrderItemQuantity() >= i.getOrderItemQuantity();
		// check whether the item and the quantity of the item can meet the requirement of order
	}
	
	/**
	 * @author Charles Carlson
	 * @param i
	 * @return boolean
	 * 
	 * I decided to split up itemAvailable(OrderItem i) because by doing so you can give the costumer more detail as to exactly why their Order cannot go through
	 * It could be because we simply don't stock the item
	 * or it could be that we are currently insufficient on stock to fill that Order at this time
	 */
	public static boolean itemExists(OrderItem i ) {
		if(!inventory.containsKey(i.getOrderItemName())) {
			return false;
		}
		else { return true; }
	}
	
	public static boolean itemQuantityClears(OrderItem i, Order o) {
		return inventory.get(i.getOrderItemName()).getOrderItemQuantity() >= o.getQuantity();
	}
}
