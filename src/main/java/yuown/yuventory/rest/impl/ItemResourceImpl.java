package yuown.yuventory.rest.impl;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import yuown.yuventory.business.services.ItemService;
import yuown.yuventory.business.services.SupplierService;
import yuown.yuventory.entity.Item;
import yuown.yuventory.model.ItemModel;
import yuown.yuventory.model.SupplierModel;
import yuown.yuventory.security.YuownTokenAuthenticationService;

@RestController
@RequestMapping(value = "/items", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ItemResourceImpl {

	@Autowired
	private ItemService itemService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private YuownTokenAuthenticationService yuownTokenAuthenticationService;

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseEntity<ItemModel> save(@RequestBody ItemModel model, @Context HttpServletRequest httpRequest) {
		model.setUser(yuownTokenAuthenticationService.getUser(httpRequest));

		HttpHeaders headers = new HttpHeaders();
		HttpStatus responseStatus = HttpStatus.BAD_REQUEST;

		if (model.getCategory() == 0) {
			headers.add("errormessage", "Invalid Category, please select valid Category or contact Admin!");
		} else if (StringUtils.isBlank(model.getItemType())) {
			headers.add("errormessage", "Invalid Item Type, please select valid Item Type or contact Admin!");
		} else if (model.getStockType() == 0) {
			headers.add("errormessage", "Invalid Stock Type, please select valid Stock Type or contact Admin!");
		} else if (model.getSupplier() == 0) {
			headers.add("errormessage", "Invalid Supplier, please select valid Supplier or contact Admin!");
		} else if (model.getLendTo() > 0) {
			headers.add("errormessage", "Invalid Lending while Purchasing, please contact Admin!");
		} else {
			model = itemService.save(model);
			responseStatus = HttpStatus.OK;
		}

		return new ResponseEntity<ItemModel>(model, headers, responseStatus);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "/sell")
	@ResponseBody
	public ResponseEntity<ItemModel> sell(@RequestBody ItemModel model, @Context HttpServletRequest httpRequest) {
		model.setUser(yuownTokenAuthenticationService.getUser(httpRequest));

		HttpHeaders headers = new HttpHeaders();
		HttpStatus responseStatus = HttpStatus.OK;

		ItemModel itemFromDB = itemService.getById(model.getId());

		if (null != itemFromDB) {
			if (itemFromDB.getSold() == true) {
				headers.add("errorMessage", "Item Already Sold, cannot sell Again!");
				responseStatus = HttpStatus.BAD_REQUEST;
			} else {
				if (itemFromDB.getLendTo() > 0) {
					SupplierModel supplier = supplierService.getById(itemFromDB.getLendTo());
					headers.add("errorMessage",
							"Item Lent to '" + supplier.getName() + "', cannot sell until return back!");
					responseStatus = HttpStatus.BAD_REQUEST;
				} else {
					if (model.getLendTo() == 0) {
						headers.add("errorMessage", "Invalid Sell Selection!");
						responseStatus = HttpStatus.BAD_REQUEST;
					} else {
						model = itemService.sell(model);
					}
				}
			}
		} else {
			headers.add("errorMessage", "Item Not found!");
		}

		return new ResponseEntity<ItemModel>(model, headers, responseStatus);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "/lend")
	@ResponseBody
	public ResponseEntity<ItemModel> lend(@RequestBody ItemModel model, @Context HttpServletRequest httpRequest) {
		model.setUser(yuownTokenAuthenticationService.getUser(httpRequest));

		HttpHeaders headers = new HttpHeaders();
		HttpStatus responseStatus = HttpStatus.OK;

		ItemModel itemFromDB = itemService.getById(model.getId());

		if (null != itemFromDB) {
			if (itemFromDB.getSold() == true) {
				headers.add("errorMessage", "Item Already Sold, cannot lend to anyone!");
				responseStatus = HttpStatus.BAD_REQUEST;
			} else {
				if (itemFromDB.getLendTo() > 0) {
					SupplierModel supplier = supplierService.getById(itemFromDB.getLendTo());
					headers.add("errorMessage",
							"Item Lent to '" + supplier.getName() + "', cannot lend until return back!");
					responseStatus = HttpStatus.BAD_REQUEST;
				} else {
					if (model.getLendTo() == 0) {
						headers.add("errorMessage", "Invalid Lending Selection!");
						responseStatus = HttpStatus.BAD_REQUEST;
					} else {
						model = itemService.lend(model);
					}
				}
			}
		} else {
			headers.add("errorMessage", "Item Not found!");
		}

		return new ResponseEntity<ItemModel>(model, headers, responseStatus);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "/getBack")
	@ResponseBody
	public ResponseEntity<ItemModel> getBack(@RequestBody ItemModel model, @Context HttpServletRequest httpRequest) {
		model.setUser(yuownTokenAuthenticationService.getUser(httpRequest));

		HttpHeaders headers = new HttpHeaders();
		HttpStatus responseStatus = HttpStatus.OK;

		ItemModel itemFromDB = itemService.getById(model.getId());

		if (null != itemFromDB) {
			if (itemFromDB.getSold() == true) {
				headers.add("errorMessage", "Item Sold, cannot get back!");
				responseStatus = HttpStatus.BAD_REQUEST;
			} else {
				if (itemFromDB.getLendTo() == 0) {
					headers.add("errorMessage", "Item Not Lent to anyone, don't have to back!");
					responseStatus = HttpStatus.BAD_REQUEST;
				} else {
					model = itemService.getBack(model);
				}
			}
		} else {
			headers.add("errorMessage", "Item Not found!");
		}

		return new ResponseEntity<ItemModel>(model, headers, responseStatus);
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
				return new ResponseEntity<String>(headers, HttpStatus.OK);
			} catch (Exception e) {
				headers.add("errorMessage", "Item with ID " + id + " cannot be Deleted");
				return new ResponseEntity<String>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ItemModel>> getAll(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
		HttpHeaders headers = new HttpHeaders();
		PageImpl<Item> pagedItems = null;
		List<ItemModel> items = null;
		if(StringUtils.isNotBlank(name)) {
			pagedItems = itemService.search(name, page, size);
		} else {
			pagedItems = itemService.getAll(page, size);
		}
		items = itemService.transformer().transformTo(pagedItems.getContent());
		
		headers.add("pages", pagedItems.getTotalPages() + StringUtils.EMPTY);
		headers.add("totalItems", pagedItems.getTotalElements() + StringUtils.EMPTY);

		return new ResponseEntity<List<ItemModel>>(items, headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/pageSize")
	public void setPageSize(@RequestBody Integer size) {
		itemService.setPageSize(size);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/pageSize")
	@ResponseBody
	public Integer getPageSize() {
		return itemService.getPageSize();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/notifySize")
	public void setNotifySize(@RequestBody Integer size) {
		itemService.setNotifySize(size);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/notifySize")
	@ResponseBody
	public Integer getNotifySize() {
		return itemService.getNotifySize();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/names")
	@ResponseBody
	public Set<String> getAllItemNames() {
		return itemService.findAllItemNames();
	}
}
