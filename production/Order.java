package production;

/**
 * 
 * @author dihuang
 * member variables of Order: address, array of itemslot which holds the item
 * and allFilled(boolean)
 *
 */
public class Order {
	public final String address;
	private ItemSlot[] itemSlots;
	private boolean allFilled;
	public Order(String address, ItemSlot[] itemSlots) {
		this.address = address;
		this.itemSlots = itemSlots;
		allFilled = false;
	}
	public ItemSlot[] getItemSlots() {
		return itemSlots;
	}
	public void setItemSlots(ItemSlot[] itemSlots) {
		this.itemSlots = itemSlots;
	}
	public boolean isAllFilled(){
		return allFilled;
	}
	public ItemInfo getUnfilledItemInfo(){
		for(ItemSlot i : itemSlots)
			if(i.getItem() == null)
				return i.itemInfo;
		allFilled = true;
		return null;
	}
	public int getNeededQuantity(ItemInfo info){
		int quantity = 0;
		for(ItemSlot i : itemSlots)
			if(i.itemInfo.equals(info))
				quantity++;
		return quantity;
	}
}

