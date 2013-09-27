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
import com.explatcreations.sft.enums.DoorColor;
import com.explatcreations.sft.enums.KeyColor;
import com.explatcreations.sft.graphics.IDoodad;
import com.explatcreations.sft.gui.TileDoodad;
import com.explatcreations.sft.tiles.DoorTile;
import com.explatcreations.sft.tiles.FloorTile;
import com.explatcreations.sft.tiles.KeyTile;

/**
 * @author deweyvm
 */
public class DoorsPage extends BasePage {
    private static final String textClass = Game.assets.DoorsText;
    public DoorsPage() {
        super(textClass);
        final int yStart = 50;
        doodads = new IDoodad[] {
            new TileDoodad(new KeyTile(KeyColor.Red),      new Point2i(StartX5, yStart)),
            new TileDoodad(new KeyTile(KeyColor.Yellow),   new Point2i(StartX5, yStart + 20)),
            new TileDoodad(new KeyTile(KeyColor.Green),    new Point2i(StartX5, yStart + 40)),
            new TileDoodad(new KeyTile(KeyColor.Blue),     new Point2i(StartX5, yStart + 60)),
            new TileDoodad(new DoorTile(DoorColor.Red),    new Point2i(MidX5, yStart)),
            new TileDoodad(new DoorTile(DoorColor.Yellow), new Point2i(MidX5, yStart + 20)),
            new TileDoodad(new DoorTile(DoorColor.Green),  new Point2i(MidX5, yStart + 40)),
            new TileDoodad(new DoorTile(DoorColor.Blue),   new Point2i(MidX5, yStart + 60)),
            new TileDoodad(new FloorTile(),                new Point2i(EndX5, yStart + 30))
        };
        plusSprite.x = BackMid5 + 4;
        plusSprite.y = yStart + 34;
        arrowSprite.x = FrontMid5;
        arrowSprite.y = yStart + 34;
    }

    @Override
    public void draw(Point2i offset) {
        super.draw(offset);
        arrowSprite.drawWithOffset(offset);
        plusSprite.drawWithOffset(offset);
    }
}
