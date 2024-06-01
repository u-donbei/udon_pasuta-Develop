/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.block;

import java.io.File;
import java.io.Serial;
import java.nio.file.Path;

import jp.co.yahoo.yu_w_main.udon_pasuta.objects.GameObject;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.block.field.Field;

/**当たり判定のあるブロックを表すクラス。
 *
 * @author 渡邉雄宇
 *
 * @version 1.0
 *
 * @see GameObject*/
public abstract class Block extends Field {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ。
     *
     * @param image 画像パス
     */
    public Block(File image) {
        super(image);
        setHited(true);
    }

    @Override
    public Object clone() {
        Block result = new Block(null) {

        };
        result.setImage(new File(getImage().toURI()));
        result.setX(getX());
        result.setY(getY());

        return result;
    }
}
