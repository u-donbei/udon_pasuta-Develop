/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.dto;

import jp.co.yahoo.yu_w_main.udon_pasuta.misc.ValueChecks;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.GameObject;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Character;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Item;
import org.w3c.dom.Attr;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 技を表すクラス。
 *
 * @author 渡邉雄宇
 * @version 2.0
 */
public class Technique implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 名前
	 */
	private String name;
	/**
	 * レベル
	 */
	private int level;
	/**
	 * 与えるダメージ
	 * @deprecated 代わりにeffectを使ってください
	 */
	@Deprecated(since = "beta 0.0.2", forRemoval = true)
	private int damage;
	/**
	 * この技の効果
	 */
	private Consumer<Character> effect;
	/**
	 * PP
	 */
	private double pp;
	/**
	 * 必要なアイテムのクラスのリスト。
	 * 不変です。
	 *
	 * @deprecated 代わりに{@link #condition}を使ってください
	 */
	@Deprecated(since="beta 0.0.2", forRemoval = true)
	private List<Class<? extends Item>> needItems;
	/**
	 * この技を使う条件
	 */
	private Predicate<Party<?>> condition;
	/**
	 * この技を使うと消費されるもの
	 */
	private Predicate<Party<?>> consume;
	/**
	 * 技の反動
	 */
	private int recoil;
	/**
	 * 命中率
	 */
	private int accuracy;
	/**
	 * 属性
	 */
	private Attribute attribute;

	/**
	 * コンストラクタ。
	 * 代わりに{@link #Technique(String, int, Consumer<Character>, double, Predicate<Party<?>>, Predicate<Party<?>>, int, int, Attribute)}を使ってください。
	 * @param n 名前
	 * @param d 与えるダメージ
	 * @param m MP
	 * @deprecated 代わりにもう一つのコンストラクタを使って下さい。
	 */
	@SafeVarargs
	public Technique(String n, int d, double m, Class<? extends Item>... nItems) {
		ValueChecks.checkString(n, "名前が不正です。");
		ValueChecks.checkNotNegative(d, "与えるダメージが不正です。");
		ValueChecks.checkNotNegative(m, "MPが不正です。");
		setName(n);
		setDamage(d);
		setPP(m);
		needItems = List.of(Objects.requireNonNull(nItems));
	}

	/**
	 * 全てのパラメータを設定するコンストラクタ
	 * @param name
	 * @param level
	 * @param effect
	 * @param pp
	 * @param condition
	 * @param consume
	 * @param recoil
	 * @param accuracy
	 * @param attribute
	 */
	public Technique(String name, int level, Consumer<Character> effect, double pp, Predicate<Party<?>> condition, Predicate<Party<?>> consume, int recoil, int accuracy, Attribute attribute) {
		setName(name);
		setLevel(level);
		setEffect(effect);
		setPP(pp);
		setCondition(condition);
		setConsume(consume);
		setRecoil(recoil);
		setAccuracy(accuracy);
		setAttribute(attribute);
	}

	/**
	 * 技のテキストを生成する。
	 *
	 * @return テキスト
	 */
	public String makeText() {
		return String.format("%s ダメージ:%d PP:" + ((pp == Double.POSITIVE_INFINITY) ? "∞" : "%d"), name, damage, (int) pp);
	}

	@Override
	public String toString() {
		return "Technique [name : " + name + ", damage : " + damage + ", pp : " + (int) pp + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(damage, pp, name);
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
		return damage == other.damage && Double.doubleToLongBits(pp) == Double.doubleToLongBits(other.pp)
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

	public double getPP() {
		return pp;
	}

	public void setPP(double pp) {
		this.pp = pp;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Consumer<Character> getEffect() {
		return effect;
	}

	public void setEffect(Consumer<Character> effect) {
		this.effect = effect;
	}

	public double getPp() {
		return pp;
	}

	public void setPp(double pp) {
		this.pp = pp;
	}

	public void setNeedItems(List<Class<? extends Item>> needItems) {
		this.needItems = needItems;
	}

	public Predicate<Party<?>> getCondition() {
		return condition;
	}

	public void setCondition(Predicate<Party<?>> condition) {
		this.condition = condition;
	}

	public Predicate<Party<?>> getConsume() {
		return consume;
	}

	public void setConsume(Predicate<Party<?>> consume) {
		this.consume = consume;
	}

	public int getRecoil() {
		return recoil;
	}

	public void setRecoil(int recoil) {
		this.recoil = recoil;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	@Deprecated(since = "beta 0.0.2", forRemoval = true)
	public List<Class<? extends Item>> getNeedItems() {
		return needItems;
	}
	public void run(Character target) {
		effect.accept(target);
	}

	public Technique toNewInstance() {
		return new Technique(this.name, 1, t -> {
			t.removeHp(getDamage());
		}, pp, t -> true, t -> true, 0, 100, Attribute.STRIKE);
	}

	public enum Attribute {
		STRIKE,
		PRESS,
		WATER,
		STIMULATION,
		INDIRECT,
		SLASHING,
	}
}
