package yuown.yuventory.rest.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.business.services.ItemService;
import yuown.yuventory.model.ItemModel;
import yuown.yuventory.rest.intf.ItemResource;

@Service
public class ItemResourceImpl implements ItemResource {

	@Autowired
	private ItemService itemService;

	public ItemModel save(ItemModel model) {
		return itemService.save(model);
	}

	public ItemModel getById(int id) {
		return itemService.getById(id);
	}

	public Response removeById(int id) {
		ItemModel stockType = itemService.getById(id);
		if (null == stockType) {
			return Response.status(Response.Status.NOT_FOUND).entity("Item with ID " + id + " Not Found").build();
		} else {
			return Response.status(Response.Status.OK).entity("Item with ID " + id + " Deleted Successfully").build();
		}
	}

	public List<ItemModel> getAll() {
		return itemService.getAll();
	}
}
