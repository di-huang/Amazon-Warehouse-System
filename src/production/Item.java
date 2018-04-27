package production;

/**
 * 
 * @author dihuang, wangyang xu
 *
 */
public class Item {
	final ItemInfo itemInfo;
	Shelf holder;

	public Item(ItemInfo info) {
		itemInfo = info;
		holder = null;
	}

	public int ID() {
		return itemInfo.ID;
	}

	public String description() {
		return itemInfo.description;
	}

	public Shelf getHolder() {
		return holder;
	}

	public void setHolder(Shelf holder) {
		this.holder = holder;
	}

	public boolean match(ItemInfo info) {
		return itemInfo.equals(info);
	}

	public boolean match(ItemSlot slot) {
		return itemInfo.equals(slot.getItemInfo());
	}

	@Override
	public String toString() {
		return itemInfo.toString();
	}
}

/**
 * 
 * @author dihuang, wangyang xu
 *
 */
class ItemSlot {
	private final ItemInfo itemInfo;
	private Item item;

	public ItemSlot(ItemInfo info) {
		itemInfo = info;
		item = null;
	}

	public int ID() {
		return itemInfo.ID;
	}

	public String description() {
		return itemInfo.description;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item i) {
		item = i;
	}
	
	public ItemInfo getItemInfo() {
		return itemInfo;
	}

	public boolean match(ItemInfo info) {
		return itemInfo.equals(info);
	}

	public boolean match(Item item) {
		return itemInfo.equals(item.itemInfo);
	}

	@Override
	public String toString() {
		return itemInfo.toString();
	}
}
