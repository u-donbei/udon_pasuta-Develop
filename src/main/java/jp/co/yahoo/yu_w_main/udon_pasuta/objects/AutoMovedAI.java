/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects;
/**自動的に動く*/
public interface AutoMovedAI {
    /**動く。
     *
     * @param process その時だけ実行したいこと*/
    void move(Runnable process);
}
