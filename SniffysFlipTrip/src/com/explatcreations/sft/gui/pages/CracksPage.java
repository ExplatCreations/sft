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
import com.explatcreations.sft.tiles.BrokenCrackedFlipTile;
import com.explatcreations.sft.tiles.CrackedFlipTile;

/**
 * @author deweyvm
 */
public class CracksPage extends BasePage {
    private static final String textClass = Game.assets.CracksText;
    public CracksPage() {
        super(textClass);
        final int y = 115;
        doodads = new IDoodad[] {
            new TileDoodad(new CrackedFlipTile(false), new Point2i(StartX5, y)),
            new TileDoodad(new CrackedFlipTile(true), new Point2i(MidX5, y)),
            new TileDoodad(new BrokenCrackedFlipTile(true, false).endAnimation(), new Point2i(EndX5, y)),
        };
        centerArrow();
        arrowSprite.y = y + 5;
    }

    @Override
    public void draw(Point2i offset) {
        super.draw(offset);
        arrowSprite.x = BackMid5;
        arrowSprite.drawWithOffset(offset);
        arrowSprite.x = FrontMid5;
        arrowSprite.drawWithOffset(offset);
    }
}
