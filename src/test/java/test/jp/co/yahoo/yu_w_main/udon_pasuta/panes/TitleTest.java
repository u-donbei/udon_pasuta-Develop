/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package test.jp.co.yahoo.yu_w_main.udon_pasuta.panes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jp.co.yahoo.yu_w_main.udon_pasuta.panes.Inventory;
import jp.co.yahoo.yu_w_main.udon_pasuta.panes.TitlePane;

public class TitleTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        TitlePane titlePane = new TitlePane();
        Scene mainScene = new Scene(titlePane, 800, 500);
        stage.setTitle("Inventoryのテスト");
        stage.setScene(mainScene);
        stage.show();
    }
}
