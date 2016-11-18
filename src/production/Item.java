package production;

public class Item {
	
	public final String name;
	public int weight;
	public int[] size;			// length, width, height
	public int quantity;
	public String shelfID;
	public int row;
	
	public Item(String name, int quantity, String shelfID) {
		this.name = name;
		//this.weight = weight;
		//this.size = size;
		this.quantity = quantity;
		this.shelfID = shelfID;
		//this.row = row;
	}
	
	@Override
	public String toString() {
		return this.name;
		
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getName() {
		return name;
	}
	public double getWeight() {
		return weight;
	}

}
