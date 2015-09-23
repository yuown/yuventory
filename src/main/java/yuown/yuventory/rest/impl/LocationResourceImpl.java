package yuown.yuventory.rest.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import yuown.yuventory.business.services.LocationService;
import yuown.yuventory.model.LocationModel;

@RestController
@RequestMapping(value = "/locations", produces = { MediaType.APPLICATION_JSON_VALUE })
public class LocationResourceImpl {

	@Autowired
	private LocationService locationService;

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public LocationModel save(@RequestBody LocationModel model) {
		return locationService.save(model);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public LocationModel getById(@PathVariable("id") int id) {
		return locationService.getById(id);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<String> removeById(@PathVariable("id") int id) throws Exception {
		LocationModel location = locationService.getById(id);
		HttpHeaders headers = new HttpHeaders();
		if (null == location) {
			headers.add("errorMessage", "Location with ID " + id + " Not Found");
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		} else {
			try {
				locationService.removeById(id);
				headers.add("errorMessage", "Location with ID " + id + " Deleted Successfully");
				return new ResponseEntity<String>(headers, HttpStatus.OK);
			} catch (Exception e) {
				headers.add("errorMessage", "Location with ID " + id + " cannot be Deleted");
				return new ResponseEntity<String>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<LocationModel> getAll() {
		return locationService.getAll();
	}
}
