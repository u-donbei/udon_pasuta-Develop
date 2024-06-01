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

public class Agedama extends Food {
	public Agedama() {
		super(Paths.get(PathConsts.ITEM_MODEL_DIR.getPath() + "agedama.json"), 5, Udon.class);
	}

	@Override
	public void use(Party<Udon> targets) {
		Udon udon = targets.getLeader();
		if ((udon.getHp() + 8 <= Udon.getByLevelMaxHP(udon.getLevel()))) {
			targets.getLeader().addHp(8);
		} else {
			udon.addHp(Udon.getByLevelMaxHP(udon.getLevel()) - udon.getHp());
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
