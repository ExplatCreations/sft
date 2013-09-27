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

package com.explatcreations.sft.modes.title;

import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2f;
import com.explatcreations.sft.data.Point2i;

import java.util.ArrayList;

/**
 * @author deweyvm
 */
public class StarCollection {
    private Point2i[] starPositions = new Point2i[] {
            new Point2i(89, -131),
            new Point2i(119, 148),
            new Point2i(376, 44),
            new Point2i(274, -206),
            new Point2i(375, 103),
            new Point2i(328, -38),
            new Point2i(154, 22),
            new Point2i(139, -78),
            new Point2i(311, -148),
            new Point2i(204, 45),
            new Point2i(23, 99),
            new Point2i(191,170),
            new Point2i(470,179),
    };

    private ArrayList<SkyStar> stars = new ArrayList<SkyStar>();
    private final int NumStars = starPositions.length;
    public StarCollection() {
        if (starPositions.length == 0) {
            starPositions = new Point2i[NumStars];
            for (int i = 0; i < NumStars; i += 1) {
                final float x = Game.RenderWidth * (float)Math.random();
                final float y = (Game.RenderHeight + 180) * (float)Math.random() - Game.RenderHeight;
                starPositions[i] = new Point2f(x, y).toPoint2i();
            }
        }
        for (Point2i pos:starPositions) {
            stars.add(new SkyStar(pos));
        }
    }

    public void update() {
        for (SkyStar star:stars) {
            star.update();
        }
    }

    public void draw(Point2i offset) {
        for (SkyStar star:stars) {
            star.draw(offset);
        }
    }
}
