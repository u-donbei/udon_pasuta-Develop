/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package test.jp.co.yahoo.yu_w_main.udon_pasuta.plugins.texture;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.block.field.Ground;
import jp.co.yahoo.yu_w_main.udon_pasuta.plugin.texture.Texture;

public class TextureTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Texture.setTexture("テスト");
        PathConsts.reloadTexture();
        Ground test = new Ground();

        StackPane pane = new StackPane(test.getImageView());

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
