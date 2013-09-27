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
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.explatcreations.sft.Game;

/**
 * @author moopslc
 */
public class RectSprite extends SpriteBase {
    private static Texture texture;

    private static Texture makeTexture() {
        final Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        final Texture result = new Texture(pixmap);
        pixmap.dispose();
        return result;
    }

    private static Sprite makeSprite(int width, int height, Color color) {
        final Sprite result = new Sprite(texture);
        result.setOrigin(0,0);
        result.setScale(width, height);
        result.setColor(color);
        return result;
    }

    public static RectSprite screen(Color color) {
        return new RectSprite(Game.RenderWidth, Game.RenderHeight, color);
    }

    private final Sprite sprite;

    public RectSprite(int width, int height, Color color) {
        super(width, height);
        if (texture == null) {
            texture = makeTexture();
        }
        sprite =  makeSprite(width, height, color);
    }

    @Override
    public void setAlpha(float alpha) {
        final Color color = sprite.getColor();
        color.a = alpha;
        setColor(color);
    }

    @Override
    public void setColor(Color color) {
        sprite.setColor(color);
    }

    @Override
    public void update() {

    }

    @Override
    public TextureRegion getRegion() {
        throw new RuntimeException("dont use this");
    }

    @Override
    public void drawSelf(float x, float y) {
        sprite.setPosition(x, y);
        Renderer.draw(sprite);
    }
}
