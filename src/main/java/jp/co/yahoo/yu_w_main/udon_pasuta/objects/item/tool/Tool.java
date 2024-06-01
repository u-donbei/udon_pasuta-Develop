/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.tool;

import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Party;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Item;

import java.nio.file.Path;

public abstract class Tool extends Item {
    /**
     * コンストラクタ。
     *
     * @param model
     */
    public Tool(Path model) {
        super(model);
    }

    @Override
    public void preUse(Message message, Party<Udon> targets, Runnable exited) {
        super.preUse(message, targets, exited);
    }
}
