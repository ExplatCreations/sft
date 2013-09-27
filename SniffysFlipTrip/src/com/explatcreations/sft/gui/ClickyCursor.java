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

/**
 * @author deweyvm
 */
public class ClickyCursor {
    private final int max;
    private int value;
    public ClickyCursor(int max) {
        this.max = max;
        this.value = 0;
    }

    public ClickyCursor(int initialValue, int max) {
        this(max);
        this.value = initialValue;
    }

    public int getValue() {
        return value;
    }


    public void increment() {
        change(1);
    }

    public void decrement() {
        change(-1);
    }

    public void change(int amount) {
        final int prevValue = value;
        value = MathUtils.clamp(value + amount, 0, max);
        if (value != prevValue) {
            Game.assets.ButtonclickSound.play();
        }
    }

}
