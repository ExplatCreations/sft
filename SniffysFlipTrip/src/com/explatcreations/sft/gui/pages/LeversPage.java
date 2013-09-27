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
import com.explatcreations.sft.graphics.IDoodad;
import com.explatcreations.sft.gui.TileDoodad;
import com.explatcreations.sft.tiles.LeverTile;
import com.explatcreations.sft.tiles.TrapdoorTile;

/**
 * @author moopslc
 */
public class LeversPage extends BasePage {
    private static final String textClass = Game.assets.LeversText;
    private final int LeverY = 65;
    private final int TrapdoorY = 165;
    public LeversPage() {
        super(textClass);
        doodads = new IDoodad[] {
            new TileDoodad(new LeverTile(), new Point2i(StartX3, LeverY)),
            new TileDoodad(new LeverTile().forceRight(), new Point2i(EndX3, LeverY)),

            new TileDoodad(new TrapdoorTile(true, true), new Point2i(StartX3, TrapdoorY)),
            new TileDoodad(new TrapdoorTile(true, false), new Point2i(EndX3, TrapdoorY)),

            new TileDoodad(new TrapdoorTile(false, false), new Point2i(StartX3, TrapdoorY + 20)),
            new TileDoodad(new TrapdoorTile(false, true), new Point2i(EndX3, TrapdoorY + 20)),
        };
        centerArrow();

    }

    @Override
    public void draw(Point2i offset) {
        super.draw(offset);
        arrowSprite.y = LeverY + 5;
        arrowSprite.drawWithOffset(offset);

        arrowSprite.y = TrapdoorY + 5 + 10;
        arrowSprite.drawWithOffset(offset);
    }
}
