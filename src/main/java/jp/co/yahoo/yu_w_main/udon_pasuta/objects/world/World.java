/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.world;

import jp.co.yahoo.yu_w_main.udon_pasuta.objects.block.field.Field;

/**ワールドを表すクラス。*/
public class World{
    /**ワールドのブロック*/
    private Field[][] blocks;

    /**コンストラクタ。*/
    public World(){
        blocks = new Field[80][50];
    }

    /**コンストラクタ。
     *
     * @param blocks ワールド*/
    public World(Field[][] blocks){
        this.blocks = blocks;
    }

    /**blocksを取得する。
     *
     * @return blocks*/
    public Field[][] getBlocks(){
        return blocks;
    }
}
