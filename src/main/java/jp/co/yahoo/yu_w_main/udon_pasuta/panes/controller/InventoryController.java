/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.panes.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.Message;
import jp.co.yahoo.yu_w_main.udon_pasuta.control.ObjectBox;
import jp.co.yahoo.yu_w_main.udon_pasuta.dto.Party;
import jp.co.yahoo.yu_w_main.udon_pasuta.font.Fonts;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.ally.Ebi;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.ally.Onigiri;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.ally.Soba;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.ally.Somen;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Daikon;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Empty;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Item;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Negi;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.food.*;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.tool.EmptyTool;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.tool.Oroshigane;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.tool.TestTool;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.tool.Tool;

import java.net.URI;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class InventoryController {

	private static InventoryController instance;
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private TitledPane ebiten;
	@FXML
	private Label hp;
	@FXML
	private ProgressBar hpBar;
	@FXML
	private Label lvl;
	@FXML
	private ProgressBar lvlBar;
	@FXML
	private Label inventory;
	@FXML
	private TitledPane onigiri;
	@FXML
	private Label party;
	@FXML
	private TitledPane soba;
	@FXML
	private TitledPane soumen;
	@FXML
	private Label status;
	@FXML
	private Label xp;
	@FXML
	private ProgressBar xpBar;
	@FXML
	private Label item;
	@FXML
	private HBox itemBoxs;
	@FXML
	private Label tool;
	@FXML
	private HBox toolBoxs;
	@FXML
	private Label tenkasu;
	@FXML
	private Label saifu;
	private ObservableList<ObjectBox<Item>> items;
	private ObservableList<ObjectBox<Tool>> tools;
	private ImageView ebitenv, sobav, somenv, onigiriv;
	private SimpleIntegerProperty h, x;

	private SimpleObjectProperty<Item[]> itemsp;
	private SimpleObjectProperty<Tool[]> toolsp;

	public InventoryController() {
	}

	public static InventoryController getInstance() {
		return instance;
	}

	public Label getTenkasu() {
		return tenkasu;
	}

	@FXML
	@SuppressWarnings("unchecked")
	void initialize() {
		instance = this;
		hp.setFont(Fonts.PIXEL_MPLUS_12_18);
		xp.setFont(Fonts.PIXEL_MPLUS_12_18);
		lvl.setFont(Fonts.PIXEL_MPLUS_12_18);
		inventory.setFont(Fonts.PIXEL_MPLUS_12_18);
		soba.setFont(Fonts.PIXEL_MPLUS_12_10);
		onigiri.setFont(Fonts.PIXEL_MPLUS_12_10);
		soumen.setFont(Fonts.PIXEL_MPLUS_12_10);
		ebiten.setFont(Fonts.PIXEL_MPLUS_12_10);
		party.setFont(Fonts.PIXEL_MPLUS_12_18);
		status.setFont(Fonts.PIXEL_MPLUS_12_18);
		item.setFont(Fonts.PIXEL_MPLUS_12_18);
		tool.setFont(Fonts.PIXEL_MPLUS_12_18);
		tenkasu.setFont(Fonts.PIXEL_MPLUS_12_18);
		saifu.setFont(Fonts.PIXEL_MPLUS_12_18);

		items = FXCollections.observableArrayList(
				new ObjectBox<>(new Daikon()),
				new ObjectBox<>(new Komugiko()),
				new ObjectBox<>(new Nori()),
				new ObjectBox<>(new KizamiNori()),
				new ObjectBox<>(new Negi()),
				new ObjectBox<>(new KizamiNegi()),
				new ObjectBox<>(new Agedama()),
				new ObjectBox<>(new Agedama()),
				new ObjectBox<>(new Agedama()),
				new ObjectBox<>(new Agedama()));

		tools = FXCollections.observableArrayList(
				new ObjectBox<>(new EmptyTool()),
				new ObjectBox<>(new Oroshigane()),
				new ObjectBox<>(new TestTool()),
				new ObjectBox<>(new TestTool()),
				new ObjectBox<>(new TestTool()),
				new ObjectBox<>(new TestTool()),
				new ObjectBox<>(new TestTool()),
				new ObjectBox<>(new TestTool()),
				new ObjectBox<>(new TestTool()),
				new ObjectBox<>(new TestTool()));


		ebitenv = new ImageView(URI.create(PathConsts.TEXTURE_DIR.makeURI() + "/ebi.png").toString());
		sobav = new ImageView(URI.create(PathConsts.TEXTURE_DIR.makeURI() + "/soba.png").toString());
		somenv = new ImageView(URI.create(PathConsts.TEXTURE_DIR.makeURI() + "/soumen.png").toString());
		onigiriv = new ImageView(URI.create(PathConsts.TEXTURE_DIR.makeURI() + "/onigiri.png").toString());

		ebiten.setContent(ebitenv);
		soba.setContent(sobav);
		soumen.setContent(somenv);
		onigiri.setContent(onigiriv);
	}

	public void init(Party<Udon> party) {
		final Udon udon = party.getLeader();
		hp.setText("HP:" + udon.getHp());
		hpBar.setProgress((double) udon.getHp() / Udon.getByLevelMaxHP(udon.getLevel()));
		xp.setText("XP:" + udon.getXP());
		xpBar.setProgress(udon.getXP() / (double) 1000 * udon.getLevel());
		lvl.setText("Lvl:" + udon.getLevel());
		lvlBar.setProgress((double) udon.getLevel() / 8);

		h = new SimpleIntegerProperty(udon.getHp());
		x = new SimpleIntegerProperty(udon.getXP());

		udon.hpProperty().addListener((ob, o, n) -> {
			hp.setText("HP:" + n);
			hpBar.setProgress((int) n / (double) Udon.getByLevelMaxHP(udon.getLevel()));
		});


		udon.xpProperty().addListener((ob, o, n) -> {
			xp.setText("XP:" + n);
			xpBar.setProgress((int) n / ((double) 1000 * udon.getLevel()));
		});

		udon.levelProperty().addListener((ob, o, n) -> {
			lvl.setText("Lvl:" + n);
			lvlBar.setProgress((double) n / 8);
		});

		//パーティの表示を初期化
		try {
			party.members().stream().filter(t -> t instanceof Onigiri)	//おにぎりくんを抽出
					.limit(1)
					.toList().getFirst().aliveProperty().addListener((ob, o, n) -> {
						System.out.println("onigiri alive:" + n);
						if (!n) {
							onigiriv.setBlendMode(BlendMode.DIFFERENCE);
						}
					});
		} catch (NoSuchElementException e) {	//おにぎりくんがいない場合
			//何もしない
		}

		try {
			party.members().stream().filter(t -> t instanceof Soba)	//そばさんを抽出
					.limit(1)
					.toList().getFirst().aliveProperty().addListener((ob, o, n) -> {
						System.out.println("soba alive:" + n);
						if (!n) {
							sobav.setBlendMode(BlendMode.DIFFERENCE);
						}
					});
		} catch (NoSuchElementException e) {	//そばさんがいない場合
			//何もしない
		}

		try {
			party.members().stream().filter(t -> t instanceof Somen)	//そうめんくんを抽出
					.limit(1)
					.toList().getFirst().aliveProperty().addListener((ob, o, n) -> {
						if (!n) {
							somenv.setBlendMode(BlendMode.DIFFERENCE);
						}
					});
		} catch (NoSuchElementException e) {	//そうめんくんがいない場合
			//何もしない
		}

		try {
			party.members().stream().filter(t -> t instanceof Ebi)	//えびてんさんを抽出
					.limit(1)
					.toList().getFirst().aliveProperty().addListener((ob, o, n) -> {
						if (!n) {
							ebitenv.setBlendMode(BlendMode.DIFFERENCE);
						}
					});
		} catch (NoSuchElementException e) {	//えびてんさんがいない場合
			//何もしない
		}
	}

	public void init(Party<Udon> party, Message message) {
		final Udon udon = party.getLeader();
		hp.setText("HP:" + udon.getHp());
		hpBar.setProgress((double) udon.getHp() / Udon.getByLevelMaxHP(udon.getLevel()));
		xp.setText("XP:" + udon.getXP());
		xpBar.setProgress((udon.getXP() / (double) 1000 * udon.getLevel()) / 10);
		lvl.setText("Lvl:" + udon.getLevel());
		lvlBar.setProgress((double) udon.getLevel() / 8);

		h = new SimpleIntegerProperty(udon.getHp());
		x = new SimpleIntegerProperty(udon.getXP());

		itemsp = new SimpleObjectProperty<>(udon.getItems());
		toolsp = new SimpleObjectProperty<>(udon.getTools());


		udon.hpProperty().addListener((ob, o, n) -> {
			hp.setText("HP:" + n);
			hpBar.setProgress((int) n / (double) Udon.getByLevelMaxHP(udon.getLevel()));
		});


		udon.xpProperty().addListener((ob, o, n) -> {
			xp.setText("XP:" + n);
			xpBar.setProgress((int) n / (double) 1000 * udon.getLevel());
		});

		udon.levelProperty().addListener((ob, o, n) -> {
			lvl.setText("Lvl:" + n);
			lvlBar.setProgress((double) n / 8);
		});


		for (int i = 0; i < udon.getItems().length; i++) {
			ObjectBox<Item> item = new ObjectBox<>(udon.getItems()[i]);
			itemBoxs.getChildren().add(item);
			item.setFocusTraversable(true);
			int finalI = i;
			item.setOnMouseClicked(e -> {
				if (!(item.getElement() instanceof Empty)) {
					item.getElement().preUse(message, party, () -> {
						udon.setItem(finalI, new Empty());
					});
				}
			});
		}

		for (int i = 0; i < udon.getItems().length; i++) {
			ObjectBox<Tool> item = new ObjectBox<>(udon.getTools()[i]);
			toolBoxs.getChildren().add(item);
			item.setFocusTraversable(true);
			int finalI = i;
			item.setOnMouseClicked(e -> {
				if (!(item.getElement() instanceof EmptyTool)) {
					item.getElement().preUse(message, party, () -> {
						udon.setTool(finalI, new EmptyTool());
					});
				}
			});
		}
	}

	public void updateItem(Item[] items) {
		System.out.println("updateItem");
		for (int j = 0; j < itemBoxs.getChildren().size(); j++) {
			ObjectBox<Item> i = (ObjectBox<Item>) itemBoxs.getChildren().get(j);
			i.setElement(items[j]);
		}
	}

	public void updateTool(Tool[] tools) {
		System.out.println("updateTool");
		for (int j = 0; j < toolBoxs.getChildren().size(); j++) {
			ObjectBox<Tool> i = (ObjectBox<Tool>) toolBoxs.getChildren().get(j);
			i.setElement(tools[j]);
		}
	}
}

