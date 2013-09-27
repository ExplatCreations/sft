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

import com.explatcreations.sft.Game;
import com.explatcreations.sft.input.AxisInfo;
import com.explatcreations.sft.input.FaceInfo;
import com.explatcreations.sft.input.JoypadButton;

/**
 * @author moopslc
 */
public class JoypadTrigger implements Trigger {
    public final JoypadButton button;

    public JoypadTrigger(JoypadButton button) {
        if (button == null) {
            throw new RuntimeException();
        }
        this.button = button;
    }

    @Override
    public boolean isPressed() {
        if (Game.joypadHelper.controller == null) {
            return false;
        }
        if (button.info instanceof AxisInfo) {
            final AxisInfo info = (AxisInfo)button.info;
            return Game.joypadHelper.controller.isAxisPressed(info.axisIndex) == info.sign;
        } else {
            final FaceInfo info = (FaceInfo)button.info;
            return Game.joypadHelper.controller.isButtonPressed(info.code);
        }
    }
}
