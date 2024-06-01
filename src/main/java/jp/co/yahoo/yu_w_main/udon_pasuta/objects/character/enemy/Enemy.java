/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.enemy;

import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Window;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Technique;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.AutoMovedAI;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.action.ActionObject;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Character;

/**敵を表すクラス。
 * 
 * @author 渡邉雄宇
 * 
 * @version 1.0
 * 
 * @see Character*/
public abstract class Enemy extends Character implements AutoMovedAI, ActionObject {
	
	private static final long serialVersionUID = 1L;
	/**技*/
	private  ObservableList<Technique> techniques;
	/**
	 * 倒したときに得られるXP
	 */
	private int xp;
	
	/**コンストラクタ。
	 * 
	 * @param path 画像パス
	 *
	 * @param techniques 技*/
	public Enemy(File path, Technique... techniques) {
		this(path, 0, techniques);
	}

	/**
	 * コンストラクタ。
	 * @param path 画像パス
	 * @param xp 倒した時のXP
	 * @param techniques 技
	 */
	public Enemy (File path, int xp, Technique... techniques) {
		super(path);
		this.techniques = FXCollections.observableArrayList(techniques);
		setXp(xp);
		hitPriority = 1;
	}

	/**技を取得する。
	 * 
	 * @return 技*/
	public ObservableList<Technique> getTechniques() {
		return techniques;
	}
	
	/**攻撃をする。
	 * 
	 * @param targets 対象(複数指定可)
	 *
	 * @param  message メッセージの表示先
	 * 
	 * @return 与えたダメージ*/
	public abstract int attack(Message message, Character...targets);
	public int attack(Message message, Runnable onExit, Character ...targets) {
		return 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(techniques);
	}

	@Override
	public String toString() {
		return "Enemy{" +
				"techniques=" + techniques +
				", hp=" + getHp() +
				'}';
	}

	@Override
	protected void doUpdate() {
		move(() -> { });
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public abstract int attack(Message message, Runnable onExit, Runnable onFinish, Character... targets);

	public abstract int attack(Message message, Runnable onExit, Runnable onFinish, Runnable change, Character... targets);
}
