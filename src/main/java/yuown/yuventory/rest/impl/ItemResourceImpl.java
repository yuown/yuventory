package yuown.yuventory.rest.impl;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import yuown.yuventory.business.services.ItemService;
import yuown.yuventory.model.ItemModel;
import yuown.yuventory.rest.intf.ItemResource;

@RestController
@RequestMapping(value = "/items", produces = { MediaType.APPLICATION_JSON })
public class ItemResourceImpl implements ItemResource {

	@Autowired
	private ItemService itemService;

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON })
	@ResponseBody
	public ItemModel save(@RequestBody ItemModel model) {
		return itemService.save(model);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public ItemModel getById(@PathVariable("id") int id) {
		return itemService.getById(id);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public Response removeById(@PathVariable("id") int id) {
		ItemModel stockType = itemService.getById(id);
		if (null == stockType) {
			return Response.status(Response.Status.NOT_FOUND).entity("Item with ID " + id + " Not Found").build();
		} else {
			return Response.status(Response.Status.OK).entity("Item with ID " + id + " Deleted Successfully").build();
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<ItemModel> getAll() {
		return itemService.getAll();
	}
}
