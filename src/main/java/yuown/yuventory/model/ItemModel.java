package yuown.yuventory.model;

import java.util.Date;

public class ItemModel extends Model {

	private String name;

	private String itemType;

	private double weight;

	private int supplier;

	private int stockType;

	private int user;

	private int lendTo;

	private Date lendDate;

	private String lendDescription;

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

	public Date getLendDate() {
		return lendDate;
	}

	public void setLendDate(Date lendDate) {
		this.lendDate = lendDate;
	}

	public String getLendDescription() {
		return lendDescription;
	}

	public void setLendDescription(String lendDescription) {
		this.lendDescription = lendDescription;
	}
}
