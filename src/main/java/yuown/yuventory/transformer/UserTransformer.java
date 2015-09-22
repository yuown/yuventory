package yuown.yuventory.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import yuown.yuventory.entity.User;
import yuown.yuventory.model.UserModel;
import yuown.yuventory.security.YuownGrantedAuthority;

@Component
public class UserTransformer extends AbstractDTOTransformer<UserModel, User> {

	private static final String[] FROM_EXCLUDES = new String[] {"authorities"};
	private static final String[] TO_EXCLUDES = new String[] {"authorities"};

	@Override
	public User transformFrom(UserModel source) {
		User dest = null;
		if (source != null) {
			try {
				dest = new User();
				BeanUtils.copyProperties(source, dest, FROM_EXCLUDES);
				dest.setFullName(source.getFullName().toUpperCase());
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
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}

	public UserModel transformFromSecurityUser(org.springframework.security.core.userdetails.User userFromDB) {
		UserModel dest = new UserModel();
		dest.setUsername(userFromDB.getUsername());
		dest.setPassword(userFromDB.getPassword());
		dest.setEnabled(userFromDB.isEnabled());
		dest.setAuthorities(transformAuthorities(userFromDB.getAuthorities()));
		return dest;
	}

	public org.springframework.security.core.userdetails.User transformToSecurityUser(UserModel userFromClient) {
		org.springframework.security.core.userdetails.User dest = new org.springframework.security.core.userdetails.User(
				userFromClient.getUsername(),
				userFromClient.getPassword(),
				userFromClient.getAuthorities());
		return dest;
	}

	private ArrayList<YuownGrantedAuthority> transformAuthorities(Collection<GrantedAuthority> authoritiesFromDB) {
		ArrayList<YuownGrantedAuthority> authorities = new ArrayList<YuownGrantedAuthority>();
		for (GrantedAuthority grantedAuthority : authoritiesFromDB) {
			authorities.add(new YuownGrantedAuthority(grantedAuthority.getAuthority()));
		}
		return authorities;
	}
	
	public ArrayList<YuownGrantedAuthority> transformAdminAuthorities(List<String> adminAuthorities) {
		ArrayList<YuownGrantedAuthority> authorities = new ArrayList<YuownGrantedAuthority>();
		for (String adminAuthority : adminAuthorities) {
			authorities.add(new YuownGrantedAuthority(adminAuthority));
		}
		return authorities;
	}
}
