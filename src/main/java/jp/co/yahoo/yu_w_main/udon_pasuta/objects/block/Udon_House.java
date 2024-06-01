/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.block;

import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.action.ActionObject;

import javax.swing.*;
import java.io.File;

/**うどんべいの家を表すクラス。
 *
 * @author 渡邉雄宇
 *
 * @see Block*/
public class Udon_House extends Block implements ActionObject {
    /**ID*/
    public static final int ID = 1;

    /**コンストラクタ。*/
    public Udon_House() {
        super(new File(PathConsts.TEXTURE_DIR.getPath() + "udonHouse.png"));
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public void handle(Runnable process, Message message) {
        if (process != null) {
            process.run();
        }
        System.out.println("Udon_House handle");
    }
}
