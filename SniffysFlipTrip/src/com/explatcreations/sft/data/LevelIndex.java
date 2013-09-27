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

package com.explatcreations.sft.data;

import com.explatcreations.sft.Game;
import com.explatcreations.sft.World;

/**
 * @author deweyvm
 */
public class LevelIndex {
    public static final LevelIndex First = new LevelIndex(0);
    public static final LevelIndex Last = new LevelIndex(World.NumLevels - 1);

    private final int rawIndex;
    public LevelIndex(int raw) {
        this.rawIndex = raw;
    }

    public int getRawIndex() {
        return rawIndex;
    }

    public boolean isLastDemoStageOrGreater() {
        return rawIndex >= Game.globals.LastDemoStageIndex;
    }

    public boolean isGreaterThanLastDemoStage() {
        return rawIndex > Game.globals.LastDemoStageIndex;
    }

    public boolean isFirstSequence() {
        return rawIndex < 44;
    }

    public boolean isSecondSequence() {
        return !isFirstSequence();
    }

    public boolean isLast() {
        return successor() == null;
    }

    public boolean containedIn(int[] array) {
        for (int i : array) {
            if (i == rawIndex) {
                return true;
            }
        }
        return false;
    }

    public LevelState defaultSetting() {
        if (rawIndex == 0) {
            return LevelState.Unlocked;
        } else {
            return LevelState.Locked;
        }
    }

    public LevelIndex successor() {
        final int newIndex = rawIndex + 1;
        if (newIndex >= World.NumLevels) {
            return null;
        } else {
            return new LevelIndex(newIndex);
        }
    }

    public String stageName() {
        return (rawIndex + 1) + "";
    }

    public <T> T getItem(T[] items) {
        return items[rawIndex];
    }

    public Point2i getCoords() {
        final int i = rawIndex % World.LevelsPerWorld;
        final int j = rawIndex / World.LevelsPerWorld;
        return new Point2i(i, j);
    }

    public static LevelIndex[] getAll() {
        final LevelIndex[] result = new LevelIndex[World.NumLevels];
        LevelIndex next = First;
        for (int i = 0; i < World.NumLevels; i += 1) {
            result[i] = next;
            next = next.successor();
        }
        return result;
    }

    public boolean eq(LevelIndex other) {
        return other.rawIndex == rawIndex;
    }

    public boolean isSpecialStage() {
        return rawIndex > 59;
    }

    public String getKey() {
        return rawIndex + "";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof LevelIndex && ((LevelIndex) other).rawIndex == rawIndex;

    }

    @Override
    public int hashCode() {
        return rawIndex;
    }

}
