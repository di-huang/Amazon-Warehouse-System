package production_unitTesting;

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
   Object o;
   
   public Cell(){
	   
   }
   
   public Object getContents(){
	   return 1;
   }
   public void getCell(Point one){
	    System.out.println(one.getName());
	    
   }
   public Object cellType(Point p1){
	   System.out.println(p1.getObject().toString());
	   return p1.getObject();
	   
   }
   public boolean isBin(Point p1){
	   Point myP = p1;
	   String name = p1.getName();
	   if (name == "Bin"){
		   System.out.println("At belt spot- x: " + myP.getX() + " and y: " + myP.getY() + " is object type: " + name);
		   return true;
	   }
	   else {
		   System.out.println(" WTF At belt spot- x: " + myP.getX() + " and y: " + myP.getY() + " is object type: " + name);
		   return false;
	   }
   }
   
   public Parcel setContents(Parcel p){
	   //will replace bin with parcel
	   pOrig = p;
	   return pOrig;
   }
   
}
