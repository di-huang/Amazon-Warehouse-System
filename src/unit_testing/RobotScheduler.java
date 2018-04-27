package production_unitTesting;

import java.util.ArrayList;

import production_unitTesting.Point;
import production_unitTesting.Tickable;
/**
 * 
 * @author Wei Gui
 *
 */
public class RobotScheduler implements Tickable{
	/**
	 * This works as the robot control subsystem
	 */
	
	public ArrayList<Robot> robots;//for testing only
	
	public RobotScheduler(int Robotnum){
		robots = new ArrayList<Robot>();
		for(int a=0;a<Robotnum;a++) {
			Point temp = new Point(0,0);
			addRobot(new MockRobot(Integer.toString(a),temp,this));
		}
	}
	
	public RobotScheduler(ArrayList<Robot> robots){
		this.robots = robots;
	}

	@Override
	public void tick(int tick) {
		robots.forEach(r -> ((Tickable)r).tick(tick));
	}
	
	public void addRobot(Robot r){
		robots.add(r);
	}
	public Robot getAvailableRobot() {
		for(Robot r:robots) {
			if(((MockRobot)r).isBusy()==false) {
				return (Robot)r;
			}
		}
		//All Robots are busy now
		return null;
	}
	public boolean collisioncheck(Point p) {
		for(Robot r:robots) {
			if(((MockRobot)r).getPOS().getX()==p.getX()&&((MockRobot)r).getPOS().getY()==p.getY()) {
				return true;
			}
		}
		return false;
	}

}
