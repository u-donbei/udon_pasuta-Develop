package jp.co.yahoo.yu_w_main.udon_pasuta.objects.item;

import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Party;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Character;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.food.Food;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Daikon extends Item {
	public Daikon() {
		super(Paths.get(PathConsts.ITEM_MODEL_DIR + "daikon.json"));
	}

	@Override
	public void use(Party<Udon> targets) {

	}

	@Override
	public void preUse(Message message, Party<Udon> targets, Runnable exited) {
		super.preUse(message, targets, exited);
	}
}
