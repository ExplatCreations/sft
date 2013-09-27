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

package com.explatcreations.sft.particles;

import com.badlogic.gdx.graphics.Texture;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.graphics.Drawable;
import com.explatcreations.sft.graphics.SpriteBase;

/**
 * @author deweyvm
 */
public class StarSprite implements Drawable {
    private static final Texture starClass = Game.assets.StarGraphic;
    private final SpriteBase sprite = makeSprite();

    protected SpriteBase makeSprite() {
        final AnimatedSprite sprite = Assets.makeTileSprite(starClass);
        final String name = "play";
        sprite.addAnimation(name, 12, new int[]{11, 12, 13, 14}, true);
        sprite.play(name);
        return sprite;
    }

    public StarSprite(int x, int y) {
        sprite.x = x;
        sprite.y = y;
    }

    @Override
    public void update() {
        sprite.update();
    }

    @Override
    public void draw() {
        sprite.draw();
    }

    @Override
    public void setX(int x) {
        sprite.x = x;
    }

    @Override
    public void setY(int y) {
        sprite.y = y;
    }

    @Override
    public int getX() {
        return sprite.x;
    }

    @Override
    public int getY() {
        return sprite.y;
    }
}
