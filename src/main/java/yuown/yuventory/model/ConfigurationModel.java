package yuown.yuventory.model;

public class ConfigurationModel extends Model {

	/**
     * 
     */
	private static final long serialVersionUID = -3340859196299627969L;

	private String name;

	private Integer value;
	
	private String strValue;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

}
