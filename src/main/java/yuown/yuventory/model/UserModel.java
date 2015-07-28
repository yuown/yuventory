package yuown.yuventory.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import yuown.yuventory.security.YuownGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class UserModel extends Model implements UserDetails {

    private static final long serialVersionUID = 3212743390766576226L;

    private String username;

    private String password;

    private boolean enabled;

    private String fullName;

    private ArrayList<YuownGrantedAuthority> authorities;

	private long expires;

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

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isAccountNonExpired() {
        return this.enabled;
    }

    public boolean isAccountNonLocked() {
        return this.enabled;
    }

    public boolean isCredentialsNonExpired() {
        return this.enabled;
    }

    public void setAuthorities(ArrayList<YuownGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

	public void setExpires(long l) {
		this.expires = l;
	}
	
	public long getExpires() {
		return expires;
	}
}
