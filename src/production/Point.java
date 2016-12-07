package production;

/**
 * @author josephtleiferman, dihuang
 * member variables of Point: (x,y), next_point, name?
 */
public class Point {
	private int x, y;
	private String name; // not used for our simulation pattern 
	private Point next;
	public Point(int x, int y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
	}
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		name = "";
	}
	public void setPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setName(String n) {
		name = n;
	}
	public String getName() {
		return name;
	}
	public void setNext(Point next) {
		this.next = next;
	}
	public Point getNext() {
		return next;
	}
	public boolean equals(Point other) {
		return x == other.getX() && y == other.getY();
	}
	public Point nextpoint(Directions d){
		switch(d){
			case DOWN:
				return new Point(x, y+1);
			case UP:
				return new Point(x, y-1);
			case LEFT:
				return new Point(x-1, y);
			case RIGHT:
				return new Point(x+1, y);
			default:
				return null;		// stay when d is null
		}
	}
	public boolean out(){
		return x < 0 || y < 0;
	}
	public boolean closeTo(Directions d, Point end){
		Point next = this.nextpoint(d);
		double nX = (next.getX()-end.getX()) * (next.getX()-end.getX());
		double nY = (next.getY()-end.getY()) * (next.getY()-end.getY());
		double nDis =  Math.sqrt(nX+nY);
		double cX = (this.x-end.getX()) * (this.getX()-end.getX());
		double cY = (this.y-end.getY()) * (this.y-end.getY());
		double cDis =  Math.sqrt(cX+cY);
		if(nDis<cDis){
			return true;
		}else{
			return false;
		}
	}
	public boolean closeTo(Point next, Point end){
		double nX = (next.getX()-end.getX()) * (next.getX()-end.getX());
		double nY = (next.getY()-end.getY()) * (next.getY()-end.getY());
		double nDis =  Math.sqrt(nX+nY);
		double cX = (this.x-end.getX()) * (this.getX()-end.getX());
		double cY = (this.y-end.getY()) * (this.y-end.getY());
		double cDis =  Math.sqrt(cX+cY);
		if(nDis<cDis){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
