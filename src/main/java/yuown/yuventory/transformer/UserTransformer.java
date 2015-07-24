package yuown.yuventory.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import jersey.repackaged.com.google.common.collect.Lists;
import yuown.yuventory.entity.User;
import yuown.yuventory.model.UserModel;
import yuown.yuventory.security.YuownGrantedAuthority;

@Component
public class UserTransformer extends AbstractDTOTransformer<UserModel, User> {

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
				authorities.add(new YuownGrantedAuthority("ROLE_USER"));
				dest.setAuthorities(authorities);
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}
}
