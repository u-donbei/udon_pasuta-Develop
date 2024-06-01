/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.panes;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;

import java.io.IOException;
import java.net.URI;

public class LoadingPane extends Pane {
    public LoadingPane(){
        try {
            getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/loading.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
