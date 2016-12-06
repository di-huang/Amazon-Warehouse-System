package production;

/**
 * @author wangyang xu, dihuang
 * member variables of Item: itemInfo, (shelf)holder
 */
public class Item {
	final ItemInfo itemInfo;
	Shelf holder;
	Item(ItemInfo info) {
		itemInfo = info;
		holder = null;
	}
	int ID(){
		return itemInfo.ID;
	}
	String description(){
		return itemInfo.description;
	}
	Shelf getHolder() {
		return holder;
	}
	void setHolder(Shelf holder) {
		this.holder = holder;
	}
	boolean match(ItemInfo info) {
		return itemInfo.equals(info);
	}
	boolean match(ItemSlot slot){
		return itemInfo.equals(slot.itemInfo);
	}
	@Override
	public String toString() {
		return itemInfo.toString();
	}
}

/**
 * @author wangyang xu, dihuang
 * member variables of ItemSlot: itemInfo, item(being hold)
 */
class ItemSlot {
	final ItemInfo itemInfo;
	Item item;
	ItemSlot(ItemInfo info) {
		itemInfo = info;
		item = null;
	}
	int ID(){
		return itemInfo.ID;
	}
	String description(){
		return itemInfo.description;
	}
	Item getItem() {
		return item;
	}
	void setItem(Item i) {
		item = i;
	}
	boolean match(ItemInfo info) {
		return itemInfo.equals(info);
	}
	boolean match(Item item) {
		return itemInfo.equals(item.itemInfo);
	}
	@Override
	public String toString() {
		return itemInfo.toString();
	}
}
