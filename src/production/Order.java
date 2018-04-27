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
	public Order(String address, ItemSlot[] itemSlots) {
		this.address = address;
		this.itemSlots = itemSlots;
	}
	public ItemSlot[] getItemSlots() {
		return itemSlots;
	}
	public void setItemSlots(ItemSlot[] itemSlots) {
		this.itemSlots = itemSlots;
	}
	public boolean isAllFilled(){
		for(ItemSlot is : itemSlots){
			if(is.getItem() == null){
				return false;
			}
		}
		return true;
	}
	public ItemInfo getUnfilledItemInfo(){
		for(ItemSlot i : itemSlots)
			if(i.getItem() == null)
				return i.getItemInfo();
		return null;
	}
	public int getNeededQuantity(ItemInfo info){
		int quantity = 0;
		for(ItemSlot i : itemSlots)
			if(i.getItemInfo().equals(info))
				quantity++;
		return quantity;
	}
	@Override
	public String toString() {
		String s = "Order[" + address + "; ";
		for(ItemSlot is : itemSlots){
			s += is.getItemInfo();
		}
		s += "]";
		return s;
	}
}

