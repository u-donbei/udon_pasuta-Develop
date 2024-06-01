/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.enemy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Window;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Technique;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Character;

import java.io.File;
import java.util.Random;

/**巨大ミートソースを表すクラス。
 *
 * @author 渡邉雄宇
 *
 * @see MeatSauce_Fork*/
public class MeatSauce_Big extends MeatSauce_Fork{
    /**技*/
    private static final ObservableList<Technique> TECHNIQUES_ORIGINAL = FXCollections.observableArrayList(new Technique("ふみつける", 18, Double.POSITIVE_INFINITY)
            , new Technique("ひっさつわざ", 25, Double.POSITIVE_INFINITY)
            , new Technique("つぶす", 23, Double.POSITIVE_INFINITY));
    /**画像*/
    private static final File IMAGE = new File(PathConsts.IMAGE_DIR.getPath() + "meat_big.png");

    /**コンストラクタ。*/
    public MeatSauce_Big(){
        super();
        setImage(IMAGE);
        setHp(80);
        getTechniques().addAll(TECHNIQUES_ORIGINAL);
    }

    @Override
    public int attack(Message message, Character... targets) {
        return attack(message, () -> {}, () -> {}, () -> {}, targets);
    }

    @Override
    public int attack(Message message, Runnable onExit, Runnable onFinish, Runnable change, Character... targets) {
        //与えたダメージ
        int damage = 0;

        //50%の確率でランダムとAIを選ぶ
        if(new Random().nextBoolean()){
            damage = aiAttack(message, onExit, onFinish, change, targets);
        } else {
            damage = randomAttack(message, onExit, onFinish, change, targets);
        }

        return damage;
    }
}
