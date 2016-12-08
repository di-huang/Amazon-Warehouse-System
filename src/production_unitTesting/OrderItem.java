package production_unitTesting;
//
public class OrderItem {
	
	/**
	 * @author Charles Carlson
	 * 
	 * an Order Item is an item that is in itemController and may be placed in an Order
	 * three fields
	 * 	1. String name
	 * 	2. Integer quantity (this is the quantity that the ItemController possesses, not the quantity requested by the customer)
	 * 	3. String shelfID (which shelf, and therefore, where in the warehouse is the Order Item)
	 * I had to differentiate from Item and ItemControl somehow
	 */
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
	
	public Integer subtract(Order o) {
		Integer currentStock = this.getOrderItemQuantity();
		Integer subtractee = o.getQuantity();
		Integer stockAfterOrder = currentStock - subtractee;
		if(stockAfterOrder < 0) {
			return 0;
		}
		return stockAfterOrder;
	}
	
	@Override
	public String toString() {
		return this.getOrderItemName();
	}

}
