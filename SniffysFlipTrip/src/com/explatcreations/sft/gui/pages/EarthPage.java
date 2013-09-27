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

import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.enums.MagicType;
import com.explatcreations.sft.graphics.IDoodad;
import com.explatcreations.sft.gui.TileDoodad;
import com.explatcreations.sft.tiles.BreakableWallTile;
import com.explatcreations.sft.tiles.FloorTile;

/**
 * @author moopslc
 */
public class EarthPage extends MagicPage {
    private static final String textClass = Game.assets.EarthMagicText;
    public EarthPage()  {
        super(textClass, MagicType.Earth);
        orbSprite.y += 10;
        doodads = new IDoodad[] {
            new TileDoodad(new FloorTile(), new Point2i(StartX3, 90)),
            new TileDoodad(new BreakableWallTile(false), new Point2i(EndX3, 90)),
        };
        centerArrow();
        arrowSprite.y = 95;
    }

    @Override
    public void draw(Point2i offset) {
        super.draw(offset);
        arrowSprite.drawWithOffset(offset);
    }
}
