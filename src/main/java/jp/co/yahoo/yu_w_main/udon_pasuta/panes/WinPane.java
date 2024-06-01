package jp.co.yahoo.yu_w_main.udon_pasuta.panes;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import jp.co.yahoo.yu_w_main.udon_pasuta.font.Fonts;

public class WinPane extends StackPane {
	public WinPane() {
		Label win = new Label("YOU WIN!");
		win.setFont(Fonts.getPixelMplus12(50));
		win.setTextFill(Color.WHITE);
		getChildren().add(win);
		setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
	}
}
