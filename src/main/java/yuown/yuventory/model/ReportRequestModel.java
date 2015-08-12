package yuown.yuventory.model;

public class ReportRequestModel extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5271383530174439111L;

	private String startDate;

	private String endDate;

	private String itemName;

	private String itemType;

	private int category;

	private int stockType;

	private int supplier;

	private int lent;

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getItemName() {
		return itemName;
	}

	public String getItemType() {
		return itemType;
	}

	public int getCategory() {
		return category;
	}

	public int getStockType() {
		return stockType;
	}

	public int getSupplier() {
		return supplier;
	}

	public int getLent() {
		return lent;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public void setStockType(int stockType) {
		this.stockType = stockType;
	}

	public void setSupplier(int supplier) {
		this.supplier = supplier;
	}

	public void setLent(int lent) {
		this.lent = lent;
	}
}
