/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.managers;

import javafx.geometry.Bounds;
import jp.co.yahoo.yu_w_main.udon_pasuta.annotations.Manager;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.Facing;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.GameObject;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.block.field.Field;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Character;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;

import java.util.Arrays;
import java.util.List;

@Manager
public final class CollisionManager {
    private CollisionManager() {
    }

    public static void collision(Character target, GameObject[][] objects) {
		System.out.println("coll call objs");
        Arrays.stream(objects).forEach(objs -> Arrays.stream(objs).forEach(obj -> {
            if (obj == null) return;
            if (obj == target) return;

            if (target.isHit(obj) && (!(obj instanceof Character) || ((Character) obj).getHitPriority() < target.getHitPriority())) {
                Bounds objb = obj.getImageView().getBoundsInParent();
                Bounds tb = target.getImageView().getBoundsInParent();
				System.out.println("hit");
				System.out.println(target.facing);
                if (target.facing == Facing.NORTH) {
                    if (GameManager.udon.getY() > 250 && target instanceof Udon) {
                        GameManager.screenY += objb.getMaxY() - tb.getMinY() + 1;
                    }
                    target.moveY(objb.getMaxY() - tb.getMinY() + 1);
                } else if (target.facing == Facing.SOUTH) {
                    if (GameManager.udon.getY() > 250 && target instanceof Udon) {
                        GameManager.screenY -= tb.getMaxY() - objb.getMinY() + 1;
                    }
                    target.moveY(-(tb.getMaxY() - objb.getMinY() + 1));
                } else if (target.facing == Facing.EAST) {
                    if(GameManager.udon.getX() > 300 && target instanceof Udon) {
                        GameManager.screenX -= tb.getMaxX() - objb.getMinX() + 1;
                    }
                    target.moveX(-(tb.getMaxX() - objb.getMinX() + 1));
                } else if (target.facing == Facing.WEST) {
                    if (GameManager.udon.getX() > 400 && target instanceof Udon) {
                        GameManager.screenX += objb.getMaxX() - tb.getMinX() + 1;
                    }
                    target.moveX(objb.getMaxX() - tb.getMinX() + 1);
                }
            }
        }));
    }

    public static void collision(Character target, List<? extends GameObject> objects) {
		System.out.println("coll call");
        objects.forEach(obj -> {
            if (obj == null) return;
            if (obj instanceof Field) return;
            if (obj == target) return;

            if (target.isHit(obj) && (!(obj instanceof Character) || ((Character) obj).getHitPriority() < target.getHitPriority())) {
				System.out.println("hit:" + target);
                Bounds objb = obj.getImageView().getBoundsInParent();
                Bounds tb = target.getImageView().getBoundsInParent();
				System.out.println(target.facing);
                if (target.facing == Facing.NORTH) {
                    if (GameManager.udon.getY() > 250 && target instanceof Udon) {
                        GameManager.screenY += objb.getMaxY() - tb.getMinY() + 1;
                    }
                    target.moveY(objb.getMaxY() - tb.getMinY() + 1);
                } else if (target.facing == Facing.SOUTH) {
                    if (GameManager.udon.getY() > 250 && target instanceof Udon) {
                        GameManager.screenY -= tb.getMaxY() - objb.getMinY() + 1;
                    }
                    target.moveY(-(tb.getMaxY() - objb.getMinY() + 1));
                } else if (target.facing == Facing.EAST) {
                    if(GameManager.udon.getX() > 300 && target instanceof Udon) {
                        GameManager.screenX -= tb.getMaxX() - objb.getMinX() + 1;
                    }
                    target.moveX(-(tb.getMaxX() - objb.getMinX() + 1));
                } else if (target.facing == Facing.WEST) {
                    if (GameManager.udon.getX() > 400 && target instanceof Udon) {
                        GameManager.screenX += objb.getMaxX() - tb.getMinX() + 1;
                    }
                    target.moveX(objb.getMaxX() - tb.getMinX() + 1);
                }
            }
        });
    }
}
