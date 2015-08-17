package yuown.yuventory.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.User;
import yuown.yuventory.jpa.services.UserRepositoryService;
import yuown.yuventory.model.UserModel;
import yuown.yuventory.transformer.UserTransformer;

@Service
public class UserService extends AbstractServiceImpl<Integer, UserModel, User, UserRepositoryService, UserTransformer> {

	@Autowired
	private UserRepositoryService userRepositoryService;

	@Autowired
	private UserTransformer userTransformer;

	@Autowired
	private JdbcUserDetailsManager jdbcUserDetailsManager;

	@Override
	protected UserRepositoryService repoService() {
		return userRepositoryService;
	}

	@Override
	protected UserTransformer transformer() {
		return userTransformer;
	}

	public UserModel getByUsername(String name) {
		org.springframework.security.core.userdetails.User userFromDB = (org.springframework.security.core.userdetails.User) jdbcUserDetailsManager.loadUserByUsername(name);
		UserModel user = transformer().transformTo(userFromDB);
		user.setId(userRepositoryService.findByUsername(name).getId());
		return user;
	}
}
