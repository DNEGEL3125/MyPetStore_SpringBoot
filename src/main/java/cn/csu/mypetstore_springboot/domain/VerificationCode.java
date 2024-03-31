package cn.csu.mypetstore_springboot.domain;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class VerificationCode {
    public byte[] generateVerificationCode() {
        final int width = 60;
        final int height = 20;

        //创建内存图像并获得图形上下文
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        /*
         * 产生随机验证码
         * 定义验证码的字符表
         */
        final String possibleChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] rands = new char[4];
        for (int i = 0; i < rands.length; i++) {
            int rand = (int) (Math.random() * possibleChars.length());
            rands[i] = possibleChars.charAt(rand);
        }

        /*
         * 产生图像
         * 画背景
         */
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);

        /*
         * 随机产生120个干扰点
         */

        for (int i = 0; i < 120; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            int red = (int) (Math.random() * 255);
            int green = (int) (Math.random() * 255);
            int blue = (int) (Math.random() * 255);
            g.setColor(new Color(red, green, blue));
            g.drawOval(x, y, 1, 0);
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font(null, Font.ITALIC | Font.BOLD, 18));

        //在不同高度输出验证码的不同字符
        g.drawString(String.valueOf(rands[0]), 1, 17);
        g.drawString(String.valueOf(rands[1]), 16, 15);
        g.drawString(String.valueOf(rands[2]), 31, 18);
        g.drawString(String.valueOf(rands[3]), 46, 16);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "JPEG", baos);
        } catch (IOException e) {
            return new byte[0];
        }
        g.dispose();

        return baos.toByteArray();
    }
}
