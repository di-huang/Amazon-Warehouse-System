package production_unitTesting;

import production_unitTesting.Item;

public class Order {
	
    /**
    * used _ to differentiate from previously existing Order class for now 
    */
	public Item itemBeingOrdered;
    public String shippingAddress;
    public Integer quantity;
   
    
    /**
     * @author Charles Carlson
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
     * Alot of these don't get used by are there just incase
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
    	return this.itemBeingOrdered.toString();
    }
    
    public String getItemBeingOrderedNameString() {
    	return this.itemBeingOrdered.toString();
    }
    
    public String getItemBeingOrderedShelfID() {
    	if(this.itemBeingOrdered.shelfID!=null) { return this.itemBeingOrdered.shelfID; }
    	else { return null; }
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
