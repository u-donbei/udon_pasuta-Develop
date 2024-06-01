/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.panes.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import jp.co.yahoo.yu_w_main.udon_pasuta.font.Fonts;
import jp.co.yahoo.yu_w_main.udon_pasuta.logging.Logging;
import jp.co.yahoo.yu_w_main.udon_pasuta.variables.Variable;

import java.net.URL;
import java.util.ResourceBundle;

import static jp.co.yahoo.yu_w_main.udon_pasuta.logging.Logging.LOGGER;

public class MenuController implements Initializable {

    @FXML
    private Button back;

    @FXML
    private VBox box;

    @FXML
    private Button exit;

    @FXML
    private Button setting;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        back.setFont(Fonts.getPixelMplus12(16));
        exit.setFont(Fonts.getPixelMplus12(16));
        setting.setFont(Fonts.getPixelMplus12(16));
        back.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(t1) {
                back.setText(back.getText() + "◀");
            } else {
                StringBuilder builder = new StringBuilder(back.getText());

                if(builder.toString().matches(".*◀")) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                back.setText(builder.toString());
            }
        });

        exit.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(t1) {
                exit.setText(exit.getText() + "◀");
            } else {
                StringBuilder builder = new StringBuilder(exit.getText());

                if(builder.toString().matches(".*◀")) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                exit.setText(builder.toString());
            }
        });

        setting.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(t1) {
                setting.setText(setting.getText() + "◀");
            } else {
                StringBuilder builder = new StringBuilder(setting.getText());

                if(builder.toString().matches(".*◀")) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                setting.setText(builder.toString());
            }
        });
    }

    @FXML
    public void onBackKeyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER, SPACE -> Variable.IS_BACK = true;
        }
    }

    @FXML
    public void onExitKeyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER, SPACE -> {
                Variable.IS_GAME_EXIT = true;
                LOGGER.debug("GAME EXIT.");
            }
        }
    }

    @FXML
    public void onSettingKeyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER, SPACE -> {
                LOGGER.debug("Setting pressed.");
                Variable.IS_SETTING = true;
            }
        }
    }
}
