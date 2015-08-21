package yuown.yuventory.business.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.User;
import yuown.yuventory.jpa.services.UserRepositoryService;
import yuown.yuventory.model.UserModel;
import yuown.yuventory.security.YuownGrantedAuthority;
import yuown.yuventory.transformer.UserTransformer;

@Service
public class UserService extends AbstractServiceImpl<Integer, UserModel, User, UserRepositoryService, UserTransformer> {

	@Value("#{'${SUPER_USERS}'.split(',')}")
	private List<String> SUPER_USERS;

	@Value("#{'${yuventory.valid.roles}'.split(',')}")
	private List<String> ALL_ROLES;

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
		org.springframework.security.core.userdetails.User userFromDB = (org.springframework.security.core.userdetails.User) jdbcUserDetailsManager
				.loadUserByUsername(name);
		UserModel user = transformer().transformFromSecurityUser(userFromDB);
		User dbUser = userRepositoryService.findByUsername(name);
		user.setId(dbUser.getId());
		user.setFullName(dbUser.getFullName());
		return user;
	}

	public UserModel findByUsername(String username) {
		return transformer().transformTo(userRepositoryService.findByUsername(username));
	}

	public void createUser(UserModel fromClient) throws Exception {
		fromClient.getAuthorities().removeAll(fromClient.getAuthorities());
		UserDetails user = transformer().transformToSecurityUser(fromClient);
		jdbcUserDetailsManager.createUser(user);
	}

	public void removeUser(UserModel fromClient) {
		if (null != fromClient) {
			jdbcUserDetailsManager.deleteUser(fromClient.getUsername());
		}
	}

	@Override
	public List<UserModel> getAll() {
		List<UserModel> allUsers = super.getAll();
		for (Iterator<UserModel> iterator = allUsers.iterator(); iterator.hasNext();) {
			UserModel userModel = iterator.next();
			if (SUPER_USERS.contains(userModel.getUsername())) {
				iterator.remove();
			}
		}
		return allUsers;
	}

	public void enable(UserModel user) {
		if (null != user) {
			UserModel fromDB = findByUsername(user.getUsername());
			if (null != fromDB && user.isEnabled() != fromDB.isEnabled()) {
				fromDB.setEnabled(user.isEnabled());
				userRepositoryService.save(transformer().transformFrom(fromDB));
			}
		}
	}

	public List<String> getAllAuthorities() {
		return ALL_ROLES;
	}

	public List<String> getGroups() {
		return jdbcUserDetailsManager.findAllGroups();
	}

	public void createGroup(String groupName) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		jdbcUserDetailsManager.createGroup(groupName, authorities);
	}

	public void deleteGroup(String groupName) {
		jdbcUserDetailsManager.deleteGroup(groupName);
	}

	public List<String> findGroupAuthorities(String groupName) {
		List<String> authorities = new ArrayList<String>();
		List<GrantedAuthority> auths = jdbcUserDetailsManager.findGroupAuthorities(groupName);
		for (GrantedAuthority grantedAuthority : auths) {
			authorities.add(grantedAuthority.getAuthority());
		}
		return authorities;
	}

	public void addGroupAuthority(String groupName, List<String> authoritiesFromClient) {
		for (String authority : authoritiesFromClient) {
			jdbcUserDetailsManager.addGroupAuthority(groupName, new YuownGrantedAuthority(authority));
		}
	}

	public void removeGroupAuthority(String groupName, List<String> authorities) {
		for (String authority : authorities) {
			jdbcUserDetailsManager.removeGroupAuthority(groupName, new YuownGrantedAuthority(authority));
		}
	}

	public void addUserToGroup(String username, String groupName) {
		UserModel dbUser = findByUsername(username);
		if (dbUser != null) {
			jdbcUserDetailsManager.addUserToGroup(username, groupName);
		}
	}

	public void removeUserFromGroup(String username, String groupName) {
		UserModel dbUser = findByUsername(username);
		if (dbUser != null) {
			jdbcUserDetailsManager.removeUserFromGroup(username, groupName);
		}
	}
}
