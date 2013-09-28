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

package com.explatcreations.sft.enums;

import com.explatcreations.sft.Game;

/**
 * @author moopslc
 */
public class MDisplayMode {
    public enum DisplayType {
        FullScreen,
        WindowedFullScreen,
        Windowed
    }
    public DisplayType type;
    public int width;
    public int height;
    public MDisplayMode(int width, int height, DisplayType type) {
        this.width = width;
        this.height = height;
        this.type = type;
    }

    public MDisplayMode() {

    }

    public static MDisplayMode makeNew() {
        return new MDisplayMode(Game.WindowWidth, Game.WindowHeight, DisplayType.Windowed);
    }
}
