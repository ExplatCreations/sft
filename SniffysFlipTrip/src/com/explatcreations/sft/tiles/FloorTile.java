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

import com.badlogic.gdx.graphics.Texture;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.data.Recti;
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.graphics.StaticSprite;

/**
 * @author deweyvm
 */
public class FloorTile extends Tile {
    private static Texture spriteClass = Game.assets.FloorTileGraphic;
    public final static AnimatedSprite sprite = makeSprite();

    public static AnimatedSprite makeSprite() {
        return Assets.makeTileSprite(spriteClass);
    }

    public static StaticSprite makeStaticSprite() {
        return Assets.makeStaticSprite(spriteClass, new Recti(0, 0, Tile.Size, Tile.Size));
    }

    public FloorTile() {
        super(sprite);
    }

    /**
     * Often useful for tiles which need to draw a background behind them.
     */
    public static void drawFloorTileSprite(int i, int j, Point2i offset) {
        drawSprite(sprite, i, j, offset);
    }
}
