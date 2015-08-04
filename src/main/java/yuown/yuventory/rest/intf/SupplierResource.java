package yuown.yuventory.rest.intf;

import java.util.List;

import javax.ws.rs.core.Response;

import yuown.yuventory.model.SupplierModel;

public interface SupplierResource {

	public SupplierModel save(SupplierModel model);

	public SupplierModel getById(int id);

	public List<SupplierModel> getAll();

	public Response removeById(int id);
}
