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

package com.explatcreations.sft.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.explatcreations.sft.data.LevelIndex;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.graphics.TextSprite;

/**
 * @author deweyvm
 */
public class NumberStamp extends SpriteBase {
    private final TextSprite text;
    private final TextSprite blackText;
    public NumberStamp(String s) {
        super(0,0);
        this.text = new TextSprite(0, 0, s);
        this.blackText = new TextSprite(0, 0, s);
        blackText.setColor(Color.BLACK);

    }

    @Override
    public void setAlpha(float alpha) {
        text.setAlpha(alpha);
        blackText.setAlpha(alpha);
    }

    @Override
    public void setColor(Color color) {
        text.setColor(color);
    }

    @Override
    public int getWidth() {
        return text.getWidth();
    }

    @Override
    public void update() {

    }

    @Override
    public int getHeight() {
        return text.getHeight();
    }

    @Override
    public void drawSelf(float x, float y) {
        blackText.drawSelf(x + 1, y);
        blackText.drawSelf(x - 1, y);
        blackText.drawSelf(x, y + 1);
        blackText.drawSelf(x, y - 1);
        text.drawSelf(x, y);
    }

    @Override
    public TextureRegion getRegion() {
        return text.getRegion();
    }


    public static SpriteBase getWhiteSprite(LevelIndex index) {
        final SpriteBase result = new NumberStamp(index.stageName());
        result.setColor(Color.WHITE);
        return result;
    }

    public static SpriteBase getBrownSprite(LevelIndex index) {
        final SpriteBase result = new NumberStamp(index.stageName());
        result.setColor(TextSprite.DefaultColor);
        return result;
    }
}
