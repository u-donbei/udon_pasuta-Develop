/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.plugin;

import jp.co.yahoo.yu_w_main.udon_pasuta.objects.GameObject;

public interface Register<T extends GameObject> {
    void register(T element);
}
