/**
 * @author Charles Carlson
 * 
 * CURRENT ISSUES: 
 * 	want to use static methods from ItemControl but cannot because the packages are all wrong because I can't pull from GitHub for some reason
 * HOW TO FIX: 
 * 	first have to figure out GitHub bug/error. 
 * 	then change the packages.
 * 	there will be many many errors at first but they should be easily fixed. 
 * 	change my methods to static
 */


/**
 * 
 * @author Charles Carlson
 * 
 * definitions in regards to Orders
 * 
 * CLEARED: The Order has passed through Order Control which has confirmed that the Inventory contains the Item(s) of the Order and can support the quantity requested
 * NOT CLEARED: If one of the criteria above fails, the Item is NOT CLEARED, and the Order is abandoned.
 * 
 * would be cool if we could re-upload the Order to the end of Pending Orders so that potentially on its next time around it will be cleared.
 * this only makes sense if the Inventory is regularly updated. Probably not going to happen.
 */
package production_unitTesting;

import org.junit.Test;

public class TestOrders {
	
	/**
	 * @author Charles Carlson
	 * 
	 * d
	 * this class is used to test if the Order Control is properly clearly and denying clearance to Orders
	 * that run through the Order Queue
	 * as of now it is not properly working with the Inventory (ItemControl) because of difficulties with GitHub resulting in package problems
	 * goal is to only use the ItemControl "catalog" and delete the 9 Items created below
	 * though whether I use Items created below or Items from ItemControl Order Control should act the same
	 */
	
	@Test
	public void test() {


		OrderControl O = new OrderControl();
		
		/**
		 * @author Charles Carlson
		 * 
		 * 9 Items for 9 Orders
		 * These used for now until packages fixed... then testing will be done with ItemControl
		 * 
		 */
		Item RED_SHIRT = new Item("RED_SHIRT", 200, "1");
		Item BLUE_SHIRT = new Item("BLUE_SHIRT", 300, "1");
		Item BICYCLE = new Item("BICYCLE", 2, "2");
		Item BASKETBALL = new Item("BASKETBALL", 50, "2");
		Item SHAVING_CREAM= new Item("SHAVING_CREAM", 10, "3");
		Item DUCT_TAPE = new Item("DUCT_TAPE", 1, "3");
		Item BOOK = new Item("BOOK", 20, "4");
		Item EGG_PLANT = new Item("EGG_PLANT", 2000, "5");
		Item FLUTE = new Item("FLUTE", 5, "6");
		Item KITE = new Item("KITE", 1, "7");
		Item SKI_POLE = new Item("SKI_POLE",3,"3");
		
		
		/**
		 * @author Charles Carlson
		 * 
		 * Orders are created with Items(above, not from ItemControl), quantity, and shipping address
		 * using above Items for now, with proper package it will use ItemControl
		 */
		Order order1 = new Order(RED_SHIRT, 2, "501 South Dodge Street");
		Order order2 = new Order(BLUE_SHIRT, 5, "313 South Gilbert Street");
		Order order3 = new Order(BICYCLE, 29, "613 East Court Street");
		Order order4 = new Order(BASKETBALL, 2, "512 North Dodge Street");
		Order order5 = new Order(SHAVING_CREAM, 4, "902 Davenport Street");
		Order order6 = new Order(DUCT_TAPE, 1, "451 East Market Street");
		Order order7 = new Order(BOOK, 4, "222 Governor Street");
		Order order8 = new Order(EGG_PLANT, 1, "378 Lucas Street");
		Order order9 = new Order(FLUTE, 9, "987 Clinton Ave");
		
		//This Order should not be cleared because should not be able to support quantity
		//can't actually test until packages fixed
		Order order10 = new Order(KITE, 2,"999 Street");
		
		
		/**
		 * @author Charles Carlson
		 * 
		 * Orders are added to Pending Orders in Order Control
		 */
		O.addOrder(order1);
		O.addOrder(order2);
		O.addOrder(order3);
		O.addOrder(order4);
		O.addOrder(order5);
		O.addOrder(order6);
		O.addOrder(order7);
		O.addOrder(order8);
		O.addOrder(order9);
		O.addOrder(order10);
		
		
		/**
		 * @author Charles Carlson
		 * 
		 * set up and run unit test
		 */
		System.out.println("The followng are requested Items that will be run through Order Control to check if clearable");
		
		for(int i=0;i<O.getLengthPendingOrders()-1;i++) {
			System.out.println(O.getPendingOrders().get(i) + " x" + (O.getPendingOrders().get(i).getQuantity()).toString());
		}
		
		System.out.println("\nBegin unit test of Order Control");
		System.out.println("\n");
		for(int i=0;i<100;i++) {
			O.tick(i);
		}
		
		System.out.println("Test completed");
		
			
	}
	
}
