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
import com.explatcreations.sft.graphics.AnimatedSprite;

/**
 * @author deweyvm
 */
public class WallTile extends Tile {
    private static final Texture wallSpriteClass = Game.assets.WallTileGraphic;
    private static final Texture darkWallSpriteClass = Game.assets.DarkWallTileGraphic;
    private static final AnimatedSprite[] lightBlocks = initBlocks(wallSpriteClass);
    private static final AnimatedSprite[] darkBlocks = initBlocks(darkWallSpriteClass);
    private static final int NumBlocks = 5;
    private static final java.util.Random random = new java.util.Random(0);

    public static AnimatedSprite[] initBlocks(Texture spriteClass) {
        final AnimatedSprite[] result = new AnimatedSprite[NumBlocks];
        for (int i = 0; i < 5; i += 1) {
            final AnimatedSprite sprite = Assets.makeTileFrame(spriteClass, i);
            result[i] = sprite;
        }
        return result;
    }


    public WallTile(boolean isDark) {
        super(isDark?darkBlocks[random.nextInt(5)]:lightBlocks[random.nextInt(5)]);
    }


    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public boolean isPassable() {
        return false;
    }
}
