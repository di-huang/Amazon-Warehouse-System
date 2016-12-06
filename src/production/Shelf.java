package production;

/**
 * @author Ted Herman, Di Huang
 * members of Shelf: resting, current_position, home_position
 */
public class Shelf {
	private boolean resting; // true if not being carried by a robot
	private Point currPos; // current position of shelf
	public final Point home; // home location of shelf
	public Shelf(Point home) {
		this.home = home;
		currPos = new Point(home.getX(), home.getY());
		resting = true; // initially sits on Floor
	}
	public boolean onFloor() {
		return resting;
	}
	public void pickup() {
		resting = false;
	}
	public void putdown() {
		resting = true;
	}
	public String toString() {
		return "Shelf";
	}
	public Point getPos() {
		return currPos;
	}
	public void setPos(Point p){
		currPos = p;
	}
}