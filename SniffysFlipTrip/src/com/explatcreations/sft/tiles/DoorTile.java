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
import com.explatcreations.sft.enums.DoorColor;
import com.explatcreations.sft.enums.KeyColor;
import com.explatcreations.sft.graphics.AnimatedSprite;

import java.util.HashMap;
import java.util.Map;

/**
 * @author moopslc
 */
public class DoorTile extends Tile {
    private static final Texture redSpriteClass = Game.assets.RedDoorGraphic;
    private static final Texture yellowSpriteClass = Game.assets.YellowDoorGraphic;
    private static final Texture greenSpriteClass = Game.assets.GreenDoorGraphic;
    private static final Texture blueSpriteClass = Game.assets.BlueDoorGraphic;

    private static final Map<DoorColor, KeyColor> doorToKey = makeConversions();

    private static final String OpenName = "open";

    private static Map<DoorColor,KeyColor> makeConversions() {
        final HashMap<DoorColor,KeyColor> result = new HashMap<DoorColor, KeyColor>();
        result.put(DoorColor.Red, KeyColor.Red);
        result.put(DoorColor.Yellow, KeyColor.Yellow);
        result.put(DoorColor.Green, KeyColor.Green);
        result.put(DoorColor.Blue, KeyColor.Blue);
        return result;
    }

    public static AnimatedSprite makeSprite(DoorColor color) {
        Texture spriteClass;
        if (color == DoorColor.Red) {
            spriteClass = redSpriteClass;
        } else if (color == DoorColor.Yellow) {
            spriteClass = yellowSpriteClass;
        } else if (color == DoorColor.Green) {
            spriteClass = greenSpriteClass;
        } else {
            spriteClass = blueSpriteClass;
        }
        final AnimatedSprite door = Assets.makeTileSprite(spriteClass);
        door.addAnimation(OpenName, 10, new int[]{1, 2, 3, 4, 5, 6, 7}, false);
        return door;
    }

    private final DoorColor color;
    public DoorTile(DoorColor color) {
        super(makeSprite(color));
        this.color = color;
    }

    public OpeningDoorTile getOpen() {
        final AnimatedSprite result = makeSprite(color);
        result.play(OpenName);
        Game.assets.DoorunlockSound.play();
        return new OpeningDoorTile(result);
    }

    public KeyColor toKey() {
        return doorToKey.get(color);
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
