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

import com.badlogic.gdx.math.MathUtils;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.World;
import com.explatcreations.sft.data.LevelIndex;
import com.explatcreations.sft.data.LevelState;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.functions.IFunction2;
import com.explatcreations.sft.input.Controls;
import com.explatcreations.sft.modes.MenuButton;

/**
 * @author moopslc
 */
public class StageButtonCollection {
    private final int xStart = 20;
    private final int yStart = 114;

    private final MenuButton[] stageButtons;
    private final Patch stageButtonBackground;

    private int cursor;

    public StageButtonCollection(int cursor, IFunction2<LevelIndex, IAction> getToLevel, IFunction2<LevelIndex, IAction> getSelect) {
        this.stageButtonBackground = Patch.makeSmallParchment(209, 152);
        stageButtonBackground.setPosition(xStart - Patch.ParchmentSize + 4,
                                          yStart - Patch.ParchmentSize + 8);
        this.stageButtons = makeLevelButtons(getToLevel, getSelect);
        this.cursor = cursor;
    }

    private MenuButton[] makeLevelButtons(IFunction2<LevelIndex, IAction> getToLevel,
                                          IFunction2<LevelIndex, IAction> getSelect) {
        final int buffer = 5; //space between buttons
        final int size = MenuButton.Size;

        final MenuButton[] stageButtons = new MenuButton[World.NumLevels];
        int i = 0;
        for (LevelIndex index:LevelIndex.getAll()) {
            final Point2i coords = index.getCoords();
            final LevelState type = Game.save.getLevelState(index);

            float x = coords.x * (buffer + size) + xStart;
            float y = coords.y * (buffer + size) + yStart;
            final IAction confirmCallback = getToLevel.apply(index);
            final IAction selectCallback = getSelect.apply(index);
            stageButtons[i] = new MenuButton(type, index, x, y, confirmCallback, selectCallback);
            i += 1;
        }
        return stageButtons;
    }

    public void forceSelect(int value) {
        stageButtons[value].forceSelect();
    }


    public void update() {
        for (MenuButton button:stageButtons) {
            button.update();
        }

        final int start = 15;
        final int num = 4;

        final int prevCursor = cursor;

        if (Controls.Right.zip(start, num)) {
            cursor += 1;
        } else if (Controls.Left.zip(start, num)) {
            cursor -= 1;
        }
        if (Controls.Up.zip(start, num)) {
            cursor -= World.LevelsPerWorld;
        } else if (Controls.Down.zip(start, num)) {
            cursor += World.LevelsPerWorld;
        }

        cursor = MathUtils.clamp(cursor, 0, stageButtons.length - 1);

        if (stageButtons[cursor].isLocked()) {
            //this is stupid
            for(int i = World.NumLevels - 1; i > 0; i -= 1) {
                if (!stageButtons[i].isLocked()) {
                    cursor = i;
                    break;
                }
            }
        }

        if (cursor != prevCursor) {
            Game.assets.ButtonclickSound.play();
        }

        stageButtons[cursor].forceSelect();
    }

    public void draw(Point2i offset) {
        stageButtonBackground.draw(offset);

        for (MenuButton button:stageButtons) {
            button.draw(offset);
        }
    }

    public MenuButton getButton(LevelIndex index)  {
        return index.getItem(stageButtons);
    }
}
