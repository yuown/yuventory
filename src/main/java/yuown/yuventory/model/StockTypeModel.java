package yuown.yuventory.model;

import yuown.yuventory.holders.StockTypeMethod;

public class StockTypeModel extends Model {

	private String name;

	private StockTypeMethod method;

	private boolean delete;

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

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}
}
