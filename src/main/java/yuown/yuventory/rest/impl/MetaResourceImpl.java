package yuown.yuventory.rest.impl;

import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import yuown.yuventory.holders.ItemType;
import yuown.yuventory.holders.StockTypeMethod;

@RestController
@RequestMapping(value = "/meta", produces = { MediaType.APPLICATION_JSON })
public class MetaResourceImpl {

	@RequestMapping(value = "/itemTypes", method = RequestMethod.GET)
	@ResponseBody
	public Set<String> itemTypes() {
		return ItemType.all();
	}

	@RequestMapping(value = "/stockTypeMethods", method = RequestMethod.GET)
	@ResponseBody
	public Set<String> stockTypeMethodss() {
		return StockTypeMethod.all();
	}
}
