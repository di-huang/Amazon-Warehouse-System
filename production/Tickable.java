package production;

/**
 * @author dihuang
 * Tickable is implemented by Belt, ItemControl, OrderControl, Robot,
 * RobotScheduler and Visualizer, which works as tunnel that transmits 
 * central tick to other components.
 */
interface Tickable {
	/**
	 * transmit central clock
	 * @param tick
	 */
	public void tick(int tick);
	/**
	 * @param suspticks,currtick
	 * @return true if specific ticks are already passed
	 */
	public boolean suspend(int suspticks, int currtick);
}