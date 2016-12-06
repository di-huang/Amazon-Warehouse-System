package production;

/**
 * @author josephtleiferman, dihuang
 * member variables of Point: (x,y), next_point, name?
 */
class Point {
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
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
