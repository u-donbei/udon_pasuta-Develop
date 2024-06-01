/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.font;

import javafx.scene.text.Font;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;

import java.nio.file.Path;

public final class Fonts {
    public static final Font PIXEL_MPLUS_12_18 = Font.loadFont(PathConsts.FONT_DIR.makeURI().toString() + "/PixelMplus/PixelMplus12-Regular.ttf",18);
    public static final Font PIXEL_MPLUS_12_10 = Font.loadFont(PathConsts.FONT_DIR.makeURI().toString() + "/PixelMplus/PixelMplus12-Regular.ttf", 10);

    public static Font getPixelMplus12(double size){
        return Font.loadFont(PathConsts.FONT_DIR.makeURI().toString() + "/PixelMplus/PixelMplus12-Regular.ttf", size);
    }
}
