package production;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 
 * @author wangyang xu, Ted Herman, dihuang
 *
 */
public class ItemControl implements Tickable {
	static HashSet<Item> stock = new HashSet<Item>();
    /**
     * @author Wei
     */
	public static void clearstaticFields() {
		stock = new HashSet<Item>();
	}
	ItemControl() {
		Shelf shelf1 = Floor.SHELVES[0];//suppose there are two shelves		
		Shelf shelf2 = Floor.SHELVES[1];
		Shelf shelf3 = Floor.SHELVES[2];
		Shelf shelf4 = Floor.SHELVES[3];
		Shelf shelf5 = Floor.SHELVES[4];

		initItems(Catalog.at(0), 100, shelf1);	// pen            		
		initItems(Catalog.at(1), 100, shelf1);	// paper
		initItems(Catalog.at(2), 100, shelf1);	// book
		initItems(Catalog.at(3), 100, shelf2);	// fork
		initItems(Catalog.at(4), 100, shelf2);	// spoon
		initItems(Catalog.at(5), 100, shelf2);	// plate
		// more...
		initItems(Catalog.at(6), 20, shelf3);	// tank
		initItems(Catalog.at(7), 20, shelf4);	// fighter
		initItems(Catalog.at(8), 20, shelf5);	// submarine
	}
	private void initItems(ItemInfo info, int quantity, Shelf shelf) {
		for (int i = 0; i < quantity; i++) {
			Item item = new Item(info);
			item.setHolder(shelf);
			stock.add(item);
		}
	}
	/**
	 * only used for some possible testing
	 * @param item, shelf
	 */
	public static void addItem(Item item, Shelf shelf) {
		item.setHolder(shelf);
		stock.add(item);
	}
	/**
	 * @author Ted Herman
	 */
	public static Item removeItem(Item a, Shelf s) {
		for (Item e : stock) {
			if (e.getHolder().onFloor())
				continue; // only remove from carried Shelf
			if (!e.equals(a))
				continue; // look for this item only
			if (!e.getHolder().getPos().equals(s.getPos()))
				continue; // only Shelf s
			stock.remove(e);
			e.setHolder(null); // not on Shelf anymore
			return e;
		}
		return null; // not supposed to happen
	}
	/**
	 * @param info
	 * @return current stock of some item(find by itemInfo)
	 */
	public static int itemStock(ItemInfo info) {
		int quantity = 0;
		for (Item item : stock)
			if (item.match(info))
				quantity++;
		return quantity;
	}
	/**
	 * @author Ted Herman, Di Huang
	 */
	public static Shelf findItem(ItemInfo info) {
		System.out.println("Finding item"+info+"on shelves...");
		for (Item e : stock) {
			if (!e.getHolder().onFloor())
				continue; // ignore moving shelves
			if (e.itemInfo.equals(info))
				return e.getHolder();
		}
		System.out.println("There are no enough item, so doing magic replenishments...");
		// even though this is going to return null (out of stock)
		// go ahead and put the needed item somewhere, like on another
		// shelf
		Item magic = new Item(info); // just what we need
		// search for a shelf which isn't being transported
		magic.setHolder(Floor.SHELVES[Floor.SHELVES.length-1]);	// for temp solution
		return null;
	}
	/**
	 * @author Ted Herman, Di Huang
	 * @param Shelf 
	 * @return array of Item that are on this shelf
	 */
	public static Item[] onShelf(Shelf s) {
		List<Item> scan = new ArrayList<Item>();
		for (Item e: stock) {
			if (!(e.getHolder().home).equals(s.home)) continue;
			scan.add(e);
		}
		return scan.toArray(new Item[0]);
    }
	/**
	 * @param order
	 * @return true if an order could be fulfiled
	 */
	public static boolean itemsAvailable(Order order) {
		for (ItemSlot ip : order.getItemSlots())
			if (itemStock(ip.itemInfo) < order.getNeededQuantity(ip.itemInfo))
				return false;
		return true;
	}

	@Override
	public void tick(int tick) {
	}
	@Override
	public boolean suspend(int suspticks, int currtick) {
		return false;
	}
}


