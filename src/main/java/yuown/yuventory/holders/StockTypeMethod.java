package yuown.yuventory.holders;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum StockTypeMethod {

	Entry("Entry", "entry"), Exit("Exit", "exit");

	private String name;
	private String type;

	private static Map<String, StockTypeMethod> map = new HashMap<String, StockTypeMethod>();

	static {
		for (StockTypeMethod stockTypeMethod : EnumSet.allOf(StockTypeMethod.class)) {
			map.put(stockTypeMethod.name, stockTypeMethod);
		}
	}

	StockTypeMethod(String name, String type) {
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

	public static Set<String> all() {
		return map.keySet();
	}
}