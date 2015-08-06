package yuown.yuventory.rest.intf;

import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.PathVariable;

import yuown.yuventory.model.UserModel;

public interface UserResource {

	public UserModel save(UserModel model);

	public UserModel getById(@PathVariable int id);

	public List<UserModel> getAll();

	public Response removeById(@PathVariable int id);

	public UserModel login(UserModel user);

}
