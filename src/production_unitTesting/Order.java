package production_unitTesting;


import production_unitTesting.Item;


public class Order {
	
	/**
	 * @author Charles Carlson
	 * this class is an Order object
	 * Order object contains three fields:
	 * 	1. Item object being ordered by customer
	 * 	2. Integer quantity demanded
	 * 	3. String adress to be shipped to
	 *
	 */


	public OrderItem itemBeingOrdered;
    public Integer quantity;
    public String shippingAddress;


    /**
     * @author Charles Carlson
     * @param itemName
     * @param itemName
     * @param shippingAddress
     * @param quantity
     * @param quantity 
     * 
     * constructor
     * 
     */
    
    /**
     * @author Charles Carlson
     * @param itemBeingOrdered
     * @param quantity
     * @param shippingAddress
     * 
     */
    public Order(OrderItem itemBeingOrdered, Integer quantity, String shippingAddress ) {


        /*this.serial = serial;
        this.itemName = itemName;*/
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
     * 
     */


    public OrderItem getItemBeingOrdered() {
    	return this.itemBeingOrdered;
    }
    
    public String getItemBeingOrderedName() {
    	return this.itemBeingOrdered.toString();
    }
    
    public String getItemBeingOrderedNameString() {
    	return this.itemBeingOrdered.toString();
    }
    
    
    /**
     * @author Charles Carlson
     * @return null or !=null
     * 
     * used in OrderControl, the point of this one is to see if an Item in the inventory. 
     * if it has a shelfID, then it is in the inventory
     * if its shelfID == null, then it is not in the inventory
     * however, locating shelves for robots will not be done using this method
     */
    public String getItemBeingOrderedShelfID() {
    	if(this.itemBeingOrdered.getOrderItemShelfID()!=null) { 
    		return this.itemBeingOrdered.getOrderItemShelfID(); 
    	}
    	
    	else { 
    		return null; 
    	}
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
