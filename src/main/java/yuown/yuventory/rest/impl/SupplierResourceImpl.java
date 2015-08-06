package yuown.yuventory.rest.impl;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import yuown.yuventory.business.services.SupplierService;
import yuown.yuventory.model.SupplierModel;

@RestController
@RequestMapping(value = "/suppliers", produces = { MediaType.APPLICATION_JSON })
public class SupplierResourceImpl {

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
	public ResponseEntity<String> removeById(@PathVariable("id") int id) throws Exception {
		SupplierModel supplier = supplierService.getById(id);
		HttpHeaders headers = new HttpHeaders();
		if (null == supplier) {
			headers.add("errorMessage", "Supplier with ID " + id + " Not Found");
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		} else {
			try {
				supplierService.removeById(id);
				headers.add("errorMessage", "Supplier with ID " + id + " Deleted Successfully");
				return new ResponseEntity<String>(headers, HttpStatus.OK);
			} catch (Exception e) {
				headers.add("errorMessage", "Supplier with ID " + id + " cannot be Deleted");
				return new ResponseEntity<String>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<SupplierModel> getAll() {
		return supplierService.getAll();
	}
}
