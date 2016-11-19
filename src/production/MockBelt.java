package production;


//import warehouse_system.belt.Parcel;
//import warehouse_system.floor.*;
//import warehouse_system.orders.*;
import java.util.List;

/**
 * @author Ted Herman, Nicholas Barnes
 * @author Di Huang
 */
//github.com/dhuang12138/CS2820.git
public class MockBelt implements Belt {
   int mockTick = 0;
   Floor F;
   List<Point> beltarea;
   Point p = new Point(0,0, "mock");

  /**
   * @author Ted Herman, Nicholas Barnes
   * @param Floor object.
   * Floor is needed to find location of belt area, cells etc.
   */
  public MockBelt(Floor F) {
	  Point f1 = new Point(0,0,"beltStart");
	  Point f2 = new Point(20,0, "beltEnd");
	this.F = F;

	beltarea.add(f1);
	beltarea.add(f2);
	 
    }
  
  /**
   * the tick() method is where belt moving gets done;
   * it will have to move any Bin or Parcel withing the Cell
   * of a Belt area to the next Cell, and this has to be done
   * on all Points of the beltarea in parallel (not coded yet here)
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
   * beltarea when they are completed
   * 
   * and a really thorough Belt would simulate the shipping dock,
   * collecting a lot of parcels and grouping them into a truck
   *
   */
  public void tick(int count) {
	  count = count + 1;
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
    }
  /**
   * Called by Orders to simulate a Picker starting a new Bin
   */
  public Bin getBin() {
	return null; 
    }
  }