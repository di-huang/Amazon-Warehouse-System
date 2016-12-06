package production_unitTesting;

import java.util.HashMap;

import production_unitTesting.Order;
import production_unitTesting.Report;


/**
 * @author wangyang xu 
 * it is item control system, including checking item available, adding item, reduece item quantity.
 */
 
public class ItemController {
	/**
	 * This works as the inventory subsystem
	 */
	private HashMap<String, Item> inventory;

	public ItemController() {
		inventory = new HashMap<String, Item>();
	}

	public ItemController(HashMap<String, Item> database) {
		this.inventory = database;
	}
	
	public void addItem(Item i){
		inventory.put(i.name, i);
		// if the quantity of the item lower then the minimum (set by administrator), we should additem
	}
	
	public boolean itemAvailable(Item i) {
		if (!inventory.containsKey(i.name)) {
			return false; }
		return inventory.get(i.name).quantity >= i.quantity;
		// check whether the item and the quantity of the item can meet the requirement of order
	}
}
