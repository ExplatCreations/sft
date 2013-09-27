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
 * @author moopslc
 */
public class MeltingIceBlockTile extends Tile {
    private static final Texture iceBlocKSpriteClass = Game.assets.IceBlockGraphic;

    private static AnimatedSprite makeSprite() {
        final AnimatedSprite sprite = Assets.makeTileSprite(iceBlocKSpriteClass);
        final String meltName = "melt";
        sprite.addAnimation(meltName, 7, new int[]{0, 1, 2, 3, 4, 5}, false);
        sprite.play(meltName);
        return sprite;
    }

    private boolean isMelting = true;

    public MeltingIceBlockTile() {
        super(makeSprite());
    }

    @Override
    public void update() {
        super.update();
        if (isMelting && getSprite().isFinished()) {
            isMelting = false;
        }
    }

    @Override
    public boolean isWalkable() {
        return !isMelting;
    }

    @Override
    public void draw(int i, int j, Point2i offset) {
        FloorTile.drawFloorTileSprite(i, j, offset);
        super.draw(i, j, offset);
    }

}
