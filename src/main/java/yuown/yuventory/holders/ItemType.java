package yuown.yuventory.holders;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ItemType {

	Gold("Gold", "gold"), Silver("Silver", "silver"), Others("Others", "others");

	private String name;
	private String type;

	private static Map<String, ItemType> map = new HashMap<String, ItemType>();

	static {
		for (ItemType stockType : EnumSet.allOf(ItemType.class)) {
			map.put(stockType.name, stockType);
		}
	}

	ItemType(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}