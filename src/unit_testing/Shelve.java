package production_unitTesting;
import java.util.HashMap;
/**
 * 
 * @author josephtleiferman
 *
 */
public class Shelve extends wareHouseLocation {
	
	private HashMap<String,Item> itemsOnShelves = new HashMap<>();
	private Point location; 
	public Shelve(Point p) {
		super(p);
		
	}

	public Point getLocation() {
		return location;
	}
	public void setLocatoin(int x, int y) {
		location.setPoint(x,y);
	}
	public void put(String n, Item i) {
		itemsOnShelves.put(n, i);
	}
	public void remove(String n) {
		itemsOnShelves.remove(n);
	}
}