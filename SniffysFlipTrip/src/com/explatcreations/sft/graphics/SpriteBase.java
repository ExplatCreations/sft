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

package com.explatcreations.sft.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.gui.Patch;

/**
 * @author deweyvm
 */
public abstract class SpriteBase implements Sprite2d {
    private final int width;
    private final int height;

    public int x;
    public int y;

    private int rectX;
    private int rectY;

    public Point2i offset = Point2i.Zero;

    public SpriteBase(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public abstract void setAlpha(float alpha);
    public abstract void setColor(Color color);


    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public SpriteBase makeHighlight() {
        final SpriteBase result = new BatWing();
        result.x = x - 22;
        result.y = y - 9;
        return result;
    }

    public SpriteBase makeKeyBorder(boolean isOnParchment) {
        return Patch.makeKeyBox(getWidth() + 20, getHeight(), isOnParchment);
    }

    public SpriteBase makeJoyBorder(boolean isOnParchment) {
        final Texture t = isOnParchment? Game.assets.JoyBorderParchmentGraphic : Game.assets.JoyBorderGraphic;
        return Assets.makeSpriteFrame(t, t.getWidth(), t.getHeight(), 0);
    }

    @Override
    public void draw() {
        drawInternal(x + offset.x, y + offset.y);
    }

    public void drawWithOffset(Point2i addedOffset) {
        drawInternal(x + offset.x + addedOffset.x, y + offset.y + addedOffset.y);
    }

    private void drawInternal(float x, float y) {
        rectX = (int)x;
        rectY = (int)y;
        drawSelf(x, y);
    }

    public abstract void drawSelf(float x, float y);

    public abstract TextureRegion getRegion();
}
