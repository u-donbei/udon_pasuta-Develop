/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package test.jp.co.yahoo.yu_w_main.udon_pasuta.panes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jp.co.yahoo.yu_w_main.udon_pasuta.panes.Inventory;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class InventoryTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Inventory inventory = new Inventory();
        Scene mainScene = new Scene(inventory, 800, 500);
        stage.setTitle("Inventoryのテスト");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
