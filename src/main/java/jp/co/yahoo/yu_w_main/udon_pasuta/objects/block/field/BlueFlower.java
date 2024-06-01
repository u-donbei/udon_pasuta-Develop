/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.block.field;

import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;

import java.io.File;

public class BlueFlower extends Field {
	public static final int ID = 5;
	public BlueFlower() {
		super(new File(PathConsts.TEXTURE_DIR + "blue_flower.png"));
	}
}
