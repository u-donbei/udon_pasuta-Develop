/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.panes;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Party;
import jp.co.yahoo.yu_w_main.udon_pasuta.font.Fonts;
import jp.co.yahoo.yu_w_main.udon_pasuta.managers.BGMManager;
import jp.co.yahoo.yu_w_main.udon_pasuta.managers.BattleManager;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.enemy.Enemy;
import jp.co.yahoo.yu_w_main.udon_pasuta.variables.Variable;

public class BattlePane extends VBox {
	private final HBox root;
	private final VBox players, udonStat, select;
	private final VBox onigiris0, onigiriStat;
	private final HBox udons, onigiris;
	private final VBox enemy;
	private final ImageView enemyView;
	private final ImageView udonView;
	private final ImageView onigiriView;
	private final Label enemyName;
	private final Label udonName;
	private final Label enemyHPl, udonHPl, onigiriHPl;
	private final Message message;
	private Party<Udon> party;
	private Party<Enemy> enemies;
	private ProgressBar udonHP, enemyHP, onigiriHP;
	private Stage stage;

	public BattlePane() {
		setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		onigiris = new HBox();
		message = new Message();
		enemyView = new ImageView();
		udonView = new ImageView();
		enemyName = new Label();
		udonName = new Label();
		udonHP = new ProgressBar();
		enemyHP = new ProgressBar();
		udonHPl = new Label();
		enemyHPl = new Label();
		enemy = new VBox(enemyName, enemyView, enemyHP, enemyHPl);
		select = new VBox();
		players = new VBox();
		udons = new HBox();
		udonStat = new VBox();
		onigiris0 = new VBox();
		onigiriStat = new VBox();
		onigiriView = new ImageView();
		onigiriHPl = new Label();
		onigiriHP = new ProgressBar();

		enemyHPl.setFont(Fonts.getPixelMplus12(12));
		udonHPl.setFont(Fonts.getPixelMplus12(12));
		enemyHPl.setTextFill(Color.WHITE);
		udonHPl.setTextFill(Color.WHITE);

		enemyName.setFont(Fonts.getPixelMplus12(10));
		udonName.setFont(Fonts.getPixelMplus12(10));

		enemyName.setTextFill(Color.WHITE);
		udonName.setTextFill(Color.WHITE);

		udonStat.getChildren().addAll(udonName, udonView, udonHP, udonHPl);
		udons.getChildren().addAll(udonStat);
		players.getChildren().addAll(udons);

		players.setMinWidth(400);
		enemy.setMinWidth(400);

		root = new HBox(players, enemy);
		getChildren().addAll(root, message);
	}

	public void init(Party<Udon> party, Party<Enemy> enemies, Stage stage) {
		this.stage = stage;
		udonHPl.setText(party.getLeader().getHp() + "/" + Udon.getByLevelMaxHP(party.getLeader().getLevel()));
		enemyHPl.setText(enemies.getLeader().getHp() + "/" + enemies.getLeader().getHp());
		onigiriHPl.setText(party.members().getFirst().getHp() + "/" + party.members().getFirst().getHp());
		udonHP.setProgress(1);
		enemyHP.setProgress(1);
		onigiriHP.setProgress(1);
		enemyView.setImage(new Image(enemies.getLeader().getImage().toURI().toString(), 100, 100, true, true));
		enemyName.setText(enemies.getLeader().getName());

		udonView.setImage(new Image(party.getLeader().getImage().toURI().toString(), 100, 100, true, true));
		udonName.setText(party.getLeader().getName());
		this.party = party;
		this.enemies = enemies;
	}

	public void battleStart() {
		BattleManager.battleStart(party, enemies.getLeader(), message, () -> {
			party.getLeader().setHp(30);
			party.getLeader().setTenkasu(0);
			party.getLeader().setLevel(1);
			party.getLeader().setXp(0);
			Variable.IS_BACK = true;
			Platform.runLater(() -> {
				BGMManager.stop("bgm.battle");
				BGMManager.play("bgm.field");
			});
		}, () -> {
			Platform.runLater(() -> {
				stage.setScene(new Scene(new WinPane(), 800, 700));
			});
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			Variable.IS_BACK = true;
			Platform.runLater(() -> {
				BGMManager.stop("bgm.battle");
				BGMManager.play("bgm.field");
			});
		}, true, udonHP, enemyHP, udonHPl, enemyHPl);
	}
}
