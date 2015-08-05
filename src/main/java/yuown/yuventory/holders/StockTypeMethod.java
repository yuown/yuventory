package yuown.yuventory.holders;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

public enum StockTypeMethod {

	Entry("Entry", false), Exit("Exit", true);

	private String name;
	private boolean deleteAllowed;

	private static Map<String, StockTypeMethod> map = new HashMap<String, StockTypeMethod>();

	static {
		for (StockTypeMethod stockTypeMethod : EnumSet.allOf(StockTypeMethod.class)) {
			map.put(stockTypeMethod.name, stockTypeMethod);
		}
	}

	StockTypeMethod(String name, boolean deleteAllowed) {
		this.name = name;
		this.setDeleteAllowed(deleteAllowed);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDeleteAllowed() {
		return deleteAllowed;
	}

	public void setDeleteAllowed(boolean deleteAllowed) {
		this.deleteAllowed = deleteAllowed;
	}

	public static Set<String> allKeys() {
		return map.keySet();
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}