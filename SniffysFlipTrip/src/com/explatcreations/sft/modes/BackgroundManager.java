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

package com.explatcreations.sft.modes;

import com.explatcreations.sft.graphics.background.ParchmentBackground;

/**
 * @author moopslc
 */
public class BackgroundManager {
    private static ParchmentBackground background = new ParchmentBackground();

    public static void clear() {
        background = null;
    }

    public static void start() {
        setNewBackground(new ParchmentBackground());
    }

    public static void setNewBackground(ParchmentBackground newBackground) {
        background = newBackground;
    }

    public static void draw() {
        if (background != null) {
            background.draw();
        }
    }

}
