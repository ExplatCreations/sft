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

import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.graphics.Drawable;
import com.explatcreations.sft.graphics.TextSprite;
import com.explatcreations.sft.gui.Patch;

/**
 * @author deweyvm
 */
public class IndicatorBase implements Drawable {
    protected Patch bg;
    protected TextSprite text;
    private final int xOffset = -7;
    private final int yOffset = -4;
    public IndicatorBase(String str, Point2i pos) {
        this.text = new TextSprite(pos.x, pos.y, str);
        this.bg = Patch.makeCharred(text.getWidth() + 24, 16);
    }

    @Override
    public void update() {

    }

    @Override
    public int getX() {
        return text.x;
    }

    @Override
    public int getY() {
        return text.y;
    }

    @Override
    public void setX(int x) {
        text.x = x;
        bg.x = x + xOffset;
    }

    @Override
    public void setY(int y) {
        text.y = y;
        bg.y = y + yOffset;
    }

    @Override
    public void draw() {
        bg.draw(Point2i.Zero);
        text.drawWithOffset(new Point2i(2, 2));
    }

}
