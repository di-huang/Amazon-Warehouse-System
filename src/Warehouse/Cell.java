package Warehouse;

//Nicholas Barnes

//import warehouse_system.*;
//import warehouse_system.belt.Parcel;
//import warehouse_system.floor.*;

public class Cell  {
   Integer cellNum = 0;
   Point p2 = new Point(0,0,"Hello");
   Point p1;
   Parcel pOrig;
   
   public Cell(){
	   
   }
   
   public int getContents(){
	   return 1;
   }
   public int getCell(){
	   return 0;
   }
   public Parcel setContents(Parcel p){
	   //will replace bin with parcel
	   pOrig = p;
	   return pOrig;
   }
   
}
