package production;

import org.junit.Test;
import production.Floor;
import production.MockFloor;
import production.Point;
import production.Item;
import production.ItemController;
import production.Order;
import production.OrderControl;
import production.MockRobot;
import production.RobotScheduler;
import production.Visualizer;

/**
 * 
 * @author josephtLeiferman
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
		I.addItem(new Item("apple", 5, "777"));
		I.addItem(new Item("banana", 5, "778"));
		
		OrderControl O = new OrderControl(I, R);
		Item testItem1 = new Item("apple", 1, "1");
		Item testItem2 = new Item("banana", 1, "2");
		
		Visualizer V = new Visualizer(F);
		
		Master M = new Master(B, F, I, O, R, V);
		M.setLimit(10);
		M.setUnitTime(0);
		M.start();
	}
	
}
