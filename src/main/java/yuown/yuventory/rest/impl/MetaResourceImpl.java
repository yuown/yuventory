package yuown.yuventory.rest.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import yuown.yuventory.holders.ItemType;
import yuown.yuventory.holders.StockTypeMethod;

@RestController
@RequestMapping(value = "/meta", produces = { MediaType.APPLICATION_JSON_VALUE })
public class MetaResourceImpl {

	@RequestMapping(value = "/itemTypes", method = RequestMethod.GET)
	@ResponseBody
	public Set<String> itemTypes() {
		return ItemType.all();
	}

	@RequestMapping(value = "/stockTypeMethods", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Boolean> stockTypeMethods() {
		Map<String, Boolean> stockTypeMetnods = new HashMap<String, Boolean>();
		StockTypeMethod[] array = StockTypeMethod.values();
		for (int i = 0; i < array.length; i++) {
			stockTypeMetnods.put(array[i].getName(), array[i].isDeleteAllowed());
		}
		return stockTypeMetnods;
	}
}
