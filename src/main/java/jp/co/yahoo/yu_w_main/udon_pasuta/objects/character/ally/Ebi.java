/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.ally;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Technique;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.enemy.Enemy;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**えび天さんを表すクラス。
 *
 * @author 渡邉雄宇
 *
 * @see Ally*/
public class Ebi extends Ally{

    /**画像*/
    private static final File IMAGE = new File(PathConsts.IMAGE_DIR.getPath() + "ebi.png");

    /**技*/
    private static final ObservableList<Technique> ORIGINAL_TECHNIQUES = FXCollections.observableArrayList(new Technique("たたく", 6, Double.POSITIVE_INFINITY), new Technique("ころも", 16, Double.POSITIVE_INFINITY));

    /**コンストラクタ。*/
    public Ebi() {
        super(IMAGE);
        setHp(50);

    }

    @Override
    public void attack(Message message, Enemy... target) {
        //技の選択
        Collections.shuffle(ORIGINAL_TECHNIQUES);
        Technique technique = ORIGINAL_TECHNIQUES.get(0);

        //ランダムに対象を選ぶ
        List<Enemy> enemies = Arrays.asList(target);
        Collections.shuffle(enemies);
        Enemy targetE = enemies.get(0);

        //メッセージの表示
        message.reText("えび天さんの" + technique.getName() + "攻撃!" ,null, 0.1);

        //攻撃
        try {
            targetE.removeHp(technique.getDamage());
        } catch (IllegalArgumentException e){
            targetE.removeHp(targetE.getHp());
        }

    }

    @Override
    public void move(Runnable process) {

    }

    @Override
    public void handle(Runnable process, Message message) {

    }
}
