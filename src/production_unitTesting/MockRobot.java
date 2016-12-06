package production_unitTesting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import production_unitTesting.Floor;
import production_unitTesting.Point;
import production_unitTesting.Report;
import production_unitTesting.Tickable;
import production_unitTesting.MockFloor.Directions;


/**
 * 
 * @author Wei Gui
 *
 */
public class MockRobot implements Robot, Tickable, Report{
	public static int ROBOT_STATE_IDLE=0;
	public static int ROBOT_STATE_CARRYING=1;
	public static int ROBOT_STATE_CHARGING=2;
	public static int ROBOT_STATE_CHARGING_INPROGRESS=3;
	public static int ROBOT_STATE_BACK=4;
	private final String ID;
	private int state=ROBOT_STATE_IDLE;
	private ArrayList<Directions> route;
	private Object i;//item
	private Object d;//destination
	private Iterator<Directions> I;
	private Directions temp=null;
	private Point p;
	private RobotScheduler sys;
	public boolean chargecompleted=false;//for testing only
	private Random r = new Random();


	public MockRobot(String ID,Point p,RobotScheduler system){
		this.ID = ID;
		this.p=p;
		this.sys=system;
	}
	public int getstate() {
		return state;
	}
	public String getID() {
		return ID;
	}
	public Point getPOS() {
		return p;
	}
	public boolean getBatteryState() {
		// return false when lowBattery
		return false;
	}
	public boolean isBusy() {
		// return true when ROBOT_STATE is not IDLE
		if(this.state!=ROBOT_STATE_IDLE) {
			return true;
		}
		return false;
	}
	public void gocharge() {
		//Assume that the FloorSubsystem would give me the right direction here
		//this.route=new warehouse_system.floor.Floor().getRoute(new int[]{0,0},new int[]{5,5});
		///////////////FOR TEST USE ONLY
		this.route=new ArrayList<Directions>();
		if(this.p.getX()==3) {
			this.route.add(Directions.UP);
			this.route.add(Directions.UP);
			this.route.add(Directions.UP);
			this.route.add(Directions.UP);
			this.route.add(Directions.UP);
		} else {
			this.route.add(Directions.RIGHT);
			this.route.add(Directions.RIGHT);
			this.route.add(Directions.RIGHT);
			this.route.add(Directions.RIGHT);
			this.route.add(Directions.RIGHT);
		}
		///////////////FOR TEST USE ONLY
		this.I=this.route.iterator();
		this.state=2;
	}
	@SuppressWarnings("unused")
	private int charge() {
		// check itself and get ready for charging
		// return 1 if it is not at the battery station, return 2 if it is full.
		if(this.getBatteryState()==true) {return 2;}
		if(true /*assume that the check result is true*/) {
			this.state=ROBOT_STATE_CHARGING_INPROGRESS;
			return 0;
		}
		return 1;
	}

	public void Activate(Object targetItem,Object droparea) {
		this.i=targetItem;
		this.d=droparea;
		//Assume that the FloorSubsystem would give me the right direction here
		
		this.route = Floor.getRoute(new Point(r.nextInt(5),0),new Point(r.nextInt(5),5));
		this.I=this.route.iterator();
		this.state=ROBOT_STATE_CARRYING;
	}
	private void back() {
		//Mission accomplished, get back to the original position
		//Assume that the FloorSubsystem would give me the right direction here
		//this.route=new warehouse_system.floor.Floor().getRoute(new int[]{d.hashCode(),0},new int[]{i.hashCode(),5});
		///////////////FOR TEST USE ONLY
		this.route=new ArrayList<Directions>();
		if(this.p.getX()==3) {
			this.route.add(Directions.DOWN);
			this.route.add(Directions.DOWN);
			this.route.add(Directions.DOWN);
			this.route.add(Directions.DOWN);
			this.route.add(Directions.DOWN);
		} else {
			this.route.add(Directions.LEFT);
			this.route.add(Directions.LEFT);
			this.route.add(Directions.LEFT);
			this.route.add(Directions.LEFT);
			this.route.add(Directions.LEFT);

		}
		///////////////FOR TEST USE ONLY
		this.I=this.route.iterator();
		this.state=4;
	}
	@Override
	public void tick(int tick) {
		System.out.println(tick+":Robot "+this.ID+" State:"+this.state);
		System.out.println(tick+":Robot "+this.ID+" Position:"+this.p.getX()+" "+this.p.getY());
		//Detecting collision
		if(I!=null&&I.hasNext()&&this.temp==null) {
			//int[] myposition=new warehouse_system.floor.Floor().getLocation(this.getID());
			temp=I.next();
			//if(new warehouse_system.floor.Floor().objectAt(nextstep(myposition,temp))) {
			Point flag = new Point(nextstep(new int[]{this.p.getX(),this.p.getY()},temp)[0],nextstep(new int[]{this.p.getX(),this.p.getY()},temp)[1],"flag");
			if(sys.collisioncheck(flag)) {
				System.out.println("Robot "+this.ID+":There is something blocking my way! Waiting...");
			} else {
				this.p =new Point(nextstep(new int[]{this.p.getX(),this.p.getY()},temp)[0],nextstep(new int[]{this.p.getX(),this.p.getY()},temp)[1],"temp");
				temp=null;
			}
		} else if(temp!=null) {
			Point flag = new Point(nextstep(new int[]{this.p.getX(),this.p.getY()},temp)[0],nextstep(new int[]{this.p.getX(),this.p.getY()},temp)[1],"new Point");
			if(sys.collisioncheck(flag)) {
				System.out.println("Robot "+this.ID+":There is something blocking my way! Waiting...");
			} else {
				this.p=new Point(nextstep(new int[]{this.p.getX(),this.p.getY()},temp)[0],nextstep(new int[]{this.p.getX(),this.p.getY()},temp)[1],"temp");
				temp=null;
			}
			
		} else {
			//route ended
			switch(this.state) {
			case 0:{
				if(getBatteryState()==false) {
					this.gocharge();
				}
				break;
			}
			case 1:{
				System.out.println("back");
				this.back();
				break;
			}
			case 2:{
				this.charge();
				break;
			}
			case 3:{
				if(this.chargecompleted==true) {
					//this.state=ROBOT_STATE_BACK;
					System.out.println("Robot "+this.ID+":Chargecompleted");
					this.state=ROBOT_STATE_IDLE;
				}
				break;
			}
			case 4:{
				this.state=ROBOT_STATE_IDLE;
				break;
			}
			}
		}


	}

	public static int[] nextstep(int[] pos,Directions d) {
		if(d==Directions.DOWN) {
			return(new int[]{pos[0],pos[1]-1});
		}
		if(d==Directions.UP) {
			return(new int[]{pos[0],pos[1]+1});
		}
		if(d==Directions.LEFT) {
			return(new int[]{pos[0]-1,pos[1]});
		}
		if(d==Directions.RIGHT) {
			return(new int[]{pos[0]+1,pos[1]});
		}
		return null;
	}

	@Override
	public void printEvent(String event) {
		System.out.println("Robot " + ID + ": " + event);
	}

}
