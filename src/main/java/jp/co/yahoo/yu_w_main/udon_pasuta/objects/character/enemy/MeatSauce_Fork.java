/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.enemy;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Window;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Technique;
import jp.co.yahoo.yu_w_main.udon_pasuta.misc.Collections_Original;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Character;
import jp.co.yahoo.yu_w_main.udon_pasuta.variables.Variable;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**フォーク持ちミートソースを表すクラス。
 *
 * @author 渡邉雄宇
 *
 * @see MeatSauce*/
public class MeatSauce_Fork extends MeatSauce {

    /**
     * フォーク持ちミートソースの技
     */
    private static final ObservableList<Technique> TECHNIQUES_ORIGINAL = FXCollections.observableArrayList(new Technique("フォークをさす", 15, Double.POSITIVE_INFINITY)
            , new Technique("フォークでなげる", 10, Double.POSITIVE_INFINITY));

    /**画像*/
    public static final File IMAGE = Paths.get(PathConsts.TEXTURE_DIR.getPath() + "meat_fork.png").toFile();

    @SuppressWarnings("unchecked")
    /**コンストラクタ。*/
    public MeatSauce_Fork() {
        super();
        setImage(IMAGE);
        getTechniques().addAll(TECHNIQUES_ORIGINAL);
        setHp(30);
        setName("フォーク持ちミートソース");
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

    protected int randomAttack(Message message, Runnable onExit, Runnable onFinish, Runnable change, Character... targets){
        return super.attack(message, onExit, onFinish, change, targets);
    }

    protected int aiAttack(Message message, Runnable onExit, Runnable onFinish, Runnable change, Character... targets){
        List<Character> t = Arrays.asList(targets);

        Collections.shuffle(t);
        Character target = t.getFirst();

        //与えたダメージ
        int damage = 0;

        //対象のHPによって技を変える
        List<Technique> techniques;
        if(target.getHp() <= 10){
            techniques = getTechniques().stream()
                    .filter(te -> te.getDamage() <= 10)
                    .collect(Collectors.toList());

        } else {
            techniques = getTechniques().stream()
                    .filter(te -> te.getDamage() >= 10)
                    .collect(Collectors.toList());
        }
        Collections.shuffle(techniques);
        damage = techniques.getFirst().getDamage();
        Technique tec = techniques.getFirst();


        message.reText("フォーク持ちミートソースの" + tec.getName() + "こうげき!", null, 100, () -> {
            try {
                target.removeHp(tec.getDamage());
            } catch (IllegalArgumentException e) {
                target.removeHp(target.getHp());
            }
            change.run();
            Variable.IS_MSG_FINISH.set(true);
        }, () -> {
            Platform.exitNestedEventLoop("tec-loop", null);
        }, true);
        Platform.enterNestedEventLoop("tec-loop");

        message.reText(target.getName() + "は" + tec.getDamage() + "ダメージを受けた", null, 100, () -> Variable.IS_MSG_FINISH.set(true), () -> {
            onFinish.run();
            Platform.exitNestedEventLoop("tec-loop", null);
        }, true);
        Platform.enterNestedEventLoop("tec-loop");

        return tec.getDamage();
    }

    @Override
    public void move(Runnable process) {
        super.move(process);
    }

    @Override
    public void handle(Runnable process, Message message) {
        super.handleImpl(process, message, "フォーク持ちミートソース");
    }
}
