/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.managers;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jp.co.yahoo.yu_w_main.udon_pasuta.annotations.Manager;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.StatusMessage;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Party;
import jp.co.yahoo.yu_w_main.udon_pasuta.misc.TextUtilities;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.action.ActionObject;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Character;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.ally.Onigiri;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.ally.Soba;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.enemy.Enemy;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.enemy.MeatSauce;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.enemy.MeatSauce_Fork;
import jp.co.yahoo.yu_w_main.udon_pasuta.panes.*;
import jp.co.yahoo.yu_w_main.udon_pasuta.panes.controller.InventoryController;
import jp.co.yahoo.yu_w_main.udon_pasuta.plugin.PluginExecutor;
import jp.co.yahoo.yu_w_main.udon_pasuta.variables.Variable;
import org.apache.commons.lang3.StringUtils;
import org.intellij.lang.annotations.MagicConstant;

import java.io.IOException;
import java.util.*;

import static jp.co.yahoo.yu_w_main.udon_pasuta.logging.Logging.LOGGER;

/**
 * ゲームに関する処理を行うクラス。
 */
@Manager
public final class GameManager {
    private static List<PluginExecutor> plugins;
    static Udon udon;
    //各種フラグ
    static boolean isUp, isDown, isRight, isLeft, isShift, isE, isSpace;
    static boolean isInventory;
    //画面の座標
    static double screenX, screenY;
    //キャラクターを格納するリスト
    private static List<Character> characters;
    private static AnimationTimer timer;
    static Party<Udon> party;

    static {
        plugins = new ArrayList<>();

    }

    private GameManager() {
    }

    /**
     * ゲームループのメソッド
     */
    public static void gameLoop(Udon udon, GamePane gamepane, Scene scene, Stage window, Scene title) {
        GameManager.udon = udon;
        screenX = udon.getX() > 300 ? udon.getX() - 300 : 0;
        screenY = udon.getY() > 400 ? udon.getY() - 400 : 0;
        characters = null;
        isInventory = false;
        window.setScene(scene);
        GameViewPane pane = gamepane.getGameViewPane();
        StatusMessage message = gamepane.getMessage();
        //勝った時の画面
        WinPane winPane = new WinPane();
        Scene winScene = new Scene(winPane, 800, 700);
        //持ち物画面
        Inventory inventory = new Inventory();
        Message inMessage = new Message();
        VBox inPane = new VBox(inventory, inMessage);
        VBox.layoutInArea(inventory, 0, 0, 800, 500, 0, Insets.EMPTY, false, false, HPos.CENTER, VPos.CENTER, false);
        VBox.layoutInArea(inMessage, 0, 500, 800, 200, 0, Insets.EMPTY, false, false, HPos.CENTER, VPos.CENTER, false);
        Scene inScene = new Scene(inPane, 800, 700);
        MenuPane menu = new MenuPane();
        Scene menuScene = new Scene(menu, 800, 700);
        //設定画面
        SettingPane setting = new SettingPane(window);
        Scene settingSC = new Scene(setting, 800, 600, Color.BLACK);
        //対戦画面
        BattlePane battle = new BattlePane();
        Scene battleSC = new Scene(battle);

        //キャラクタター

        udon.setTenkasu(100);
        Onigiri onigiri = new Onigiri();
        MeatSauce meatSauce = new MeatSauce();
        MeatSauce_Fork mf = new MeatSauce_Fork();
        onigiri.setX(300);
        onigiri.setY(400);
        Soba soba = new Soba();
        characters = new ArrayList<>(8);
        characters.add(onigiri);
        characters.add(soba);
        characters.add(meatSauce);
        characters.add(mf);
        //パーティ
        party = new Party<>(udon, FXCollections.observableArrayList());
        //パーティに🍙を追加
        party.members().add(onigiri);
        party.members().add(soba);

        InventoryController.getInstance().init(party, inMessage);
        //イベントハンドラの登録
        pane.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W -> isUp = (!isDown && !isLeft && !isRight);
                case S -> isDown = (!isUp && !isLeft && !isRight);
                case A -> isLeft = (!isDown && !isUp && !isRight);
                case D -> isRight = (!isDown && !isLeft && !isUp);
                case SHIFT -> isShift = true;
                case E -> {
                    LOGGER.debug("inventory showed.");
                    window.setScene(inScene);
                    isInventory = true;
                    inventory.requestFocus();
                    getTimer().stop();
                }
                case SPACE -> isSpace = true;

                case ESCAPE -> window.setScene(menuScene);
            }
        });

        inventory.setOnKeyPressed(e -> {
            if (Objects.requireNonNull(e.getCode()) == KeyCode.E) {
                LOGGER.debug("inventory hided.");
                window.setScene(scene);
                isInventory = false;
                pane.requestFocus();
                getTimer().start();
            }
        });

        menu.setOnKeyPressed(e -> {
            if (Objects.requireNonNull(e.getCode()) == KeyCode.ESCAPE) {
                Variable.IS_BACK = true;
            }
        });

        pane.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case W -> isUp = false;
                case S -> isDown = false;
                case A -> isLeft = false;
                case D -> isRight = false;
                case SHIFT -> isShift = false;
                case E -> isE = false;
                case SPACE -> isSpace = false;
            }
        });
        //カメラ
        PerspectiveCamera camera = new PerspectiveCamera();
        scene.setCamera(camera);

        //キャラクターの追加
        pane.addChara(onigiri, 100, 100);
        pane.addChara(soba, 150, 100);
        pane.addChara(meatSauce, 500, 600);
        pane.addChara(mf, 800, 800);

        //キャラの初期化
        characters.forEach(t -> t.update(() -> {
        }));

        pane.requestFocus();

        //音を再生
		BGMManager.setVolume(40);
        BGMManager.play("bgm.field");
        udon.update(() -> {});

        //ループ
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                //エラー->強制終了
                if (Variable.IS_ERR) {
                    stop();
                }
                //もしフォーカスされていないならフォーカスする
                if (!pane.isFocused()) {
                    pane.requestFocus();
                }
                //プレイヤーを動かす
                if (isUp && udon.getY() > 0) {
                    LOGGER.debug("move:north");
                    udon.stepY(-3);
                    if (isShift) {
                        udon.stepY(-2);
                        udon.setDash(true);
                    } else {
                        udon.setDash(false);
                    }
                    if (udon.getY() > 250) {
                        screenY -= 3;
                        if (isShift) {
                            screenY -= 2;
                        }
                    }
                }
                if (isDown && udon.getY() + 50 < 2500) {
                    LOGGER.debug("move:south");
                    udon.stepY(3);
                    if (isShift) {
                        udon.stepY(2);
                        udon.setDash(true);
                    } else {
                        udon.setDash(false);
                    }
                    if (udon.getY() > 250) {
                        screenY += 3;
                        if (isShift) {
                            screenY += 2;
                        }
                    }
                }
                if (isLeft && udon.getX() > 0) {
                    LOGGER.debug("move:west");
                    udon.stepX(-3);
                    if (isShift) {
                        udon.stepX(-2);
                        udon.setDash(true);
                    } else {
                        udon.setDash(false);
                    }
                    if (udon.getX() > 300) {
                        screenX -= 3;
                        if (isShift) {
                            screenX -= 2;
                        }
                    }
                }
                if (isRight && udon.getX() + 50 < 4000) {
                    LOGGER.debug("move:east");
                    udon.stepX(3);
                    if (isShift) {
                        udon.stepX(2);
                        udon.setDash(true);
                    } else {
                        udon.setDash(false);
                    }
                    if (udon.getX() > 400) {
                        screenX += 3;
                        if (isShift) {
                            screenX += 2;
                        }
                    }
                }

                //毎フレーム1/500でミートソースが発生
                if (new Random().nextInt(500) == 0) {
                    Enemy n;
                    if (udon.getLevel() <= 3) {
                        n = new MeatSauce_Fork();
                    } else {
                        n = new MeatSauce();
                    }
                    n.setX(new Random().nextDouble(4001));
                    n.setY(new Random().nextInt(2501));
                    n.update(() -> {});
                    characters.add(n);
                    pane.addChara(n, n.getX(), n.getY());
                }

                if (isSpace) {
                    Arrays.stream(pane.getWorld().getBlocks())
                            .forEach(ts -> Arrays.stream(ts)
                                    .filter(t -> t.isHit(udon))
                                    .filter(t -> t instanceof ActionObject)
                                    .forEach(t -> ((ActionObject) t).handle(null, message)));
                }

                if (Variable.IS_GAME_EXIT) {
                    timer.stop();
                    //BGMを停止
                    BGMManager.stop("bgm.field");
                    try {
                        SaveManager.save(udon);
                    } catch (IOException e) {
                        Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                    }
                    //タイトル画面を表示
                    window.setScene(title);
                    window.sizeToScene();
                    Variable.IS_RESTART.set(true);
                    return;
                }
                //メニュー画面の非表示
                if (Variable.IS_BACK) {
                    window.setScene(scene);
                    Variable.IS_BACK = false;
                }
                //設定を表示
                if (Variable.IS_SETTING) {
                    window.setScene(settingSC);
                    Variable.IS_SETTING = false;
                }
                //戦闘画面
                if (Variable.IS_BATTLE) {
                    stop();
                    battle.init(party, new Party<>(Variable.BATTLE_TARGET, FXCollections.observableArrayList()), window);
                    window.setScene(battleSC);
                    battle.battleStart();
                    Variable.BATTLE_TARGET.setX(-1000);
                    Variable.BATTLE_TARGET.setY(-1000);
                    pane.getChildren().remove(Variable.BATTLE_TARGET.getImageView());
                    System.out.println(characters.remove(Variable.BATTLE_TARGET));
                    Variable.IS_BATTLE = false;
                    start();
                }

                //update
                udon.update(() -> {
                });
                characters.stream()/*.filter(t -> t.getX() >= (udon.getX() - 800)
                                && t.getY() >= (udon.getY() - 800)
                                && t.getX() <= (udon.getX() + 800)
                                && t.getY() <= (udon.getY() + 800))*/
                        .forEach(c -> {
                            c.update(() -> {
                            });
                        });


                //敵の処理
                characters.stream()
/*                        .filter(t -> t.getX() >= (udon.getX() - 200)
                                && t.getY() >= (udon.getY() - 200)
                                && t.getX() <= (udon.getX() + 200)
                                && t.getY() <= (udon.getY() + 200))*/
                        .filter(t -> t instanceof Enemy)
                        .filter(t -> t.isHit(udon))
                        .limit(1)
                        .forEach(t -> ((Enemy) t).handle(() -> {}, message));

                //当たり判定
                CollisionManager.collision(udon, pane.getWorld().getBlocks());
                CollisionManager.collision(udon, characters);
                characters.stream()/*.filter(t -> t.getX() >= (udon.getX() - 200)
                                                && t.getY() >= (udon.getY() - 200)
                                                && t.getX() <= (udon.getX() + 200)
                                                && t.getY() <= (udon.getY() + 200))*/
                        .forEach(t -> {
							CollisionManager.collision(t, characters);
                            CollisionManager.collision(t, pane.getWorld().getBlocks());
						});

                //screenXが0より小さいなら、screenXを0にする
                if (screenX < 0) {
                    screenX = 0;
                    camera.setTranslateX(screenX);
                }
                //screenYが0より小さいなら、screenYを0にする
                if (screenY < 0) {
                    screenY = 0;
                    camera.setTranslateY(screenY);
                }
                //screenXが3200より大きいなら、screenXを3200にする
                if (screenX > 3200) {
                    screenX = 3200;
                    camera.setTranslateX(screenX);
                }
                //screenYが200より大きいなら、screenYを2000にする
                if (screenY > 2000) {
                    screenY = 2000;
                    camera.setTranslateY(screenY);
                }


                //カメラを動かす
                camera.setTranslateX(screenX);
                camera.setTranslateY(screenY);

                //ステータスメッセージの位置が常に同じように見えるようにする。
                message.setTranslateX(screenX);
                message.setTranslateY(screenY);

                String tenkasu = StringUtils.leftPad(Integer.toString(udon.getTenkasu()), 8, '0');
                InventoryController.getInstance().getTenkasu().setText(tenkasu + "天かす");
                gamepane.getMessage().getTenkasu().setImage(TextUtilities.toImage(tenkasu + "天かす", java.awt.Color.WHITE, java.awt.Color.BLACK, 150, 50, 18));
                gamepane.getMessage().getHp().setImage(TextUtilities.toImage(udon.getHp() + "HP", java.awt.Color.WHITE, java.awt.Color.BLACK, 100, 50, 18));
            }
        };
        timer.start();
    }

    public static AnimationTimer getTimer() {
        return timer;
    }

    public static Udon getUdon() {
        return udon;
    }

    public static Party<Udon> getParty() {
        return party;
    }

	public static List<Character> getCharacters() {
		return characters;
	}
}
