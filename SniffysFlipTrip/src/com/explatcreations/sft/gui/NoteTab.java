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
public class NoteTab implements ISelectable {
    private final Patch selectedGraphic;
    private final Patch unselectedGraphic;
    private final TextSprite textSprite;

    public NoteTab(String text, int x, int y, int width) {
        final int height = 16;
        this.textSprite = new TextSprite(x, y, text);
        this.selectedGraphic = Patch.makeCharred(width, height);
        selectedGraphic.setPosition(x - 8, y - 5);
        this.unselectedGraphic = Patch.makeCharred(width, height).darken(0.5f);
        unselectedGraphic.setPosition(x - 8, y - 5);
    }

    @Override
    public void updateSelected() {

    }


    @Override
    public void draw(Point2i offset, boolean selected) {
        if (selected) {
            selectedGraphic.draw(offset);
            textSprite.setColor(TextSprite.DefaultColor);
        } else {
            unselectedGraphic.draw(offset);
            textSprite.setColor(TextSprite.ParchmentColor);
        }

        textSprite.drawWithOffset(offset);
    }
}
