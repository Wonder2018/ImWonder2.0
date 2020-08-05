package top.imwonder.myblog.experiment;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

import com.luciad.imageio.webp.WebPWriteParam;

import org.junit.jupiter.api.Test;

public class WebpTtest {
    @Test
    public void jpgToWebp() {
        String inputJpgPath = "photo/dw.jfif";
        String outputWebpPath = "photo/testJTWb.webp";
        try {
            // Obtain an image to encode from somewhere
            BufferedImage image = ImageIO.read(new File(inputJpgPath));

            // Obtain a WebP ImageWriter instance
            ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();

            // Configure encoding parameters
            WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
            writeParam.setCompressionMode(WebPWriteParam.MODE_DEFAULT);

            // Configure the output on the ImageWriter
            writer.setOutput(new FileImageOutputStream(new File(outputWebpPath)));

            // Encode
            writer.write(null, new IIOImage(image, null, null), writeParam);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}