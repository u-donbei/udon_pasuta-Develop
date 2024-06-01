/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.block.field;

import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;

import java.io.File;

public class SoilRoadCorner1 extends Field {
    public static final File IMAGE = new File(PathConsts.TEXTURE_DIR.getPath() + "soil_road_corner1.png");

    /**ID*/
    public static final int ID = 3;
    /**
     * コンストラクタ。
     *
     */
    public SoilRoadCorner1() {
        super(IMAGE);
    }
}
