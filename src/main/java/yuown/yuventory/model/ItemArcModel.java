package yuown.yuventory.model;

public class ItemArcModel extends Model {
	
	private Integer itemId;

	private String name;

	private long lendDate;

	private long createDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getLendDate() {
		return lendDate;
	}

	public void setLendDate(long lendDate) {
		this.lendDate = lendDate;
	}

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
}