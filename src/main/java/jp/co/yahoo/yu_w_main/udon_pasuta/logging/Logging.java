/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.logging;

import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


public class Logging {
    public static final Logger LOGGER = LoggerFactory.getLogger("udon-logger");
    public static final boolean DEV;

    static {
        File f = new File(".");
        if (f.isFile()) {
            DEV = false;
        } else {
            DEV = true;
        }
    }
}
