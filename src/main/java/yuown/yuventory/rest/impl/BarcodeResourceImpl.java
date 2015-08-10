package yuown.yuventory.rest.impl;

import java.util.HashMap;
import java.util.Map;

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

import yuown.yuventory.business.services.BarcodeService;
import yuown.yuventory.business.services.ConfigurationService;
import yuown.yuventory.model.ConfigurationModel;

@RestController
@RequestMapping(value = "/barcode")
public class BarcodeResourceImpl {

	@Autowired
	private BarcodeService barcodeService;

	@Autowired
	private ConfigurationService configurationService;

	private static final String BARCODE_WIDTH = "barcode_width";

	private static final String BARCODE_HEIGHT = "barcode_height";

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<byte[]> generateBarcodeFromItemID(@PathVariable("id") Integer id) {
		byte[] barcodeImage = barcodeService.generateBarcodeFromItemID(id);
		if (null != barcodeImage && barcodeImage.length > 0) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType(MediaType.TEXT_PLAIN_VALUE));
			return new ResponseEntity<byte[]>(barcodeImage, headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<byte[]>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/dimensions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Integer> getDimensions() {
		Map<String, Integer> dimensions = new HashMap<String, Integer>();
		ConfigurationModel width = configurationService.getByName(BARCODE_WIDTH);
		ConfigurationModel height = configurationService.getByName(BARCODE_HEIGHT);
		if (width != null) {
			dimensions.put(BARCODE_WIDTH, width.getValue());
		} else {
			dimensions.put(BARCODE_WIDTH, 0);
		}
		if (height != null) {
			dimensions.put(BARCODE_HEIGHT, height.getValue());
		} else {
			dimensions.put(BARCODE_HEIGHT, 0);
		}
		return dimensions;
	}

	@RequestMapping(value = "/dimensions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Map<String, Integer>> setDimensions(@RequestBody Map<String, Integer> dimensions) {
		HttpHeaders headers = new HttpHeaders();
		if (dimensions.size() != 2) {
			headers.add("errorMessage", "Invalid Update for Barcode Dimensions");
			return new ResponseEntity<Map<String, Integer>>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (!dimensions.containsKey(BARCODE_WIDTH) || !dimensions.containsKey(BARCODE_HEIGHT)) {
			headers.add("errorMessage", "Invalid Update for Barcode Dimensions");
			return new ResponseEntity<Map<String, Integer>>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		ConfigurationModel width = configurationService.getByName(BARCODE_WIDTH);
		if (width != null) {
			width.setValue(dimensions.get(BARCODE_WIDTH));
			configurationService.save(width);
		} else {
			width = new ConfigurationModel();
			width.setName(BARCODE_WIDTH);
			width.setValue(dimensions.get(BARCODE_WIDTH));
			configurationService.save(width);
		}
		ConfigurationModel height = configurationService.getByName(BARCODE_HEIGHT);
		if (height != null) {
			height.setValue(dimensions.get(BARCODE_HEIGHT));
			configurationService.save(height);
		} else {
			height = new ConfigurationModel();
			height.setName(BARCODE_HEIGHT);
			height.setValue(dimensions.get(BARCODE_HEIGHT));
			configurationService.save(height);
		}
		return new ResponseEntity<Map<String, Integer>>(dimensions, headers, HttpStatus.OK);
	}
}
