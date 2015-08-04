package yuown.yuventory.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import yuown.yuventory.business.services.BarcodeService;
import yuown.yuventory.rest.intf.BarcodeResource;

@RestController
@RequestMapping(value = "/items")
public class BarcodeResourceImpl implements BarcodeResource {

	@Autowired
	private BarcodeService barcodeService;

	@Override
	@RequestMapping(value = "/barcode/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> generateBarcodeFromItemID(@PathVariable("id") Integer id) {
		byte[] barcodeImage = barcodeService.generateBarcodeFromItemID(id);
		if (null != barcodeImage && barcodeImage.length > 0) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE));
			return new ResponseEntity<byte[]>(barcodeImage, headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<byte[]>(HttpStatus.NO_CONTENT);
		}
	}
}
