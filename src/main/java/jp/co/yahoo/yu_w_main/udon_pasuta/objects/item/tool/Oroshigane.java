package jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.tool;

import javafx.scene.control.Button;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Party;
import jp.co.yahoo.yu_w_main.udon_pasuta.font.Fonts;
import jp.co.yahoo.yu_w_main.udon_pasuta.logging.Logging;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.ally.Somen;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Daikon;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Empty;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Item;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.food.DaikonOroshi;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;

public class Oroshigane extends Tool {
	public Oroshigane() {
		super(Paths.get(PathConsts.ITEM_MODEL_DIR + "oroshigane.json"));
	}

	@Override
	public void use(Party<Udon> targets) {
		throw new UnsupportedOperationException();
	}

	public void useDaikon(Udon udon) {
		Item[] items = udon.getItems();
		if (Arrays.stream(items).anyMatch(t -> t instanceof Daikon)) {	//うどんべいのアイテム内に大根があるかどうかを検査する
			int daikonIdx;
			for (int i = 0; i < 10; i++) {
				daikonIdx = new Random().nextInt(10);
				if (items[daikonIdx] instanceof Daikon) {
					items[daikonIdx] = new DaikonOroshi();
				}
			}
		}
	}

	@Override
	public void preUse(Message message, Party<Udon> targets, Runnable exited) {
		Button useD = new Button("大根おろしをつくる");
		Button useW = new Button("わさびをつくる");
		useD.setStyle("""
				-fx-background-color: transparent;
				-fx-text-fill: white;
				""");
		useW.setStyle("""
				-fx-background-color: transparent;
				-fx-text-fill: white;
				""");
		useD.setFont(Fonts.getPixelMplus12(12));
		useW.setFont(Fonts.getPixelMplus12(12));
		useD.setOnAction(e -> {
			useDaikon(targets.getLeader());
			message.exit(() -> {});
		});
		useW.setOnAction(e -> {
			message.reText("わさびに対しては使用できません(わさびが実装されていません...)", null, 50, () -> {}, () -> message.exit(() -> {}), true);
		});
		message.reText(getName() + ':' + getDescription(), null, 50, () -> {
		}, () -> {}, true, useD, useW);
	}
}
