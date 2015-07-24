package yuown.yuventory.security;

import org.springframework.security.core.GrantedAuthority;

public class YuownGrantedAuthority implements GrantedAuthority {

    private static final long serialVersionUID = 2811224602083076264L;

    private String authority;

    public YuownGrantedAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

}
