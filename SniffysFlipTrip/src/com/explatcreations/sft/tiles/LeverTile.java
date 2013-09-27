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
 * @author moopslc
 */
public class LeverTile extends Tile {
    private static final String LeftName = "left";
    private static final String RightName = "right";
    private static final String ForceRightName = "rightStopped";

    private static final Texture spriteClass = Game.assets.LeverTileGraphic;
    private static final AnimatedSprite sprite = makeSprite();

    private static boolean isOn;

    private static AnimatedSprite makeSprite() {
        final AnimatedSprite sprite = Assets.makeTileSprite(spriteClass);
        final int speed = 12;
        final String startName = "leftStopped";
        sprite.addAnimation(startName, speed, new int[]{0}, false);
        sprite.addAnimation(LeftName, speed, new int[]{2, 1, 0}, false);
        sprite.addAnimation(RightName, speed, new int[]{0, 1, 2}, false);
        sprite.addAnimation(ForceRightName, speed, new int[]{2}, false);
        isOn = false;
        sprite.play(startName);
        return sprite;
    }

    public LeverTile() {
        super(sprite);
    }

    public static void toggle() {
        isOn = !isOn;
        if (isOn) {
            sprite.play(RightName);
        } else {
            sprite.play(LeftName);
        }
    }

    public LeverTile forceRight() {
        setSprite(makeSprite());
        getSprite().play(ForceRightName);
        return this;
    }

}
