/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.item;

import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Party;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;

import java.nio.file.Paths;

public class Empty extends Item{
    public Empty() {
        super(Paths.get(PathConsts.ITEM_MODEL_DIR.getPath() + "empty.json"));
    }

    @Override
    public void use(Party<Udon> targets) {

    }
}
