/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.block.field;

import javafx.scene.image.Image;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.block.field.Field;

import java.io.File;

/**地面を表すクラス。*/
public class Ground extends Field {
    public static final Image IMAGE = new Image(new File(PathConsts.TEXTURE_DIR.getPath() + "ground.png").toURI().toString());
    /**ID*/
    public static final int ID = 0;
    /**
     * コンストラクタ。
     */
    public Ground() {
        super(new File(PathConsts.TEXTURE_DIR.getPath() + "ground.png"));
        background = null;
    }

    @Override
    protected void doUpdate() {
        getImageView().toBack();
    }
}
