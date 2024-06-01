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

public class Carbonara extends MeatSauce_Big {
    /**
     * 技
     */
    private static final ObservableList<Technique> ORIGINAL_TECHNIQUES = FXCollections.observableArrayList(new Technique("たまご", 25, Double.POSITIVE_INFINITY)
            , new Technique("しるをとばす", 29, Double.POSITIVE_INFINITY)
            , new Technique("さいきょう", 40, Double.POSITIVE_INFINITY));
    /**画像*/
    private static final File IMAGE = new File(PathConsts.IMAGE_DIR.getPath() + "carbo.png");

    /**
     * コンストラクタ。
     */
    public Carbonara(){
        super();
        setImage(IMAGE);
        setHp(100);
        getTechniques().addAll(ORIGINAL_TECHNIQUES);
    }

    @Override
    public int attack(Message message,  Character... targets) {
        return super.attack(message, targets);
    }
}
