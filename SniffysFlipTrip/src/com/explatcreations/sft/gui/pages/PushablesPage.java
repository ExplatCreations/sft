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
import com.explatcreations.sft.enums.BallType;
import com.explatcreations.sft.graphics.IDoodad;
import com.explatcreations.sft.gui.BallDoodad;
import com.explatcreations.sft.gui.ShutterDoodad;
import com.explatcreations.sft.gui.TileDoodad;
import com.explatcreations.sft.tiles.PitTile;
import com.explatcreations.sft.tiles.TrapdoorTile;
import com.explatcreations.sft.tiles.WaterTile;

/**
 * @author deweyvm
 */
public class PushablesPage extends BasePage {
    private static final String textClass = Game.assets.PushablesText;
    private final int StartY = 100;
    public PushablesPage() {
        super(textClass);
        plusSprite.x = BackMid5 + 3;
        plusSprite.y = StartY + 20 + 3;
        doodads = new IDoodad[] {
            new BallDoodad(BallType.NormalBall,          new Point2i(StartX5, StartY)),
            new BallDoodad(BallType.PaintBall,           new Point2i(StartX5, StartY + 20)),
            new BallDoodad(BallType.Block,               new Point2i(StartX5, StartY + 40)),
            new TileDoodad(new PitTile(),                new Point2i(MidX5, StartY)),
            new TileDoodad(new WaterTile(),              new Point2i(MidX5, StartY + 20)),
            new TileDoodad(new TrapdoorTile(true, true), new Point2i(MidX5, StartY + 40)),
            new ShutterDoodad(BallType.NormalBall,       new Point2i(EndX5, StartY)),
            new ShutterDoodad(BallType.PaintBall,        new Point2i(EndX5, StartY + 20)),
            new ShutterDoodad(BallType.Block,            new Point2i(EndX5, StartY + 40))
        };
    }

    @Override
    public void draw(Point2i offset) {
        super.draw(offset);
        arrowSprite.x = FrontMid5;
        arrowSprite.y = StartY + 20 + 5;
        arrowSprite.drawWithOffset(offset);
        plusSprite.drawWithOffset(offset);
    }
}
