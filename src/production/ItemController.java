package production;

import java.util.HashMap;

<<<<<<< HEAD
import production.Order;
import production.Report;

=======
>>>>>>> branch 'master' of https://github.com/dhuang12138/CS2820.git
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
		inventory.put(i.getName(), i);
	}

	public boolean itemAvailable(Item i) {
		if (!inventory.containsKey(i.name)) {
			return false; }
		return inventory.get(i.name).quantity >= i.quantity;
	}
}
