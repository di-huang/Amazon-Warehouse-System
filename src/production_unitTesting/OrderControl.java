package production_unitTesting;

import java.util.*;

public class OrderControl implements Tickable {
	
	/**
	 * @author Charles Carlson
	 * 
	 * Orders are held in a queue
	 * Orders are kept in Pending Orders and are popped off and
	 * pushed onto the Order Queue with each tick 
	 */
	ArrayList<Order> pendingOrders = new ArrayList<Order>();
	private LinkedList<Order> orderQueue;
	private Order currentOrder;
	
	/**
	 * 
	 * @author Charles Carlson
	 * constructor initiates the Order Queue
	 */
	public OrderControl() {
		orderQueue = new LinkedList<>();
	}
	
	/**
	 * @author Charles Carlson
	 * @return int length of Pending Orders Queue
	 * used for testing only
	 */
	public int getLengthPendingOrders() {
		return pendingOrders.size();
	}
	
	/**
	 * @author Charles Carlson
	 * @return ArrayList<Order> that is the Pending Orders
	 * used for testing only
	 */
	public ArrayList<Order> getPendingOrders() {
		return pendingOrders;
	}


	/**
	 * @author Charles Carlson
	 *  
	 * add an Order to the Pending Orders
	 * the Order will be held here until ticking begins
	 */
	public void addOrder(Order o) {
		pendingOrders.add(o);
	}
	
	/**
	 * @author Charles Carlson
	 * 
	 * 1. 	Orders that are being held in the Pending Orders Queue are popped and pushed onto the Order Queue
	 * 2.	Information about that Order is printed to the console
	 * 3.	Order Control will check to see if the inventory can support the Item and quantity requested
	 * 4.	If the Order cannot be fulfilled there will be a message in the console and the clock will continue to tick
	 * 5. 	If the Order can be fulfilled it there will be a message in the console and the clock will continue to tick
	 * 6.	The next Order begins 
	 * 7. 	when there are no more Orders in the Order Queue or Pending Orders,
	 * 		the clock will continue to tick and Pending Orders will await any new Orders	
	 */


	/**
	 * 
	 * @author Charles Carlson
	 * 
	 * A NOTE ON TICK COSTS:
	 * 
	 * certain actions take a tick to complete. Here is a table:
	 * Order from Pending Orders to Order Queue 															1 Tick
	 * if Order Control denies clearance because of quantity or existence of Item							1 Tick
	 * clearing an Order																					1 Tick
	 * 
	 * an Order will take 2 ticks, regardless of whether it's cleared or not, to pass through the Order Control
	 */
	public void tick(int tick) {
		int lastPendingOrder = -1;
		
		//tick until new Order comes in
		if(pendingOrders.size() == 0) {
			return;
		}
		
		if (orderQueue.size() < 1) {
			orderQueue.push(pendingOrders.get(0));
			System.out.println("************** Tick " + tick + "*************************");
			System.out.println("Order: " + pendingOrders.get(0).toString() + " has been pushed onto orderQueue from pendingOrders");
			pendingOrders.remove(pendingOrders.get(0));
			if(pendingOrders.size() != 0) {
				return; //if orderQueue empty, add from orderQueueTesting
			}
			if(pendingOrders.size() == 0) {
				lastPendingOrder = 1;
			}
		}
		
		//Current Order becomes first Order in the Order Queue
		if(currentOrder == null) {
			currentOrder = orderQueue.pop();
			System.out.println("OrderControl starts a new Order:  " 
					+ "|| Item: " + currentOrder + 
					", Quantity: " + currentOrder.getQuantity() + 
					", Shipping Address: " + currentOrder.getShippingAddress() + " ||");
		}
		
		//check if the inventory can support the Order
		/*String currentOrderShelf = currentOrder.getItemBeingOrderedShelfID();
		if(currentOrderShelf == null) {
			System.out.println("[Current Order " + currentOrder + " cannot be CLEARED at this time");
			Integer s = currentOrder.getItemBeingOrdered().subtract(currentOrder);
			System.out.println("STOCK REMAINING: " + s);
			System.out.println("Beginning Next Order...");
			System.out.println("\n");
			currentOrder = null;
			return; //tick because this Order cannot be completed
		}*/
		
		//the ItemController CANNOT support the order because it does not possess the Item
		if(!ItemController.itemExists(currentOrder.getItemBeingOrdered())) {
			System.out.println("[Current Order " + currentOrder + " cannot be CLEARED at this time]");
			System.out.println("Reason: Item not found in Inventory");
			System.out.println("STOCK REMAIING: 0");
			System.out.println("Beginning Next Order...");
			System.out.println("\n");
			currentOrder = null;
			return; //tick because this Order cannot be completed
		}

		//The ItemController CANNOT support the Order because of quantity
		if(!ItemController.itemQuantityClears(currentOrder.getItemBeingOrdered(), currentOrder)) {
			System.out.println("[Current Order " + currentOrder + " cannot be CLEARED at this time]");
			System.out.println("Reason: Inventory cannot support the quantity requested");
			System.out.println("STOCK REMAINING; " + currentOrder.getItemBeingOrdered().getOrderItemQuantity());
			System.out.println("Beginning Next Order...");
			System.out.println("\n");
			currentOrder = null;
			return;
		}
		
		//success.... the inventory can support the Order
		System.out.println("The Inventory CAN support that Order quantity");
		System.out.println( "[Current Order " + currentOrder +  " has been CLEARED]");
		if(lastPendingOrder!=1) {
			Integer s = currentOrder.getItemBeingOrdered().subtract(currentOrder);
			System.out.println("STOCK REMAINING: " + s);
			System.out.println("Beginning Next Order...");
			System.out.println("\n");
		}
		currentOrder = null; //Current Order has been cleared and we can look at the next Order, if one exists
		if(lastPendingOrder==1) { 
			System.out.println("\n");
			System.out.println("There are no more Pending Orders at this time. Continue ticking...");
		}
	}
}


