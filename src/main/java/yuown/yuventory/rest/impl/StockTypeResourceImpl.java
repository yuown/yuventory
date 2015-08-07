package yuown.yuventory.rest.impl;

import java.util.List;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
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
	public ResponseEntity<String> removeById(@PathVariable int id) {
		StockTypeModel stockType = stockTypeService.getById(id);
		HttpHeaders headers = new HttpHeaders();
		if (null == stockType) {
			headers.add("errorMessage", "StockType with ID " + id + " Not Found");
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		} else {
			try {
				stockTypeService.removeById(id);
				headers.add("errorMessage", "StockType with ID " + id + " Deleted Successfully");
				return new ResponseEntity<String>(headers, HttpStatus.OK);
			} catch (Exception e) {
				headers.add("errorMessage", "StockType with ID " + id + " cannot be Deleted");
				return new ResponseEntity<String>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<StockTypeModel> getAll(@QueryParam("method") String method, @QueryParam("remove") String remove) {
		if (StringUtils.isBlank(method)) {
			return stockTypeService.getAll();
		} else {
			if (StringUtils.isBlank(remove)) {
				return stockTypeService.getAllByMethod(method);
			} else {
				return stockTypeService.getAllByMethodAndRemove(method, Boolean.parseBoolean(remove));
			}
		}
	}
}
