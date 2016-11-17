package warehouse_system.orders;

import java.util.LinkedList;
import warehouse_system.inventory.*;

import warehouse_system.Tickable;
import warehouse_system.inventory.ItemController;
import warehouse_system.robot.RobotScheduler;

public class OrderControl implements Tickable {
	
	/**
	 * 
	 * orders are held in a queue
	 * 
	 */
	private LinkedList<Order> orderQueue;
	private ItemController itemController;
	private RobotScheduler robotScheduler;
	

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
	 * work is done to the Orders\
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
