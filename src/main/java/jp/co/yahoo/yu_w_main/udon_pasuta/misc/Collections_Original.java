/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.misc;

import java.util.Collections;
import java.util.List;

/**java.util.Collectionsクラスを便利にしたクラス。*/
public final class Collections_Original {
    /**T型のリストをシャッフルし、リストの0番目を返す。
     *
     * @param list 対象のリスト*/
    public static <T> T getShuffle(List<T> list){
        //シャッフル
        Collections.shuffle(list);
        return list.getFirst();
    }
}
