package yuown.yuventory.business.services;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import yuown.yuventory.model.ItemModel;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

@Service
public class BarcodeService {

	@Autowired
	private ItemService itemService;

	private static final String BARCODE_WIDTH = "barcode_width";

	private static final String BARCODE_HEIGHT = "barcode_height";

	private int barcodeWidth;

	private int barcodeHeight;

	public byte[] generateBarcodeFromItemID(Integer id) {
		barcodeWidth = Integer.parseInt(System.getProperty(BARCODE_WIDTH));
		barcodeHeight = Integer.parseInt(System.getProperty(BARCODE_HEIGHT));
		ItemModel itemModel = itemService.getById(id);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (null != itemModel) {
			BitMatrix bitMatrix;
			try {
				bitMatrix = new Code128Writer().encode(id.toString(), BarcodeFormat.CODE_128, barcodeWidth, barcodeHeight, null);
				MatrixToImageWriter.writeToStream(bitMatrix, "png", baos);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Base64Utils.encode(baos.toByteArray());
	}
}
