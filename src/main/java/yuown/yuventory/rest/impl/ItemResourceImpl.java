package yuown.yuventory.rest.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
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

import yuown.yuventory.business.services.ItemService;
import yuown.yuventory.model.ItemModel;
import yuown.yuventory.security.YuownTokenAuthenticationService;

@RestController
@RequestMapping(value = "/items", produces = { MediaType.APPLICATION_JSON })
public class ItemResourceImpl {

	@Autowired
	private ItemService itemService;

	@Autowired
	private YuownTokenAuthenticationService yuownTokenAuthenticationService;

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON })
	@ResponseBody
	public ItemModel save(@RequestBody ItemModel model, @Context HttpServletRequest httpRequest) {
		model.setUser(yuownTokenAuthenticationService.getUser(httpRequest));
		return itemService.save(model);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public ItemModel getById(@PathVariable("id") int id) {
		return itemService.getById(id);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<String> removeById(@PathVariable("id") int id) {
		ItemModel item = itemService.getById(id);
		HttpHeaders headers = new HttpHeaders();
		if (null == item) {
			headers.add("errorMessage", "Item with ID " + id + " Not Found");
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		} else {
			try {
				itemService.removeById(id);
				headers.add("errorMessage", "Item with ID " + id + " Deleted Successfully");
				return new ResponseEntity<String>(headers, HttpStatus.OK);
			} catch (Exception e) {
				headers.add("errorMessage", "Item with ID " + id + " cannot be Deleted");
				return new ResponseEntity<String>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<ItemModel> getAll(@QueryParam("page") Integer page, @QueryParam("size") Integer size) {
		return itemService.getAll(page, size);
	}
}
