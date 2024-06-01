/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.enemy;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Technique;
import jp.co.yahoo.yu_w_main.udon_pasuta.logging.Logging;
import jp.co.yahoo.yu_w_main.udon_pasuta.managers.GameManager;
import jp.co.yahoo.yu_w_main.udon_pasuta.misc.Collections_Original;
import jp.co.yahoo.yu_w_main.udon_pasuta.misc.TextUtilities;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Character;
import jp.co.yahoo.yu_w_main.udon_pasuta.variables.Variable;

import java.awt.*;
import java.io.File;
import java.io.Serial;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ミートソースを表すクラス。
 *
 * @author 渡邉雄宇
 * @version 1.0
 * @see Enemy
 */
public class MeatSauce extends Enemy {

	/**
	 * 見た目
	 */
	public static final File IMAGE = Paths.get(PathConsts.TEXTURE_DIR.getPath() + "meat.png").toFile();
	/**
	 * 技
	 */
	public static final Technique[] originalTec = {new Technique("たたく", 6, Double.POSITIVE_INFINITY),
			new Technique("ソースをかける", 12, Double.POSITIVE_INFINITY), new Technique("まきつく", 7, Double.POSITIVE_INFINITY),
			new Technique("さらをなげる", 11, Double.POSITIVE_INFINITY)};
	@Serial
	private static final long serialVersionUID = -5504054933664501954L;

	/**
	 * コンストラクタ。
	 * 画像パスはIMAGEにする。
	 * 技も初期化する。
	 */
	public MeatSauce() {
		super(Paths.get(PathConsts.TEXTURE_DIR.getPath() + "meat.png").toFile(), 100,originalTec);
		setHp(20);
		setName("ミートソース");
	}

	@Override
	public int attack(Message message, Character... targets) {
		//攻撃対象を抽出
		ObservableList<Character> t = FXCollections.observableArrayList(targets);
		Character target = Collections_Original.getShuffle(t);

		//攻撃(雑魚敵なのでランダム)
		List<Technique> tecs = Arrays.asList(originalTec);
		Collections.shuffle(tecs);

		Technique tec = tecs.getFirst();

		message.reText(getName() + "の" + tec.getName() + "こうげき!", null, 100);

		try {
			target.removeHp(tec.getDamage());
		} catch (IllegalArgumentException e) {
			target.removeHp(target.getHp());
		}

		return tec.getDamage();
	}

	@Override
	public int attack(Message message, Runnable onExit, Character... targets) {
		//攻撃対象を抽出
		ObservableList<Character> t = FXCollections.observableArrayList(targets);
		Character target = Collections_Original.getShuffle(t);

		//攻撃(雑魚敵なのでランダム)
		List<Technique> tecs = Arrays.asList(originalTec);
		Collections.shuffle(tecs);

		Technique tec = tecs.getFirst();

		message.reText("ミートソースの" + tec.getName() + "こうげき!", null, 100, () -> {
			System.out.println("finish");
			try {
				System.out.println("dm");
				target.removeHp(tec.getDamage());
			} catch (IllegalArgumentException e) {
				System.out.println("ex");
				target.removeHp(target.getHp());
			}
			onExit.run();
		}, () -> Platform.exitNestedEventLoop("tec-loop", null), true);
		Platform.enterNestedEventLoop("tec-loop");
		return tec.getDamage();
	}

	@Override
	public int attack(Message message, Runnable onExit, Runnable onFinish, Character... targets) {
		return attack(message, onExit, onFinish, () -> {}, targets);
	}

	@Override
	public int attack(Message message, Runnable onExit, Runnable onFinish, Runnable change, Character... targets) {
		//攻撃対象を抽出
		ObservableList<Character> t = FXCollections.observableArrayList(targets);
		Character target = Collections_Original.getShuffle(t);

		//攻撃(雑魚敵なのでランダム)
		List<Technique> tecs = Arrays.asList(originalTec);
		Collections.shuffle(tecs);

		Technique tec = tecs.getFirst();

		message.reText("ミートソースの" + tec.getName() + "こうげき!", null, 100, () -> {
			try {
				target.removeHp(tec.getDamage());
			} catch (IllegalArgumentException e) {
				target.removeHp(target.getHp());
			}
			change.run();
			Variable.IS_MSG_FINISH.set(true);
		}, () -> {
			Platform.exitNestedEventLoop("tec-loop", null);
		}, true);
		Platform.enterNestedEventLoop("tec-loop");

		message.reText(target.getName() + "は" + tec.getDamage() + "ダメージを受けた", null, 100, () -> Variable.IS_MSG_FINISH.set(true), () -> {
			onFinish.run();
			Platform.exitNestedEventLoop("tec-loop", null);
		}, true);
		Platform.enterNestedEventLoop("tec-loop");

		return tec.getDamage();
	}

	@Override
	public void move(Runnable process) {
		//うどんべいがいる方向に動く
		if (GameManager.getUdon().getX() < getX()) {
			stepX(-1);
		} if (GameManager.getUdon().getX() > getX()) {
			stepX(1);
		} if (GameManager.getUdon().getY() < getY()) {
			stepY(-1);
		} if (GameManager.getUdon().getY() > getY()) {
			stepY(1);
		}
	}

	@Override
	public void handle(Runnable process, Message message) {
		handleImpl(process, message, "ミートソース");
	}

	protected void handleImpl(Runnable process, Message message, String name) {
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		ImageView dousuru = new ImageView(TextUtilities.toImage("どうする?", Color.WHITE, Color.BLACK, 1, 1, 15));
		Button battle = new Button("", new ImageView(TextUtilities.toImage("たたかう", Color.WHITE, Color.BLACK, 1, 1, 15)));
		Button away = new Button("", new ImageView(TextUtilities.toImage("にげる", Color.WHITE, Color.BLACK, 1, 1, 15)));
		battle.focusedProperty().addListener((ob, o, n) -> {
			if (n) {
				battle.setGraphic(new ImageView(TextUtilities.toImage("▶たたかう", Color.WHITE, Color.BLACK, 1, 1, 15)));
			} else {
				battle.setGraphic(new ImageView(TextUtilities.toImage("たたかう", Color.WHITE, Color.BLACK, 1, 1, 15)));
			}
		});

		battle.setOnKeyPressed(e -> {
			switch (e.getCode()) {
				case ENTER, SPACE -> {
					Logging.LOGGER.debug("battle!");
					Variable.IS_BATTLE = true;
					message.getTimeline().stop();
					message.getNext().fire();
				}
			}
		});

		away.focusedProperty().addListener((ob, o, n) -> {
			if (n) {
				away.setGraphic(new ImageView(TextUtilities.toImage("▶にげる", Color.WHITE, Color.BLACK, 1, 1, 15)));
			} else {
				away.setGraphic(new ImageView(TextUtilities.toImage("にげる", Color.WHITE, Color.BLACK, 1, 1, 15)));
			}
		});
		battle.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.TRANSPARENT, null, null)));
		away.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.TRANSPARENT, null, null)));
		VBox d = new VBox(dousuru, battle, away);
		d.setBorder(new Border(new BorderStroke(javafx.scene.paint.Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		Variable.BATTLE_TARGET = this;
		message.reText(name + "が現れた!", getImageView().getImage(), 50, new HBox(new ImageView(getImageView().getImage()), d));
		process.run();
	}
}
