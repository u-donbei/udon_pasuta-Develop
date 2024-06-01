/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.ally;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.AutoMovedAI;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.GameObject;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.action.ActionObject;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Character;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.enemy.Enemy;

import java.io.File;

/**味方(おにぎりくんなど)を表すクラス。
 *
 * @author 渡邉雄宇
 *
 * @see GameObject
 *
 * @see ActionObject*/
public abstract class Ally extends Character implements ActionObject, AutoMovedAI {
    private final SimpleBooleanProperty isAlive;

    /**
     * 画像を設定するコンストラクタ
     *
     * @param image 画像パス
     */
    public Ally(File image) {
        super(image);
        hitPriority = 2;
        isAlive =  new SimpleBooleanProperty(this, "isAlive", true);
        System.out.println(isAlive);
    }

    public boolean isAlive() {
        return isAlive.get();
    }

    public void setAlive(boolean alive) {
        isAlive.set(alive);
    }

    @Override
    public void setHp(int hp) {
        super.setHp(hp);
        isAlive.set(hp > 0);   //HPが0以下なら、戦闘不能にする(HPを0以上にすると戻る)
    }

    @Override
    public void removeHp(int hp) {
        super.removeHp(hp);
        isAlive.set(getHp() > 0);
    }

    @Override
    public void addHp(int hp) {
        super.addHp(hp);
        isAlive.set(getHp() > 0);
    }

    /**攻撃をする。
     *
     * @param target 対象。複数指定可。
     *
     * */
    public abstract void attack(Message message, Enemy... target);

    public BooleanProperty aliveProperty() {
        return isAlive;
    }
}
