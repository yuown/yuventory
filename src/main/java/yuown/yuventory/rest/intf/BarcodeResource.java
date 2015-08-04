package yuown.yuventory.rest.intf;

import org.springframework.http.ResponseEntity;

public interface BarcodeResource {

	public ResponseEntity<byte[]> generateBarcodeFromItemID(Integer id);

}
