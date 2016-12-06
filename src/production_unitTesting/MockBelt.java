 package production_unitTesting;

//import warehouse_system.floor.*;
//import warehouse_system.orders.*;


import java.util.*;

import java.util.List;

import production_unitTesting.Floor;
import production_unitTesting.Order;
import production_unitTesting.Point;

/**
 * @author Ted Herman, Nicholas Barnes
 * @author Di Huang
 */
public class MockBelt implements Belt {
   int mockTick = 0;
   MockFloor F;
   ArrayList<Point> beltarea = new ArrayList<Point>();
   Point p = new Point(0,0, "mock");
   Cell c2;
   Bin b1;
   ArrayList<Bin> binList = new ArrayList<Bin>();
   
   public MockBelt(){
     
   }

   public MockBelt(MockFloor F) {
	Point f1 = new Point(0,0,"pickStart");
	Point f2 = new Point(0,19, "beltEnd");

	 

	//  beltarea.add(f1);
	//  beltarea.add(f2);
	//  beltContents(F.PACKERBELT[1]);
}
  
  /**
   * the tick() method is where belt moving gets done;
   * it will have to move any Bin or Parcel within the Cell
   * of a Belt area to the next Cell, and this has to be done
   * on all Points of the belt area in parallel (not coded yet here)
   * 
   * after moving the belt, tick() should check to see whether
   * or not a Bin has arrived at the Packer - then doPacker() 
   * should be called, which removes the Bin, creates a Parcel 
   * and puts that Parcel on the belt (in more advanced versions,
   * one Bin might make more than one Parcel, if Items are too 
   * big to fit entirely into one Parcel). After the Parcel is
   * in a Cell at the Packer, the belt will be stopped until some
   * later tick when the Packer finishes the Parcel.
   * 
   * even fancier ideas are to give the Packer a queue of Bins
   * and remove each Bin that arrives, taking some non-trivial
   * number of ticks to make Parcels, returning them to the 
   * belt area when they are completed
   * 
   * and a really thorough Belt would simulate the shipping dock,
   * collecting a lot of parcels and grouping them into a truck
   *
   */
  public void tick(int count) {
	  count = count + 1;
	  Bin currentBin = getBin();
		Point oP = new Point(0,0, "Bin");
		  this.F = F;
		  binList.add(getBin());
//		  binList.add(getBin());
//		  boolean binCheck;
//		  binCheck = c2.isBin(oP);
		  if(oP.getName() == "Bin"){
			  //System.out.println("It is a bin");
	//		  Bin newBin = new Bin();
//			  binList.add(newBin);
			  System.out.println("current bins: " + binList.toString());
		  }
		  else{
			  System.out.println("Not WORKING");
		  }
	  
	  
	  
	  //bin or parcel needs to be moved here
	  //will be moved to the next cell
	  //for(int i = 0; i<pickerbeltsize; i++){
	  //      
	  //
	  //}
    }
  
  /**
   * Local method to see whether belt can be moved
   */
  private boolean isMovable() {
	for (Point p: beltarea) {
	  Cell c = new Cell(); // F.getLocation("mock");
	 // c.getContents();
	  Object o = c.getContents();
	  if (o == null) continue;  // skip empty cell
	  if ((o instanceof Bin) && !((Bin)o).isFinished()) return false;
	  if ((o instanceof Parcel) && !((Parcel)o).isFinished()) return false;
	  }
	return true;  // nothing stops belt from moving
    }
  
  /**
   * Local method doPacker() simulates a Bin arriving to the 
   * Packer via the belt moving. 
   */
  private void doPacker() {
//	Cell c = F.getCell(0 /*F.getPacker()*/);
	Cell c = new Cell();
//	c.cellNum();
	Object o = c.getContents();  // get what the Cell has in it
	assert o instanceof Bin;     // it had better be a Bin
	Bin b = (Bin)o;              // use the Bin to
	Order v = b.getOrder();      // get the finished Order
	Parcel n = new Parcel(v.getShippingAddress(),v.getItemBeingOrdered());
	c.setContents(n);  // replace Bin with Parcel on the belt
    }
  
  /**
   * Called by Orders to check whether a new Bin can be safely started
   */
  public boolean binAvailable() {
	return false;
	//if (0,0) doesn't have a bin, return true
    }
 
  public Bin getBin() {
	return new Bin(); 
    }
  
  public void beltContents(Point p){
     // String s1 = c2.getCell(p);
      String r = p.getName();
      System.out.println(" belt contents: " + r);
      //return r;
  }
}