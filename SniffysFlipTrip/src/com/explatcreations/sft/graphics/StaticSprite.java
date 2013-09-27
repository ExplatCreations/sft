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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author moopslc
 */
public class StaticSprite extends SpriteBase {

    private final Sprite sprite;
    private final TextureRegion region;
    private float alpha = 1;
    private boolean dirty = false;
    public StaticSprite(TextureRegion region) {
        super(region.getRegionWidth(), region.getRegionHeight());
        this.region = region;
        this.sprite = new Sprite(region);
    }

    public StaticSprite copy() {
        return new StaticSprite(region);
    }

    @Override
    public void update() {

    }

    @Override
    public TextureRegion getRegion() {
        return region;
    }

    @Override
    public void setAlpha(float a) {
        alpha = a;
        dirty = true;
    }

    public void setOrigin(int x, int y) {
        sprite.setOrigin(x, y);
    }

    public void setColor(Color color) {
        sprite.setColor(color);
    }


    public void setScale(float x, float y) {
        sprite.setScale(x, y);
    }


    @Override
    public void drawSelf(float x, float y) {
        sprite.setPosition(x, y);
        if (dirty) {
            final Color oldColor = sprite.getColor();
            oldColor.a = alpha;
            sprite.setColor(oldColor);
            dirty = false;
        }

        Renderer.draw(sprite);
    }
}
