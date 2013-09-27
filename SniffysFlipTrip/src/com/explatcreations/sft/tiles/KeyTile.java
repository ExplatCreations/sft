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
import com.explatcreations.sft.enums.KeyColor;
import com.explatcreations.sft.graphics.AnimatedSprite;

/**
 * @author deweyvm
 */
public class KeyTile extends Tile {
    private static final Texture redSpriteClass = Game.assets.RedKeyGraphic;
    private static final Texture yellowSpriteClass = Game.assets.YellowKeyGraphic;
    private static final Texture greenSpriteClass = Game.assets.GreenKeyGraphic;
    private static final Texture blueSpriteClass = Game.assets.BlueKeyGraphic;

    public static AnimatedSprite makeSprite(KeyColor color) {
        Texture spriteClass;
        if (color == KeyColor.Red) {
            spriteClass = redSpriteClass;
        } else if (color == KeyColor.Yellow) {
            spriteClass = yellowSpriteClass;
        } else if (color == KeyColor.Green) {
            spriteClass = greenSpriteClass;
        } else {
            spriteClass = blueSpriteClass;
        }
        final AnimatedSprite sprite = Assets.makeTileSprite(spriteClass);
        final String name = "play";
        sprite.addAnimation(name, 10, new int[] {0, 1, 2, 3, 4, 5, 6, 7}, true);
        sprite.play(name);
        return sprite;
    }

    private final KeyColor color;

    public KeyTile(KeyColor color) {
        super(makeSprite(color));
        this.color = color;
    }

    public KeyColor getColor() {
        return color;
    }

    @Override
    public Tile stepOn() {
        return new FloorTile();
    }

    @Override
    public void draw(int i, int j, Point2i offset) {
        FloorTile.drawFloorTileSprite(i, j, offset);
        super.draw(i, j, offset);
    }


}
