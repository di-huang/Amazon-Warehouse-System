/** 
 * @author Charles Carlson
 * 
 * definitions in regards to Orders
 * 
 * CLEARED: The Order has passed through Order Control which has confirmed that the Inventory contains the Item(s) of the Order and can support the quantity requested
 * NOT CLEARED: If one of the criteria above fails, the Item is NOT CLEARED, and the Order is abandoned.
 */
package production_unitTesting;

import org.junit.Test;

public class TestOrders {
	
	/**
	 * @author Charles Carlson
	 * 
	 * this class is used to test if the Order Control is properly clearly and denying clearance to Orders
	 * that run through the Order Queue
	 * as of now it is not properly working with the Inventory (ItemControl) because of difficulties with GitHub resulting in package problems
	 * goal is to only use the ItemControl "catalog" and delete the 9 Items created below
	 * though whether I use Items created below or Items from ItemControl Order Control should act the same
	 */
	@Test
	public void test() {

		OrderControl O = new OrderControl();
		ItemController I = new ItemController();
		
		/**
		 * @author Charles Carlson
		 * 
		 * 9 Items to be added to the Inventory
		 */
		OrderItem RED_SHIRT = new OrderItem("RED_SHIRT", 200, "1");
		OrderItem BLUE_SHIRT = new OrderItem("BLUE_SHIRT", 300, "1");
		OrderItem BICYCLE = new OrderItem("BICYCLE", 2, "2");
		OrderItem BASKETBALL = new OrderItem("BASKETBALL", 50, "2");
		OrderItem SHAVING_CREAM= new OrderItem("SHAVING_CREAM", 10, "3");
		//Item created but will not clear because the quantity requested is too great
		OrderItem DUCT_TAPE = new OrderItem("DUCT_TAPE", 2, "3");
		OrderItem BOOK = new OrderItem("BOOK", 20, "4");
		OrderItem EGG_PLANT = new OrderItem("EGG_PLANT", 2000, "5");
		OrderItem FLUTE = new OrderItem("FLUTE", 5, "6");
		//Item created but will not be added to ItemController, to test if checks are working
		OrderItem KITE = new OrderItem("KITE", 1, "7");
		OrderItem SKI_POLE = new OrderItem("SKI_POLE",3,"3"); 
		
		I.addItem(RED_SHIRT);
		I.addItem(BLUE_SHIRT);
		I.addItem(BICYCLE);
		I.addItem(BASKETBALL);
		I.addItem(SHAVING_CREAM);
		I.addItem(DUCT_TAPE);
		I.addItem(EGG_PLANT);
		I.addItem(BOOK);
		I.addItem(FLUTE); 
		//commented out for testing
		//I.addItem(KITE);
		I.addItem(SKI_POLE);
		
		/**
		 * @author Charles Carlson
		 * 
		 * Orders are created with Items(above, not from ItemControl), quantity, and shipping address
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
		 * 
		 * there are not very many Orders being tested but Order Control is being asked to handle every possible situation:
		 * 	1. the item is stocked in the ItemController, there is enough stock in ItemController to support the Order
		 * 	2. the item is not stocked in the ItemController, ***It obviously won't check if it can support the quantity if it knows it doesn't have it
		 * 	3. The item is stocked however we do not currently have the quantity stocked to support the Order
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
