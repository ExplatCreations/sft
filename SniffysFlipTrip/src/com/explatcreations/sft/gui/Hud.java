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

import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.grid.Grid;
import com.explatcreations.sft.grid.Inventory;
import com.explatcreations.sft.tiles.Tile;

/**
 * @author moopslc
 */
public class Hud {
    public static final int Height = Tile.Size * 8;
    public static final int FlyTime = 30;

    private final Patch bg;
    private final HudTop top;
    private final Inventory inventory;
    public Hud(Inventory inventory, Grid grid) {
        this.inventory = inventory;
        final int minus = 50;
        this.bg = Patch.makeParchment(Game.RenderWidth - minus, 32);
        bg.setPosition(4 + minus/2, Game.RenderHeight - 16);
        this.top = new HudTop(grid);
    }

    public boolean isTopActive() {
        return top.isActive();
    }

    public void update() {

        top.update();
    }

    public void draw() {
        bg.draw(Point2i.Zero);
        inventory.draw(Point2i.Zero);
        top.draw();
    }
}
