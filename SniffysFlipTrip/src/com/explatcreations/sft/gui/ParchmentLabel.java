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

import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.graphics.TextSprite;

/**
 * @author moopslc
 */
public class ParchmentLabel {
    public final TextSprite text;
    public final Patch bg;
    public ParchmentLabel(int x, int y, String label) {
        this.text = new TextSprite(0, 0, label);
        this.bg = Patch.makeCharred(text.getWidth() + 24, text.getHeight());
        bg.setPosition(x, y);
        setX(x);
        text.y = bg.y + 5;
    }

    public void setX(int x) {
        bg.setPosition(x, bg.y);
        text.x = bg.x + 10;
    }

    public void draw(Point2i offset) {
        bg.draw(offset);
        text.drawWithOffset(offset);
    }

    public int getHeight() {
        return bg.getHeight();
    }

    public int getWidth() {
        return bg.getWidth();
    }
}