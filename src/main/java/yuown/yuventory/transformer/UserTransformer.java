package yuown.yuventory.transformer;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

import yuown.yuventory.entity.User;
import yuown.yuventory.model.UserModel;
import yuown.yuventory.security.YuownGrantedAuthority;

@Component
public class UserTransformer extends AbstractDTOTransformer<UserModel, User> {

	@Autowired
	private JdbcUserDetailsManager jdbcUserDetailsManager;

	private static final String[] FROM_EXCLUDES = new String[] {};
	private static final String[] TO_EXCLUDES = new String[] {};

	@Override
	public User transformFrom(UserModel source) {
		User dest = null;
		if (source != null) {
			try {
				dest = new User();
				BeanUtils.copyProperties(source, dest, FROM_EXCLUDES);
				dest.setFullName(dest.getFullName().toUpperCase());
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}

	@Override
	public UserModel transformTo(User source) {
		UserModel dest = null;
		if (source != null) {
			try {
				dest = new UserModel();
				BeanUtils.copyProperties(source, dest, TO_EXCLUDES);
				ArrayList<YuownGrantedAuthority> authorities = new ArrayList<YuownGrantedAuthority>();
				authorities.add(new YuownGrantedAuthority("ROLE_DATAENTRY"));
				authorities.add(new YuownGrantedAuthority("ROLE_VIEW_ITEMS"));
				dest.setAuthorities(authorities);
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}

	public UserModel transformTo(org.springframework.security.core.userdetails.User userFromDB) {
		UserModel dest = new UserModel();
		dest.setUsername(userFromDB.getUsername());
		dest.setPassword(userFromDB.getPassword());
		dest.setEnabled(userFromDB.isEnabled());
		dest.setAuthorities(transformAuthorities(userFromDB.getAuthorities()));
		return dest;
	}

	private ArrayList<YuownGrantedAuthority> transformAuthorities(Collection<GrantedAuthority> authoritiesFromDB) {
		ArrayList<YuownGrantedAuthority> authorities = new ArrayList<YuownGrantedAuthority>();
		for (GrantedAuthority grantedAuthority : authoritiesFromDB) {
			authorities.add(new YuownGrantedAuthority(grantedAuthority.getAuthority()));
		}
		return authorities;
	}
}
