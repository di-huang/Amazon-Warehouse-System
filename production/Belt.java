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
	private static Point[] belt1Points = {new Point(0,5),new Point(0,4),new Point(0,3),new Point(0,2)};
	private static Point[] belt2Points = {new Point(0,2),new Point(0,1),new Point(0,0)};
	final static Point belt1Start = belt1Points[0];
	final static Point belt2Start = belt2Points[0];
	final static Point belt1End = belt1Points[belt1Points.length-1];
	final static Point belt2End = belt2Points[belt2Points.length-1];
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
	private static boolean moveable = true;
	public static boolean belt1Moveable(){
		return moveable;
	}
	/**
	 * operations of picker and packer
	 * @param order
	 */
	public static void doPicker(Order order){
		belt1Content.add(new Bin(order, belt1Start));
	}
	private Parcel doPacker(Bin bin) {
		return new Parcel(bin.order, belt2Start);
	}
	private int lasttick = -1;
	@Override
	public boolean suspend(int suspticks, int currtick){
		if(currtick == lasttick + suspticks){
			lasttick = -1;
			return true;
		}else{
			return false;
		}
	}
	@Override
	public void tick(int tick) {
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
				moveable = false;
				if(lasttick == -1){
					lasttick = tick;
				}
				if(suspend(1,tick)){
					belt2Content.add(doPacker(belt1Content.remove()));
					moveable = true;
					continue;
				}
			}
			if(moveable){
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
	public Parcel(Order order, Point point){
		this.order = order;
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
	public final int width = 10;
	public Bin(Order order, Point point){
		this.order = order;
		this.point = point;
	}
	public Point getPos() {
		return point;
	}
	public void setPos(Point point) {
		this.point = point;
	}
}
