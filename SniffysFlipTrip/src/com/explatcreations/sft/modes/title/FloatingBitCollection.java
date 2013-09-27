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

package com.explatcreations.sft.modes.title;

import com.badlogic.gdx.graphics.Texture;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.graphics.SpriteBase;

import java.util.ArrayList;

/**
 * @author deweyvm
 */
public class FloatingBitCollection {
    private final Texture blueBitsClass = Game.assets.BlueBits;
    private final Texture yellowBitsClass = Game.assets.YellowBits;
    private static final int BitWidth = 8;
    private static final int BitHeight = 32;
    private static final int xOffset = 36;
    private static final int yOffset = 16;

    private static final int xOffset2 = 120;
    private static final int yOffset2 = 12;


    private final Point2i[] bluePositions = new Point2i[] {
        new Point2i(33 + xOffset, 126 + yOffset),
        new Point2i(42 + xOffset, 149 + yOffset),
        new Point2i(52 + xOffset, 111 + yOffset),
        new Point2i(60 + xOffset, 150 + yOffset),
        new Point2i(71 + xOffset, 122 + yOffset),
        new Point2i(77 + xOffset, 154 + yOffset),
        new Point2i(91 + xOffset, 135 + yOffset)
    };

    private final Point2i[] yellowPositions = new Point2i[] {
        new Point2i(219 + xOffset2, 72 + yOffset2),
        new Point2i(235 + xOffset2, 94 + yOffset2),
        new Point2i(239 + xOffset2, 66 + yOffset2),
        new Point2i(259 + xOffset2, 78 + yOffset2),
        new Point2i(274 + xOffset2, 62 + yOffset2),
        new Point2i(282 + xOffset2, 96 + yOffset2)
    };

    private ArrayList<SpriteBase> blueBits;
    private ArrayList<SpriteBase> yellowBits;
    private ArrayList<SpriteBase> allBits;
    private int t = 0;
    public FloatingBitCollection() {
        this.blueBits = initSprites(blueBitsClass, bluePositions);
        this.yellowBits = initSprites(yellowBitsClass, yellowPositions);
        final ArrayList<SpriteBase> all = new ArrayList<SpriteBase>(blueBits);
        all.addAll(yellowBits);
        allBits = all;
    }

    private ArrayList<SpriteBase> initSprites(Texture spriteClass, Point2i[] positions) {
        final ArrayList<SpriteBase> result = new ArrayList<SpriteBase>();
        for (int i = 0; i < positions.length; i += 1) {
            final Point2i point = positions[i];
            final SpriteBase nextSprite = Assets.makeSpriteFrame(spriteClass, BitWidth, BitHeight, i);
            nextSprite.x = point.x;
            nextSprite.y = point.y;
            result.add(nextSprite);
        }
        return result;
    }


    public void update() {
        t += 1;
        final float timeScale = 40.0f;
        for (SpriteBase bit:allBits) {
            final int maxOffset = 6;
            final float yOffset = maxOffset * (float)Math.sin(bit.x + t / timeScale);
            bit.offset = bit.offset.setY((int)yOffset);
        }
    }


    public void draw(float alpha) {
        for (SpriteBase blueBit:blueBits) {
            blueBit.setAlpha(alpha);
            blueBit.draw();
        }

        for (SpriteBase yellowBit:yellowBits) {
            yellowBit.setAlpha(alpha);
            yellowBit.draw();
        }
    }
}
