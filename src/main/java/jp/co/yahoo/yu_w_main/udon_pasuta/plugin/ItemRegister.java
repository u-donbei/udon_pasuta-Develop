/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.plugin;

import jp.co.yahoo.yu_w_main.udon_pasuta.constants.game.GameInfo;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Item;

import java.util.Map;

/**アイテムの登録を担当するクラス。*/
public class ItemRegister<T extends Item> implements Register<T>{
    public ItemRegister() { }
    public void register(T element){
        GameInfo.ITEMS.put(element.getName(), element);
    }

    public static Map<String, Item> getItems() {
        return GameInfo.ITEMS;
    }
}
