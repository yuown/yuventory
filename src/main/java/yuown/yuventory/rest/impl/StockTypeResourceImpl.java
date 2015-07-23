package yuown.yuventory.rest.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.business.services.StockTypeService;
import yuown.yuventory.model.StockTypeModel;
import yuown.yuventory.rest.intf.StockTypeResource;

@Service
public class StockTypeResourceImpl implements StockTypeResource {

	@Autowired
	private StockTypeService stockTypeService;

	public StockTypeModel save(StockTypeModel model) {
		return stockTypeService.save(model);
	}

	public StockTypeModel getById(int id) {
		return stockTypeService.getById(id);
	}

	public Response removeById(int id) {
		StockTypeModel stockType = stockTypeService.getById(id);
		if (null == stockType) {
			return Response.status(Response.Status.NOT_FOUND).entity("StockType with ID " + id + " Not Found").build();
		} else {
			return Response.status(Response.Status.OK).entity("StockType with ID " + id + " Deleted Successfully").build();
		}
	}

	public List<StockTypeModel> getAll() {
		return stockTypeService.getAll();
	}
}
