/*****************************************************************************
 * Copyright 2013, see AUTHORS file.
 *
 * This file is part of Sniffy's Flip Trip.
 *
 * Sniffy's Flip Trip is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Sniffy's Flip Trip is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Sniffy's Flip Trip.
 * If not, see <http://www.gnu.org/licenses/>.
 ****************************************************************************/

package com.explatcreations.sft.grid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.enums.KeyColor;
import com.explatcreations.sft.enums.MagicType;
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.graphics.TextSprite;

import java.util.HashMap;
import java.util.Map;

/**
 * @author moopslc
 */
public class Inventory {
    private static final Texture magicIconClass = Game.assets.MagicIcons;
    private static final Texture keyIconClass = Game.assets.KeyIcons;

    private final Map<KeyColor, Integer> keys = new HashMap<KeyColor, Integer>();
    private final Map<MagicType, Integer> magic= new HashMap<MagicType, Integer>();

    private static Map<KeyColor, AnimatedSprite> keyIcons;
    private static Map<MagicType, AnimatedSprite> magicIcons;

    private TextSprite magicText;
    private TextSprite keyText;
    private TextSprite stepText;
    private IGridView grid;

    private int keyStart;
    private int magicStart;


    private void initItems() {
        for (KeyColor key : KeyColor.values()) {
            keys.put(key, 0);
        }

        for (MagicType m: MagicType.values()) {
            magic.put(m, 0);
        }
    }

    private static AnimatedSprite makeIcon(Texture spriteClass, int frame) {
        return makeIcon(spriteClass, frame, 0);
    }

    private static AnimatedSprite makeIcon(Texture spriteClass, int frame, int heightIncrease) {
        return Assets.makeSpriteFrame(spriteClass, 8, 8 + heightIncrease, frame);
    }

    private static void initIcons() {
        magicIcons = new HashMap<MagicType, AnimatedSprite>();
        magicIcons.put(MagicType.Fire, makeIcon(magicIconClass, 0));
        magicIcons.put(MagicType.Earth, makeIcon(magicIconClass, 1));
        magicIcons.put(MagicType.Wind, makeIcon(magicIconClass, 2));
        magicIcons.put(MagicType.Ice, makeIcon(magicIconClass, 3));

        keyIcons = new HashMap<KeyColor, AnimatedSprite>();
        keyIcons.put(KeyColor.Red, makeIcon(keyIconClass, 0, 1));
        keyIcons.put(KeyColor.Yellow, makeIcon(keyIconClass, 1, 1));
        keyIcons.put(KeyColor.Green, makeIcon(keyIconClass, 2, 1));
        keyIcons.put(KeyColor.Blue, makeIcon(keyIconClass, 3, 1));
    }

    public Inventory(IGridView grid, int maxSteps) {
        initItems();
        if (keyIcons == null || magicIcons == null) {
            initIcons();
        }
        this.grid = grid;

        final int height = Game.RenderHeight;
        final int xStart = 45;
        this.magicStart = xStart;
        this.magicText = new TextSprite(xStart, height - 12, "magic: ");
        this.keyStart = xStart + 145;
        this.keyText = new TextSprite(keyStart, height - 12, "keys: ");
        for (MagicType type : MagicType.values()) {
            magicIcons.get(type).y = height - 10;
        }
        for (KeyColor color : KeyColor.values()) {
            keyIcons.get(color).y = height - 10;
        }
        this.stepText = new TextSprite(xStart + 300, height - 12, "steps: ");
    }

    public boolean hasAnyMagic() {
        for  (MagicType type : MagicType.values()) {
            if (hasMagic(type)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasMagic(MagicType type) {
        return magic.get(type) > 0;
    }

    public void update() {
        stepText.setText(getStepText());
        stepText.update();
    }

    private String getStepText() {
        return String.format("steps: %4d", grid.getSteps());
    }

    public void drawKeyIcons(int xStart, Point2i offset) {
        int xIndex = 0;
        for  (KeyColor key : KeyColor.values()) {
            for (int i = 0; i < keys.get(key); i += 1) {
                final AnimatedSprite sprite = keyIcons.get(key);
                sprite.offset = offset;
                sprite.x = xStart + xIndex * 9;
                sprite.draw();
                xIndex += 1;
            }
        }
    }

    public void drawMagicIcons(int xStart, Point2i offset) {
        int xIndex = 0;
        for (MagicType m : MagicType.values()) {
            for (int i = 0; i < magic.get(m); i += 1) {
                final AnimatedSprite sprite = magicIcons.get(m);
                sprite.offset = offset;
                sprite.x = xStart + xIndex * 9;
                sprite.draw();
                xIndex += 1;
            }
        }
    }

    private static <K> void change(Map<K, Integer> map, K key, int amount) {
        Integer value = map.get(key);
        if (value == null) {
            value = 0;
        }

        map.put(key, MathUtils.clamp(value + amount, 0, 999));

    }

    public void addKey(KeyColor type) {
        change(keys, type, 1);
    }

    public void addMagic(MagicType type) {
        change(magic, type, 1);
    }


    public void removeKey(KeyColor type) {
        change(keys, type, -1);
    }

    public void removeMagic(MagicType type) {
        change(magic, type, -1);
    }

    public void spendMagic(MagicType type) {
        removeMagic(type);
    }

    public boolean hasKey(KeyColor key) {
        return keys.get(key) > 0;
    }

    public void draw(Point2i offset) {
        drawKeyIcons(keyStart + keyText.getWidth(), offset);
        drawMagicIcons(magicStart + magicText.getWidth(), offset);

        stepText.drawWithOffset(offset);
        magicText.drawWithOffset(offset);
        keyText.drawWithOffset(offset);
    }
}
