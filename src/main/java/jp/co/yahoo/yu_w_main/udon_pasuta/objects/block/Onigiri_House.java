/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.block;

import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;

import java.io.File;

/**うどんべいの家を表すクラス。
 *
 * @author 渡邉雄宇
 *
 * @see Block*/
public class Onigiri_House extends Block{
    /**画像*/
    private static final File IMAGE = new File(PathConsts.TEXTURE_DIR.getPath() + "onigiriHouse.png");

    /**コンストラクタ。*/
    public Onigiri_House() {
        super(IMAGE);
    }

}
