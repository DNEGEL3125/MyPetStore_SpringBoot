package cn.csu.mypetstore_springboot.Services;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageService {

    public static byte[] resizeImage(MultipartFile image, int width, int height) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(image.getInputStream())
                .size(width, height)
                .outputFormat("jpg") // 设置输出格式
                .toOutputStream(outputStream);
        return outputStream.toByteArray();
    }

    public static void saveImage(byte[] imageData, String outputPath) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
        Path filePath = Paths.get(outputPath);
        Files.copy(inputStream, filePath);
    }

    public static void saveImage(byte[] imageData, Path outputPath) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
        Files.copy(inputStream, outputPath);
    }
}
