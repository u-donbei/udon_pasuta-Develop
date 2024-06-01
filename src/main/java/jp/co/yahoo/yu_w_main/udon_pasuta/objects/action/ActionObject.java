/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.action;

import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;

/**「アクションがあること」を表すインタフェース
 * 
 * @author 渡邉雄宇
 * 
 * @version 1.0*/
public interface ActionObject {
	/**アクションが行われるときに呼び出される。
	 * 
	 * @param process 処理内容。クラス固有の処理が優先されます。
	 *
	 * @param message メッセージ
	 *
	 * @implSpec 処理内容が次のようになっていること <br>
	 * {@snippet lang = java id = tasks:
	 * //クラス固有の処理
	 * process.run();
	 * }
	 * */
    void handle(Runnable process, Message message);
}
