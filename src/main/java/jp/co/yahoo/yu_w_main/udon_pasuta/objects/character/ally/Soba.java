/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.ally;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Party;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Technique;
import jp.co.yahoo.yu_w_main.udon_pasuta.managers.GameManager;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Character;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.enemy.Enemy;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**そばくんを表すクラス。
 *
 * @author 渡邉雄宇 */
public class Soba extends Ally{
    /**画像*/
    private static final File IMAGE = new File(PathConsts.TEXTURE_DIR.getPath() + "soba.png");
    /**技*/
    private static final ObservableList<Technique> ORIGINAL_TECHNIQUES =
            FXCollections.observableArrayList(
                    new Technique("たたく", 6, Double.POSITIVE_INFINITY),
                    new Technique("ぐのしる", 11, Double.POSITIVE_INFINITY));

    /**コンストラクタ。*/
    public Soba(){
        super(IMAGE);
        setName("そばくん");
    }

    @Override
    public void handle(Runnable run, Message message) {

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
        message.reText("そばくんの" + technique.getName() + "攻撃!" ,null, 0.1);

        //攻撃
        try {
            targetE.removeHp(technique.getDamage());
        } catch (IllegalArgumentException e){
            targetE.removeHp(targetE.getHp());
        }

    }
    @Override
    public void move(Runnable process) {
        //うどんべいについていく
        Party<Udon> party = GameManager.getParty();
        Character main;
        if (party.members().getFirst() instanceof Onigiri) {
			main = party.members().getFirst();
        } else {
			main = party.getLeader();
		}

        //もし、パーティに参加していないならreturn
        if (!party.members().contains(this)) return;

        if (getX() < main.getX() && getX() < main.getX() - 80) {
            stepX(3);
        } else if (getX() > main.getX() && getX() > main.getX() + 80) {
            stepX(-3);
        }

        if (getY() < main.getY() && getY() < main.getY() - 80) {
            stepY(3);
        } else if (getY() > main.getY() && getY() > main.getY() + 80) {
            stepY(-3);
        }
    }

    @Override
    protected void doUpdate() {
        move(null);
    }
}
