package yuown.yuventory.rest.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import yuown.yuventory.business.services.ConfigurationService;
import yuown.yuventory.model.ConfigurationModel;

@RestController
@RequestMapping(value = "/settings", produces = { MediaType.APPLICATION_JSON_VALUE })
public class SettingsResourceImpl {

	@Autowired
	private ConfigurationService configurationService;

	private static final String PAGE_WIDTH = "page_width";

	private static final String PAGE_HEIGHT = "page_height";

	private static final String MARGIN_TOP = "margin_top";

	private static final String MARGIN_RIGHT = "margin_right";

	private static final String MARGIN_BOTTOM = "margin_bottom";

	private static final String MARGIN_LEFT = "margin_left";
	
	private static final String GOLD_PRICE = "goldPrice";

	private static final String GOLD_WASTAGE = "goldWastagePerc";

	private static final String GOLD_MAKING_CHARGES = "goldMakingChgs";

	private static final String SILVER_PRICE = "silverPrice";

	private static final String SILVER_WASTAGE = "silverWastagePerc";

	private static final String SILVER_MAKING_CHARGES = "silverMakingChgs";

	@RequestMapping(value = "/barPage", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Double> readPageSettings() {
		Map<String, Double> pageSettings = new HashMap<String, Double>();
		pageSettings.put(PAGE_WIDTH, getConfigValue(PAGE_WIDTH));
		pageSettings.put(PAGE_HEIGHT, getConfigValue(PAGE_HEIGHT));
		pageSettings.put(MARGIN_TOP, getConfigValue(MARGIN_TOP));
		pageSettings.put(MARGIN_RIGHT, getConfigValue(MARGIN_RIGHT));
		pageSettings.put(MARGIN_BOTTOM, getConfigValue(MARGIN_BOTTOM));
		pageSettings.put(MARGIN_LEFT, getConfigValue(MARGIN_LEFT));

		return pageSettings;
	}

	@RequestMapping(value = "/barPage", method = RequestMethod.POST)
	@ResponseBody
	public void writePageSettings(@RequestBody Map<String, Double> pageSettings) {
		try {
			saveConfigValue(PAGE_WIDTH, pageSettings.get(PAGE_WIDTH));
			saveConfigValue(PAGE_HEIGHT, pageSettings.get(PAGE_HEIGHT));
			saveConfigValue(MARGIN_TOP, pageSettings.get(MARGIN_TOP));
			saveConfigValue(MARGIN_RIGHT, pageSettings.get(MARGIN_RIGHT));
			saveConfigValue(MARGIN_BOTTOM, pageSettings.get(MARGIN_BOTTOM));
			saveConfigValue(MARGIN_LEFT, pageSettings.get(MARGIN_LEFT));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/defaultRates", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Double> readDefaultRatesSettings() {
		Map<String, Double> defaultRates = new HashMap<String, Double>();
		defaultRates.put(GOLD_PRICE, getConfigValue(GOLD_PRICE));
		defaultRates.put(GOLD_WASTAGE, getConfigValue(GOLD_WASTAGE));
		defaultRates.put(GOLD_MAKING_CHARGES, getConfigValue(GOLD_MAKING_CHARGES));
		defaultRates.put(SILVER_PRICE, getConfigValue(SILVER_PRICE));
		defaultRates.put(SILVER_WASTAGE, getConfigValue(SILVER_WASTAGE));
		defaultRates.put(SILVER_MAKING_CHARGES, getConfigValue(SILVER_MAKING_CHARGES));

		return defaultRates;
	}

	@RequestMapping(value = "/defaultRates", method = RequestMethod.POST)
	@ResponseBody
	public void writeDefaultRatesSettings(@RequestBody Map<String, Double> defaultRates) {
		try {
			saveConfigValue(GOLD_PRICE, defaultRates.get(GOLD_PRICE));
			saveConfigValue(GOLD_WASTAGE, defaultRates.get(GOLD_WASTAGE));
			saveConfigValue(GOLD_MAKING_CHARGES, defaultRates.get(GOLD_MAKING_CHARGES));
			saveConfigValue(SILVER_PRICE, defaultRates.get(SILVER_PRICE));
			saveConfigValue(SILVER_WASTAGE, defaultRates.get(SILVER_WASTAGE));
			saveConfigValue(SILVER_MAKING_CHARGES, defaultRates.get(SILVER_MAKING_CHARGES));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Double getConfigValue(String configName) {
		ConfigurationModel configItem = configurationService.getByName(configName);
		Double value = 1.0;
		if (configItem != null) {
			value = Double.parseDouble(configItem.getStrValue());
		}
		return value;
	}

	public void saveConfigValue(String configName, Double value) {
		ConfigurationModel configItem = configurationService.getByName(configName);
		if (configItem == null) {
			configItem = new ConfigurationModel();
			configItem.setName(configName);
		}
		if (value == null || value < 0) {
			value = 1.0;
		}
		configItem.setStrValue(value.toString());
		configItem.setValue(value.intValue());
		configurationService.save(configItem);
	}
}
