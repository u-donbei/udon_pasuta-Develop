package jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.food;

import javafx.scene.control.Button;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Party;
import jp.co.yahoo.yu_w_main.udon_pasuta.font.Fonts;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Character;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.Paths;

public class DaikonOroshi extends Food {

	public DaikonOroshi() {
		super(Paths.get(PathConsts.ITEM_MODEL_DIR + "daikonOroshi.json"), 5, Udon.class);
	}

	@Override
	public void use(Party<Udon> targets) {
		Udon udon = targets.getLeader();
		if ((udon.getHp() + 5 <= Udon.getByLevelMaxHP(udon.getLevel()))) {
			targets.getLeader().addHp(5);
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
