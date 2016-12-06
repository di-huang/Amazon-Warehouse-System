package production;

/**
 * 
 * @author dihuang, Wei Gui
 *
 */
public class RobotScheduler implements Tickable{
	static Robot[] robots;
	/**
	 * initialize robots on Charger area
	 */
	public RobotScheduler(){
		robots = new Robot[1];
		robots[0] = new Robot("0", Floor.CHARGER);
	}
	@Override
	public void tick(int tick) {
		for(Robot r : robots)
			r.tick(tick);
	}
	/**
	 * only used for some possible tests
	 * @return available robot right now
	 */
	public static Robot getAvailableRobot() {
		for(Robot r:robots)
			if(r.isIdle())
				return r;
		return null;
	}
	@Override
	public boolean suspend(int suspticks, int currtick) {
		return false;
	}
}

/**
 * An enumeration of robot's state
 * @author dihuang
 */
enum State{
	IDLE, HeadingToPicker, HeadingToShelf, ReturningShelf, GoToCharge
}

