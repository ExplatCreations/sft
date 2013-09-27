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

import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.LevelIndex;
import com.explatcreations.sft.entities.Player;
import com.explatcreations.sft.functions.IFunction1;
import com.explatcreations.sft.grid.Atomizer;
import com.explatcreations.sft.grid.Grid;
import com.explatcreations.sft.gui.Hud;

/**
 * @author deweyvm
 */
public class ToPlayMode extends CustomMode {
    private Atomizer atomizer;
    private Grid grid;
    private Hud hud;
    private boolean isResetting;
    public ToPlayMode(LevelIndex index, boolean isResetting) {
        this.grid = new Grid(index);
        this.atomizer = makeEnterAtomizer();
        this.hud = grid.makeHud();
        this.isResetting = isResetting;
    }

    public static IFunction1<Mode> getFactory(final LevelIndex index, final boolean isResetting) {
        return new IFunction1<Mode>() {
            @Override
            public ToPlayMode apply() {
                return new ToPlayMode(index, isResetting);
            }
        };
    }

    public Atomizer makeEnterAtomizer() {
        return new Atomizer(Player.makeSprite(), grid.getPlayerStart(), false);
    }

    @Override
    public void update() {
        hud.update();
        atomizer.update();
        if (atomizer.isDone()) {
            Game.mode.setMode(new PlayMode(grid, isResetting));
        }
        grid.updateTiles();
    }

    @Override
    public void draw() {
        super.draw();
        grid.draw(false);
        atomizer.draw();
        hud.draw();
    }
}
