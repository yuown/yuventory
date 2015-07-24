package yuown.yuventory.model;

public class UserModel extends Model {

	/**
     * 
     */
	private static final long serialVersionUID = -3340859196299627969L;

	private String userName;

	private String password;

	private boolean enabled;

	private String fullName;

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String getFullName() {
		return fullName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
