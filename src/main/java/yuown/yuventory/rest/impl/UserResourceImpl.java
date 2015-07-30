package yuown.yuventory.rest.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.business.services.UserService;
import yuown.yuventory.model.UserModel;
import yuown.yuventory.rest.intf.UserResource;

@Service
public class UserResourceImpl implements UserResource {

	@Autowired
	private UserService userService;

	public UserModel save(UserModel model) {
		return userService.save(model);
	}

	public UserModel getById(int id) {
		return userService.getById(id);
	}

	public Response removeById(int id) {
		UserModel user = userService.getById(id);
		if (null == user) {
			return Response.status(Response.Status.NOT_FOUND).entity("User with ID " + id + " Not Found").build();
		} else {
			return Response.status(Response.Status.OK).entity("User with ID " + id + " Deleted Successfully").build();
		}
	}

	public List<UserModel> getAll() {
		return userService.getAll();
	}

	public UserModel login(UserModel user) {
		user.setPassword(null);
		return user;
	}
}
