package yuown.yuventory.rest.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import yuown.yuventory.business.services.ItemService;
import yuown.yuventory.model.ItemModel;
import yuown.yuventory.model.ReportRequestModel;

@RestController
@RequestMapping(value = "/reports")
public class ReportsResourceImpl {

	@Autowired
	private ItemService itemService;

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE }, value = "/generate")
	@ResponseBody
	public ResponseEntity<List<ItemModel>> sell(@RequestBody ReportRequestModel model) {
		HttpHeaders headers = new HttpHeaders();
		HttpStatus responseStatus = HttpStatus.OK;

		List<ItemModel> items = itemService.generateReport(model);

		return new ResponseEntity<List<ItemModel>>(items, headers, responseStatus);
	}
}
