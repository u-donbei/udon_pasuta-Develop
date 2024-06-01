/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.management;

import jp.co.yahoo.yu_w_main.udon_pasuta.logging.Logging;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Item;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.util.HashMap;

public interface UdonMonitorMBean {
	String NAME = "jp.co.yahoo.yu_w_main.udon_pasuta.jmx:type=UdonMonitoring";
	public String getVersion();
	public String getRegisteredItems();
	public boolean isSnapshot();
	public boolean isDevelopmentVersion();
	public void forceExitGame();
	public static ObjectName createObjectName() {
		try {
			return new ObjectName(UdonMonitorMBean.NAME);
		} catch (MalformedObjectNameException e) {
			throw new RuntimeException(e);
		}
	}
}
