package yuown.yuventory.model;

public class CustomerModel extends Model {

	private String name;

	private String mobile;

	private String address;

	private Double paid;

	private Double pending;

	private Double total;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public String getAddress() {
		return address;
	}

	public Double getPaid() {
		return paid;
	}

	public Double getPending() {
		return pending;
	}

	public Double getTotal() {
		return total;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPaid(Double paid) {
		this.paid = paid;
	}

	public void setPending(Double pending) {
		this.pending = pending;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
