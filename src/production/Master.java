package production;

/**
 * 
 * @author dihuang
 * 
 */
public class Master implements Runnable {
	private int limit = 100;					// default: limit = 100 ticks
	private int unitTime = 1000;				// default: 1 second per tick
	private boolean running = false;
	private volatile Thread me=new Thread(this);
	private Belt B;
	@SuppressWarnings("unused")
	private Floor F;
	private ItemControl I;
	private OrderControl O;
	private RobotScheduler R;
	private Visualizer V;
	/**
	 * @param add all components here for possible tests
	 */
	public Master(Belt b, Floor f, ItemControl i, OrderControl o, RobotScheduler r, Visualizer v) {
		B = b;
		F = f;
		I = i;
		O = o;
		R = r;
		V = v;
		V.givenMaster(this);
	}
	int tick = 0;
	@Override
	public void run() {
		Thread thisThread = Thread.currentThread();
		while (running && tick < limit && me==thisThread) {
			System.out.println("tick " + tick);
			tick(tick);
			System.out.println();
			unitTime();
			tick++;
		}
	}
	/**
	 * provide some available operations for user
	 */
	public void start() {
		if(running == true){
			return;
		}
		running = true;
		me=new Thread(this);
		me.start();
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
	public void kill() {
		me=null;
	}
	private void unitTime(){
		try {
			Thread.sleep(unitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Ticking simulation begins
	 * @param tick
	 */
	private void tick(int tick){
		O.tick(tick);
		R.tick(tick);
		I.tick(tick);
		B.tick(tick);
		V.tick(tick);
		V.getext().getTextField().setText(Integer.toString(tick));
	}
	public int getLimit() {
		return limit;
	}
	public int getUnitTime() {
		return unitTime;
	}
}

