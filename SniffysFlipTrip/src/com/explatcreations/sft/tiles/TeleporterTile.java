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
import com.explatcreations.sft.enums.TeleporterColor;
import com.explatcreations.sft.graphics.AnimatedSprite;

import java.util.HashMap;
import java.util.Map;

/**
 * @author moopslc
 */
public class TeleporterTile extends Tile {
    private static final Texture spriteClass = Game.assets.TeleportersGraphic;
    private static final Map<TeleporterColor, AnimatedSprite> sprites = makeSprites();

    private static Map<TeleporterColor, AnimatedSprite> makeSprites() {
        Map<TeleporterColor, AnimatedSprite> result = new HashMap<TeleporterColor, AnimatedSprite>();
        for (TeleporterColor color:TeleporterColor.values()) {
            result.put(color, makeSprite(color));
        }
        return result;
    }

    private static AnimatedSprite makeSprite(TeleporterColor color) {
        final AnimatedSprite sprite = Assets.makeTileSprite(spriteClass);
        int[] frames;
        if (color == TeleporterColor.Red) {
            frames = new int[]{0,1,2,3,4};
        } else if (color == TeleporterColor.Yellow) {
            frames = new int[]{5,6,7,8,9};
        } else if (color == TeleporterColor.Green) {
            frames = new int[]{10,11,12,13,14};
        } else {
            frames = new int[]{15,16,17,18,19};
        }
        final String name = "play";
        sprite.addAnimation(name, 12, frames, true);
        sprite.play(name);
        return sprite;
    }



    private TeleporterColor color;
    public TeleporterTile(TeleporterColor color) {
        super(sprites.get(color));
        this.color = color;
    }

    public TeleporterColor getColor() {
        return color;
    }

    @Override
    public void draw(int i, int j, Point2i offset) {
        FloorTile.drawFloorTileSprite(i, j, offset);
        super.draw(i, j, offset);
    }
}
