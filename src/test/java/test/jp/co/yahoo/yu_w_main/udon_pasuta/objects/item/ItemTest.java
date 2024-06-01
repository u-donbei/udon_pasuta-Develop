/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package test.jp.co.yahoo.yu_w_main.udon_pasuta.objects.item;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Party;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Item;

import java.net.URI;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest extends Application {
    public void testInstance() {
        TestItem testItem = new TestItem();

        assertEquals("testItem", testItem.getName());
        assertEquals("test用のアイテムです。", testItem.getDescription());

        assertEquals(Paths.get(URI.create(PathConsts.ITEM_MODEL_DIR.makeURI() + "/testItem.json")), testItem.getModelPath());
        assertEquals("C:\\Users\\user\\AppData\\Roaming\\うどんべいのパスタ退治\\Textures\\うどんべいのパスタ退治\\debug.png", testItem.getImage().toString());
    }

    @Override
    public void start(Stage stage) throws Exception {
        testInstance();
        Platform.exit();
    }
}
class TestItem extends Item {

    @Override
    public void use(Party<Udon> targets) {

    }

    /**
     * コンストラクタ。
     */
    public TestItem() {
        super(Paths.get(URI.create(PathConsts.ITEM_MODEL_DIR.makeURI() + "/testItem.json")));
    }
}
