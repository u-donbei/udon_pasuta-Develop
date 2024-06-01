/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.character;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.Facing;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Technique;
import jp.co.yahoo.yu_w_main.udon_pasuta.misc.Collections_Original;
import jp.co.yahoo.yu_w_main.udon_pasuta.misc.ValueChecks;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.enemy.Enemy;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Empty;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Item;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Mentsuyu;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Negi;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.food.KizamiNori;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.tool.EmptyTool;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.tool.Tool;
import jp.co.yahoo.yu_w_main.udon_pasuta.save.deserialize.UdonDeserialize;
import jp.co.yahoo.yu_w_main.udon_pasuta.save.serialize.UdonSerialize;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * うどんべいを表すクラス。
 *
 * @author 渡邉雄宇
 * @version 1.0
 * @see Character
 */
@JsonSerialize(using = UdonSerialize.class)
@JsonDeserialize(using = UdonDeserialize.class)
public class Udon extends Character {
	private transient int stepFrameCount;
	private static final long serialVersionUID = 1L;
	/**
	 * レベル1の技
	 */
	public static Technique[] techn1 = {new Technique("はしつんつん", 2, Double.POSITIVE_INFINITY), new Technique("まきつく", 6, 10), new Technique("たたく", 7, 5)};
	/**
	 * レベル2の技
	 */
	public static Technique[] techn2 = {new Technique("めんつゆでっぽう", 7, 15, Mentsuyu.class), new Technique("あげだまでっぽう", 8, 10), new Technique("ねぎ", 6, Double.POSITIVE_INFINITY, Negi.class)};
	/**
	 * レベル3の技
	 */
	public static Technique[] techn3 = {new Technique("だいこんおろし", 10, 15), new Technique("わさび", 12, 10)};
	/**
	 * レベル4の技
	 */
	public static Technique[] techn4 = {new Technique("おろしがね", 12, 10), new Technique("めんつゆシャワー", 15, 5, Mentsuyu.class)};
	/**
	 * レベル5の技
	 */
	public static Technique[] techn5 = {new Technique("あげだまふんしゃ", 15, 10), new Technique("さしばし", 16, 5)};
	/**
	 * レベル6の技
	 */
	public static Technique[] techn6 = {new Technique("こうあつめんつゆ", 17, 10, Mentsuyu.class), new Technique("しょうが", 16, 10)};
	/**
	 * レベル7の技
	 */
	public static Technique[] techn7 = {new Technique("きざみねぎ", 18, 15, KizamiNori.class)};
	/**
	 * レベル8の技
	 */
	public static Technique[] techn8 = {new Technique("ひっさつわざ", 28, 10), new Technique("かいしんのいちげき!", 35, 5)};

	/**
	 * xp
	 */
	private SimpleIntegerProperty xp = new SimpleIntegerProperty(this, "xp");
	/**
	 * レベル
	 */
	private SimpleIntegerProperty level = new SimpleIntegerProperty(this, "level");
	/**
	 * 天かす
	 */
	private int tenkasu;
	/**
	 * ダッシュしているかどうか
	 */
	@JsonIgnore
	private transient boolean isDash;
	private Object rval;

	/**
	 * アイテム
	 */
	private final Item[] items = new Item[10];

	/**
	 * 道具
	 */
	private final Tool[] tools = new Tool[10];

	/**
	 * コンストラクタ。
	 */
	public Udon(String name) {
		super(new File(PathConsts.TEXTURE_DIR.getPath() + "udon1.png"));
		setLevel(1);
		setName(name);
		facing = Facing.SOUTH;
		hitPriority = 10;

		//アイテムをEmptyで埋める
		for (int i = 0; i < items.length; i++) {
			items[i] = new Empty();
		}

		for (int i = 0; i < tools.length; i++) {
			tools[i] = new EmptyTool();
		}
	}

	public Udon() {
		this("user");
	}

	public static int getByLevelMaxHP(int level) {
		return switch (level) {
			case 1 -> 30;
			case 2 -> 35;
			case 3 -> 40;
			case 4 -> 45;
			case 5 -> 50;
			case 6 -> 70;
			case 7 -> 80;
			case 8 -> 90;
			default ->
					throw new IllegalArgumentException("レベルが範囲外です(" + level + ")。レベルは1～8にしてください。");
		};
	}

	/**
	 * レベルを取得する。
	 *
	 * @return level
	 */
	public int getLevel() {
		return level.get();
	}

	/**
	 * レベルを設定する。
	 *
	 * @param level 新しい値
	 */
	public void setLevel(int level) {
		ValueChecks.checkOnNotNegative(level, "レベルが不正です。");
		setHp(getByLevelMaxHP(level));
		this.level.set(level);
	}

	/**
	 * レベルを1増やす。
	 */
	public void incrementLevel() {
		this.level.set(this.level.get() + 1);
	}

	/**
	 * 攻撃をする。
	 *
	 * @param targets 対象(複数指定可)
	 * @param message メッセージインスタンス
	 * @return 与えたダメージ
	 */
	public int attack(boolean isSelect, Message message, Enemy... targets) {
		ObservableList<Technique> tecs = FXCollections.observableArrayList();

		//レベルごとに技を追加
		switch (level.get()) {
			case 1 -> tecs.addAll(techn1);
			case 2 -> tecs.addAll(techn2);
			case 3 -> tecs.addAll(techn3);
			case 4 -> tecs.addAll(techn4);
			case 5 -> tecs.addAll(techn5);
			case 6 -> tecs.addAll(techn6);
			case 7 -> tecs.addAll(techn7);
			case 8 -> tecs.addAll(techn8);
		}
		//技の取得
		AtomicReference<Technique> technique = new AtomicReference<>();
		VBox t = new VBox();
		ScrollPane scroll = new ScrollPane(t);
		scroll.setContent(t);
		for (Technique t0 : tecs) {
			Button b = new Button(t0.makeText());
			t.getChildren().add(b);
			b.setOnAction(e -> {
				technique.set(t0);
				Platform.exitNestedEventLoop("tec-select-loop", rval);
			});
		}
		message.reText("技を選択してください", new Image(getImage().toURI().toString()), 100, scroll);
		rval = Platform.enterNestedEventLoop("tec-select-loop");

		//対象のシャッフル・取得
		Enemy target = Collections_Original.getShuffle(Arrays.asList(targets));

		//メッセージの表示
		message.reText(getName() + "の" + technique.get().getName() + "こうげき!", null, 100);
		//攻撃
		int damage = 0;
		if (technique.get().getMp() <= 0 && technique.get().getMp() != Double.POSITIVE_INFINITY) {
			message.reText(getName() + "はこうげきにしっぱいした!", null, 100);
		} else {
			try {
				technique.get().setMp(technique.get().getMp() - 1);
				target.removeHp(technique.get().getDamage());
				damage = technique.get().getDamage();
			} catch (IllegalArgumentException e) {
				damage = target.getHp();
				target.removeHp(target.getHp());
			}
		}
		return damage;
	}

	/**
	 * 攻撃をする。
	 *
	 * @param targets 対象(複数指定可)
	 * @param message メッセージインスタンス
	 * @return 与えたダメージ
	 */
	public int attack(boolean isSelect, Message message, Runnable onExit, Enemy... targets) {
		ObservableList<Technique> tecs = FXCollections.observableArrayList();

		//レベルごとに技を追加
		switch (level.get()) {
			case 8:
				tecs.addAll(techn8);
			case 7:
				tecs.addAll(techn7);
			case 6:
				tecs.addAll(techn6);
			case 5:
				tecs.addAll(techn5);
			case 4:
				tecs.addAll(techn4);
			case 3:
				tecs.addAll(techn3);
			case 2:
				tecs.addAll(techn2);
			case 1:
				tecs.addAll(techn1);
		}
		//技の取得
		AtomicReference<Technique> technique = new AtomicReference<>();
		VBox t = new VBox();
		ScrollPane scroll = new ScrollPane(t);
		t.setStyle("""
				-fx-background-color: black;
				""");
		scroll.setStyle("""
				-fx-background-color: black;
				""");
		scroll.setContent(t);
		for (Technique t0 : tecs) {
			Button b = new Button(t0.makeText());
			b.getStylesheets().add(getClass().getResource("/tecButton.css").toExternalForm());

			t.getChildren().add(b);
			b.setOnAction(e -> {
				technique.set(t0);
				Platform.exitNestedEventLoop("tec-select-loop", rval);
			});
		}
		message.reText("技を選択してください", new Image(getImage().toURI().toString()), 100, () -> {
		}, () -> {
		}, false, scroll);
		rval = Platform.enterNestedEventLoop("tec-select-loop");

		//対象のシャッフル・取得
		Enemy target = Collections_Original.getShuffle(Arrays.asList(targets));

		//メッセージの表示
		message.reText(getName() + "の" + technique.get().getName() + "こうげき!", null, 100, () -> {
		}, () -> Platform.exitNestedEventLoop("b", null));
		Platform.enterNestedEventLoop("b");
		//攻撃
		int damage = 0;
		if (technique.get().getMp() <= 0 || !new HashSet<>(Arrays.stream(items)
				.map(Item::getClass)
				.toList())
				.containsAll(technique.get().getNeedItems())) {
			message.reText(getName() + "はこうげきにしっぱいした!", null, 100, () -> {}, () -> Platform.exitNestedEventLoop("b", null));
			Platform.enterNestedEventLoop("b");
		} else {
			try {
				technique.get().setMp(technique.get().getMp() - 1);
				target.removeHp(technique.get().getDamage());
				damage = technique.get().getDamage();
			} catch (IllegalArgumentException e) {
				damage = target.getHp();
				target.removeHp(target.getHp());
			}
			message.reText(getName() + "は" + target.getName() + "に" + technique.get().getDamage() + "ダメージを与えた!", null, 100, () -> {
			}, () -> Platform.exitNestedEventLoop("b", null));
			Platform.enterNestedEventLoop("b");
		}
		onExit.run();
		return damage;
	}

	/**
	 * 攻撃をする。
	 *
	 * @param targets 対象(複数指定可)
	 * @param message メッセージインスタンス
	 * @return 与えたダメージ
	 */
	public int attack(Message message, Enemy... targets) {
		return attack(true, message, () -> {}, targets);
	}

	@Override
	public Object clone() {
		super.clone();
		Udon udon = new Udon(getName());
		udon.setHp(getHp());
		udon.setImage(new File(getImage().toString()));
		udon.setLevel(getLevel());
		udon.setX(getX());
		udon.setY(getY());
		return udon;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Udon{");
		sb.append("xp=").append(xp.get());
		sb.append(", level=").append(level.get());
		sb.append(", name='").append(getName()).append('\'');
		sb.append(", tenkasu=").append(tenkasu);
		sb.append(", facing=").append(facing);
		sb.append(", hp=").append(getHp());
		sb.append('}');
		return sb.toString();
	}

	@Override
	public void stepX(double step) {
		if (step < 0) {
			setImage(new File(PathConsts.TEXTURE_DIR.getPath() + "udon3.png"));
		} else {
			setImage(new File(PathConsts.TEXTURE_DIR.getPath() + "udon2.png"));
		}
		super.stepX(step);
	}

	@Override
	public void stepY(double step) {
		if (step < 0) {
			setImage(new File(PathConsts.TEXTURE_DIR.getPath() + "udon4.png"));
		} else {
			if (++stepFrameCount > 40 &&stepFrameCount <= 80) {
				setImage(new File(PathConsts.TEXTURE_DIR.getPath() + "udon1_step.png"));
				if (stepFrameCount == 80) {
					stepFrameCount = 0;
				}
			} else {
				setImage(new File(PathConsts.TEXTURE_DIR.getPath() + "udon1.png"));
			}
		}
		super.stepY(step);
	}

	public int getTenkasu() {
		return tenkasu;
	}

	public void setTenkasu(int tenkasu) {
		this.tenkasu = tenkasu;
	}

	public boolean isDash() {
		return isDash;
	}

	public void setDash(boolean dash) {
		isDash = dash;
	}

	public int getXP() {
		return xp.get();
	}

	public void setXp(int xp) {
		this.xp.set(xp);
	}

	public void addXP(int xp) {
		this.xp.set((int) (this.xp.get() + (xp)));
		if (this.xp.get() > level.get() * 1000) {
			this.xp.set((int) (this.xp.get() - (level.get() * 1000)));
			level.set((int) (level.get() + (1)));
		}
	}

	public void removeXP(int xp) {
		ValueChecks.checkOnNotNegative(xp, "減算するXPが不正です");
		this.xp.set((int) (this.xp.get() - (xp)));
	}

	public SimpleIntegerProperty xpProperty() {
		return xp;
	}

	public SimpleIntegerProperty levelProperty() {
		return level;
	}

	public Item[] getItems() {
		return items;
	}

	public Tool[] getTools() {
		return tools;
	}
}
