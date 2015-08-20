package yuown.yuventory.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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
		UserModel user = transformer().transformFromSecurityUser(userFromDB);
		User dbUser = userRepositoryService.findByUsername(name);
		user.setId(dbUser.getId());
		user.setFullName(dbUser.getFullName());
		return user;
	}
	
	public UserModel createUser(UserModel fromClient) {
		fromClient.getAuthorities().removeAll(fromClient.getAuthorities());
		UserDetails user = transformer().transformToSecurityUser(fromClient);
		jdbcUserDetailsManager.createUser(user);
		return fromClient;
	}
}
