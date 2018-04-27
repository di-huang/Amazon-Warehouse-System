package production_unitTesting;

//import warehouse_system.*;
//import warehouse_system.inventory.*;


public class Parcel {
  /**
   * 
   * @author Ted Herman, Nicholas Barnes
   * 
   * A Parcel has an address and a list of Items
   *
   */
	
  String address;
 // Item[] items;
  boolean finished;
  public Parcel(String address, Item item){
	  this.address = address;
	  finished = false;
  }
  public Parcel(String address, Item[] items) { 
	this.address = address;
//	this.items = items;
	finished = false;
    }
  public boolean isFinished() { return finished; }
  public void setFinished() { finished = true; }
  }