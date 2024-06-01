/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.block.field;

import javafx.scene.image.Image;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.block.Block;

import java.io.File;

public class SoilRoad extends Field {
    public static final File IMAGE = new File(PathConsts.TEXTURE_DIR.getPath() + "soil_road.png");

    /**ID*/
    public static final int ID = 2;
    /**
     * コンストラクタ。
     *
     */
    public SoilRoad() {
        super(IMAGE);
        background = null;
    }

    @Override
    protected void doUpdate() {
        this.getImageView().toBack();
    }
}
