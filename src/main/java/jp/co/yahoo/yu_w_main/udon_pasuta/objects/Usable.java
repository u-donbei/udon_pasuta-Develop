/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects;

import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Party;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;

/**使用できることを表すインタフェース*/
public interface Usable {
	void use(Party<Udon> targets);
	default void preUse(Message message, Party<Udon> targets, Runnable exited) {

	}
}
