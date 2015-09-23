package yuown.yuventory.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "ITEMS", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
@AttributeOverrides(value = {
        @AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false)),
        @AttributeOverride(name = "name", column = @Column(name = "name")),
        @AttributeOverride(name = "createDate", column = @Column(name = "create_date")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "update_date")),
        @AttributeOverride(name = "itemType", column = @Column(name = "type")),
        @AttributeOverride(name = "weight", column = @Column(name = "weight")),
        @AttributeOverride(name = "category", column = @Column(name = "category")),
        @AttributeOverride(name = "supplier", column = @Column(name = "supplier")),
        @AttributeOverride(name = "stockType", column = @Column(name = "stock_type")),
        @AttributeOverride(name = "user", column = @Column(name = "user_entered")),
        @AttributeOverride(name = "lendTo", column = @Column(name = "lend_to")),
        @AttributeOverride(name = "lendDate", column = @Column(name = "lend_date")),
        @AttributeOverride(name = "lendDescription", column = @Column(name = "lend_description")),
        @AttributeOverride(name = "sold", column = @Column(name = "sold")),
        @AttributeOverride(name = "location", column = @Column(name = "location"))
})
public class Item extends BaseEntity<Integer> implements Serializable {

	private static final long serialVersionUID = 4289151143888117381L;

	private String name;

	private String itemType;

	private double weight;

	private Category category;

	private long createDate;

	private long updateDate;

	private Supplier supplier;

	private StockType stockType;

	private User user;

	private Supplier lendTo;

	private long lendDate;

	private String lendDescription;

	private Boolean sold;
	
	private Location location;

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

	@ManyToOne
	@JoinColumn(name = "category", nullable = false)
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@ManyToOne
	@JoinColumn(name = "supplier", nullable = false)
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@ManyToOne
	@JoinColumn(name = "lend_to", nullable = true)
	public Supplier getLendTo() {
		return lendTo;
	}

	public void setLendTo(Supplier lendTo) {
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

	@ManyToOne
	@JoinColumn(name = "location", nullable = true)
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Boolean getSold() {
		return sold == null ? false : sold;
	}

	public void setSold(Boolean sold) {
		this.sold = sold == null ? false : sold;
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

	@ManyToOne
	@JoinColumn(name = "stock_type", nullable = false)
	public StockType getStockType() {
		return stockType;
	}

	public void setStockType(StockType stockType) {
		this.stockType = stockType;
	}

	@ManyToOne
	@JoinColumn(name = "user_entered", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		Item rhs = (Item) obj;
		return (new EqualsBuilder()).append(this.id, rhs.id).isEquals();
	}
}
