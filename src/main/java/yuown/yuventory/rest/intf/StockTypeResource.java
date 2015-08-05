package yuown.yuventory.rest.intf;

import java.util.List;

import javax.ws.rs.core.Response;

import yuown.yuventory.model.StockTypeModel;

public interface StockTypeResource {

	public StockTypeModel save(StockTypeModel model);

	public StockTypeModel getById(int id);

	public List<StockTypeModel> getAll(String method);

	public Response removeById(int id);

}
