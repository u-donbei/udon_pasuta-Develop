/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.panes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;

import java.io.IOException;
import java.net.URI;

/**タイトルのペイン。
 * レイアウトはうどんべいのパスタ退治/app/fxml/title.fxmlを参考にしてください。
 *
 * @author 渡邉雄宇
 *
 * @see Pane
 *
 * */
public class TitlePane extends Pane {
    public TitlePane() {
        try {
            getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/title.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
