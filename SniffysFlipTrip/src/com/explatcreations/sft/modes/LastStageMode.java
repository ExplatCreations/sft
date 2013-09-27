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
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.enums.Corner;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.functions.IFunction1;
import com.explatcreations.sft.grid.Grid;
import com.explatcreations.sft.grid.MiniGrid;

/**
 * @author moopslc
 */
public class LastStageMode extends CustomMode {
    private MiniGrid miniGrid;
    private SummaryWidget resultWidget;

    public LastStageMode(final Grid grid, int steps) {
        this.miniGrid = new MiniGrid(grid, Corner.TopRight);
        final IAction retry = new IAction() {
            @Override
            public void eval() {
                Game.mode.transitionTo(ToPlayMode.getFactory(grid.levelIndex, false));
            }
        };
        final IAction continueAction = new IAction() {
            @Override
            public void eval() {
                Game.mode.transitionTo(MenuMode.getFactory(0));
            }
        };
        final IAction quitAction = continueAction;
        this.resultWidget = new SummaryWidget(new LevelWidget(grid.levelIndex), steps, retry, quitAction, continueAction);
    }

    public static IFunction1<Mode> getFactory(final Grid grid, final int steps) {
        return new IFunction1<Mode>() {
            @Override
            public Mode apply() {
                return new LastStageMode(grid, steps);
            }
        };
    }

    @Override
    public void update() {
        super.update();
        resultWidget.update();
    }

    @Override
    public void draw() {
        super.draw();
        resultWidget.draw(Point2i.Zero);
        miniGrid.draw();
    }
}
