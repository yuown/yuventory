package yuown.yuventory.model;

import yuown.yuventory.holders.StockTypeMethod;

public class StockTypeModel extends Model {

	private String name;

	private StockTypeMethod method;

	private boolean remove;

	public StockTypeModel() {
	}

	public StockTypeModel(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StockTypeMethod getMethod() {
		return method;
	}

	public void setMethod(StockTypeMethod method) {
		this.method = method;
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

}
