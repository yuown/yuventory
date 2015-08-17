package yuown.yuventory.business.services;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import yuown.yuventory.model.ConfigurationModel;
import yuown.yuventory.model.ItemModel;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

@Service
public class BarcodeService {

	@Autowired
	private ItemService itemService;

	@Autowired
	private ConfigurationService configurationService;

	public static final String BARCODE_WIDTH = "barcode_width";

	public static final String BARCODE_HEIGHT = "barcode_height";

	private int barcodeWidth;

	private int barcodeHeight;

	public byte[] generateBarcodeFromItemID(Integer id) {
		if (System.getProperty(BARCODE_WIDTH) == null || System.getProperty(BARCODE_HEIGHT) == null) {
			configurationService.saveSettingsToSystem();
		}
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

	public void saveDimensions(Map<String, Integer> dimensions) {
		ConfigurationModel width = configurationService.getByName(BARCODE_WIDTH);
		if (width == null) {
			width = new ConfigurationModel();
			width.setName(BARCODE_WIDTH);
		}
		ConfigurationModel height = configurationService.getByName(BARCODE_HEIGHT);
		if (height == null) {
			height = new ConfigurationModel();
			height.setName(BARCODE_HEIGHT);
		}
		width.setValue(dimensions.get(BARCODE_WIDTH));
		height.setValue(dimensions.get(BARCODE_HEIGHT));
		configurationService.save(width);
		configurationService.save(height);
	}
}
