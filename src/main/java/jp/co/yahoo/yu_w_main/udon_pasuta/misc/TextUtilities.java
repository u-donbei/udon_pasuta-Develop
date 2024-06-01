/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.misc;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.logging.Logging;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class TextUtilities {
    private static final Font font;
    static {
        try {
            font = Font.createFont(Font.PLAIN, new FileInputStream(PathConsts.FONT_DIR + "PixelMplus\\PixelMplus12-Regular.ttf"));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Image toImage(String text, java.awt.Color textFill, java.awt.Color background, int width, int height, int size){
        Font font = TextUtilities.font.deriveFont((float) size);

        BufferedImage tmp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = tmp.getGraphics();
        graphics.setFont(font);
        FontMetrics metrics = graphics.getFontMetrics();
        BufferedImage image = new BufferedImage(metrics.stringWidth(text), metrics.getHeight(), BufferedImage.TYPE_INT_ARGB);
        graphics = image.getGraphics();

        graphics.setFont(font);
        graphics.setColor(textFill);
        graphics.drawString(text, 0, size);
        return SwingFXUtils.toFXImage(image, null);
    }

    public static String convertToUnicode(String plain) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plain.length(); i++) {
            sb.append(String.format("\\u%04X", Character.codePointAt(plain, i)));
        }
        String unicode = sb.toString();
        return new String(unicode.getBytes(Charset.forName("Unicode")), Charset.forName("Unicode"));
    }

    public static String convertToPlain(String unicode) {
        String[] strs = unicode.split("\\\\u");
        int[] points = new int[strs.length - 1];
        for (int i = 0; i < points.length; i++) {
            points[i] = Integer.parseInt(strs[i + 1], 16);
        }
        String plain = new String(points, 0, points.length);
        return new String(plain.getBytes(Charset.forName("Unicode")), Charset.forName("Unicode"));
    }
}
