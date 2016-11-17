package production;
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
class Item {
	private double weight;
	private String name;
	
	public Item(double weight,String name) {
		this.weight = weight;
		this.name = name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getName() {
		return name;
	}
	public double getWeight() {
		return weight;
	}
}