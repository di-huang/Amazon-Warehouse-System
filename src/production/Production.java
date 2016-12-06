package production;

/**
 * 
 * @author dihuang
 *
 */
public class Production {
	public static void main(String[] args) {
		Floor F = new Floor();
		Belt B = new Belt();
		RobotScheduler R = new RobotScheduler();
		ItemControl I = new ItemControl();
		OrderControl O = new OrderControl();
		Visualizer V = new Visualizer();
		Master M = new Master(B, F, I, O, R, V);
		M.setLimit(110);
		M.setUnitTime(500);
	}
}
