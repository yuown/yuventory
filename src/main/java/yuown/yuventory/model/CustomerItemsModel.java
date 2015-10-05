package yuown.yuventory.model;

public class CustomerItemsModel extends Model {

	private Integer customerId;

	private Integer itemId;

	public Integer getCustomerId() {
		return customerId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
}
