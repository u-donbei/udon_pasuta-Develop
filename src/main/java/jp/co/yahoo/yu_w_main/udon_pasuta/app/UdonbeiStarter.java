/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.app;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.game.GameInfo;
import jp.co.yahoo.yu_w_main.udon_pasuta.management.UdonMonitor;
import jp.co.yahoo.yu_w_main.udon_pasuta.management.UdonMonitorMBean;
import jp.co.yahoo.yu_w_main.udon_pasuta.managers.GameManager;
import jp.co.yahoo.yu_w_main.udon_pasuta.managers.SaveManager;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;
import jp.co.yahoo.yu_w_main.udon_pasuta.panes.GamePane;
import jp.co.yahoo.yu_w_main.udon_pasuta.panes.LoadingPane;
import jp.co.yahoo.yu_w_main.udon_pasuta.panes.TitlePane;
import jp.co.yahoo.yu_w_main.udon_pasuta.panes.controller.TitleController;
import jp.co.yahoo.yu_w_main.udon_pasuta.variables.Variable;

import javax.management.MBeanServer;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.net.URI;

import static jp.co.yahoo.yu_w_main.udon_pasuta.logging.Logging.LOGGER;

public final class UdonbeiStarter extends Application {
	private static Application theInstance;

	private Object rval;
	private GamePane gamePane;

	private Scene mainScene;
	private Timeline timeline;

	public static void main(String[] args) {
		launch(args);
	}

	public static Application getApplicationInstance() {
		return theInstance;
	}

	@Override
	public void start(Stage stage) throws Exception {
		LOGGER.debug("APPLICATION START.");
		LOGGER.debug("MBean Register.");
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		mbs.registerMBean(new UdonMonitor(), UdonMonitorMBean.createObjectName());
		theInstance = this;

		//ファイルからプレイヤーを読み込み
		Udon udon = SaveManager.readUdon();

		//シャットダウンフックを追加
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
		}));

		//例外の設定
		Thread.currentThread().setUncaughtExceptionHandler((t, e) -> {
			LOGGER.error("An fatal error has occurred", e);
			Variable.IS_ERR = true;
			Platform.runLater(() -> {
				class LogWindow extends Stage {
					{
						TextArea log = new TextArea();
						try (StringWriter sw = new StringWriter();
							 PrintWriter pw = new PrintWriter(sw)) {
							e.printStackTrace(pw);
							log.setText(sw.toString());
						} catch (IOException e) {
							e.printStackTrace();
						}
						log.setEditable(false);
						Label l = new Label("エラーが発生し、ゲームがクラッシュしました。\r\n詳細:");
						VBox root = new VBox(l, log);
						Scene scene = new Scene(root, 400, 300);
						setScene(scene);
						setTitle("エラー");
					}
				}
				LogWindow l = new LogWindow();
				l.setResizable(false);
				l.initOwner(stage);
				l.showAndWait();
				stage.hide();
			});
		});
		//タイトル
		TitlePane title = new TitlePane();
		//プレイヤーの生成

		//表示
		Scene titleScene = new Scene(title);

		stage.getIcons().setAll(new Image(PathConsts.TEXTURE_DIR.makeURI() + "udon1.png"));
		stage.setScene(titleScene);
		stage.setTitle("うどんべいのパスタ退治 " + GameInfo.VERSION);
		stage.show();

		LOGGER.debug("WINDOW SHOW.");
		stage.sizeToScene();

		//終了処理
		stage.setOnHidden(e -> {
			LOGGER.debug("APPLICATION EXIT.");
			timeline.stop();
			System.exit(0);
		});

		Variable.IS_RESTART.set(true);
		//スタートが押されたなら次に進む
		//Platform.runLater(Runnable)を多用しているため、ものすごく複雑になっています。
		Thread start_thread = Thread.ofVirtual().start(() -> {
			while (true) {
				if (Variable.IS_RESTART.get()) {
					try {
						//フラグの初期化
						Variable.IS_RESTART.set(false);
						Variable.IS_LOAD.set(false);
						Variable.IS_GAME_EXIT = false;
						TitleController.isGameStart.set(false);
						Platform.runLater(() -> {
							rval = null;

							timeline = new Timeline();
							timeline.setCycleCount(Timeline.INDEFINITE);
							timeline.getKeyFrames().add(new KeyFrame(Duration.millis(33), (e2) -> {
								if (TitleController.isGameStart.get()) {
									try {
										Platform.exitNestedEventLoop("start-loop", rval);
									} catch (Exception ignored) {
									}
								}
							}));
							timeline.play();
							rval = Platform.enterNestedEventLoop("start-loop");
							timeline.stop();

							LOGGER.debug("game started.");
							//ロード画面の表示
							Scene loadingScene = new Scene(new LoadingPane());
							stage.setScene(loadingScene);
							stage.sizeToScene();

							//ワールドの構築
							Thread world_build = Thread.startVirtualThread(() -> {
								gamePane = new GamePane(udon);
							});

							var thread = Thread.ofVirtual().unstarted(() -> {
								while (true) {
									if (Variable.IS_LOAD.get()) {
										Platform.runLater(() -> {
											Platform.exitNestedEventLoop("world-build-loop", rval);
										});
										break;
									}
								}
							});
							thread.start();
							rval = Platform.enterNestedEventLoop("world-build-loop");
							timeline.stop();
							//初期化
							gamePane.getGameViewPane().getChildren().clear();
							//キャラクターの追加
							gamePane.getGameViewPane().addChara(udon, udon.getX(), udon.getY());
							LOGGER.debug("loading exit.");

							stage.setWidth(805);
							stage.setHeight(730);
							stage.setResizable(false);
							stage.centerOnScreen();
							mainScene = new Scene(gamePane);
							mainScene.setFill(Color.BLACK);

							LOGGER.debug("game started.");
							//通知を表示
							try {
								java.awt.Image img = Toolkit.getDefaultToolkit().getImage(URI.create(PathConsts.TEXTURE_DIR.makeURI() + "/udon1.png").toURL());
								TrayIcon icon = new TrayIcon(img, "うどんべいのパスタ退治");
								icon.setImageAutoSize(true);
								SystemTray.getSystemTray().add(icon);
								icon.displayMessage("うどんべいのパスタ退治", "ロードが完了しました", TrayIcon.MessageType.INFO);
							} catch (Exception e) {
								LOGGER.error("An error has occurred", e);
							}
							//ゲームループ
							GameManager.gameLoop(udon, gamePane, mainScene, stage, titleScene);
						});
					} catch (Exception e) {
						LOGGER.error("An error has occurred", e);
					}
				}
			}
		});

	}


}
