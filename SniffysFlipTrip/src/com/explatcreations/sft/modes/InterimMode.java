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
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.enums.Corner;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.functions.IFunction1;
import com.explatcreations.sft.graphics.MiniGridRenderer;
import com.explatcreations.sft.grid.Grid;
import com.explatcreations.sft.grid.MiniGrid;
import com.explatcreations.sft.particles.ParticleManager;

import java.util.ArrayList;

/**
 * @author deweyvm
 */
public class InterimMode extends CustomMode {
    enum State {
        Start,
        ShowUnlock,
        Wait
    }

    private State state = State.Start;

    private final Timer startTimer = new Timer(90);

    private LevelWidget newStageWidget;
    private SummaryWidget resultWidget;
    private MiniGridRenderer newMini;
    private MiniGridRenderer oldMini;

    private ArrayList<LevelIndex> unlockedLevels;
    private int unlockedPtr = 0;
    private Timer unlockedLevelTimer = new Timer(75);
    public InterimMode(final Grid oldLevel, final Grid newLevel, int steps, ArrayList<LevelIndex> unlockedLevels) {
        this.oldMini = new MiniGridRenderer(new MiniGrid(oldLevel, Corner.BottomLeft));
        this.newMini = new MiniGridRenderer(new MiniGrid(newLevel, Corner.TopRight));
        this.newStageWidget = new LevelWidget(newLevel.levelIndex);

        final IAction retry = new IAction() {
            @Override
            public void eval() {
                Game.mode.transitionTo(ToPlayMode.getFactory(oldLevel.levelIndex, false));
            }
        };

        final IAction quit = new IAction() {
            @Override
            public void eval() {
                Game.mode.transitionTo(MenuMode.getFactory(newLevel.levelIndex.getRawIndex()));
            }
        };

        final IAction continueAction = new IAction() {
            @Override
            public void eval() {
                Game.mode.transitionTo(ToPlayMode.getFactory(newLevel.levelIndex, false));
            }
        };

        this.resultWidget = new SummaryWidget(new LevelWidget(oldLevel.levelIndex), steps, retry, quit, continueAction);

        this.unlockedLevels = unlockedLevels;
    }

    public static IFunction1<Mode> getFactory(final Grid oldLevel,
                                              final Grid newLevel,
                                              final int steps,
                                              final ArrayList<LevelIndex> unlockedLevels) {
        return new IFunction1<Mode>() {
            @Override
            public Mode apply() {
                return new InterimMode(oldLevel, newLevel, steps, unlockedLevels);
            }
        };
    }


    @Override
    public void update() {
        super.update();
        newStageWidget.update();
        resultWidget.update();
        if (state == State.Start) {
            if (startTimer.incrementUntilDone()) {
                state = State.ShowUnlock;
            }
        } else if (state == State.ShowUnlock) {
            if (unlockedPtr >= unlockedLevels.size())  {
                state = State.Wait;
                return;
            }
            if (unlockedLevelTimer.getValue() == 0) {
                ParticleManager.spawnLevelUnlockParticle(unlockedLevels.get(unlockedPtr));
                Game.assets.LevelunlockSound.play();
                unlockedPtr += 1;
            }
            unlockedLevelTimer.incrementUntilDone();
        } else if (state == State.Wait) {

        }
        oldMini.update();
        newMini.update();
    }

    @Override
    public void draw() {
        super.draw();
        newStageWidget.draw();
        resultWidget.draw(Point2i.Zero);
        newMini.draw();
        oldMini.draw();
        Game.sepiaizer.render();
    }
}
