/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package test.jp.co.yahoo.yu_w_main.udon_pasuta.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.StatusMessage;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;

public class StatusMessageTest extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane pane = new BorderPane();
		StatusMessage message = new StatusMessage(new Udon("test"));
		pane.setCenter(message);

		Scene scene = new Scene(pane, 800, 500);

		stage.setScene(scene);
		stage.show();

		stage.setOnHiding(e -> {
			System.exit(0);
		});

		message.reText("a b c d e f g h i j k l m n o p q r s t u v r x y z", null, 80);

		message.reText("ミートソースが現れた!", new Image(PathConsts.IMAGE_DIR.makeURI().toString() + "meat.png"), 80);

	}

	public static void main(String[] args) {
		launch(args);
	}

}
