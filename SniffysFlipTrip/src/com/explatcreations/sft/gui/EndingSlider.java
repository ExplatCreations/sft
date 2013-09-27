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

import com.explatcreations.sft.Game;
import com.explatcreations.sft.audio.MusicManager;
import com.explatcreations.sft.enums.Ending;
import com.explatcreations.sft.functions.IAction1;
import com.explatcreations.sft.functions.IFunction2;
import com.explatcreations.sft.input.Controls;
import com.explatcreations.sft.modes.Slider;
import com.explatcreations.sft.modes.ending.EndingMode;
import com.explatcreations.sft.particles.ParticleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author moopslc
 */
public class EndingSlider extends Slider<Integer> {
    public EndingSlider(int x, int y) {
        super(x, y, "View ending:", getUnlocked(), setValue, makeString, 0, false, 30);

    }

    public static List<Integer> getUnlocked() {
        final List<Integer> result = new ArrayList<Integer>();
        for (Ending ending : Ending.values()) {
            if (Game.save.hasShownEnding(ending)) {
                result.add(ending.id);
            }
        }
        return result;
    }

    private static IAction1<Integer> setValue = new IAction1<Integer>() {
        @Override
        public void eval(Integer arg1) {
            //do nothing
        }
    };

    private static IFunction2<Integer, String> makeString = new IFunction2<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return (input + 1) + "";
        }
    };

    @Override
    public void updateSelected() {
        super.updateSelected();
        if (Controls.Enter.justPressed()) {
            ParticleManager.resume();
            MusicManager.startFadeOut(15);
            Game.mode.transitionTo(EndingMode.getFactory(Ending.fromInt(getIndex())));
        }
    }
}
