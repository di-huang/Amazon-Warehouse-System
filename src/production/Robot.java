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
	LinkedList<Directions> route;
	State state;
	Shelf shelf;
	Order currO;
	boolean carrying;
	int battery = 40;	// full battery is 40
	public Robot(String id, Point p) {
		ID = id;
		pos = p;
		route = null;
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
	Point end;
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
				if(battery <= 15){	// low battery is 15
					System.out.println(this+" has low battery("+battery+")");
					state = State.GoingToCharge;
					carrying = false;
					end = Floor.CHARGER;
					route = Floor.getRoute(this, pos, end);
					break;
				}
				LinkedList<Order> pendingOrders = OrderControl.getPendingOrders();
				if(!pendingOrders.isEmpty() && currO == null){// has work to do~ (currO reset to null when order is fulfilled in ReturnShelf case)
					currO = pendingOrders.poll();
					shelf = ItemControl.findItem(currO.getUnfilledItemInfo());
					System.out.println(this+":New Order! Target shelf: " + shelf.getPos());
					state = State.HeadingToShelf;
					carrying = false;
					end = shelf.getPos();
					route = Floor.getRoute(this, pos, end);
				}else if(pendingOrders.isEmpty()){				// no job now :(
					System.out.println(this+":There's no order right now.");
					if(!pos.equals(Floor.CHARGER)){
						end = Floor.CHARGER;		// backing to home
						route = Floor.getRoute(this, pos, end);
					}else{
						route = null;
					}
				}
				break;
			case HeadingToShelf:
				if(pos.equals(end)){
					System.out.println(this+" is picking up the shelf...");
					carrying = true;
					if(suspend(1,tick)){
						shelf.pickup();
						state = State.HeadingToPicker;
						end = Floor.PICKER_WAITTING_AREA;
					}
				}
				route = Floor.getRoute(this, pos, end);
				break;
			case HeadingToPicker:
				if(pos.equals(Floor.PICKER_WAITTING_AREA)){
					System.out.println(this+":Picker is pickng items...");
					Belt.generateBin(currO);
					if(suspend(2, tick)){
						state = State.ReturningShelf;
						carrying = true;
						end = shelf.home;
						Belt.doPicker(currO, shelf);
					}
				}
				route = Floor.getRoute(this, pos, end);
				break;
			case ReturningShelf:
				if(pos.equals(end)){
					System.out.println(this+" is putting down the shelf...");
					carrying = false;
					if(suspend(1, tick)){
						shelf.putdown();
						ItemInfo nxtinfo = currO.getUnfilledItemInfo();
						if(currO.isAllFilled()){
							System.out.println(this+":Current order is fulfilled!");
							currO = null;
						}
						if(nxtinfo == null){
							shelf = null;
							state = State.IDLE;
						}else{
							shelf = ItemControl.findItem(nxtinfo);
							System.out.println(this+":Continue on current Order,Target shelf: " + shelf.getPos());
							state = State.HeadingToShelf;
							end = shelf.getPos();
						}
					}
				}
				route = Floor.getRoute(this, pos, end);
				break;
			case GoingToCharge:
				if(pos.equals(end)){
					state = State.Charging;
					carrying = false;
				}
				route = Floor.getRoute(this, pos, end);
				break;
			case Charging:
				System.out.println(this+" is charging itself.");
				if(battery < 40){
					battery += 10;
					if(battery > 40){
						battery = 40;
					}
				}else{
					state = State.IDLE;
					carrying = false;
				}
		}
		if(route != null && !route.isEmpty()){
			pos = nextpoint(pos, route.removeFirst());
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
	public Point nextpoint(Point pos,Directions d) {
		if(d==Directions.DOWN) {
			return new Point(pos.getX(), pos.getY()+1);
		}
		if(d==Directions.UP) {
			return new Point(pos.getX(), pos.getY()-1);
		}
		if(d==Directions.LEFT) {
			return new Point(pos.getX()-1, pos.getY());
		}
		if(d==Directions.RIGHT) {
			return new Point(pos.getX()+1, pos.getY());
		}
		return null;
	}
}
