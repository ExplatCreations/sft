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

package com.explatcreations.sft.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.explatcreations.sft.Game;
import com.badlogic.gdx.graphics.Color;

/**
 * @author deweyvm
 */
public class BatWing extends SpriteBase {
    private static Texture WingGraphic = Game.assets.BatWingGraphic;
    private SpriteBase sprite;
    public BatWing() {
        super(0, 0);
        final AnimatedSprite wing = new AnimatedSprite(WingGraphic, 16, WingGraphic.getHeight());
        final String name = "animate";
        wing.addAnimation(name, 6, new int[]{0, 1, 2, 1}, true);
        wing.play(name);
        this.sprite = wing;
    }

    @Override
    public int getWidth() {
        return sprite.getWidth();
    }

    @Override
    public int getHeight() {
        return sprite.getHeight();
    }

    @Override
    public TextureRegion getRegion() {
        return sprite.getRegion();
    }

    @Override
    public void setAlpha(float alpha) {
        sprite.setAlpha(alpha);
    }

    @Override
    public void setColor(Color color) {
        sprite.setColor(color);
    }

    @Override
    public void update() {
        sprite.update();
    }

    @Override
    public void drawSelf(float x, float y) {
        sprite.drawSelf(x, y);
    }



}
