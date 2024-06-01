/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.block.field;

import javafx.scene.image.ImageView;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.GameObject;

import java.io.File;
import java.io.Serial;

/**
 * 装飾を表すクラス。
 * <br>
 * ブロック・フィールドは各クラスにIDが振られています。こんな感じです。
 * <br>
 * Ground = 0<br>
 * Udon_House = 1<br>
 * SoilRoad = 2<br>
 * SoilRoadCorner1 = 3<br>
 * Onigiri_House= 4<br>
 * BlueFlower = 5<br>
 *
 * @author 渡邉雄宇
 * @version 1.0
 * @see GameObject
 */
public abstract class Field extends GameObject {
    @Serial
    private static final long serialVersionUID = 1L;
    protected ImageView background;

    /**
     * コンストラクタ。
     *
     * @param image 画像パス
     */
    public Field(File image) {
        super(image);
        setHited(false);
        background = new ImageView(Ground.IMAGE);
    }


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    protected void doUpdate() {
        getBackground().setTranslateX(getX());
        getBackground().setTranslateY(getY());

        getImageView().toBack();
        getBackground().toBack();
    }

    public ImageView getBackground() {
        return background;
    }
}
