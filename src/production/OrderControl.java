package production;

import java.util.LinkedList;

import production.Tickable;
import production.ItemController;
import production.RobotScheduler;

public class OrderControl implements Tickable, Orders, Picker {
	
	/**
	 * 
	 * orders are held in a queue
	 * 
	 */
	private LinkedList<Order> orderQueue = new LinkedList<>();
	private ItemController itemController;
	private RobotScheduler robotScheduler;
	private Belt belt;
	private Bin currentBin;
	private Item neededItem;


	/**
	 * 
	 * @author Charles Carlson
	 * ItemController to check inventory
	 * RobotScheduler to do work after checking inventory
	 * 
	 * @param itemController
	 * @param robotScheduler
	 */
	public OrderControl(ItemController itemController, RobotScheduler robotScheduler) {
		this.itemController = itemController;
		this.robotScheduler = robotScheduler;
		
		/**
		 * @author Ted Herman
		 * 
		 */
		for(int i=0;i<3;i++) {
			orderQueue.addLast(getRandomOrder());
		}
	}
	
	/**
	 * adds an order object to the queue of orders
	 * 
	 * @author Charles Carlson
	 * @param orderToBeAdded
	 */
	public void addOrder(Order orderToBeAdded) {
		orderQueue.add(orderToBeAdded);
	}

	/**
	 * @author Charles Carlson
	 * 
	 * as the master clock ticks orders are taken taken from the queue by the "picker"
	 * 1.	Checks if the Item of specified quantity of the Order is available in the inventory
	 * 		Retrieve the location (shelfID/itemShelf) to be given to the robot
	 * 
	 *  
	 */
	@Override
	public void tick(int tick) {
		if(orderQueue.isEmpty()) {
			System.out.println("There are no orders");
			return;
		}
		
		System.out.println("Order:" + orderQueue.peek().getItemBeingOrderedNameString());
		if(itemController.itemAvailable(orderQueue.peek().getItemBeingOrdered())) {
			
			String itemShelf = orderQueue.peek().getItemBeingOrderedShelfID();
			orderQueue.poll();
			
			
		}
		else {
			System.out.println("There are no orders");
			orderQueue.poll();
		}
	}
}
