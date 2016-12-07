package production;

import java.util.LinkedList;

/**
 * 
 * @author dihuang, Wei Gui
 *
 */
public class Robot implements Tickable{
	Point pos;
	LinkedList<Directions> route;
	State state;
	Shelf shelf;
	Order currO;
	int battery = 40;	// full battery is 40
	String ID;
	public Robot(String id, Point p) {
		ID = id;
		pos = p;
		route = null;
		state = State.IDLE;
		shelf = null;
		currO = null;
	}
	public boolean isIdle() {
		return state == State.IDLE;
	}
	public Point getPOS() {
		return pos;
	}
	public void setRoute(LinkedList<Directions> r){
		route = r;
	}
	Point end;
	private int lasttick = -1;
	private int Tick = 0;;
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
				route = null;
				if(battery <= 15){	// low battery is 15
					System.out.println(this+" has low battery("+battery+")");
					state = State.GoToCharge;
					end = Floor.CHARGER;
					route = Floor.getRoute(pos, end);
					break;
				}
				LinkedList<Order> pendingOrders = OrderControl.getPendingOrders();
				if(!pendingOrders.isEmpty() && currO == null){
					currO = pendingOrders.poll();
					Shelf target = ItemControl.findItem(currO.getUnfilledItemInfo());
					System.out.println("Target shelf: " + target.getPos());
					end = target.getPos();
					state = State.HeadingToShelf;
					shelf = target;
					route = Floor.getRoute(pos, target.getPos());
				}else if(pendingOrders.isEmpty()){
					System.out.println("There's no order right now.");
				}
				break;
			case HeadingToShelf:
				if(pos.equals(end)){
					System.out.println(this+" is picking up the shelf...");
					if(suspend(1,tick)){
						shelf.pickup();
						state = State.HeadingToPicker;
						end = Floor.PICKER_WAITTING_AREA;
						route = Floor.getRouteWithShelf(pos, end);
					}
				}
				break;
			case HeadingToPicker:
				if(pos.equals(Floor.PICKER_WAITTING_AREA)){
					System.out.println("Picker is pickng items...");
					if(suspend(2,tick)){
						end = shelf.home;
						state = State.ReturningShelf;
						route = Floor.getRouteWithShelf(pos, end);
						Item[] itemsOnShelf = ItemControl.onShelf(shelf);
						for(ItemSlot is : currO.getItemSlots()){
							for(Item i : itemsOnShelf){
								if(i.match(is)){
									System.out.println("Picker put " + i + "in the bin.");
									is.setItem(i);
									ItemControl.removeItem(i, shelf);
									break;
								}
							}
						}
						Belt.doPicker(currO);
					}
				}
				break;
			case ReturningShelf:
				if(pos.equals(end)){
					System.out.println(this+" is putting down the shelf...");
					if(suspend(1,tick)){
						shelf.putdown();
						ItemInfo nxtinfo = currO.getUnfilledItemInfo();
						if(currO.isAllFilled()){
							System.out.println("Current order is fulfilled!");
							currO = null;
						}
						if(nxtinfo == null){
							state = State.IDLE;
							shelf = null;
						}else{
							state = State.HeadingToShelf;
							Shelf target = ItemControl.findItem(nxtinfo);
							System.out.println("Target shelf: " + target.getPos());
							end = target.getPos();
							shelf = target;
						}
						route = Floor.getRoute(pos, end);
					}
				}
				break;
			case GoToCharge:
				if(pos.equals(end)){
					state = State.Charging;
				}
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
				}
		}
		if(route != null && !route.isEmpty()){
			pos = nextpoint(pos, route.removeFirst());
		}
		if(shelf != null && !shelf.onFloor()){
			shelf.setPos(pos);
		}
		if(battery == 0){
			System.out.println("Warning: "+this+" run out of battery!");
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
