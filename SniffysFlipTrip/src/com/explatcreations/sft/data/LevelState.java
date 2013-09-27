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

/**
 * @author deweyvm
 */
public enum LevelState {
    Locked(0),
    Unlocked(1),
    Completed(2),
    Golden(3);
    private final int id;
    private LevelState(int id) {
        this.id = id;
    }

    public boolean moreLockedThan(LevelState other) {
        return id < other.id;
    }

    public boolean moreLockedThanOrEqual(LevelState other) {
        return id <= other.id;
    }

    public boolean eq(LevelState other) {
        return id == other.id;
    }


}
