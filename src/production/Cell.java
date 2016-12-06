package production;

//Nicholas Barnes

//import warehouse_system.*;
//import warehouse_system.belt.Parcel;
//import warehouse_system.floor.*;

public class Cell  {
   Integer cellNum = 0;
   Point p2;
   Point p1;
   Parcel pOrig;
   MockFloor m;
   MockFloor RetM;
   Point cellHas;
   Integer cellX;
   Integer cellY;
   
   public Cell(){
	   
   }
   
   public int getContents(){
	   return 1;
   }
   public void getCell(Point one){
	    cellX = one.getX();
	    cellY = one.getY();
	    p1 = one;
	   // cellHas = p1.getName();
	   // System.out.println(cellHas + " is located at" + cellX + ", " + cellY);
	 //   return cellHas;
   }
   public Parcel setContents(Parcel p){
	   //will replace bin with parcel
	   pOrig = p;
	   return pOrig;
   }
   
}
