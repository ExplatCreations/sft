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

package com.explatcreations.sft.loading;

import com.explatcreations.sft.data.Point2i;

/**
 * @author moopslc
 */
public class LevelData {
    public final int[][] tiles;
    public final int cols;
    public final int rows;
    public final int goldSteps;
    public final Point2i start;
    public final String name;
    public final String uuid;
    public LevelData(int[][] tiles, int cols, int rows, int goldSteps, Point2i start, String name, String uuid) {
        this.tiles = tiles;
        this.cols = cols;
        this.rows = rows;
        this.goldSteps = goldSteps;
        this.start = start;
        this.name = name;
        this.uuid = uuid;
    }


}
