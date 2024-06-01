/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.control;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jp.co.yahoo.yu_w_main.udon_pasuta.logging.Logging;
import jp.co.yahoo.yu_w_main.udon_pasuta.misc.TextUtilities;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;

import java.io.IOException;

/**
 * ステータスを表示するクラス。
 */
public class StatusMessage extends Message {
	/**
	 * 天かすの表示
	 */
	private ImageView tenkasu;
	/**
	 * HPの表示
	 */
	private ImageView hp;
	/**
	 * プレイヤー
	 */
	private Udon udon;

	/**
	 * コンストラクタ
	 */
	public StatusMessage(Udon udon) throws IOException {
		init(udon);
	}

	public ImageView getTenkasu() {
		return tenkasu;
	}

	public ImageView getHp() {
		return hp;
	}

	@Override
	public void reText(String text, Image image, double millis, Node... nodes) {
		getChildren().clear();
		setCenter(this.text);
		super.reText(text, image, millis, nodes);
		Thread.ofVirtual()
				.name("next-thread")
				.start(() -> {
					while (!Message.isNext.get()) {
						//DO NOT FOUND
					}
					Platform.runLater(() -> {
						getChildren().clear();
						try {
							init(udon);
						} catch (IOException e) {
							Logging.LOGGER.error("An error has occurred", e);
						}
					});
				});
	}

	private void init(Udon udon) throws IOException {
		tenkasu = new ImageView(TextUtilities.toImage(udon.getTenkasu() + "天かす", java.awt.Color.WHITE, java.awt.Color.BLACK, 100, 50, 18));
		hp = new ImageView(TextUtilities.toImage(udon.getHp() + "HP", java.awt.Color.WHITE, java.awt.Color.BLACK, 100, 50, 18));

		//追加
		setTop(tenkasu);
		setAlignment(tenkasu, Pos.CENTER);
		setLeft(hp);
		setAlignment(hp, Pos.TOP_LEFT);
		this.udon = udon;
	}

}
