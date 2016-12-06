package production_unitTesting;
import java.util.*;

//import warehouse_system.orders.*;
//import warehouse_system.*;


//Couldn't get javadoc's to work, will keep trying


/**
 * 
 * @author Ted Herman, Nick Barnes
 *  
 * A Bin contains an Order that has all its items ready to go
 *
 */
//public class Bin {
//  Order order;  // ready to go on the Belt
//  boolean finished;  // initially false, becomes true when ready to go
//  public Bin() { order = null; finished = false; }
//  public boolean isFinished() { return finished; }
//  public void setFinished() { finished = true; }
//  public Order getOrder() { return order; }
//  public void setOrder(Order o) { order = o; }
//  }

public class Bin  {
    //for binList, 0 is starting position, 19 is last position
    Order order;
	boolean finished = false;
	int idnum = 0;
	
	public Bin() {
		order = null;
		finished = false;
		
		//Create random number 0 - 99
		double randNumber = (Math.random() * 100);
		idnum = (int)randNumber; 

		
	}
	public Bin(Order o, int id){
		order = o;
		finished = false;
		idnum = id;
	}
	public boolean isFinished(){
		//if order = bin then it is finished
		return finished;
	}
	public void setFinished(){
		//if order = bin then it is finished
		finished = true;
		System.out.println("Order is finished");
	}

	
	//Hashmap for an <orderNumber, itemID>
	//Map<Integer, Integer> order = new HashMap<Integer, Integer>();
    
	
//	public int binBin = 1;
    
//    public Bin(){
//    	 order.put(Belt.orderNum, 322);
//    }
    public Order getOrder(){
    	return order;
    }
    public void setOrder(Order o) { 
    	order = o; 
    }
    
    public void addBin(Order o){
    	setFinished();
    	Order myOrd = o;
    //	Bin b = new Bin(o);
    }
    
    //this is where orders will be saved to a bin, will need to wait
    //for integration otherwise I am not too sure how to do that
}
