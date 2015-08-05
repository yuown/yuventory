package yuown.yuventory.rest.impl;

import java.util.List;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import yuown.yuventory.business.services.StockTypeService;
import yuown.yuventory.model.StockTypeModel;

@RestController
@RequestMapping(value = "/stockTypes", produces = { MediaType.APPLICATION_JSON })
public class StockTypeResourceImpl {

	@Autowired
	private StockTypeService stockTypeService;

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON })
	@ResponseBody
	public StockTypeModel save(@RequestBody StockTypeModel model) {
		return stockTypeService.save(model);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public StockTypeModel getById(@PathVariable int id) {
		return stockTypeService.getById(id);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public Response removeById(@PathVariable int id) {
		StockTypeModel stockType = stockTypeService.getById(id);
		if (null == stockType) {
			return Response.status(Response.Status.NOT_FOUND).entity("StockType with ID " + id + " Not Found").build();
		} else {
			return Response.status(Response.Status.OK).entity("StockType with ID " + id + " Deleted Successfully").build();
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<StockTypeModel> getAll(@QueryParam("method") String method) {
		if (StringUtils.isBlank(method)) {
			return stockTypeService.getAll();
		} else {
			return stockTypeService.getAllByMethod(method);
		}
	}
}
