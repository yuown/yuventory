package yuown.yuventory.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

import yuown.yuventory.security.YuownGrantedAuthority;

public class UserModel extends Model {

	private static final long serialVersionUID = 2733430016846177569L;

	private String username;

	private String password;

	private boolean enabled;

	private String fullName;

	private long expires;

	private ArrayList<YuownGrantedAuthority> authorities;

	public String getPassword() {
		return password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String getFullName() {
		return fullName;
	}

	public void setUsername(String userName) {
		this.username = userName;
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

	public String getUsername() {
		return this.username;
	}

	@JsonIgnore
	public boolean isAccountNonExpired() {
		return this.enabled;
	}

	@JsonIgnore
	public boolean isAccountNonLocked() {
		return this.enabled;
	}

	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return this.enabled;
	}

	public void setExpires(long l) {
		this.expires = l;
	}

	public long getExpires() {
		return expires;
	}

	public void setAuthorities(ArrayList<YuownGrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public ArrayList<YuownGrantedAuthority> getAuthorities() {
		return authorities;
	}
}
