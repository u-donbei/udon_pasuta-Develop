/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.panes;

import javafx.application.Platform;
import javafx.scene.layout.*;
import jp.co.yahoo.yu_w_main.udon_pasuta.managers.WorldManager;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.block.field.Field;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Character;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.world.World;

/**ゲーム画面を表示するペイン。
 * 
 * @author 渡邉雄宇
 * 
 * @version 1.0
 * 
 * @see Pane*/
public class GameViewPane extends Pane {
	/**ワールド*/
	private World world;
	
	/**コンストラクタ。
	 * ワールドを構築します。
	 * 処理に時間がかかる可能性があります。*/
	public GameViewPane() {
		super();
		System.out.println("GameViewPane");
		world = new World();
		WorldManager.buildWorld(world, this);
		setPrefSize(4000, 2500);
	}
	
	/**ブロックを追加する。
	 * 
	 * @param object 対象
	 * 
	 * @param x X座標
	 * 
	 * @param y Y座標*/
	public void addBlock(Field object, int x, int y) {
		world.getBlocks()[x][y] = object;
		Platform.runLater(() -> {
			getChildren().add(object.getImageView());
			if (object.getBackground() != null) {
				getChildren().add(object.getBackground());
			}
			object.setX(x *  50);
			object.setY(y * 50);
			object.update(() -> { });
		});
	}
	
	/**ブロックを削除する。
	 * 
	 * @param x X座標
	 * 
	 * @param y Y座標*/
	public void removeBlock(int x, int y) {
		world.getBlocks()[x][y] = null;
	}
	
	/**キャラクターを追加する。
	 * このメソッドは、addBlockと違って、<b>座標に50を掛けない</b>ので注意してください。
	 * 
	 * @param chara 対象
	 * 
	 * @param x X座標
	 * 
	 * @param y Y座標*/
	public void addChara(Character chara, double x, double y) {
		getChildren().add(chara.getImageView());
		chara.setX(x);
		chara.setY(y);
	}

	public final World getWorld() {
		return world;
	}
}
