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
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.ally.Onigiri;

import java.nio.file.Paths;

public class Nori extends Food {
    public Nori() {
        super(Paths.get(PathConsts.ITEM_MODEL_DIR + "nori.json"), 3, Onigiri.class);
    }

    public void use(Party<Udon> targets) {
        Onigiri onigiri = (Onigiri) targets.members().stream().filter(t -> t instanceof Onigiri)
                .toList()
                .getFirst();
        if (onigiri.getHp() + 5 < 20) {
            targets.getLeader().addHp(5);
        } else {
            onigiri.addHp(20 - onigiri.getHp());
        }
    }

    @Override
    public void preUse(Message message, Party<Udon> targets, Runnable exited) {
        Button use = new Button("おにぎりくんが食べる");
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
