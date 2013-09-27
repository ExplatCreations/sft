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
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.grid.Grid;
import com.explatcreations.sft.input.Controls;
import com.explatcreations.sft.modes.MenuMode;
import com.explatcreations.sft.modes.ToPlayMode;

import java.util.ArrayList;

/**
 * @author deweyvm
 */
public class HudTop {
    private Button[] buttons;
    private ArrayList<Panel> panels = new ArrayList<Panel>();
    private Grid grid;
    private IAction resetPanels;
    public static final int X = 3;
    public static final int AuxWidgetY = 130;
    public static final int ySpace = 26;
    public HudTop(Grid grid) {
        this.grid = grid;
        this.buttons = new Button[] {
            createNotesButton(X, 0),
            createUndoButton(X, 1),
            createRetryButton(X, 2),
            createLeaveButton(X, 3)
        };
        this.resetPanels = new IAction() {
            @Override
            public void eval() {
                panels = new ArrayList<Panel>();
            }
        };
    }

    private Button createLeaveButton(final int x, int index) {
        final IAction onPress = new IAction() {
            @Override
            public void eval() {
                grid.skipFrame();
                final IAction yesFunction = new IAction() {
                    @Override
                    public void eval() {
                        resetPanels.eval();
                        Game.mode.transitionTo(MenuMode.getFactory(grid.levelIndex.getRawIndex()));
                    }
                };
                panels.add(new YesNoPanel("Return to stage select?", yesFunction, resetPanels));
            }
        };
        return new Button("Leave", x, ySpace*index, onPress, Controls.Cancel);
    }

    private Button createRetryButton(final int x, int index) {
        final IAction onPress = new IAction() {
            @Override
            public void eval() {
                grid.skipFrame();
                final IAction  yesFunction = new IAction() {
                    @Override
                    public void eval() {
                        resetPanels.eval();
                        Game.mode.transitionTo(ToPlayMode.getFactory(grid.levelIndex, true));
                    }
                };
                panels.add(new YesNoPanel("Retry stage?", yesFunction, resetPanels));
            }
        };
        return new Button("Retry", x, ySpace*index, onPress, Controls.Retry);
    }

    private Button createNotesButton(final int x, int index) {
        final IAction notePress = new IAction() {
            @Override
            public void eval() {
                final IAction thing = new IAction() {
                    @Override
                    public void eval() {
                        panels = new ArrayList<Panel>();
                    }
                };
                panels.add(new NotesPanel(Game.save.getNoteIndex(), thing));
            }
        };
        return new Button("Notes", x, ySpace*index, notePress, Controls.Notes);
    }

    private Button createUndoButton(final int x, int index) {
        final IAction onPress = new IAction() {
            @Override
            public void eval() {
                grid.clickUndo();
            }
        };

        return new Button("Undo ", x, ySpace*index, onPress, Controls.Undo);
    }

    public void update() {
        if (!isActive()) {
            for (Button button: buttons) {
                button.updateSelected();
            }
        }

        for (Panel panel: panels) {
            panel.update();
        }
    }

    public boolean isActive() {
        return panels.size() != 0;
    }

    public void draw() {
        for (Button button: buttons) {
            button.draw(Point2i.Zero);
        }
        for (Panel panel:panels) {
            panel.draw();
        }
    }
}
