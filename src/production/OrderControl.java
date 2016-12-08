package production;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * 
 * @author dihuang
 *
 */
public class OrderControl implements Tickable{
	private static LinkedList<Order> orderQueue = new LinkedList<Order>();
    /**
     * @author Wei
     */
	public static void clearstaticFields() {
		orderQueue = new LinkedList<Order>();
		pendingOrders = new LinkedList<Order>();
	}
	public OrderControl(){
		// schedule orders for testing
		Random rd=new Random();
		orderQueue.add(new Order("a st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(3)),new ItemSlot(Catalog.at(0))}));	// fork, pen
		orderQueue.add(new Order("b st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(4))}));	// spoon
		orderQueue.add(new Order("c st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(1)), new ItemSlot(Catalog.at(0)),new ItemSlot(Catalog.at(5))}));// paper, pen, plate
		orderQueue.add(new Order("d st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(2)),new ItemSlot(Catalog.at(2))}));	// book(2)
		// more...
		orderQueue.add(new Order("e st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(3))}));	
		orderQueue.add(new Order("f st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(2)), new ItemSlot(Catalog.at(1))}));	
		orderQueue.add(new Order("e st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(4))}));	
		orderQueue.add(new Order("e st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(2))}));	
		orderQueue.add(new Order("e st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(8)), new ItemSlot(Catalog.at(7)), new ItemSlot(Catalog.at(6))}));	
		orderQueue.add(new Order("e st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(7))}));	
		orderQueue.add(new Order("e st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(3))}));	
		orderQueue.add(new Order("e st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(8))}));	
		orderQueue.add(new Order("e st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(3)), new ItemSlot(Catalog.at(7))}));	
		orderQueue.add(new Order("e st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(4))}));	
		orderQueue.add(new Order("e st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(6))}));	
		orderQueue.add(new Order("e st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(2)), new ItemSlot(Catalog.at(5))}));	
		orderQueue.add(new Order("e st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(1))}));	
		orderQueue.add(new Order("e st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(8)), new ItemSlot(Catalog.at(3))}));	
		orderQueue.add(new Order("e st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(4))}));	
		orderQueue.add(new Order("e st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(1)), new ItemSlot(Catalog.at(8)), new ItemSlot(Catalog.at(1))}));	
		orderQueue.add(new Order("e st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(7))}));	
		orderQueue.add(new Order("e st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(8))}));	
		orderQueue.add(new Order("e st "+rd.nextInt(10000), new ItemSlot[]{new ItemSlot(Catalog.at(6))}));	
		// ...
	}
	/**
	 * only used for some possible testing
	 * @param orderToBeAdded
	 */
	public static void addOrder(Order orderToBeAdded) {
		orderQueue.add(orderToBeAdded);
	}
	/**
	 * orderQueue holds scheduled orders; pendingOrders holds the orders which already arrived
	 * and are waitting for system to process
	 */
	private static LinkedList<Order> pendingOrders = new LinkedList<Order>();
	public static LinkedList<Order> getPendingOrders(){
		return pendingOrders;
	}
	private int lasttick = -1;
	private int Tick = 0;
	@Override
	public boolean suspend(int suspticks, int currtick){
		if(currtick == 0){
			return true;
		}
		if(lasttick == -1){
			lasttick = Tick;
		}
		if(currtick == lasttick + suspticks){
			lasttick = -1;
			return true;
		}else{
			return false;
		}
	}
	@Override
	public void tick(int tick) {
		DefaultTableModel model=null;
		if(Production.getOG()!=null) {
			Production.getOG().getTable_1().setModel(new DefaultTableModel(
					new Object[][] {
						{"Order name(address)", "Items"},
					},
					new String[] {
						"Order name(address)", "Items"
					}
				));
			model=(DefaultTableModel)Production.getOG().getTable_1().getModel();
		}
		for(Order o : pendingOrders){
			if(model!=null)model.addRow(new Object[]{o.address, Arrays.toString(o.getItemSlots())});
			if(o.isAllFilled()){
				pendingOrders.remove(o);
			}
		}
		model=null;
		if(Production.getOG()!=null) {
			Production.getOG().getTable().setModel(new DefaultTableModel(
					new Object[][] {
						{"Order name(address)", "Items"},
					},
					new String[] {
						"Order name(address)", "Items"
					}
				));
			model=(DefaultTableModel)Production.getOG().getTable().getModel();
		}
		for(Order o : orderQueue){
			if(model!=null)model.addRow(new Object[]{o.address, Arrays.toString(o.getItemSlots())});
		}
		if(orderQueue.isEmpty()) {
			if(Production.getOG()!=null) {
			Production.getOG().getTable().setModel(new DefaultTableModel(
					new Object[][] {
						{"Order name(address)", "Items"},
					},
					new String[] {
							"Order name(address)", "Items"
					}
				));
			((DefaultTableModel)Production.getOG().getTable().getModel()).addRow(new Object[]{"empty", "empty"});
			}
			return;
		}
		Tick = tick;
		if(suspend(10,tick)){	// poll an order every 10 ticks
			Order currO = orderQueue.poll();
			System.out.println("=New Order=: " + currO);
			if(!ItemControl.itemsAvailable(currO)){
				System.out.println("OrderControl:There is no plenty stock for this order right now.");
				orderQueue.add(currO);	// re-order right now
				return;
			}
			System.out.println("OrderControl:New order is in pending.");
			pendingOrders.add(currO);
		}
	}
}

