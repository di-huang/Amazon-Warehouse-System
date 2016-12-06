package production_unitTesting;
/**
 * 
 * @author Wei Gui
 *
 */
public class TestRobot {

	public void TestingRobot() throws InterruptedException {

		RobotScheduler rs=new RobotScheduler(0);
		//The MockRobot is designed to hold only 2-robots test in the charging pattern
		//The position of these two robots are changeable
		//However, due to the situation that i don't have the working Floor Subsystem, the route that the robot have is always the same
		//You can check out that route in MockRobot.java , it is like just "left left left left left" or "up up up up up"
		//(in fact there is no difference between the charging and moving shelf pattern)
		rs.addRobot(new MockRobot("001",new Point(0,3),rs));
		rs.addRobot(new MockRobot("002",new Point(3,0),rs));
		for(int a=0;a<13;a++) {
			Thread.sleep(2500);
			rs.tick(a);
			if(a==10) {
				//Force completing the charging of both two robots at the same tick
				//I didn't set up a charging timer for the MockRobot
				((MockRobot)rs.robots.get(0)).chargecompleted=true;
				((MockRobot)rs.robots.get(1)).chargecompleted=true;
			}
		}
		System.out.println("Test Ended");
	}

}
