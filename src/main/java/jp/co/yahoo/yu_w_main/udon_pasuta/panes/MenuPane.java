/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.panes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MenuPane extends Pane {
    public MenuPane() {
        try {
            getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/menu.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
