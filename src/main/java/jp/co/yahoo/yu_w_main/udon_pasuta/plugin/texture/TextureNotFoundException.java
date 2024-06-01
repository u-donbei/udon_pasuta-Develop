/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.plugin.texture;

/**テクスチャが存在しないことを表す例外。*/
public class TextureNotFoundException extends Exception{
    /**コンストラクタ。*/
    public TextureNotFoundException(String message) {
        super(message);
    }
}
