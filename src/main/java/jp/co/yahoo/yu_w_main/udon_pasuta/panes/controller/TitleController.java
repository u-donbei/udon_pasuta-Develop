/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.panes.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BorderPane;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.font.Fonts;
import jp.co.yahoo.yu_w_main.udon_pasuta.logging.Logging;


public class TitleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exit;

    @FXML
    private BorderPane pane;

    @FXML
    public Button setting;

    @FXML
    public Button start;


    public static final AtomicBoolean isGameStart = new AtomicBoolean(false);

    @FXML
    void initialize() {
        exit.setFont(Fonts.getPixelMplus12(50));
        setting.setFont(Fonts.getPixelMplus12(50));
        start.setFont(Fonts.getPixelMplus12(50));

        pane.setBackground(new Background(new BackgroundImage(new Image(PathConsts.TEXTURE_DIR.makeURI() + "title_logo.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT , null, null)));
    }

    @FXML
    public void handleStart(ActionEvent e){
        System.out.println("start");
        isGameStart.set(true);
    }

    @FXML
    public void handleExit(ActionEvent e){
        Logging.LOGGER.debug("APPLICATION EXIT.");
        System.exit(0);
    }

    @FXML
    public void handleSetting(ActionEvent e){

    }
}
