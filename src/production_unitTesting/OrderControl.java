package production_unitTesting;


import java.util.*;

import production_unitTesting.*;


public class OrderControl implements Tickable {
	
	/**
	 * 
	 * Orders are held in a queue
	 * mock Orders are kept in orderQueueTesting and are popped off and
	 * pushed onto the orderQueue with each tick
	 * 
	 */
	ArrayList<Order> orderQueueTesting = new ArrayList<Order>();
	private LinkedList<Order> orderQueue;
	private ItemController I;
	private RobotScheduler R;
	private MockBelt B;
	private Order currentOrder;
	
	/**
	 * 
	 * mock Orders
	 * these 9 mock Items all exist in the inventory, so there will never be a case where the Item field 
	 * of an Order cannot be filled, just for now 
	 * 
	 */
	Item RED_SHIRT = new Item("RED_SHIRT", 200, "1");
	Item BLUE_SHIRT = new Item("BLUE_SHIRT", 300, "1");
	Item BICYCLE = new Item("BICYCLE", 2, "2");
	Item BASKETBALL = new Item("BASKETBALL", 50, "2");
	Item SHAVING_CREAM= new Item("SHAVING_CREAM", 10, "3");
	Item DUCT_TAPE = new Item("DUCT_TAPE", 1, "3");
	Item BOOK = new Item("BOOK", 20, "4");
	Item EGG_PLANT = new Item("EGG_PLANT", 2000, "5");
	Item FLUTE = new Item("FLUTE", 5, "6");
	
	
	Order order1 = new Order(RED_SHIRT, 2, "501 South Dodge Street");
	Order order2 = new Order(BLUE_SHIRT, 5, "313 South Gilbert Street");
	Order order3 = new Order(BICYCLE, 1, "613 East Court Street");
	Order order4 = new Order(BASKETBALL, 2, "512 North Dodge Street");
	Order order5 = new Order(SHAVING_CREAM, 4, "902 Davenport Street");
	Order order6 = new Order(DUCT_TAPE, 1, "451 East Market Street");
	Order order7 = new Order(BOOK, 1, "222 Governor Street");
	Order order8 = new Order(EGG_PLANT, 1, "378 Lucas Street");
	Order order9 = new Order(FLUTE, 1, "987 Clinton Ave");
	
	/**
	 * 
	 * @author Charles Carlson
	 * ItemController to check inventory
	 * RobotScheduler to do work after checking inventory
	 * 
	 * @param itemController
	 * @param robotScheduler
	 * probably Belt soon
	 * 
	 */
	public OrderControl(ItemController I, RobotScheduler R, MockBelt B) {
		this.I = I;
		this.R = R;
		this.B = B;
		
		orderQueue = new LinkedList<>();
		}
	
	/**
	 * @author Charles Carlson
	 * 1. 	Check if queue of orders is empty
	 * 		if so:
	 * 			pop an order from orderQueueTesting and add it to orderQueue
	 * 2.	Check if the current order being processed is NULL
	 * 		if so:
	 * 			current order becomes first Order popped off of order queue
	 * 			Print statement 
	 * 3.	Check if robot is available:
	 * 		if not:	
	 * 			tick
	 * 		if so:
	 * 			give robot shelfID and request robot to fetch
	 * 4.	tick
	 * 			
	 * 
	 */
	public void tick(int tick) {
		
		orderQueueTesting.add(order1);
		orderQueueTesting.add(order2);
		orderQueueTesting.add(order3);
		orderQueueTesting.add(order4);
		orderQueueTesting.add(order5);
		orderQueueTesting.add(order6);
		orderQueueTesting.add(order7);
		orderQueueTesting.add(order8);
		orderQueueTesting.add(order9);


		if (orderQueue.size() < 1) {
			orderQueue.push(orderQueueTesting.get(0));
			orderQueueTesting.remove(orderQueueTesting.get(0));
			return; //if orderQueue empty, add from orderQueueTesting
		}
		
		if(currentOrder == null) {
			currentOrder = orderQueue.pop();
			System.out.println("OrderControll starts a new Order");
			//set currentOrder to first Order in OrderQueue
		}
		
		if(R.getAvailableRobot() == null) {
			return; //wait until later tick
		}
	
		String currentOrderShelf = currentOrder.getItemBeingOrderedShelfID();
		//String currentOrderShelf = currentOrder.getItemBeingOrderedShelfID();
		if(currentOrderShelf == null) { 
			System.out.println("Item not in inventory");
			return; //tick
		}
		
		/**
		 * 
		 * method doesn't exist yet
		 * commented out for the sake of errors
		 * 
		 */
		/*R.requestShelf(currentOrderShelf,(Picker)this);
		return;//tick*/
		
		/**
		 * 
		 * create a new bin, put the Order in the bin, mark the order as finished
		 * for now, probably won't keep
		 * 
		 */
		if(B.binAvailable()) {
			B.getBin();
		}
		
		
	}
}
