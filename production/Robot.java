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
	public Robot(Point p) {
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
		System.out.println("Robot_"+"state:"+state);
		System.out.println("Robot_"+"position:"+pos);
		if(tick == 0){
			return;
		}
		switch(state){
			case IDLE:
				route = null;
				LinkedList<Order> pendingOrders = OrderControl.pendingOrders;
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
					System.out.println("Robot is picking up the shelf...");
					if(suspend(1,tick)){
						shelf.pickup();
						state = State.HeadingToPicker;
						end = Floor.PICKER_WAITTING_AREA;
						route = Floor.getRoute(pos, end);
					}
				}
				break;
			case HeadingToPicker:
				if(pos.equals(Floor.PICKER_WAITTING_AREA)){
					System.out.println("Picker is pickng items...");
					if(suspend(2,tick)){
						end = shelf.home;
						state = State.ReturningShelf;
						route = Floor.getRoute(pos, end);
						Item[] itemsOnShelf = ItemControl.onShelf(shelf);
						for(ItemSlot is : currO.getItemSlots()){
							for(Item i : itemsOnShelf){
								if(i.match(is)){
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
					System.out.println("Robot is putting down the shelf...");
					if(suspend(1,tick)){
						shelf.putdown();
						ItemInfo nxtinfo = currO.getUnfilledItemInfo();
						if(currO.isAllFilled()){
							currO = null;
						}
						if(nxtinfo == null){
							state = State.GoToCharge;
							end = Floor.CHARGER;
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
					System.out.println("Robot is at charger station...");
					if(suspend(1,tick)){
						state = State.IDLE;
					}
				}
		}
		if(route != null && !route.isEmpty()){
			pos = nextpoint(pos, route.removeFirst());
		}
		if(shelf != null && !shelf.onFloor()){
			shelf.setPos(pos);
		}
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
