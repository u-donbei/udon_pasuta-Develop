/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package test.jp.co.yahoo.yu_w_main.udon_pasuta.plugins;

import javafx.application.Application;
import javafx.stage.Stage;
import jp.co.yahoo.yu_w_main.udon_pasuta.plugin.PluginExecutor;
import jp.co.yahoo.yu_w_main.udon_pasuta.plugin.PluginLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PluginTest {
    public static void main(String[] args) {
        try {
            PluginExecutor executor = PluginLoader.getExecutor("test plugin");

            executor.setUp();
            executor.gameLoop(null, null, null, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
