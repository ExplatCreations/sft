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
import com.explatcreations.sft.graphics.AnimatedSprite;

/**
 * @author deweyvm
 */
public class IceBlockTile extends Tile {
    private static final Texture spriteClass = Game.assets.IceBlockGraphic;
    private static final AnimatedSprite sprite = makeSprite();

    public static AnimatedSprite makeSprite() {
        return Assets.makeTileSprite(spriteClass);
    }

    public IceBlockTile() {
        super(sprite);
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public void draw(int i, int j, Point2i offset) {
        FloorTile.drawFloorTileSprite(i, j, offset);
        super.draw(i, j, offset);
    }
}
