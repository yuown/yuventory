package yuown.yuventory.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "STOCK_TYPE", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }) )
@AttributeOverrides(value = {
		@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false) ),
		@AttributeOverride(name = "name", column = @Column(name = "name") ),
		@AttributeOverride(name = "method", column = @Column(name = "method") ),
		@AttributeOverride(name = "remove", column = @Column(name = "remove") )
})
public class StockType extends BaseEntity<Integer>implements Serializable {

	private static final long serialVersionUID = 4289151143888117381L;

	private String name;

	private String method;

	private Boolean remove;

	private List<Item> items;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Boolean getRemove() {
		return remove;
	}

	public void setRemove(Boolean remove) {
		this.remove = remove;
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

	@OneToMany(mappedBy = "stockType", fetch = FetchType.LAZY)
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
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
		StockType rhs = (StockType) obj;
		return (new EqualsBuilder()).append(this.id, rhs.id).isEquals();
	}
}
