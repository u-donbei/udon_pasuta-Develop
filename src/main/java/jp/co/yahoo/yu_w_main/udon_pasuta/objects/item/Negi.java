/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.item;

import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Party;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;

import java.nio.file.Paths;

public class Negi extends Item {
    public Negi() {
        super(Paths.get(PathConsts.ITEM_MODEL_DIR + "negi.json"));
    }

    @Override
    public void use(Party<Udon> targets) {

    }

    @Override
    public void preUse(Message message, Party<Udon> targets, Runnable exited) {
        super.preUse(message, targets, exited);
    }
}
