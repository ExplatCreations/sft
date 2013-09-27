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
import com.explatcreations.sft.entities.Ball;
import com.explatcreations.sft.enums.BallType;
import com.explatcreations.sft.graphics.IDoodad;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.grid.GridMock;
import com.explatcreations.sft.tiles.ShutterTile;

/**
 * @author deweyvm
 */
public class ShutterDoodad implements IDoodad {
    private ShutterTile tile;
    private SpriteBase ballSprite;
    private Point2i pos;
    public ShutterDoodad(BallType ball, Point2i pos) {
        this.ballSprite = Ball.makeSprite(ball);
        ballSprite.x = pos.x;
        ballSprite.y = pos.y;
        this.tile = new ShutterTile(makeBall(ball, Point2i.Zero)).finishAnimation();
        this.pos = pos;
    }

    private static Ball makeBall(BallType type, Point2i pos) {
        return new Ball(new GridMock(), type, Point2i.Zero, pos);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Point2i offset) {
        final Point2i o = pos.add(offset);
        ballSprite.drawWithOffset(offset);
        tile.draw(0, 0, o);
    }
}
