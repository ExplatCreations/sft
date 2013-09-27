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
import com.explatcreations.sft.enums.TeleporterColor;
import com.explatcreations.sft.graphics.IDoodad;
import com.explatcreations.sft.gui.TileDoodad;
import com.explatcreations.sft.tiles.TeleporterTile;

/**
 * @author moopslc
 */
public class TeleportersPage extends BasePage {
    private static final String textClass = Game.assets.TeleportersText;
    private final int yStart = 80;
    public TeleportersPage() {
        super(textClass);
        doodads = new IDoodad[]{
            new TileDoodad(new TeleporterTile(TeleporterColor.Red),    new Point2i(StartX3, yStart)),
            new TileDoodad(new TeleporterTile(TeleporterColor.Yellow), new Point2i(StartX3, yStart+20)),
            new TileDoodad(new TeleporterTile(TeleporterColor.Green),  new Point2i(StartX3, yStart+40)),
            new TileDoodad(new TeleporterTile(TeleporterColor.Blue),   new Point2i(StartX3, yStart+60)),

            new TileDoodad(new TeleporterTile(TeleporterColor.Red),    new Point2i(EndX3, yStart)),
            new TileDoodad(new TeleporterTile(TeleporterColor.Yellow), new Point2i(EndX3, yStart+20)),
            new TileDoodad(new TeleporterTile(TeleporterColor.Green),  new Point2i(EndX3, yStart+40)),
            new TileDoodad(new TeleporterTile(TeleporterColor.Blue),   new Point2i(EndX3, yStart+60)),
        };
        centerArrow();
        arrowSprite.y = 115;
    }

    @Override
    public void draw(Point2i offset) {
        super.draw(offset);
        arrowSprite.drawWithOffset(offset);
    }
}
