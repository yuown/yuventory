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

import yuown.yuventory.business.services.SupplierService;
import yuown.yuventory.model.SupplierModel;
import yuown.yuventory.rest.intf.SupplierResource;

@RestController
@RequestMapping(value = "/suppliers", produces = { MediaType.APPLICATION_JSON })
public class SupplierResourceImpl implements SupplierResource {

	@Autowired
	private SupplierService supplierService;

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON })
	@ResponseBody
	public SupplierModel save(@RequestBody SupplierModel model) {
		return supplierService.save(model);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public SupplierModel getById(@PathVariable("id") int id) {
		return supplierService.getById(id);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public Response removeById(@PathVariable("id") int id) {
		SupplierModel supplier = supplierService.getById(id);
		if (null == supplier) {
			return Response.status(Response.Status.NOT_FOUND).entity("Supplier with ID " + id + " Not Found").build();
		} else {
			return Response.status(Response.Status.OK).entity("Supplier with ID " + id + " Deleted Successfully").build();
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<SupplierModel> getAll() {
		return supplierService.getAll();
	}
}
