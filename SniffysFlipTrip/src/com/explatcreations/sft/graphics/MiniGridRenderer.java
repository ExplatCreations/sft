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

package com.explatcreations.sft.graphics;

import com.explatcreations.sft.Game;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.grid.MiniGrid;

/**
 * @author moopslc
 */
public class MiniGridRenderer {
    private final MiniGrid miniGrid;
    public MiniGridRenderer(MiniGrid miniGrid) {
        this.miniGrid = miniGrid;
    }

    public void update() {

    }

    public void draw() {
        miniGrid.draw();
        Game.sepiaizer.addDrawer(new IAction() {
            @Override
            public void eval() {
                miniGrid.drawTiles();
            }
        });
    }
}