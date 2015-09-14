package yuown.yuventory.model;

public class SupplierStatsModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5271383530174439111L;

	private double gold;

	private double silver;

	private String supplier;

	public double getGold() {
		return gold;
	}

	public double getSilver() {
		return silver;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setGold(double gold) {
		this.gold = gold;
	}

	public void setSilver(double silver) {
		this.silver = silver;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
}
