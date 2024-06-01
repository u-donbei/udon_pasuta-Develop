/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.character;

import java.io.File;
import java.io.Serial;
import java.util.Objects;

import javafx.beans.property.SimpleIntegerProperty;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.Facing;
import jp.co.yahoo.yu_w_main.udon_pasuta.misc.ValueChecks;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.GameObject;

/**キャラクターを表すクラス。
 * 
 * @author 渡邉雄宇
 * 
 * @version 1.0
 * 
 * @see GameObject
 * */
public abstract class Character extends GameObject {
	private static final long serialVersionUID = 1L;
	/**全てのキャラクター(フィールド上に存在する)*/
	public static final String[] CHARACTERS = {"Udon", "Udon_Ally", "Ebi", "Onigiri", "Soba", "Soumen"};

	public int getHitPriority() {
		return hitPriority;
	}

	public void setHitPriority(int hitPriority) {
		this.hitPriority = hitPriority;
	}

	protected int hitPriority;

	/**
	 * 残り体力
	 */
	private SimpleIntegerProperty hp = new SimpleIntegerProperty(this, "hp");

	/**コンストラクタ。*/
	public Character(File path) {
		super(path);
		setHited(true);
	}

	/**体力を設定する。
	 * 
	 * @param hp 新しい値
	 * 
	 * @exception IllegalArgumentException hpが負の数のとき*/
	public void setHp(int hp) {
		ValueChecks.checkOnNotNegative(hp, "HPが負の数です");
		this.hp.set(hp);
	}

	/**体力を取得する。
	 * 
	 * @return 体力*/
	public int getHp() {
		return hp.get();
	}

	/**体力を加算する。
	 * 
	 * @param hp 加算する値
	 * 
	 * @exception IllegalArgumentException hpが負の数のとき*/
	public void addHp(int hp) {
		ValueChecks.checkOnNotNegative(hp, "HPが負の数です");
		this.hp.set((int) (this.hp.get() + (hp)));
	}

	/**体力を減算する。
	 * hpが負の数または
	 * 
	 * @param hp 減算する値
	 * 
	 * @exception IllegalArgumentException hpが負の数のとき,hpがを減らしたときに負の数になる場合*/
	public void removeHp(int hp) {
		ValueChecks.checkOnNotNegative(hp, "HPが負の数です");
		ValueChecks.checkOnNotNegative(this.hp.get() - hp, "HPを減らせません");
		this.hp.set( (this.hp.get() - (hp)));
	}

	@Override
	public int hashCode() {
		return Objects.hash(hp.get());
	}

	@Override
	public Object clone() {
		Character result = new Character(getImage()) {
			@Serial
			private static final long serialVersionUID = 1L;
		};
		result.setHp(hp.get());
		result.setX(getX());
		result.setY(getY());
		return result;
	}

	@Override
	public String toString() {
		return getClass().getName() + "{" +
			   "hp=" + hp.get() +
			   '}';
	}

	/**キャラクターをX方向に動かす。
	 * @param step 歩数
	 * */
	public void stepX(double step) {
		this.moveX(step);
		if (step < 0) {
			facing = Facing.WEST;
		} else {
			facing = Facing.EAST;
		}
	}

	/**キャラクターをY方向に動かす。
	 * @param step 歩数
	 * */
	public void stepY(double step) {
		this.moveY(step);

		if (step < 0) {
			facing = Facing.NORTH;
		} else {
			facing = Facing.SOUTH;
		}
	}

	public SimpleIntegerProperty hpProperty() {
		return hp;
	}
}
