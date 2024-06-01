/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.panes;

import javafx.application.Platform;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.StatusMessage;
import jp.co.yahoo.yu_w_main.udon_pasuta.logging.Logging;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;
import jp.co.yahoo.yu_w_main.udon_pasuta.variables.Variable;

import java.io.IOException;
import java.util.ArrayList;

/** ゲーム全体のペイン。*/
public class GamePane extends Pane {
    private static GamePane instance;
    /**ステータスメッセージ*/
    private StatusMessage message;
    /**ゲーム本体のペイン*/
    private GameViewPane gameViewPane;
    /**コンストラクタ。*/
    public GamePane(Udon udon){
        instance = this;
        Platform.runLater(() -> {
            gameViewPane = new GameViewPane();
            try {
                message = new StatusMessage(udon);
            } catch (IOException e) {
                Logging.LOGGER.error("An error has occurred", e);
            }
            message.setLayoutY(500);
            message.setPrefHeight(190);
            message.setPrefWidth(790);
            getChildren().add(gameViewPane);
            getChildren().add(message);
        });
        Variable.IS_LOAD.set(true);
    }

    public StatusMessage getMessage() {
        return message;
    }

    public GameViewPane getGameViewPane() {
        return gameViewPane;
    }

    public static GamePane getInstance() {
        return instance;
    }
}
