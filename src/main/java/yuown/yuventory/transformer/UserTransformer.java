package yuown.yuventory.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import yuown.yuventory.entity.User;
import yuown.yuventory.model.UserModel;

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
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}
}
