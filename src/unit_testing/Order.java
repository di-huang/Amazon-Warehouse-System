package production_unitTesting;


import production_unitTesting.Item;


public class Order {
	
	/**
	 * @author Charles Carlson
	 * this class is an Order object
	 * Order object contains three fields:
	 * 	1. Order Item object being ordered by customer
	 * 	2. Integer quantity demanded
	 * 	3. String address to be shipped to
	 *
	 */
	public OrderItem itemBeingOrdered;
    public Integer quantity;
    public String shippingAddress;

    /**
     * @author Charles Carlson
     * @param itemBeingOrdered
     * @param quantity
     * @param shippingAddress
     * 
     * Constructor    
     */
    public Order(OrderItem itemBeingOrdered, Integer quantity, String shippingAddress ) {

    	this.itemBeingOrdered= itemBeingOrdered;
        this.quantity = quantity;
        this.shippingAddress = shippingAddress;
    }
    
    /**
     * @author Charles Carlson
     * 
     * following methods are straightforward getters and setters
     * there are also getters for the fields of the Item of the Order
     * a lot of these don't get used by are there just in case
     */
    public OrderItem getItemBeingOrdered() {
    	return this.itemBeingOrdered;
    }
    
    public String getItemBeingOrderedName() {
    	return this.itemBeingOrdered.toString();
    }
    
    public String getItemBeingOrderedShelfID() {
    	return this.itemBeingOrdered.getOrderItemShelfID();
    }
    public String getShippingAddress() {
        return shippingAddress;
    }

    public Integer getQuantity() {
        return quantity;
    }
    
    public void setShippingAddress (String shippingAddressSetter) {
        this.shippingAddress = shippingAddressSetter;
    }

    public void setQuantity (int quantitySetter) {
        this.quantity = quantitySetter;
    }
    
    /**
     * 
     * @author Charles Carlson
     * toString used by TestOrders 
     */
    @Override
    public String toString() {    	
    	return this.getItemBeingOrderedName().toString(); 
    }
  }
