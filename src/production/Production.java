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
	private static OrderGUI OG;
	public static Visualizer getV() {
		return V;
	}
	public static OrderGUI getOG() {
		return OG;
	}
	public static OrderControl getO() {
		return O;
	}
	public static void setOG(OrderGUI og) {
		OG=og;
	}
	public static void main(String[] args) {
		M.setLimit(620);
		M.setUnitTime(200);
	}
	public static void restart() throws Throwable {
		restart(620,200);
	}
	public static void restart(int a,int b) throws Throwable {
		M.kill();
		Belt.clearstaticFields();
		Floor.clearstaticFields();
		ItemControl.clearstaticFields();
		OrderControl.clearstaticFields();
		if(V.getext()!=null)V.getext().callfinalize();
		V.getFrame().setVisible(false);
		V.getFrame().dispose();
		OG.dispose();
		System.gc();
		F=new Floor();
		B = new Belt();
		R = new RobotScheduler();
		I = new ItemControl();
		O = new OrderControl();
		V = new Visualizer();
		M = new Master(B, F, I, O, R, V);
		OG=null;
		M.setLimit(a);
		M.setUnitTime(b);
	}
	public static Master getM() {
		return M;
	}
		
}
