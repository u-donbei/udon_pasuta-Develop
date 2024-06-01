/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.logging;

import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Logging {
    static {
        System.setProperty("log4j.configurationFile", PathConsts.HOME_DIR.getPath() + "\\log\\log4j2.xml");
    }
    public static final Logger LOGGER = LogManager.getLogger("udon-logger");

}
