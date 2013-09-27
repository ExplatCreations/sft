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

package com.explatcreations.sft.tiles;

import com.explatcreations.sft.data.Point2i;

/**
 * @author moopslc
 */
public class NullTile extends Tile {
    public NullTile() {
        super(null);
    }

    @Override
    public MiniTile getMiniTile() {
        return null;
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public void draw(int i, int j, Point2i offset) { }

}
