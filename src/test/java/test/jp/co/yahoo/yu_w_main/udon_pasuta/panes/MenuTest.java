/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package test.jp.co.yahoo.yu_w_main.udon_pasuta.panes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jp.co.yahoo.yu_w_main.udon_pasuta.logging.Logging;
import jp.co.yahoo.yu_w_main.udon_pasuta.panes.MenuPane;

public class MenuTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        MenuPane menuPane = new MenuPane();

        Scene scene = new Scene(menuPane);
        stage.setScene(scene);
        stage.setTitle("MenuTest");
        stage.show();
    }
}
