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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.explatcreations.sft.Game;

/**
 * @author moopslc
 */
public class TextSprite extends SpriteBase {

    private static BitmapFont font = Game.assets.Font;

    public static final Color DefaultColor = new Color(0.69f, 0.36f, 0.03f, 1);//0xffA16C17;
    public static final Color ParchmentColor = new Color(0.9f, 0.85f, 0.59f, 1);

    protected BitmapFontCache cache;

    public TextSprite(int x, int y, String s) {
        this(x, y, s, DefaultColor);
    }

    public TextSprite(int x, int y, String s, Color color) {
        super(0, 0);
        this.cache = new BitmapFontCache(font);
        cache.setColor(color);
        setText(s);
        this.x = x;
        this.y = y;
    }

    public static TextSprite makeWrappedSprite(int x, int y, String text, float width) {
        return new WrappedTextSprite(x, y, text, (int)width, BitmapFont.HAlignment.LEFT);
    }

    public static TextSprite makeWrappedSprite(int x, int y, String text, float width, BitmapFont.HAlignment alignment) {
        return new WrappedTextSprite(x, y, text, (int)width, alignment);
    }

    public static TextSprite makeMultilineSprite(int x, int y, String text) {
        return new MultilineTextSprite(x, y, text);
    }

    public void setColor(Color color) {
        cache.setColor(color);
    }

    public void setAlpha(float amount) {
        final Color oldColor = cache.getColor();
        final Color newColor = new Color(oldColor.r, oldColor.g, oldColor.b, amount);
        cache.setColor(newColor);
    }

    public void setText(String s) {
        cache.setText(s, 0, 0);
    }

    @Override
    public void update() {

    }

    @Override
    public int getHeight() {
        return (int)cache.getBounds().height;
    }

    @Override
    public int getWidth() {
        return (int)cache.getBounds().width + 2;
    }

    @Override
    public void drawSelf(float x, float y) {
        cache.setPosition(x, y + 1);
        Renderer.draw(cache);
    }

    @Override
    public TextureRegion getRegion() {
        throw new RuntimeException("dont use this");
    }

    private static class WrappedTextSprite extends TextSprite {

        private final int width;
        private final BitmapFont.HAlignment alignment;
        public WrappedTextSprite(int x, int y, String s, int width, BitmapFont.HAlignment alignment) {
            super(x, y, s);
            this.width = width;
            this.alignment = alignment;
            cache.setUseIntegerPositions(true);
            setText(s);
        }

        @Override
        public void setText(String s) {
            cache.setWrappedText(s, 0, 0, width, alignment);
        }
    }

    private static class MultilineTextSprite extends TextSprite {
        public MultilineTextSprite(int x, int y, String s) {
            super(x, y, s);
        }
        @Override
        public void setText(String s) {
            cache.setMultiLineText(s, 0, 0);
        }
    }
}


