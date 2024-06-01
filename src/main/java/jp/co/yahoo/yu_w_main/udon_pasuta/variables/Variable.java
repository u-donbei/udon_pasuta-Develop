/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.variables;

import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.enemy.Enemy;
import jp.co.yahoo.yu_w_main.udon_pasuta.plugin.PluginExecutor;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Variable {
    public static Object NESTED_LOOP_RVAL;
    public static final AtomicBoolean IS_LOAD = new AtomicBoolean(false), IS_RESTART = new AtomicBoolean(false), IS_MESSAGE_EXIT = new AtomicBoolean(false), IS_TEC_EXIT = new AtomicBoolean(false), IS_MSG_FINISH = new AtomicBoolean(false);
    public static boolean IS_GAME_EXIT = false, IS_MENU, IS_BACK, IS_SETTING, IS_BATTLE, IS_ERR;
    public static ArrayList<PluginExecutor> plugins = new ArrayList<>();
    public static Enemy BATTLE_TARGET;
}
