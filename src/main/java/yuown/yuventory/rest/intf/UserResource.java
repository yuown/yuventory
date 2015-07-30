package yuown.yuventory.rest.intf;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import yuown.yuventory.model.UserModel;

@RestController
@RequestMapping(value = "/users", produces = { MediaType.APPLICATION_JSON })
public interface UserResource {

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON })
	public UserModel save(UserModel model);

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public UserModel getById(@PathVariable int id);

	@RequestMapping(method = RequestMethod.GET)
	public List<UserModel> getAll();

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public Response removeById(@PathVariable int id);

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public UserModel login(UserModel user);

}
