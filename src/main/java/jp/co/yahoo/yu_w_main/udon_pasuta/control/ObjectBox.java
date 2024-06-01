/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.control;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.font.Fonts;
import jp.co.yahoo.yu_w_main.udon_pasuta.logging.Logging;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.GameObject;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Empty;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ObjectBox<E extends GameObject> extends StackPane {
    private SimpleObjectProperty<E> element;
    private List<SimpleObjectProperty<E>> elements;

    public ObjectBox(E element){
        setMaxSize(30, 30);
        this.element = new SimpleObjectProperty<>(this, "element", element);
        elements = new ArrayList<>();
        elements.add(this.element);
        setStyle("-fx-background-color: gray;" +
                "-fx-border-color: white;" +
                "-fx-border-insets: 1;");
        this.element.set(element);
        Image image = new Image(this.element.get().getImage().toURI().toString(), 30, 30, true, true);
        getChildren().add(new ImageView(image));
        Tooltip t = new Tooltip(this.element.get().getName());
        t.setFont(Fonts.getPixelMplus12(10));
        Tooltip.install(this, t);
    }

    @SuppressWarnings("unchecked")
    public ObjectBox(){
        this((E)E.getEmptyInstance());
    }

    public E getElement() {
        return element.get();
    }

    public void setElement(E element) {
        this.element.set(element);
        getChildren().clear();
        Image image = new Image(this.element.get().getImage().toURI().toString(), 30, 30, true, true);
        getChildren().add(new ImageView(image));
        Tooltip t = new Tooltip(this.element.get().getName());
        t.setFont(Fonts.getPixelMplus12(10));
        Tooltip.install(this, t);
    }

    public void addElement(E element) {
        
    }
}
