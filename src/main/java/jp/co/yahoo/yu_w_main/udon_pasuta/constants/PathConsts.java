/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.constants;

import jp.co.yahoo.yu_w_main.udon_pasuta.plugin.texture.Texture;

import java.io.File;
import java.net.URI;

/**パスをあらわす列挙型
 * @author 渡邉雄宇
 *
 */
public enum PathConsts {
	HOME_DIR(new File(System.getProperty("user.home") + "\\AppData\\Roaming\\うどんべいのパスタ退治").toString()),
	IMAGE_DIR(HOME_DIR.getPath() + "\\Textures\\"),
	TEXTURE_DIR(IMAGE_DIR.getPath() + Texture.getCurrentTextureDirName() + "\\"),
	SAVE_DIR(HOME_DIR.getPath() + "\\saves\\"),
	ITEM_DIR(HOME_DIR.getPath() + "\\Item\\"),
	FONT_DIR(HOME_DIR.getPath() + "\\Fonts\\"),
	APP_DIR(HOME_DIR.getPath() + "\\app\\"),
	FXML_DIR(APP_DIR.getPath() + "\\fxml\\"),
	ITEM_MODEL_DIR(ITEM_DIR.getPath() + "model\\"),
	PLUGIN_DIR(HOME_DIR.getPath() + "\\plugins\\"),
	BGM_DIR(HOME_DIR.getPath() + "\\BGM\\"),
	SE_DIR(HOME_DIR.getPath() + "\\SE\\"),
	;
	
	private String path;
	private PathConsts(String uri) {
		this.path = uri;
	}
	
	public String getPath() {
		return path;
	}
	
	public URI makeURI() {
		return new File(path).toURI();
	}

	@Override
	public String toString() {
		return getPath();
	}

	public static void reloadTexture() {
		TEXTURE_DIR.path = IMAGE_DIR.getPath() + Texture.getCurrentTextureDirName() + "\\";
	}
}
