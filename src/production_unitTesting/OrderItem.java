package production_unitTesting;
//
public class OrderItem {

	private String name;
	private Integer quantity;
	private String shelfID;
	
	public OrderItem(String name, Integer quantity, String shelfID) {
		this.name = name;
		this.quantity = quantity;
		this.shelfID = shelfID;
	}
	
	public String getOrderItemName() {
		return this.name;
	}
	
	public Integer getOrderItemQuantity() {
		return this.quantity;
	}
	
	public String getOrderItemShelfID() {
		return this.shelfID;
	}

}
