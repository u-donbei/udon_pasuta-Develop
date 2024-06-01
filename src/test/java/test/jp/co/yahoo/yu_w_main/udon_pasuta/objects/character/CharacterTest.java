/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package test.jp.co.yahoo.yu_w_main.udon_pasuta.objects.character;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.GameObject;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Character;
import org.junit.jupiter.api.Test;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest extends Application {
    private Character test;

    public void Characterがインスタンス化できること() {
        testInit();
        //初期値のテスト
        assertEquals(test.getHp(), 10);
    }

    public void hpのテスト() {
        testInit();

        //正常系:30をセットできるか
        test.setHp(30);
        assertEquals(test.getHp(), 30);

        //正常系:0をセットできるか
        test.setHp(0);
        assertEquals(test.getHp(), 0);

        //異常系:-1を設定したら例外が起こるべき
        assertThrows(IllegalArgumentException.class, () -> {
            test.setHp(-1);
        });

        //addHp()
        test.setHp(30);

        //正常系:1を追加できるか
        test.addHp(1);
        assertEquals(test.getHp(), 31);

        //異常系:-1を追加したら例外が起こるべき
        assertThrows(IllegalArgumentException.class, () -> {
            test.addHp(-1);
        });

        //removeHp()
        test.setHp(30);

        //正常系:1を減らせるか
        test.removeHp(1);
        assertEquals(test.getHp(), 29);

        //異常系:-1を減らしたら例外が起こるべき
        assertThrows(IllegalArgumentException.class, () -> {
            test.removeHp(-1);
        });

        //異常系:現在のhp(31)よりも大きい数を減らしたら例外が起こるべき
        assertThrows(IllegalArgumentException.class, () -> {
            test.removeHp(32);
        });
    }

    public void cloneのテスト() {
        testInit();
        assertEquals(test, test.clone());
    }

    public void hashcodeのテスト() {
        testInit();
        assertEquals(test.hashCode(), test.clone().hashCode());
    }

    private void testInit() {
        test = new Character(new File("")) {
        };
    }


    @Override
    public void start(Stage stage) throws Exception {
        Characterがインスタンス化できること();
        hpのテスト();
        cloneのテスト();
        hashcodeのテスト();
        Platform.exit();
    }
}