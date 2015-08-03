package yuown.yuventory.rest.intf;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import yuown.yuventory.model.ItemModel;

@RestController
@RequestMapping(value = "/items", produces = { MediaType.APPLICATION_JSON })
public interface ItemResource {

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON })
	public ItemModel save(ItemModel model);

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ItemModel getById(@PathVariable int id);

	@RequestMapping(method = RequestMethod.GET)
	public List<ItemModel> getAll();

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public Response removeById(@PathVariable int id);

}
