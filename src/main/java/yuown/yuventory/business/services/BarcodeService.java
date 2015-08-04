package yuown.yuventory.business.services;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

import yuown.yuventory.model.ItemModel;

@Service
public class BarcodeService {

	@Autowired
	private ItemService itemService;

	public byte[] generateBarcodeFromItemID(Integer id) {
		ItemModel itemModel = itemService.getById(id);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (null != itemModel) {
			BitMatrix bitMatrix;
			try {
				bitMatrix = new Code128Writer().encode(id.toString(), BarcodeFormat.CODE_128, 150, 80, null);
				MatrixToImageWriter.writeToStream(bitMatrix, "png", baos);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return baos.toByteArray();
	}
}
