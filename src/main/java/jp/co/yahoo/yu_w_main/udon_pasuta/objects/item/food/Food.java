/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.food;

import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Character;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.item.Item;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public abstract class Food extends Item {
    public int getNutrition() {
        return nutrition;
    }

    public void setNutrition(int nutrition) {
        this.nutrition = nutrition;
    }

    public Class<? extends Character>[] getTargetClass() {
        return targetClass;
    }

    @SafeVarargs
    public final void setTargetClass(Class<? extends Character>... targetClass) {
        this.targetClass = targetClass;
    }

    private int nutrition;
    private Class<? extends Character>[] targetClass;
    @SafeVarargs
    public Food(Path model, int nutrition, @NotNull Class<? extends Character>... targetClass) {
        super(model);
        this.nutrition = nutrition;
        this.targetClass = targetClass;
    }
}
