package mx.com.tecnetia.marcoproyectoseguridad.util;

import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Test {
    /*public static void main(String[] args) {
        String filePath = "/Users/carlosruiz/Downloads/barras2.jpeg";
        byte[] bytes = null;
        try {
           bytes = Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BarCodeUtil barCodeUtil = new BarCodeUtil();
        try {
            barCodeUtil.decodeImage(bytes);
        } catch (BarCodeUtil.BarcodeDecodingException e) {
            throw new RuntimeException(e);
        }
    }*/

    public static String readQRCode() {
        String fileName = "/Users/carlosruiz/Downloads/barras2.jpeg";
        File file = new File(fileName);
        BufferedImage image = null;
        BinaryBitmap bitmap = null;
        Result result = null;

        try {
            image = ImageIO.read(file);
            int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
            RGBLuminanceSource source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), pixels);
            bitmap = new BinaryBitmap(new HybridBinarizer(source));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (bitmap == null)
            return null;

        QRCodeReader reader = new QRCodeReader();
        try {
            result = reader.decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ChecksumException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        String res = Test.readQRCode();
        System.out.println("REs: "+res);
    }
}
