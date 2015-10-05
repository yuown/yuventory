package yuown.yuventory.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "CUSTOMERS", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
@AttributeOverrides(value = {
        @AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false)),
        @AttributeOverride(name = "name", column = @Column(name = "name")),
        @AttributeOverride(name = "createDate", column = @Column(name = "create_date")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "update_date")),
        @AttributeOverride(name = "mobile", column = @Column(name = "mobile")),
        @AttributeOverride(name = "address", column = @Column(name = "address")),
        @AttributeOverride(name = "paid", column = @Column(name = "paid")),
        @AttributeOverride(name = "pending", column = @Column(name = "pending")),
        @AttributeOverride(name = "total", column = @Column(name = "total"))
})
public class Customer extends BaseEntity<Integer> implements Serializable {

	private static final long serialVersionUID = 4289151143888117381L;

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

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public void setPaid(Double paid) {
		this.paid = paid;
	}

	public void setPending(Double pending) {
		this.pending = pending;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return (new HashCodeBuilder()).append(this.id).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer rhs = (Customer) obj;
		return (new EqualsBuilder()).append(this.id, rhs.id).isEquals();
	}
}
