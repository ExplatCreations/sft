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

package com.explatcreations.sft.grid;

import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.entities.Ball;
import com.explatcreations.sft.enums.BallType;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.tiles.TeleporterTile;
import com.explatcreations.sft.tiles.Tile;

/**
 * @author moopslc
 */
public interface IGrid extends ITileGetter {
    void allowCommit();
    boolean hasBall(Point2i p);
    void fallIn(Ball ball, Tile tile, BallType ballType, Point2i p);
    Point2i getPartner(Point2i pos, TeleporterTile tile);
    void checkerBallAffectTile(Point2i p);
    void addTransform(IAction transform);
}
