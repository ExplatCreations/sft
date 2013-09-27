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
import com.explatcreations.sft.enums.MagicType;
import com.explatcreations.sft.graphics.AnimatedSprite;

/**
 * @author moopslc
 */
public class MagicTile extends Tile {
    private static final Texture redSpriteClass = Game.assets.FirePickupGraphic;
    private static final Texture yellowSpriteClass = Game.assets.EarthPickupGraphic;
    private static final Texture greenSpriteClass = Game.assets.WindPickupGraphic;
    private static final Texture blueSpriteClass = Game.assets.IcePickupGraphic;

    public static AnimatedSprite makeSprite(MagicType type) {
        Texture spriteClass;
        int[] frames;
        if (type == MagicType.Fire) {
            spriteClass = redSpriteClass;
            frames = new int[]{0, 1, 2};
        } else if (type == MagicType.Earth) {
            spriteClass = yellowSpriteClass;
            frames = new int[]{0, 1, 2, 3, 4};
        } else if (type == MagicType.Wind) {
            spriteClass = greenSpriteClass;
            frames = new int[]{0, 1, 2, 3, 4, 5, 6};
        } else {
            spriteClass = blueSpriteClass;
            frames = new int[]{0, 1, 2, 3, 4, 5, 6};
        }
        final AnimatedSprite sprite = Assets.makeTileSprite(spriteClass);
        final String name = "play";
        sprite.addAnimation(name, 10, frames, true);
        sprite.play(name);
        return sprite;
    }

    private final MagicType type;
    public MagicTile(MagicType type) {
        super(makeSprite(type));
        this.type = type;
    }

    public MagicType getType() {
        return type;
    }

    @Override
    public void draw(int i, int j, Point2i offset) {
        FloorTile.drawFloorTileSprite(i, j, offset);
        super.draw(i, j, offset);
    }

    @Override
    public Tile stepOn() {
        return new FloorTile();
    }
}
