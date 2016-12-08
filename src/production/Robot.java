package production;

import java.util.LinkedList;

/**
 * 
 * @author dihuang, Wei Gui
 *
 */
public class Robot implements Tickable{
	String ID;
	Point pos;
	final Point home=Floor.CHARGER;
	Directions nd;	// next direction
	State state;
	Shelf shelf;
	Order currO;
	boolean carrying;
	int battery = 50;	// full battery is 50
	public Robot(String id, Point p) {
		ID = id;
		pos = p;
		nd = null;
		state = State.IDLE;
		shelf = null;
		currO = null;
		carrying = false;
	}
	public boolean isIdle() {
		return state == State.IDLE;
	}
	public boolean carrying(){
		return carrying;
	}
	public Point getPOS() {
		return pos;
	}
	private int lasttick = -1;
	private int Tick = 0;
	@Override
	public boolean suspend(int suspticks, int currtick){
		if(lasttick == -1){
			lasttick = Tick;
		}
		if(currtick == lasttick + suspticks){
			lasttick = -1;
			return true;
		}else{
			return false;
		}
	}
	Point end = null;
	@Override
	public void tick(int tick) {
		Tick = tick;
		System.out.println(this+"_position: "+pos);
		System.out.println(this+"_state: "+state);
		System.out.println(this+"_order: "+currO);
		System.out.println(this+"_battery: "+battery);
		if(tick == 0){
			return;
		}
		switch(state){
		case IDLE:
			if(battery <= 25){	// low battery is 25
				System.out.println(this+" has low battery("+battery+")");
				state = State.GoingToCharge;
				carrying = false;
				end = home;
				nd = Floor.getDirection(this, pos, end);
				break;
			}
			if(currO!=null&&currO.getUnfilledItemInfo()!=null) {
				System.out.println(this+": continue the order");
				shelf = ItemControl.findItem(currO.getUnfilledItemInfo());
				if(shelf==null) {
					System.out.println(this+": Shelf already in use,go to charge");
					state = State.GoingToCharge;
					carrying = false;
					end = home;
					nd = Floor.getDirection(this, pos, end);
					break;
				}
				System.out.println("Target shelf: " + shelf.getPos());
				state = State.HeadingToShelf;
				end = shelf.getPos();
				break;
			}
			LinkedList<Order> pendingOrders = OrderControl.getPendingOrders();
			if(!pendingOrders.isEmpty() && currO == null){// has work to do~ (currO reset to null when order is fulfilled)
				currO = pendingOrders.poll();
				System.out.println(this+":Order Received! Order:" + currO);
				shelf = ItemControl.findItem(currO.getUnfilledItemInfo());
				if(shelf==null) {
					System.out.println(this+": Shelf already in use,reordering");
					OrderControl.addOrder(currO);
					currO=null;
					break;
				}
				System.out.println("Target shelf: " + shelf.getPos());
				state = State.HeadingToShelf;
				carrying = false;
				end = shelf.getPos();
				nd = Floor.getDirection(this, pos, end);
			}else if(pendingOrders.isEmpty()){	// no job now :(
				System.out.println(this+":There's no available order right now.");
				if(!pos.equals(home)){
					end = home;		// backing to home
					nd = Floor.getDirection(this, pos, end);
				}else{
					nd = null;
				}
			}
			break;
		case HeadingToShelf:
			if(pos.equals(end)){
				System.out.println(this+" is picking up the shelf...");
				nd = null;
				if(suspend(1,tick)){
					shelf.pickup();
					carrying = true;
					state = State.HeadingToPicker;
					end = Floor.PICKER_WAITTING_AREA;
				}
				break;
			}
			nd = Floor.getDirection(this, pos, end);
			break;
		case HeadingToPicker:
			if(pos.equals(Floor.PICKER_WAITTING_AREA)){
				System.out.println(this+":Picker is pickng items...");
				Belt.generateBin(currO);
				nd = null;
				if(suspend(2, tick)){
					state = State.ReturningShelf;
					carrying = true;
					end = shelf.home;
					Belt.doPicker(currO, shelf);
				}
				break;
			}
			nd = Floor.getDirection(this, pos, end);
			break;
		case ReturningShelf:
			if(pos.equals(end)){
				System.out.println(this+" is putting down the shelf...");
				nd = null;
				if(suspend(1, tick)){
					shelf.putdown();
					carrying = false;
					ItemInfo nxtinfo = currO.getUnfilledItemInfo();
					if(nxtinfo == null){
						System.out.println("Current order is fulfilled!");
						currO = null;
						shelf = null;
						state = State.IDLE;
					}else{
						shelf = ItemControl.findItem(nxtinfo);
						if(shelf==null) {
							System.out.println(this+": Shelf already in use,go to charge");
							state = State.GoingToCharge;
							carrying = false;
							end = home;
							nd = Floor.getDirection(this, pos, end);
							break;
						}
						System.out.println("Target shelf: " + shelf.getPos());
						state = State.HeadingToShelf;
						end = shelf.getPos();
					}
				}
				break;
			}
			nd = Floor.getDirection(this, pos, end);
			break;
		case GoingToCharge:
			if(pos.equals(end)){
				nd = null;
				state = State.Charging;
				carrying = false;
				break;
			}
			nd = Floor.getDirection(this, pos, end);
			break;
		case Charging:
			System.out.println(this+" is charging itself.");
			if(battery < 50){
				battery += 10;
				if(battery > 50){
					battery = 50;
				}
			}else{
				state = State.IDLE;
				carrying = false;
			}
		}
		if(nd != null){
			if(collisioncheck()==0) pos = nextpoint();
			if(collisioncheck()!=0) System.out.println(this+":Waiting");
			if(collisioncheck()==3) {
				if(evadepoint()!=null) {
				pos = evadepoint();
				System.out.println(this+" evading collision");
				} else {
					System.out.println("Warning: "+this+" is blocked!");
				}
			}
		}
		if(shelf != null && !shelf.onFloor()){
			shelf.setPos(pos);
		}
		if(battery == 0){
			System.out.println("Warning: "+this+" runs out of battery!");
		}else if(state != State.Charging && state != State.IDLE){
			battery--;
		}
	}
	@Override
	public String toString() {
		return "Robot"+ID;
	}
	public Point nextpoint() {
		if(nd == Directions.DOWN) {
			return new Point(pos.getX(), pos.getY()+1);
		}
		if(nd == Directions.UP) {
			return new Point(pos.getX(), pos.getY()-1);
		}
		if(nd == Directions.LEFT) {
			return new Point(pos.getX()-1, pos.getY());
		}
		if(nd == Directions.RIGHT) {
			return new Point(pos.getX()+1, pos.getY());
		}
		return pos;	//stay
	}
	public Point evadepoint() {
		for(Point p:new Point[]{new Point(pos.getX(), pos.getY()+1),
				new Point(pos.getX(), pos.getY()-1),
				new Point(pos.getX()-1, pos.getY()),
				new Point(pos.getX()+1, pos.getY()),}) {
			boolean good=true;
			for(Robot r:RobotScheduler.robots) {
				if(r==this) continue;
				if(r.getPOS().equals(p)) good=false;
			}
			if(good==true) return p;
			}
		return null;
	}
	private int collisioncheck() {
		for(Robot r:RobotScheduler.robots) {
			if(r==this) continue;
			if(r.nextpoint().equals(this.nextpoint())) {
				nd=null;
				return 1; //going to the same point
			} else if(this.nextpoint().equals(r.getPOS())&&r.nextpoint().equals(r.getPOS())) {
				return 2; //waiting the robot to move
			} else if(this.nextpoint().equals(r.getPOS())&&r.nextpoint().equals(this.getPOS())) {
				return 3; //kiss
			} 
		}
		return 0;
	}
}
