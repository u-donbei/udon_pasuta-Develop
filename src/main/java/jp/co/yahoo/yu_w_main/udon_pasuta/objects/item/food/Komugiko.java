/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.food;

import javafx.scene.control.Button;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Party;
import jp.co.yahoo.yu_w_main.udon_pasuta.font.Fonts;
import jp.co.yahoo.yu_w_main.udon_pasuta.logging.Logging;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.ally.Ebi;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.ally.Somen;

import java.nio.file.Paths;
import java.util.NoSuchElementException;

public class Komugiko extends Food {
    /**
     * コンストラクタ。
     */
    public Komugiko() {
        super(Paths.get(PathConsts.ITEM_MODEL_DIR.getPath() + "komugiko.json"), 10, Udon.class, Ebi.class, Somen.class);
    }

    @Override
    public void use(Party<Udon> targets) {
        Udon udon = targets.getLeader();
        if ((udon.getHp() + 10 <= Udon.getByLevelMaxHP(udon.getLevel()))) {
            targets.getLeader().addHp(10);
        } else {
            udon.addHp(Udon.getByLevelMaxHP(udon.getLevel()) - udon.getHp());
        }
    }

    public void useS(Somen somen) {
        if ((somen.getHp() < 40)) {
            somen.addHp(1);
        }
    }

    @Override
    public void preUse(Message message, Party<Udon> targets, Runnable exited) {
        Button use = new Button("食べる");
        Button somenUse = new Button("そうめんさんが食べる");
        use.setStyle("""
				-fx-background-color: transparent;
				-fx-text-fill: white;
				""");
        somenUse.setStyle("""
				-fx-background-color: transparent;
				-fx-text-fill: white;
				""");
        use.setFont(Fonts.getPixelMplus12(12));
        somenUse.setFont(Fonts.getPixelMplus12(12));
        use.setOnAction(e -> {
            use(targets);
            message.exit(() -> {});
            exited.run();
        });
        somenUse.setOnAction(e -> {
            try {
                useS((Somen) targets.members().stream().filter(t -> t instanceof Somen).limit(1).toList().getFirst());
            } catch (NoSuchElementException e1) {
                Logging.LOGGER.debug("An minor exception has occurred", e1);
            }
            message.exit(() -> {});
            exited.run();
        });
        message.reText(getName() + ':' + getDescription(), null, 50, () -> {
        }, () -> {}, true, use, somenUse);
    }
}
