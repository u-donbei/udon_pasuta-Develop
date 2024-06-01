/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.food;

import javafx.scene.control.Button;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Party;
import jp.co.yahoo.yu_w_main.udon_pasuta.font.Fonts;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;

import java.nio.file.Paths;

public class KizamiNori extends Food {
    public KizamiNori() {
        super(Paths.get(PathConsts.ITEM_MODEL_DIR + "testItem.json"), 4, Udon.class);
    }

    public void use(Party<Udon> targets) {
        if ((targets.getLeader().getHp() + 1 <= Udon.getByLevelMaxHP(targets.getLeader().getLevel()))) {
            targets.getLeader().addHp(1);
        }
    }

    @Override
    public void preUse(Message message, Party<Udon> targets, Runnable exited) {
        Button use = new Button("食べる");
        use.setStyle("""
				-fx-background-color: transparent;
				-fx-text-fill: white;
				""");
        use.setFont(Fonts.getPixelMplus12(12));
        use.setOnAction(e -> {
            use(targets);
            message.exit(() -> {});
            exited.run();
        });
        message.reText(getName() + ":" + getDescription(), null, 50, () -> {
        }, () -> {}, true, use);
    }
}
