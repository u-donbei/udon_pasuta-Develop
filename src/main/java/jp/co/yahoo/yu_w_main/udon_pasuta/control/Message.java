/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.control;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import jp.co.yahoo.yu_w_main.udon_pasuta.managers.GameManager;
import jp.co.yahoo.yu_w_main.udon_pasuta.managers.SEManager;
import jp.co.yahoo.yu_w_main.udon_pasuta.misc.TextUtilities;
import jp.co.yahoo.yu_w_main.udon_pasuta.variables.Variable;

import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * メッセージを表すクラス。
 *
 * @see BorderPane
 */
public class Message extends BorderPane {
	public static AtomicBoolean isNext = new AtomicBoolean(false);
	/**
	 * ラベル
	 */
	ImageView text;
	ImageView icon;
	private int currentChara;
	private Timeline timeline;
	private Object rval;
	private Button next;

	/**
	 * コンストラクタ。
	 */
	public Message() {
		super();
		//初期化
		text = new ImageView();
		icon = new ImageView();
		//見た目
		setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		setBorder(new Border(
				new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT, new Insets(10))));
		setCenter(text);
		setLeft(icon);
		setPrefSize(800, 200);
	}

	/**
	 * テキストをだんだん変える。
	 *
	 * @param text   テキスト
	 * @param image  表示する画像。(nullもOK)
	 * @param millis 更新する秒数
	 */
	public void reText(String text, Image image, double millis, Node... nodes) {
		reText(text, image, millis, () -> {
		}, nodes);
	}

	public void reText(String text, Image image, double millis, Runnable onFinished, Node... nodes) {
		icon.setImage(image);

		if (timeline != null) {
			timeline.stop();
		}
		isNext.set(false);

		currentChara = 0;
		AtomicReference<String> cText = new AtomicReference<>("");
		StringReader stringReader = new StringReader(text);
		try {
			stringReader.reset();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		timeline = new Timeline();
		timeline.setCycleCount(text.length());
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(millis),
				e -> {
					try {
						currentChara = stringReader.read();
						cText.updateAndGet(v -> v + (char) currentChara);
						this.text.setImage(TextUtilities.toImage(cText.get(), java.awt.Color.WHITE, java.awt.Color.BLACK, 500, 50, 18));
						SEManager.play("se.pipipi");
					} catch (IOException e2) {
						throw new RuntimeException(e2);
					}
				}));
		//このボタンを押したらreturnする
		next = new Button("", new ImageView(TextUtilities.toImage("▼", java.awt.Color.WHITE, java.awt.Color.BLACK, 50, 50, 12)));
		next.setOnAction(e -> {
			isNext.set(true);
			Variable.IS_MESSAGE_EXIT.set(true);
			timeline.stop();
			this.text.setImage(null);

			this.icon.setImage(null);
			if (GameManager.getTimer() != null) {
				GameManager.getTimer().start();
			} else {
				Platform.exitNestedEventLoop("message-loop", rval);
			}
			setBottom(null);
			setRight(null);
		});
		next.setStyle("-fx-background-color: transparent;");
		next.setTextFill(Color.WHITE);
		next.setAlignment(Pos.CENTER);
		next.setFocusTraversable(false);
		timeline.setOnFinished(e -> {
			BorderPane.setAlignment(next, Pos.CENTER);
			setBottom(next);
			next.requestFocus();
			if (nodes != null) {
				VBox n = new VBox(nodes);
				n.setAlignment(Pos.CENTER);
				setRight(n);
			}
			onFinished.run();
		});
		timeline.play();
		if (GameManager.getTimer() != null) {
			GameManager.getTimer().stop();
		} else {
			rval = Platform.enterNestedEventLoop("message-loop");
		}
	}

	public void reText(String text, Image image, double millis, Runnable onFinished, Runnable onExited, boolean isNextButton, Node... nodes) {
		icon.setImage(image);
		AtomicBoolean isFinish = new AtomicBoolean(false);

		if (timeline != null) {
			timeline.stop();
		}
		isNext.set(false);

		currentChara = 0;
		AtomicReference<String> cText = new AtomicReference<>("");
		StringReader stringReader = new StringReader(text);
		try {
			stringReader.reset();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		timeline = new Timeline();
		timeline.setCycleCount(text.length());
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(millis),
				e -> {
					try {
						currentChara = stringReader.read();
						cText.updateAndGet(v -> v + (char) currentChara);
						this.text.setImage(TextUtilities.toImage(cText.get(), java.awt.Color.WHITE, java.awt.Color.BLACK, 500, 50, 18));
						SEManager.play("se.pipipi");
					} catch (IOException e2) {
						throw new RuntimeException(e2);
					}
				}));
		//このボタンを押したらreturnする
		if (isNextButton) {
			next = new Button("", new ImageView(TextUtilities.toImage("▼", java.awt.Color.WHITE, java.awt.Color.BLACK, 50, 50, 12)));
			next.setOnAction(e -> {
				isNext.set(true);
				if (!Variable.IS_MSG_FINISH.get()) {
					onFinished.run();
					System.out.println("finish runned");
					Variable.IS_MSG_FINISH.set(false);
				}
				Variable.IS_MESSAGE_EXIT.set(true);
				timeline.stop();
				this.text.setImage(null);

				this.icon.setImage(null);
				if (GameManager.getTimer() != null) {
					GameManager.getTimer().start();
				} else {
					Platform.exitNestedEventLoop("message-loop", rval);
				}
				setBottom(null);
				setRight(null);
				onExited.run();
			});
			next.setStyle("-fx-background-color: transparent;");
			next.setTextFill(Color.WHITE);
			next.setAlignment(Pos.CENTER);
			next.setFocusTraversable(false);
			timeline.setOnFinished(e -> {
				onFinished.run();
				isFinish.set(true);
				BorderPane.setAlignment(next, Pos.CENTER);
				setBottom(next);
				next.requestFocus();
				if (nodes != null) {
					VBox n = new VBox(nodes);
					n.setAlignment(Pos.CENTER);
					setRight(n);
				}
			});
		} else {
			timeline.setOnFinished(e -> {
				onFinished.run();
				if (nodes != null) {
					VBox n = new VBox(nodes);
					n.setAlignment(Pos.CENTER);
					setRight(n);
				}
			});
		}
		timeline.play();
		if (GameManager.getTimer() != null) {
			GameManager.getTimer().stop();
		} else {
			rval = Platform.enterNestedEventLoop("message-loop");
		}
	}

	public void reText(String text, Image image, double millis, Runnable onFinished, Runnable onExited, Node... nodes) {
		icon.setImage(image);

		if (timeline != null) {
			timeline.stop();
		}
		isNext.set(false);

		currentChara = 0;
		AtomicReference<String> cText = new AtomicReference<>("");
		StringReader stringReader = new StringReader(text);
		try {
			stringReader.reset();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		timeline = new Timeline();
		timeline.setCycleCount(text.length());
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(millis),
				e -> {
					try {
						currentChara = stringReader.read();
						cText.updateAndGet(v -> v + (char) currentChara);
						this.text.setImage(TextUtilities.toImage(cText.get(), java.awt.Color.WHITE, java.awt.Color.BLACK, 500, 50, 18));
						SEManager.play("se.pipipi");
					} catch (IOException e2) {
						throw new RuntimeException(e2);
					}
				}));
		//このボタンを押したらreturnする
		next = new Button("", new ImageView(TextUtilities.toImage("▼", java.awt.Color.WHITE, java.awt.Color.BLACK, 50, 50, 12)));
		next.setOnAction(e -> {
			exit(onExited);
		});
		next.setStyle("-fx-background-color: transparent;");
		next.setTextFill(Color.WHITE);
		next.setAlignment(Pos.CENTER);
		next.setFocusTraversable(false);
		timeline.setOnFinished(e -> {
			BorderPane.setAlignment(next, Pos.CENTER);
			setBottom(next);
			next.requestFocus();
			if (nodes != null) {
				VBox n = new VBox(nodes);
				n.setAlignment(Pos.CENTER);
				setRight(n);
			}
			onFinished.run();
		});
		timeline.play();
		if (GameManager.getTimer() != null) {
			GameManager.getTimer().stop();
		} else {
			rval = Platform.enterNestedEventLoop("message-loop");
		}
	}

	public Timeline getTimeline() {
		return timeline;
	}

	public Button getNext() {
		return next;
	}

	public void exit(Runnable onExited) {
		isNext.set(true);
		Variable.IS_MESSAGE_EXIT.set(true);
		timeline.stop();
		this.text.setImage(null);

		this.icon.setImage(null);
		if (GameManager.getTimer() != null) {
			GameManager.getTimer().start();
		} else {
			Platform.exitNestedEventLoop("message-loop", rval);
		}
		setBottom(null);
		setRight(null);
		onExited.run();
	}
}
