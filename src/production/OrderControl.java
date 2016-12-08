package production;

import java.util.LinkedList;

/**
 * 
 * @author dihuang
 *
 */
public class OrderControl implements Tickable{
	private static LinkedList<Order> orderQueue = new LinkedList<Order>();
    /**
     * @author Wei
     */
	public static void clearstaticFields() {
		orderQueue = new LinkedList<Order>();
		pendingOrders = new LinkedList<Order>();
	}
	public OrderControl(){
		// schedule orders for testing
		orderQueue.add(new Order("a st", new ItemSlot[]{new ItemSlot(Catalog.at(3)),new ItemSlot(Catalog.at(0))}));	// fork, pen
		orderQueue.add(new Order("b st", new ItemSlot[]{new ItemSlot(Catalog.at(4))}));	// spoon
		orderQueue.add(new Order("c st", new ItemSlot[]{new ItemSlot(Catalog.at(1)), new ItemSlot(Catalog.at(0)),new ItemSlot(Catalog.at(5))}));// paper, pen, plate
		orderQueue.add(new Order("d st", new ItemSlot[]{new ItemSlot(Catalog.at(2)),new ItemSlot(Catalog.at(2))}));	// book(2)
		// more...
		orderQueue.add(new Order("e st", new ItemSlot[]{new ItemSlot(Catalog.at(3))}));	
		orderQueue.add(new Order("f st", new ItemSlot[]{new ItemSlot(Catalog.at(2)), new ItemSlot(Catalog.at(1))}));	
		orderQueue.add(new Order("e st", new ItemSlot[]{new ItemSlot(Catalog.at(4))}));	
		orderQueue.add(new Order("e st", new ItemSlot[]{new ItemSlot(Catalog.at(2))}));	
		orderQueue.add(new Order("e st", new ItemSlot[]{new ItemSlot(Catalog.at(8)), new ItemSlot(Catalog.at(7)), new ItemSlot(Catalog.at(6))}));	
		orderQueue.add(new Order("e st", new ItemSlot[]{new ItemSlot(Catalog.at(7))}));	
		orderQueue.add(new Order("e st", new ItemSlot[]{new ItemSlot(Catalog.at(3))}));	
		orderQueue.add(new Order("e st", new ItemSlot[]{new ItemSlot(Catalog.at(8))}));	
		orderQueue.add(new Order("e st", new ItemSlot[]{new ItemSlot(Catalog.at(3)), new ItemSlot(Catalog.at(7))}));	
		orderQueue.add(new Order("e st", new ItemSlot[]{new ItemSlot(Catalog.at(4))}));	
		orderQueue.add(new Order("e st", new ItemSlot[]{new ItemSlot(Catalog.at(6))}));	
		orderQueue.add(new Order("e st", new ItemSlot[]{new ItemSlot(Catalog.at(2)), new ItemSlot(Catalog.at(5))}));	
		orderQueue.add(new Order("e st", new ItemSlot[]{new ItemSlot(Catalog.at(1))}));	
		orderQueue.add(new Order("e st", new ItemSlot[]{new ItemSlot(Catalog.at(8)), new ItemSlot(Catalog.at(3))}));	
		orderQueue.add(new Order("e st", new ItemSlot[]{new ItemSlot(Catalog.at(4))}));	
		orderQueue.add(new Order("e st", new ItemSlot[]{new ItemSlot(Catalog.at(1)), new ItemSlot(Catalog.at(8)), new ItemSlot(Catalog.at(1))}));	
		orderQueue.add(new Order("e st", new ItemSlot[]{new ItemSlot(Catalog.at(7))}));	
		orderQueue.add(new Order("e st", new ItemSlot[]{new ItemSlot(Catalog.at(8))}));	
		orderQueue.add(new Order("e st", new ItemSlot[]{new ItemSlot(Catalog.at(6))}));	
		// ...
	}
	/**
	 * only used for some possible testing
	 * @param orderToBeAdded
	 */
	public static void addOrder(Order orderToBeAdded) {
		orderQueue.add(orderToBeAdded);
	}
	/**
	 * orderQueue holds scheduled orders; pendingOrders holds the orders which already arrived
	 * and are waitting for system to process
	 */
	private static LinkedList<Order> pendingOrders = new LinkedList<Order>();
	public static LinkedList<Order> getPendingOrders(){
		return pendingOrders;
	}
	private int lasttick = -1;
	private int Tick = 0;
	@Override
	public boolean suspend(int suspticks, int currtick){
		if(currtick == 0){
			return true;
		}
		if(lasttick == -1){
			lasttick = Tick;
		}
		if(currtick == lasttick + suspticks){
			lasttick = -1;
			return true;
		}else{
			return false;
		}
	}
	@Override
	public void tick(int tick) {
		for(Order o : pendingOrders){
			if(o.isAllFilled()){
				pendingOrders.remove(o);
			}
		}
		if(orderQueue.isEmpty()) {
			return;
		}
		Tick = tick;
		if(suspend(10,tick)){	// poll an order every 10 ticks
			Order currO = orderQueue.poll();
			System.out.println("=New Order=: " + currO);
			if(!ItemControl.itemsAvailable(currO)){
				System.out.println("There is no plenty stock for this order right now.");
				orderQueue.add(currO);	// re-order right now
				return;
			}
			System.out.println("New order is in pending.");
			pendingOrders.add(currO);
		}
	}
}

