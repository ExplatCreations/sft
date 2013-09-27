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

package com.explatcreations.sft.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.graphics.StaticSprite;

/**
 * @author deweyvm
 */
public class MiniTile {
    public static final int Size = Tile.Size/2;
    private StaticSprite sprite;
    private static StaticSprite floor;
    public MiniTile(StaticSprite sprite) {
        if (floor == null) {
            floor = FloorTile.makeStaticSprite().copy();
            floor.setScale(scale, scale);
        }
        this.sprite = sprite.copy();
        this.sprite.setScale(scale, scale);
    }

    private static final float scale = (float)Size/Tile.Size;

    public static void drawSprite(SpriteBase sprite, int i, int j, Point2i root) {
        sprite.x = i*Size + root.x;
        sprite.y = j*Size + root.y;
        sprite.draw();
    }

    public static SpriteBase createSmallSprite(SpriteBase sprite) {
        final TextureRegion region = sprite.getRegion();
        final StaticSprite result = new StaticSprite(region);
        result.setScale(scale, scale);
        return result;
    }

    public void draw(int i, int j, Point2i root) {
        drawSprite(floor, i, j, root);
        drawSprite(sprite, i, j, root);
    }
}
