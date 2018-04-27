package production;

import java.awt.Color;
import java.util.LinkedList;

/**
 * 
 * @author dihuang
 *
 */
public class Belt implements Tickable{
	private static LinkedList<Bin> belt1Content = new LinkedList<Bin>();
	private static LinkedList<Parcel> belt2Content = new LinkedList<Parcel>();
	// place belts according to layout of Floor
	private final static Point[] belt1Points = {new Point(0,5),new Point(0,4),new Point(0,3),new Point(0,2)};
	private final static Point[] belt2Points = {new Point(0,2),new Point(0,1),new Point(0,0)};
	final static Point belt1Start = belt1Points[0];
	final static Point belt2Start = belt2Points[0];
	final static Point belt1End = belt1Points[belt1Points.length-1];
	final static Point belt2End = belt2Points[belt2Points.length-1];
	/**
     * @author Wei
     */
	public static void clearstaticFields() {
		belt1Content = new LinkedList<Bin>();
		belt2Content = new LinkedList<Parcel>();
		currB=null;
		Tick=0;
	}
	public Belt() {
		belt1Points[0].setNext(belt1Points[1]);
		belt1Points[1].setNext(belt1Points[2]);
		belt1Points[2].setNext(belt1Points[3]);
		belt1Points[3].setNext(null);
		belt2Points[0].setNext(belt2Points[1]);
		belt2Points[1].setNext(belt2Points[2]);
		belt2Points[2].setNext(null);
	}
	/**
	 * @return content of belts
	 */
	public static LinkedList<Bin> getBelt1() {
		return belt1Content;
	}
	public static LinkedList<Parcel> getBelt2() {
		return belt2Content;
	}
	/**
	 * operations of picker and packer
	 * @param order
	 */
	static Bin currB = null;
	public static void doPicker(Order order, Shelf shelf){
		Item[] itemsOnShelf = ItemControl.onShelf(shelf);
		for(ItemSlot is : order.getItemSlots()){
			for(Item i : itemsOnShelf){
				if(i.match(is)){
					System.out.println("Belt-Picker:Picker put " + i + "in the bin.");
					is.setItem(i);
					ItemControl.removeItem(i, shelf);
					break;
				}
			}
		}
		if(order.isAllFilled()){
			currB.enable();
		}
	}
	public static void generateBin(Order order){
		if(currB == null){
			currB = new Bin(order, belt1Start);
			belt1Content.add(currB);
		}
	}
	private Parcel doPacker(Bin b) {
		return new Parcel(b.order, belt2Start);
	}
	static int Tick = 0;
	private int lasttick = -1;
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
		for(Parcel pa : belt2Content){
			Point p = pa.getPos();
			if(p == belt2End){
				belt2Content.remove();
				continue;
			}
			pa.setPos(p.getNext());
		}
		for(Bin b : belt1Content){
			Point p = b.getPos();
			if(p == belt1End){
				if(suspend(1,tick)){
					belt2Content.add(doPacker(belt1Content.remove()));
					currB = null;		// reset current bin
					continue;
				}
			}else if(b.isMoveable()){
				b.setPos(p.getNext());
			}
		}
	}
}

/**
 * 
 * @author dihuang
 * Parcel and Bin contain an order, which contains loaded items 
 */
class Parcel {
	public final Order order;
	private Point point;
	public final int width = 10;
	public final Color color = Color.GRAY;
	public Parcel(Order o, Point point){
		order = o;
		this.point = point;
	}
	public Point getPos() {
		return point;
	}
	public void setPos(Point point) {
		this.point = point;
	}
}

/**
 * 
 * @author dihuang
 *
 */
class Bin{
	public final Order order;
	private Point point;
	private boolean moveable;
	public final int width = 10;
	public final Color color = Color.ORANGE;
	public Bin(Order o, Point p){
		order = o;
		this.point = p;
		moveable = false;
	}
	public boolean isMoveable(){
		return moveable;
	}
	public void enable(){
		moveable = true;
	}
	public Point getPos() {
		return point;
	}
	public void setPos(Point point) {
		this.point = point;
	}
}
