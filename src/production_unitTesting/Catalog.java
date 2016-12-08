package production;

import production.ItemInfo;

/**
 * 
 * @author wangyang xu, dihuang
 * catalog of inventory
 */
public class Catalog{
	static final ItemInfo[] catalog = {
			new ItemInfo(1000, "pen"), 		// 0
			new ItemInfo(1001, "paper"),	// 1
			new ItemInfo(1002, "book"), 	// 2
			new ItemInfo(1003, "fork"), 	// 3
			new ItemInfo(1004, "spoon"),	// 4
			new ItemInfo(1005, "plate"),	// 5
			new ItemInfo(1006, "tank"),		// 6
			new ItemInfo(1007, "fighter"),	// 7
			new ItemInfo(1008, "submarine"),// 8
			//...
	};
	/**
	 * @param index, id, description
	 * @return itemInfo 
	 */
	static ItemInfo at(int index){
		return catalog[index];
	}
	static ItemInfo get(Integer id){
		for(ItemInfo info : catalog)
			if(info.ID == id)
				return info;
		return null;
	}
	static ItemInfo get(String des){
		for(ItemInfo info : catalog)
			if(info.description == des)
				return info;
		return null;
	}
}

/**
 * @author wangyang xu, dihuang
 * ItemInfo has information of ID and description
 */
class ItemInfo{
	final int ID;
	final String description;
	ItemInfo(int id, String des){
		ID = id;
		description = des;
	}
	boolean equals(ItemInfo other){
		return this.ID == other.ID;
	}
	@Override
	public String toString() {
		return "(" + ID + "-" + description + ") ";
	}
}