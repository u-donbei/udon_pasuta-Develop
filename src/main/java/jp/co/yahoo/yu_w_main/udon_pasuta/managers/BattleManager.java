/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.managers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import jp.co.yahoo.yu_w_main.udon_pasuta.annotations.Manager;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Party;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Character;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.ally.Ally;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.enemy.Enemy;
import jp.co.yahoo.yu_w_main.udon_pasuta.variables.Variable;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Manager
public final class BattleManager {
	private BattleManager() {
	}

	public static void battleStart(Party<Udon> party, Enemy enemy, Message message) {
		battleStart(party, enemy, message, () -> {
		}, () -> {
		});
	}

	public static void battleStart(Party<Udon> party, Enemy enemy, Message message, Runnable died, Runnable win) {
		battleStart(party, enemy, message, died, win, false, new ProgressBar(), new ProgressBar(), new Label(), new Label());
	}

	public static void battleStart(Party<Udon> pty, Enemy enemy, Message message, Runnable died, Runnable win, boolean isTecSelect, ProgressBar udonHP, ProgressBar enemyHP, Label udonHPl, Label enemyHPl) {
		Platform.runLater(() -> {
			BGMManager.stop("bgm.field");
			BGMManager.play("bgm.battle");
		});
		final double enemyHP0 = enemy.getHp();
		Party<Udon> party = new Party<>(pty.getLeader(), pty.members().filtered(Ally::isAlive));

		Thread th = Thread.ofVirtual()
				.name("battle-thread")
				.unstarted(() -> {
					while (party.getLeader().getHp() != 0 && enemy.getHp() != 0) {
						Platform.runLater(() -> {
							party.getLeader().attack(true, message, () -> Variable.IS_TEC_EXIT.set(true), enemy);
						});
						while (!Variable.IS_TEC_EXIT.get()) {

						}
						Variable.IS_TEC_EXIT.set(false);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
						Platform.runLater(() -> {
							udonHP.setProgress((double) party.getLeader().getHp() / Udon.getByLevelMaxHP(party.getLeader().getLevel()));
							enemyHP.setProgress(enemy.getHp() / enemyHP0);
							Udon udon = party.getLeader();
							udonHPl.setText(udon.getHp() + "/" + Udon.getByLevelMaxHP(udon.getLevel()));
							enemyHPl.setText(enemy.getHp() + "/" + (int) enemyHP0);
						});
						if (party.getLeader().getHp() == 0) {
							died.run();
							return;
						} else if (enemy.getHp() == 0) {
							party.getLeader().addXP(enemy.getXp());
							win.run();
							return;
						}

						Platform.runLater(() -> {
							ArrayList<Character> characters = new ArrayList<>(party.members());
							characters.add(party.getLeader());
							enemy.attack(message, () -> {
							}, () ->{
								if (party.getLeader().getHp() == 0) {
									died.run();
									return;
								} else if (enemy.getHp() == 0) {
									party.getLeader().addXP(enemy.getXp());
									win.run();
									return;
								}
								Variable.IS_TEC_EXIT.set(true);
							}, () -> {
								udonHP.setProgress((double) party.getLeader().getHp() / Udon.getByLevelMaxHP(party.getLeader().getLevel()));
								enemyHP.setProgress(enemy.getHp() / enemyHP0);
								Udon udon = party.getLeader();
								udonHPl.setText(udon.getHp() + "/" + Udon.getByLevelMaxHP(udon.getLevel()));
								enemyHPl.setText(enemy.getHp() + "/" + (int) enemyHP0);
							}, characters.toArray(new Character[characters.size()]));
						});
						while (!Variable.IS_TEC_EXIT.get()) {

						}
						Variable.IS_TEC_EXIT.set(false);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
/*						try {
							Thread.currentThread().join(1000);
							Platform.runLater(() -> {
								message.exit(() -> { });
							});
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}*/
					}

					if (party.getLeader().getHp() == 0) {
						died.run();
					} else {
						party.getLeader().addXP(enemy.getXp());
						win.run();
					}
				});
		th.start();

	}

	public enum ExitType {
		PLAYER_WIN,
		PLAYER_AWAY,
		PLAYER_DIED,
	}
}
