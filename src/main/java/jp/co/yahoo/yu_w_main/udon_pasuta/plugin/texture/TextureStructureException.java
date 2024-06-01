/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.plugin.texture;

/**テクスチャパックの構造が誤っていることを表す例外。*/
public class TextureStructureException extends Exception{
    /**コンストラクタ。*/
    public TextureStructureException(String message) {
        super(message);
    }
}
