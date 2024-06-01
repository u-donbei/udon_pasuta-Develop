/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.plugin.texture;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**テクスチャパックの設定を表すクラス。*/
public final class Texture {
    /**現在のテクスチャパックのフォルダ名*/
    private static String CURRENT_TEXTURE_DIR_NAME = "うどんべいのパスタ退治";
    /**現在のテクスチャパックの名前*/
    private static String CURRENT_TEXTURE_NAME = "うどんべいのパスタ退治 デフォルト テクスチャパック";

    /**現在のテクスチャパックの<b>ディレクトリの名前</b>を返す。
     *
     * @return CURRENT_TEXTURE_DIR_NAMEの値
     */
    public static String getCurrentTextureDirName() {
        return CURRENT_TEXTURE_DIR_NAME;
    }

    /**現在のテクスチャパックの<b>名前</b>を返す。
     *
     * @return CURRENT_TEXTURE_NAMEの値
     */
    public static String getCurrentTextureName() {
        return CURRENT_TEXTURE_NAME;
    }

    /**現在のテクスチャパックを変更する。
     *
     * @param name 変更先の名前
     *
     * @throws TextureStructureException テクスチャパックの構成が誤っているとき
     * @throws TextureNotFoundException テクスチャパックが存在しないとき*/
    public static void setTexture(String name) throws TextureStructureException, TextureNotFoundException, IOException {
        //テクスチャパック フォルダのサブフォルダのリストを作成
        File texture_folder = new File(PathConsts.IMAGE_DIR.getPath());
        File[] textures = texture_folder.listFiles();

        for(File pack : Objects.requireNonNull(textures)){
            File info = new File(pack + "\\META-INF\\info.json");
            if(info.exists()){
                ObjectMapper mapper = new ObjectMapper();
                JsonNode r = mapper.readTree(info);
                JsonNode root = r.get("texturePack");

                String n = root.get("name").textValue();

                if(n.equals(name)) {
                    CURRENT_TEXTURE_NAME = n;
                    CURRENT_TEXTURE_DIR_NAME = pack.getName();
                    System.out.println(n);
                    return;
                }
            }
        }

        throw new TextureNotFoundException(name + "は存在しません。");
    }
}
