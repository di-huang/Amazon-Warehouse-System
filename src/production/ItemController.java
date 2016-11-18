package warehouse_system.inventory;

import java.util.HashMap;

import warehouse_system.Report;
import warehouse_system.orders.Order;

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
	}

	public boolean itemAvailable(Item i) {
		if (!inventory.containsKey(i.name)) {
			return false; }
		return inventory.get(i.name).quantity >= i.quantity;
	}
}
