package yuown.yuventory.rest.impl;

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

@RestController
@RequestMapping(value = "/barcode")
public class BarcodeResourceImpl {

	@Autowired
	private BarcodeService barcodeService;

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
		return barcodeService.getDimensions();
	}

	@RequestMapping(value = "/dimensions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Map<String, Integer>> saveDimensions(@RequestBody Map<String, Integer> dimensions) {
		HttpHeaders headers = new HttpHeaders();
		if (dimensions.size() != 2) {
			headers.add("errorMessage", "Invalid Update for Barcode Dimensions");
			return new ResponseEntity<Map<String, Integer>>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (!dimensions.containsKey(BarcodeService.BARCODE_WIDTH) || !dimensions.containsKey(BarcodeService.BARCODE_HEIGHT)) {
			headers.add("errorMessage", "Invalid Update for Barcode Dimensions");
			return new ResponseEntity<Map<String, Integer>>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		barcodeService.saveDimensions(dimensions);
		return new ResponseEntity<Map<String, Integer>>(dimensions, headers, HttpStatus.OK);
	}
}
