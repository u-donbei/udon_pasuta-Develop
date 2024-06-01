/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.plugin;

import javafx.scene.Scene;
import javafx.stage.Stage;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;
import jp.co.yahoo.yu_w_main.udon_pasuta.panes.GamePane;

import java.util.Objects;

/**
 * プラグインのメインクラス。
 * <b>プラグインのメインクラスは、必ずこのクラスを継承します。(そうでないと実行できません)</b>
 */
public abstract class PluginExecutor {
    private final PluginRunPos pos;
    protected abstract void doGameLoop(Udon udon, GamePane gamepane, Scene scene, Stage window, Scene title);
    protected abstract void doSetup();

    public final void setUp() { doSetup(); }

    public final void gameLoop(Udon udon, GamePane gamepane, Scene scene, Stage window, Scene title) { doGameLoop(udon, gamepane, scene, window, title); }

    public PluginExecutor(PluginRunPos pos) {
        this.pos = Objects.requireNonNull(pos);
    }

    public PluginRunPos getPos() {
        return pos;
    }

    public enum PluginRunPos {
        FIRST,
        AFTER_MOVING_UDON,
        AFTER_KEYBOARD_INPUT,
        AFTER_SCREEN_TRANSITION,
        AFTER_UPDATE,
        AFTER_COLLISION,
        AFTER_CAMERA_MOVE,
        LAST,
    }
}
