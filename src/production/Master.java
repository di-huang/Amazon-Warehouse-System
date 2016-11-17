package production;

import warehouse_system.belt.Belt;
import warehouse_system.floor.Floor;
import warehouse_system.inventory.ItemController;
import warehouse_system.orders.OrderControl;
import warehouse_system.robot.RobotScheduler;
import warehouse_system.visualizer.Visualizer;

/**
 * 
 * @author Di Huang
 *
 */
public class Master implements Runnable {
	/**
	 * 1. Master starts and continues simulation process
	 * 2. Ticking Simulation: Master publishes ticks to other components
	 */
	private int limit = 100;				// default: limit = 100 ticks
	private int unitTime = 1000;				// default: 1 second per tick
	private boolean running = false;
	
	private Belt B;
	private Floor F;
	private ItemController I;
	private OrderControl O;
	private RobotScheduler R;
	private Visualizer V;
	/**
	 * add all components here for possible unit test
	 */
	public Master(Belt b, Floor f, ItemController i, OrderControl o, RobotScheduler r, Visualizer v) {
		B = b;
		F = f;
		I = i;
		O = o;
		R = r;
		V = v;
	}

	@Override
	public void run() {
		int tick = 0;
		while (running && tick < limit) {
			System.out.println(tick + " tick");
			tick(tick);
			System.out.println();
			
			unitTime();
			
			tick++;
		}

	}
	
	public void start() {
		running = true;
		new Thread(this).start();
	}
	
	public void stop(){
		running = false;
	}
	
	public void setLimit(int count){
		limit = count;
	}
	
	public void setUnitTime(int milliseconds){
		unitTime = milliseconds;
	}
	
	private void unitTime(){
		try {
			Thread.sleep(unitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * tick(tick) works as a central clock that publishes 
	 * each tick to other components
	 */
	private void tick(int tick){
		O.tick(tick);							// getting orders
		R.tick(tick);							// updating RobotScheduler to control robots
		V.tick(tick);						    	// repainting everything
	}
	
}
