/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Party;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.GameObject;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.Usable;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**アイテムを表すクラス。
 *
 * @author 渡邉雄宇
 * */
public abstract class Item extends GameObject implements Usable {
    public Path getModelPath() {
        return modelPath;
    }

    /**モデルのパス*/
    private Path modelPath;
    /**アイテムのモデルのデータ*/
    private ModelData data;
    /**アイテムの説明(デフォルトは"")*/
    protected String description;


    /**アイテムのID*/
    protected String id;

    /**コンストラクタ。*/
    public Item(Path model) {
        super(model.toFile());
        initModel(model);
    }

    @Override
    public String toString() {
        return "Item[modelPath=" + modelPath + ", data" + data  +"]\n";
    }

    /**説明を取得する。
     *
     * @return 説明*/
    public String getDescription() {
        return description;
    }

    /**説明を設定する。
     *
     * @param description 説明*/
    public void setDescription(String description) {
        this.description = description;
    }

    private void initModel(Path model) {
        //modelPathを設定
        this.modelPath = model;
        //JSONを読み取る
        ObjectMapper mapper = new ObjectMapper();
        try {
            data = mapper.readValue(model.toFile(), ModelData.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //フィールドをJSONの値に設定する
        this.description = this.data.description;
        this.id = this.data.id;
        setName(this.data.name);
        this.setImage(new File(PathConsts.TEXTURE_DIR.getPath() + this.data.image));
    }


    /**モデルのデータを表すクラス。*/
    public static class ModelData implements Cloneable{
        public String name;
        public String description;
        public String image;
        public String id;

        @Override
        public ModelData clone() {
            try {
                ModelData clone = (ModelData) super.clone();
                clone.description = this.description;
                clone.name = this.name;
                clone.image = this.image;
                clone.id = this.id;
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }

        @Override
        public String toString() {
            return "{name=" + name + ", description=" + description + ", image=" + image + ", id=" + id + "}";
        }
    }

    @Override
    public void preUse(Message message, Party<Udon> targets, Runnable exited) {
        message.reText(getName() + " 説明:" + getDescription(), null, 50, () -> {
        }, () -> {}, true);
    }
}
