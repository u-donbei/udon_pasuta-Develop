/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.dto;

import jp.co.yahoo.yu_w_main.udon_pasuta.misc.ValueChecks;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 技を表すクラス。
 *
 * @author 渡邉雄宇
 * @version 1.0
 */
public class Technique implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 名前
	 */
	private String name;
	/**
	 * 与えるダメージ
	 */
	private int damage;
	/**
	 * MP
	 */
	private double mp;
	/**
	 * 必要なアイテムのクラスのリスト。
	 * 不変です。
	 */
	private List<Class<? extends Item>> needItems;

	/**
	 * デフォルトコンストラクタ
	 */
	public Technique() {
		setName("技");
	}

	/**
	 * コンストラクタ。
	 *
	 * @param n 名前
	 * @param d 与えるダメージ
	 * @param m MP
	 */
	@SafeVarargs
	public Technique(String n, int d, double m, Class<? extends Item>... nItems) {
		ValueChecks.checkOnString(n, "名前が不正です。");
		ValueChecks.checkOnNotNegative(d, "与えるダメージが不正です。");
		ValueChecks.checkOnNotNegative(m, "MPが不正です。");
		setName(n);
		setDamage(d);
		setMp(m);
		needItems = List.of(Objects.requireNonNull(nItems));
	}

	/**
	 * 技のテキストを生成する。
	 *
	 * @return テキスト
	 */
	public String makeText() {
		return String.format("%s ダメージ:%d MP:" + ((mp == Double.POSITIVE_INFINITY) ? "∞" : "%d"), name, damage, (int) mp);
	}

	@Override
	public String toString() {
		return "Technique [name : " + name + ", damage : " + damage + ", mp : " + (int) mp + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(damage, mp, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Technique other = (Technique) obj;
		return damage == other.damage && Double.doubleToLongBits(mp) == Double.doubleToLongBits(other.mp)
			   && Objects.equals(name, other.name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public double getMp() {
		return mp;
	}

	public void setMp(double mp) {
		this.mp = mp;
	}

	public List<Class<? extends Item>> getNeedItems() {
		return needItems;
	}
}
