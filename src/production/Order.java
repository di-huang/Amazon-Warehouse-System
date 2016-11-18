package production;

import production.Item;

public class Order {
	
    /**
    * used _ to differentiate from previously existing Order class for now 
    */
    //public final String serial;
    //public String itemName;
	public Item itemBeingOrdered;
    public String shippingAddress;
    public Integer quantity;
   
    
    /**
     * @author charlesc3929
     * @param itemName
     * @param itemName
     * @param shippingAddress
     * @param quantity
     * @param quantity 
     */
    public Order(Item itemBeingOrdered, Integer quantity, String shippingAddress ) {

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
     * 
     */
    
    /**
     * @author Charles Carlson
     * @return
     * 
     * returns String name of Item
     */
    
    public Item getItemBeingOrdered() {
    	return this.itemBeingOrdered;
    }
    
    public String getItemBeingOrderedName() {
    	return this.itemBeingOrdered.getName();
    }
    
    public String getItemBeingOrderedNameString() {
    	return this.itemBeingOrdered.toString();
    }
    
    public String getItemBeingOrderedShelfID() {
    	return this.itemBeingOrdered.shelfID;
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
    
  }
