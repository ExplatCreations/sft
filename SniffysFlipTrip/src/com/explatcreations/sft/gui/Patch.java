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

package com.explatcreations.sft.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.graphics.StaticSprite;

/**
 * @author moopslc
 */
public class Patch extends SpriteBase {
    private static final Texture parchmentClass = Game.assets.Parchment16x16Graphic;
    public static final int ParchmentSize = 16;

    private static final Texture charredClass = Game.assets.Parchment8x8CharredGraphic;
    public static final int CharredSize = 8;

    private static final Texture smallParchmentClass = Game.assets.Parchment8x8Graphic;
    public static final int SmallParchmentSize = 8;

    private static final Texture keyBoxClass = Game.assets.KeyBorderGraphic;
    private static final Texture keyBoxParchmentClass = Game.assets.KeyBorderParchmentGraphic;
    public static final int KeyBoxSize = 8;

    private final int cols;
    private final int rows;
    private final int size;
    public int x;
    public int y;
    private StaticSprite[][] sprites;
    public Patch(int size, int width, int height) {
        super(0, 0);
        this.size = size;
        this.cols = Math.max(3, nextMultiple(width, size));
        this.rows = Math.max(3, nextMultiple(height, size));
    }

    public static int nextMultiple(int value, int multiple) {
        return (int)Math.ceil(value / multiple);
    }

    @Override
    public TextureRegion getRegion() {
        throw new RuntimeException("called getRegion on Patch");
    }

    public Patch darken(float amount) {
        final Color color = new Color(amount, amount, amount, 1);
        for (int i = 0; i < cols; i += 1) {
            for (int j = 0; j < rows; j += 1) {
                sprites[i][j].setColor(color);
            }
        }
        return this;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setAlpha(float alpha) {
        for (StaticSprite[] spriteA:sprites) {
            for (StaticSprite s : spriteA) {
                s.setAlpha(alpha);
            }
        }
    }

    @Override
    public void setColor(Color color) {
        for (StaticSprite[] spriteA:sprites) {
            for (StaticSprite s : spriteA) {
                s.setColor(color);
            }
        }
    }

    @Override
    public int getWidth() {
        return cols*size;
    }

    @Override
    public int getHeight() {
        return rows*size;
    }

    private static Patch makeNew(Texture patchClass, int size, int width, int height) {
        final Patch result = new Patch(size, width, height);
        result.setSprites(makeSprites(patchClass, size, result.cols, result.rows));
        return result;
    }

    public void setSprites(StaticSprite[][] sprite) {
        this.sprites = sprite;
    }

    private static final int TopLeft = 0;
    private static final int TopMid = 1;
    private static final int TopRight = 2;
    private static final int Left = 3;
    private static final int Mid = 4;
    private static final int Right = 5;
    private static final int BottomLeft = 6;
    private static final int BottomMid = 7;
    private static final int BottomRight = 8;

    private static StaticSprite[][] makeSprites(Texture patchClass,
                                                int size,
                                                int cols,
                                                int rows) {
        final StaticSprite[][] result = new StaticSprite[cols][rows];
        final TextureRegion[] stamps = makeStamps(size, patchClass);
        for(int i = 0; i < cols; i += 1) {
            for (int j = 0; j < rows; j += 1) {
                final int index;
                if (i == 0 && j == 0) { //TopLeft
                    index = TopLeft;
                } else if (i == cols - 1 && j == 0) { //TopRight
                    index = TopRight;
                } else if (j == 0) { //TopMid
                    index = TopMid;
                } else if (i == 0 && j == rows - 1) { //BottomLeft
                    index = BottomLeft;
                } else if (i == cols - 1 && j == rows - 1) { //BottomRight
                    index = BottomRight;
                } else if (j == rows - 1) { //BottomMid
                    index = BottomMid;
                } else if (i == 0) { //Left
                    index = Left;
                } else if (i == cols - 1) { //Right
                    index = Right;
                } else { //mid
                    index = Mid;
                }
                final StaticSprite next =  new StaticSprite(stamps[index]);
                result[i][j] = next;
                final int x = i*size;
                final int y = j*size;
                next.x = x;
                next.y = y;
            }
        }
        return result;
    }

    public static Patch makeCharred(int width, int height) {
        return Patch.makeNew(charredClass, CharredSize, width, height);
    }

    public static Patch makeParchment(int width, int height) {
        return Patch.makeNew(parchmentClass, ParchmentSize, width, height);
    }

    public static Patch makeSmallParchment(int width, int height) {
        return Patch.makeNew(smallParchmentClass, SmallParchmentSize, width, height);
    }

    public static Patch makeKeyBox(int width, int height, boolean isOnParchment) {
        final Texture t = isOnParchment? keyBoxParchmentClass : keyBoxClass;
        return Patch.makeNew(t, KeyBoxSize, width, height);
    }

    private static TextureRegion[] makeStamps(int tileSize, Texture spriteClass) {
        final int numStamps = 9;
        final TextureRegion[] result = new TextureRegion[numStamps];

        for (int i = 0; i < numStamps; i += 1) {
            final int x = (i % 3) * tileSize;
            final int y = (i / 3) * tileSize;
            final TextureRegion next = Assets.makeTextureRegion(spriteClass, x, y, tileSize, tileSize);
            result[i] = next;
        }
        return result;
    }

    @Override
    public void update() {

    }

    @Override
    public void drawSelf(float x, float y) {
        this.x = (int)x;
        this.y = (int)y;
        draw(Point2i.Zero);
    }

    public void draw(Point2i offset) {
        for(int i = 0; i < cols; i += 1) {
            for (int j = 0; j < rows; j += 1) {
                sprites[i][j].drawWithOffset(offset.add(x, y));
            }
        }
    }
}
