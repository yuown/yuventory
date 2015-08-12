package yuown.yuventory.model;

public class ItemModel extends Model {

	private String name;

	private String itemType;

	private double weight;

	private int supplier;

	private int category;

	private int stockType;

	private int user;

	private int lendTo;

	private long lendDate;

	private String lendDescription;

	private Boolean sold;

	private long createDate;

	private long updateDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getSupplier() {
		return supplier;
	}

	public void setSupplier(int supplier) {
		this.supplier = supplier;
	}

	public int getStockType() {
		return stockType;
	}

	public void setStockType(int stockType) {
		this.stockType = stockType;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public int getLendTo() {
		return lendTo;
	}

	public void setLendTo(int lendTo) {
		this.lendTo = lendTo;
	}

	public long getLendDate() {
		return lendDate;
	}

	public void setLendDate(long lendDate) {
		this.lendDate = lendDate;
	}

	public String getLendDescription() {
		return lendDescription;
	}

	public void setLendDescription(String lendDescription) {
		this.lendDescription = lendDescription;
	}

	public Boolean getSold() {
		return sold;
	}

	public void setSold(Boolean sold) {
		this.sold = sold;
	}

	public long getCreateDate() {
		return createDate;
	}

	public long getUpdateDate() {
		return updateDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	public void setUpdateDate(long updateDate) {
		this.updateDate = updateDate;
	}
}
