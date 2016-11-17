/*package warehouse_system.orders;

import java.util.LinkedList;
import java.util.Queue;

import warehouse_system.Report;
import warehouse_system.Tickable;
import warehouse_system.inventory.ItemController;
import warehouse_system.robot.RobotScheduler;

public class OrderSystem implements Tickable, Report{
	/**
	 * This works as the orders placement subsystem
	 *//*
	private Queue<Order> orders;
	private int delay = 3;						// default: delay = 2 tick
	private int lastTick = -1 * delay;
	
	private ItemController I;
	private RobotScheduler R;
	
	public OrderSystem(ItemController I, RobotScheduler R){
		this.I = I;
		this.R = R;
		orders = new LinkedList<Order>();
	}
	
	public OrderSystem(ItemController I, RobotScheduler R, LinkedList<Order> orderQueue){
		this.I = I;
		this.R = R;
		this.orders = orderQueue;
	}
	
	public void setDelay(int delay) {
		this.delay = delay;
	}*/

	/*@Override
	public void tick(int tick) {
		if(orders.isEmpty() || tick - lastTick < delay){
			printEvent("there's no order currently.");
			return;
		}*/
		
		/*lastTick = tick;
		Order o = orders.peek();
		printEvent("order of " + o.name + " received.");
		if(I.itemAvailable(o)){
			if(R.fromRobotToShelf(o)){
				orders.poll();
			}
		}else{
			printEvent("there's no enough stock for " + o.name);
			orders.poll();			// we just ignore this order here in a unit test
		}
	}*/

	/*@Override
	public void printEvent(String event) {
		System.out.println("OrderSystem: " + event);
	}

	public void enqueue(Order o){
		orders.offer(o);
	}
	
}*/
