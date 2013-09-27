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

package com.explatcreations.sft.modes.title;

import com.badlogic.gdx.graphics.Texture;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.graphics.SpriteBase;

/**
 * @author deweyvm
 */
public class SkyStar {
    private static final Texture spriteClass = Game.assets.SkyStarGraphic;
    private static final int Size = 8;
    private SpriteBase sprite;
    private static SpriteBase makeSprite() {
        final AnimatedSprite result = Assets.makeSpriteFrame(spriteClass, Size, Size, 0);
        final String name = "play";

        result.addAnimation(name, 12, new int[]{0, 1, 2, 3, 2, 1}, true);
        result.play(name);
        result.setFrame((int)(4 * Math.random()));
        return result;
    }

    public SkyStar(Point2i pos) {
        sprite = makeSprite();
        sprite.x = pos.x;
        sprite.y = pos.y;
    }

    public void update() {
        sprite.update();
    }

    public void draw(Point2i offset) {
        sprite.drawWithOffset(offset);
    }
}
