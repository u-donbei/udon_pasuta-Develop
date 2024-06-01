/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.managers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import jp.co.yahoo.yu_w_main.udon_pasuta.annotations.Manager;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;

/**
 * BGMを再生する
 */
@Manager
public class SEManager {
    private static final Media pipipi;
    private static final MediaPlayer pipipiPlayer;
    private static double volume = 40;

    static {
        pipipi = new Media(PathConsts.SE_DIR.makeURI() + "pipipi.mp3");
        pipipiPlayer = new MediaPlayer(pipipi);

        pipipiPlayer.setCycleCount(1);
        pipipiPlayer.setVolume(40d / 100d);
        pipipiPlayer.setOnEndOfMedia(pipipiPlayer::stop);
    }

    /**
     * 指定したBGMを再生する。
     * このメソッドは、play(String)を使用しています。
     *
     * @param name BGM名。
     * @throws UnsupportedOperationException BGM名がおかしいとき
     */
    public static void play(String name) throws UnsupportedOperationException {
		findPlayer(name).play();
	}

    /**
     * 指定したBGMを停止する。
     * このメソッドは、play(String)を使用しています。
     *
     * @param name BGM名。
     * @throws UnsupportedOperationException BGM名がおかしいとき
     */
    public static void stop(String name) throws UnsupportedOperationException {
        findPlayer(name).stop();
    }

    /**
     * 指定したBGMを検索する。
     *
     * @param name BGM名。<br>
     *             名前は次の通りです。<br>
     *             <br>
     *             bgm.field=フィールド
     * @return nameのメディアプレイヤー
     * @throws UnsupportedOperationException BGM名がおかしいとき
     */
    public static MediaPlayer findPlayer(String name) throws UnsupportedOperationException {
        switch (name) {
            case "se.pipipi" -> {
                return pipipiPlayer;
            }
            default -> throw new UnsupportedOperationException("BGM名が不正です。");
        }
    }

    public static double getVolume() {
        return volume;
    }

    /**
     * BGMの音量を設定する。
     *
     * @param volume 音量
     */
    public static void setVolume(double volume) {
        pipipiPlayer.setVolume(volume / 100);
        SEManager.volume = volume;
    }
}