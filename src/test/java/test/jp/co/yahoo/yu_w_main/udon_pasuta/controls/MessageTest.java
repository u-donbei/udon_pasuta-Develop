/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package test.jp.co.yahoo.yu_w_main.udon_pasuta.controls;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class MessageTest extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane pane = new BorderPane();
		Message message = new Message();
		pane.setCenter(message);

		Scene scene = new Scene(pane, 800, 500);

		stage.setScene(scene);
		stage.show();

		stage.setOnHiding(e -> {
			System.exit(0);
		});

		message.reText("うどんべいのパスタ退治", null, 80);

		message.reText("ミートソースが現れた!", new Image(PathConsts.IMAGE_DIR.makeURI().toString() + "meat.png"), 80);

	}

	public static void main(String[] args) {
		launch(args);
	}

}
