package cn.csu.mypetstore_springboot.Services;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.random.RandomGenerator;

@Service
public class VerificationCodeService {
    private final Map<String, String> usernameToVerificationCode = new ConcurrentHashMap<>();
    private final RandomGenerator randomGenerator = RandomGenerator.getDefault();


    public String randGenerateCode() {
        return Integer.toString(
                Math.abs(randomGenerator.nextInt()) % 900000 + 100000
        );
    }

    // Utility method to generate the image bytes (replace this with your actual image generation logic)
    public byte[] getImageBytes(String text) {

        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 100, 50);
        graphics.setColor(Color.BLACK);
        graphics.drawString(text, 10, 25);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
}
