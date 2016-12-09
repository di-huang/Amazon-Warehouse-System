package production_unitTesting;

import org.junit.Test;

/**
 * 
 * @author dihuang,josephtLeiferman
 *
 */
public class TestMaster {
	
	@Test
	public void test000() {	
		// Doing unit test
		MockFloor F = new MockFloor();
		
		MockBelt B = new MockBelt(F);
		RobotScheduler R = new RobotScheduler(2);
		Point robotP = new Point(0,3);
		MockRobot r001= new MockRobot("001", robotP, R );
		R.addRobot(r001); 
		robotP.setPoint(3, 0);
		MockRobot r002= new MockRobot("002", robotP, R );
		R.addRobot(r002);
		
		ItemController I = new ItemController();
		
		OrderControl O = new OrderControl();
		
		//O.addOrder(new Order(testItem1, 1, "Clinton St"));
		//O.addOrder(new Order(testItem2, 1, "Johnson St"));
		
		Visualizer V = new Visualizer();
		
		Master M = new Master(B, F, I, O, R, V);
		M.setLimit(10);
		M.setUnitTime(0);
		M.start();
	}
	
}
