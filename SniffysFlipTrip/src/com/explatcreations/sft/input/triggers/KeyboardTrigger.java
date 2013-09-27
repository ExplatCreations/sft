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

package com.explatcreations.sft.input.triggers;

import com.badlogic.gdx.Gdx;
import com.explatcreations.sft.input.Controls;

/**
 * @author deweyvm
 */
public class KeyboardTrigger implements Trigger {
    public final int code;
    public KeyboardTrigger(int code) {
        this.code = code;
    }

    @Override
    public boolean isPressed() {
        return Gdx.input.isKeyPressed(code);
    }

    @Override
    public String toString() {
        return Controls.getKeyName(code);
    }
}
