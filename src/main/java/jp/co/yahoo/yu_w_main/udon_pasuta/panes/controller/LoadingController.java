/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.panes.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import jp.co.yahoo.yu_w_main.udon_pasuta.font.Fonts;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController implements Initializable {

    @FXML
    private Label loading;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loading.setFont(Fonts.getPixelMplus12(30));
    }
}
