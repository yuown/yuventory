package yuown.yuventory.bc;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;

@Component
public class BarCodeGenerator {

	public void generate() {

		BitMatrix bitMatrix;
		Writer writer = new QRCodeWriter();
		try {
			// Write Barcode
			bitMatrix = new Code128Writer().encode("123456789", BarcodeFormat.CODE_128, 150, 80, null);
			MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(new File("/Users/kirannk/Desktop/y/1.png")));
			System.out.println("Code128 Barcode Generated.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new BarCodeGenerator().generate();
	}
}
