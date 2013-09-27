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

package com.explatcreations.sft.gui.pages;

import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.enums.MagicType;
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.gui.Panel;
import com.explatcreations.sft.tiles.MagicTile;

/**
 * @author deweyvm
 */
public class MagicPage extends BasePage {
    protected AnimatedSprite orbSprite;
    public MagicPage(String textClass, MagicType magicType) {
        super(textClass);
        orbSprite = MagicTile.makeSprite(magicType);
        orbSprite.x = 5 + (Panel.Width - orbSprite.getWidth()) / 2 - 15;
        orbSprite.y = 5 + (Panel.Height - orbSprite.getHeight()) / 2 - 90;
    }

    @Override
    public void update() {
        super.update();
        orbSprite.update();
    }

    @Override
    public void draw(Point2i offset) {
        super.draw(offset);
        orbSprite.drawWithOffset(offset);
    }
}
