package yuown.yuventory.model;

import java.util.Date;

public class ReportRequestModel extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5271383530174439111L;

	private Date purchaseStartDate;

	private Date purchaseEndDate;

	private Date sellStartDate;

	private Date sellEndDate;

	private String itemName;

	private String itemType;

	private int category;

	private int stockType;

	private int supplier;

	private int lent;

	public Date getPurchaseStartDate() {
		return purchaseStartDate;
	}

	public void setPurchaseStartDate(Date purchaseStartDate) {
		this.purchaseStartDate = purchaseStartDate;
	}

	public Date getPurchaseEndDate() {
		return purchaseEndDate;
	}

	public void setPurchaseEndDate(Date purchaseEndDate) {
		this.purchaseEndDate = purchaseEndDate;
	}

	public Date getSellStartDate() {
		return sellStartDate;
	}

	public void setSellStartDate(Date sellStartDate) {
		this.sellStartDate = sellStartDate;
	}

	public Date getSellEndDate() {
		return sellEndDate;
	}

	public void setSellEndDate(Date sellEndDate) {
		this.sellEndDate = sellEndDate;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getStockType() {
		return stockType;
	}

	public void setStockType(int stockType) {
		this.stockType = stockType;
	}

	public int getSupplier() {
		return supplier;
	}

	public void setSupplier(int supplier) {
		this.supplier = supplier;
	}

	public int getLent() {
		return lent;
	}

	public void setLent(int lent) {
		this.lent = lent;
	}
}
