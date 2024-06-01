/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.management;

import jp.co.yahoo.yu_w_main.udon_pasuta.constants.game.GameInfo;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.GameObject;

public class UdonMonitor implements UdonMonitorMBean {

	@Override
	public String getVersion() {
		return GameInfo.VERSION;
	}

	@Override
	public String getRegisteredItems() {
		StringBuilder b = new StringBuilder(GameInfo.ITEMS.values().
				stream()
				.map(GameObject::getName)
				.toList()
				.toString());
		return b.substring(1, b.length() - 1);
	}

	@Override
	@SuppressWarnings("all")
	public boolean isSnapshot() {
		return GameInfo.VERSION.endsWith("-SNAPSHOT");
	}

	@Override
	@SuppressWarnings("all")
	public boolean isDevelopmentVersion() {
		return GameInfo.VERSION.startsWith("Alpha") || GameInfo.VERSION.startsWith("Beta");
	}

	@Override
	public void forceExitGame() {
		Runtime.getRuntime().halt(130);
	}
}
