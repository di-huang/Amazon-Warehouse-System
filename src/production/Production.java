package production;

/**
 * 
 * @author dihuang
 *
 */
public class Production {
	private static Floor F=new Floor();
	private static Belt B = new Belt();
	private static RobotScheduler R = new RobotScheduler();
	private static ItemControl I = new ItemControl();
	private static OrderControl O = new OrderControl();
	private static Visualizer V = new Visualizer();
	private static Master M = new Master(B, F, I, O, R, V);
	public static void main(String[] args) {
		M.setLimit(620);
		M.setUnitTime(200);
	}
	public static void restart() {
		M.stop();
		F = new Floor();
		B = new Belt();
		R = new RobotScheduler();
		I = new ItemControl();
		O = new OrderControl();
		V = new Visualizer();
		M = new Master(B, F, I, O, R, V);
		M.setLimit(620);
		M.setUnitTime(200);
	}
}
