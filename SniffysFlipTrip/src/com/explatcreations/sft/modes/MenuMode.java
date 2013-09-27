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
import com.explatcreations.sft.audio.MusicManager;
import com.explatcreations.sft.data.LevelIndex;
import com.explatcreations.sft.data.LevelState;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.enums.Corner;
import com.explatcreations.sft.enums.Ending;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.functions.IFunction1;
import com.explatcreations.sft.functions.IFunction2;
import com.explatcreations.sft.graphics.MiniGridRenderer;
import com.explatcreations.sft.grid.Grid;
import com.explatcreations.sft.grid.MiniGrid;
import com.explatcreations.sft.gui.*;
import com.explatcreations.sft.input.Controls;

import java.util.ArrayList;
import java.util.List;

/**
 * @author moopslc
 */
public class MenuMode extends CustomMode {
    private IControlWidget widget;

    private LevelWidget levelWidget;
    private MenuButton selectedLevel;

    private List<StageButtonCollection> buttonTabs;
    private ClickyCursor modeCursor;
    private MiniGridRenderer miniGrid;

    private final List<Button> panelButtons = new ArrayList<Button>();
    private ArrayList<Panel> panels = new ArrayList<Panel>();

    private final IAction clearPanels = new IAction() {
        @Override
        public void eval() {
            clearPanels();
        }
    };

    public static final int ButtonX = 290;
    public static final int ButtonY = 238;

    public MenuMode(int cursor) {
        this.widget = new MenuControlWidget();
        this.modeCursor = new ClickyCursor(1);
        this.buttonTabs = new ArrayList<StageButtonCollection>();
        final StageButtonCollection campaignButtons = new StageButtonCollection(cursor, getToLevel(), getSelect());
        buttonTabs.add(campaignButtons);
        initOptionsMenu();
        initNotesMenu();
        buttonTabs.get(0).forceSelect(0);
        MusicManager.play(Game.assets.LevelSelectBGMMusic);

        //for debugging purposes
        //panels.add(new OptionsPanel(clearPanels));
        //panels.add(new NotesPanel(10, clearPanels));
    }

    public static IFunction1<Mode> getFactory(final int cursor) {
        return new IFunction1<Mode>() {
            @Override
            public MenuMode apply() {
                return new MenuMode(cursor);
            }
        };
    }

    private void clearPanels() {
        panels = new ArrayList<Panel>();
        panelButtons.clear();
        initOptionsMenu();
        initNotesMenu();
        this.widget = new MenuControlWidget();
    }

    private void initOptionsMenu() {
        final IAction gotoOptions = new IAction() {
            @Override
            public void eval() {
                panels.add(new OptionsPanel(clearPanels));
            }
        };
        panelButtons.add(new Button("Options ", ButtonX, ButtonY - HudTop.ySpace, gotoOptions, Controls.Options));
    }

    private void initNotesMenu() {
        final IAction gotoNotes = new IAction() {
            @Override
            public void eval() {
                panels.add(new NotesPanel(0, clearPanels));
            }
        };
        panelButtons.add(new Button("Notes   ", ButtonX, ButtonY - 2*HudTop.ySpace, gotoNotes, Controls.Notes));
    }

    private IFunction2<LevelIndex, IAction> getSelect() {
        return new IFunction2<LevelIndex, IAction>() {
            @Override
            public IAction apply(final LevelIndex input) {
                return new IAction() {
                    @Override
                    public void eval() {
                        if (selectedLevel != null) {
                            if (input.eq(selectedLevel.getLevelIndex())) {
                                return;
                            }
                            selectedLevel.deselect();
                        }
                        final LevelState levelState = Game.save.getLevelState(input);
                        if (!levelState.moreLockedThanOrEqual(LevelState.Locked)) {
                            miniGrid = new MiniGridRenderer(new MiniGrid(new Grid(input), Corner.TopRight));
                        } else {
                            miniGrid = null;
                        }
                        levelWidget = new LevelWidget(input);
                        selectedLevel = buttonTabs.get(modeCursor.getValue()).getButton(input);
                        selectedLevel.select();
                    }
                };
            }
        };
    }


    private IFunction2<LevelIndex, IAction> getToLevel() {
        return new IFunction2<LevelIndex, IAction>() {
            @Override
            public IAction apply(final LevelIndex input) {
                return new IAction() {
                    @Override
                    public void eval() {
                        MusicManager.startFadeOut(30);
                        Game.mode.transitionTo(ToPlayMode.getFactory(input, false));
                    }
                };
            }
        };

    }

    private void updatePanels() {
        for (Panel panel:panels) {
            panel.update();
        }

    }

    private void updateButtons() {
        for (Button button:panelButtons) {
            button.updateSelected();
        }
    }

    @Override
    public void update() {
        super.update();
        widget.update();
        if (miniGrid != null) {
            miniGrid.update();
        }
        if (Game.globals.CheatsEnabled && Controls.CheatUnlock.justPressed()) {
            Game.save.unlockAllStages();
            for (Ending ending:Ending.values()) {
                Game.save.setHasShownEnding(ending, true);
            }

            Game.mode.transitionTo(MenuMode.getFactory(0));

        }
        MenuButton.updateReticle();
        updatePanels();
        if (panels.size() != 0) {
            return;
        }
        updateButtons();
        buttonTabs.get(modeCursor.getValue()).update();
        if (levelWidget != null) {
            levelWidget.update();
        }

    }

    @Override
    public void draw() {
        super.draw();
        widget.draw();
        if (miniGrid != null) {
            miniGrid.draw();
        }
        buttonTabs.get(modeCursor.getValue()).draw(Point2i.Zero);
        if (levelWidget != null) {
            levelWidget.draw();
        }
        for (Button button:panelButtons) {
            button.draw(Point2i.Zero);
        }
        Game.sepiaizer.render();
        for (Panel panel:panels) {
            panel.draw();
        }
    }
}
