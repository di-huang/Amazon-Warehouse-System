package production;

import java.util.LinkedList;

/**
 * 
 * @author dihuang
 *
 */
public class OrderControl implements Tickable{
	private static final LinkedList<Order> orderQueue = new LinkedList<Order>();
	public OrderControl(){
		// schedule orders for testing
		orderQueue.add(new Order("a st", new ItemSlot[]{new ItemSlot(Catalog.at(0)), new ItemSlot(Catalog.at(3))}));	// pen, fork
		orderQueue.add(new Order("b st", new ItemSlot[]{new ItemSlot(Catalog.at(4))}));	// spoon
		orderQueue.add(new Order("c st", new ItemSlot[]{new ItemSlot(Catalog.at(1)), new ItemSlot(Catalog.at(0))}));	// paper, pen
		orderQueue.add(new Order("d st", new ItemSlot[]{new ItemSlot(Catalog.at(2))}));	// book
		orderQueue.add(new Order("e st", new ItemSlot[]{new ItemSlot(Catalog.at(5))})); // plate
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

