package yuown.yuventory.rest.intf;

import java.util.List;

import javax.ws.rs.core.Response;

import yuown.yuventory.model.ItemModel;

public interface ItemResource {

	public ItemModel save(ItemModel model);

	public ItemModel getById(int id);

	public List<ItemModel> getAll();

	public Response removeById(int id);
}
