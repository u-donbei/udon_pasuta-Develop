/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.constants.game;

import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Empty;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Item;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Mentsuyu;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.food.*;

import java.util.HashMap;

/**
 * ゲームの情報を保持するクラス。
 */
public final class GameInfo {
	public static final String VERSION = "Beta 0.0.1";
	public static final HashMap<String, Item> ITEMS = new HashMap<>() {
		{
			put("小麦粉", new Komugiko());
			put("あげだま", new Agedama());
			put("めんつゆ", new Mentsuyu());
			put("きざみねぎ", new KizamiNegi());
			put("きざみのり", new KizamiNori());
			put("のり", new Nori());
			put("Empty Slot", new Empty());
		}
	};
}
