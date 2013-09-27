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

package com.explatcreations.sft.input;

import com.badlogic.gdx.math.MathUtils;
import com.explatcreations.sft.input.triggers.Trigger;

import java.util.List;

/**
 * @author moopslc
 */
public class TriggerAggregate implements Control<Boolean>{

    private int prevFlag = 0;
    private int flag = 0;
    private List<? extends Trigger> triggers;

    public TriggerAggregate(List<? extends Trigger> triggers) {
        Controls.add(this);
        this.triggers = triggers;
    }

    @Override
    public void update() {
        prevFlag = flag;
        boolean isPressed = false;
        for (Trigger t : triggers) {
            isPressed |= t.isPressed();
        }
        if (isPressed && !Controls.isSuspended()) {
            flag = MathUtils.clamp(flag + 1, 0, Integer.MAX_VALUE - 1);
        } else {
            flag = 0;
        }
    }

    @Override
    public Boolean isPressed() {
        return flag > 0;
    }

    @Override
    public Boolean justPressed() {
        return flag == 1;
    }

    @Override
    public Boolean justReleased() {
        return prevFlag != 0 && flag == 0;
    }

    @Override
    public Boolean zip(int start, int num) {
        return justPressed() || (flag > start && flag % num == 0);
    }

    @Override
    public String toString() {
        return triggers.get(0).toString();
    }
}
