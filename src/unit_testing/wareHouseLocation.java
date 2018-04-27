package production_unitTesting;

/**
 * 
 * @author josephtleiferman
 *
 */

public abstract class wareHouseLocation {
	private Point location; 
	
	public wareHouseLocation(Point p) {
		location = p;
	}
	public Point getLocation() {
		return location;
	}
	public void setLocatoin(int x, int y) {
		location.setPoint(x,y);
	}
	

}
