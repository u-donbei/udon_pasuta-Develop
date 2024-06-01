/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.panes;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.managers.BGMManager;
import jp.co.yahoo.yu_w_main.udon_pasuta.variables.Variable;

import java.net.URI;

public class SettingPane extends BorderPane {
    private Button sounds;
    private Button back;
    public SettingPane(Window owner) {
        sounds = new Button("音声・BGM");
        back = new Button("戻る");
        back.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        sounds.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        SoundsDialog sDialog = new SoundsDialog(owner);
        VBox contents = new VBox(sounds);
        contents.setAlignment(Pos.CENTER);
        getStylesheets().add(URI.create(PathConsts.APP_DIR.makeURI() + "/css/title.css").toString());
        setCenter(contents);
        setTop(back);
        setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        back.setOnAction(e -> Variable.IS_BACK = true);
        sounds.setOnAction(e -> {
            sDialog.showAndWait();
        });
    }

    private static class SoundsDialog extends Stage {
        private Slider bgmS;
        private Label bgmL;
        public SoundsDialog(Window owner) {
            setHeight(400);
            setWidth(500);
            BorderPane pane = new BorderPane();
            bgmS = new Slider();
            bgmS.setMin(0.0);
            bgmS.setMax(100.0);
            bgmS.setMajorTickUnit(10.0);
            bgmS.setMinorTickCount(5);
            bgmL = new Label("BGMの音量:" + Math.floor(BGMManager.getVolume() * 10) / 10);
            bgmS.setValue(BGMManager.getVolume() / 100);
            bgmS.setShowTickLabels(true);
            bgmS.setShowTickMarks(true);
            pane.setTop(bgmL);
            pane.setCenter(bgmS);
            Scene sc = new Scene(pane);
            setScene(sc);
            initOwner(owner);
            initModality(Modality.APPLICATION_MODAL);
            setResizable(false);

            bgmS.valueProperty().addListener((ob, o, n) -> {
                bgmL.setText("BGMの音量:" + Math.floor((double) n * 10) / 10);
                BGMManager.setVolume((double) n / 100);
            });
            this.setOnHiding(e -> {
                this.hide();
            });
        }
    }
}
