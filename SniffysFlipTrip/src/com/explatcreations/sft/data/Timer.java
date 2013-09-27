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

import com.badlogic.gdx.math.MathUtils;

/**
 * @author moopslc
 */
public class Timer {
    private int max;
    private int value = 0;
    public Timer(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public Timer forceFinish() {
        value = max;
        return this;
    }

    public int getValue() {
        return value;
    }

    public boolean isMax() {
        return value >= max;
    }

    public void increment() {
        value = MathUtils.clamp(value + 1, 0, max + 1);
    }

    public void decrement() {
        value = MathUtils.clamp(value - 1, 0, max);
    }

    public boolean incrementUntilDone() {
        increment();
        final boolean result = isMax();
        if (result) {
            reset();
        }
        return result;
    }

    public float getProgress() {
        return value/(float)max;
    }

    public float getProgress2() {
        final float x = getProgress();
        return x*x;
    }

    public float getOneMinusProgress2() {
        final float x = 1 - getProgress();
        return x * x;
    }

    public void reset() {
        value = 0;
    }
}
