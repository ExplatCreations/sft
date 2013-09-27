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

package com.explatcreations.sft.modes.title;

import com.explatcreations.sft.data.Recti;
import com.explatcreations.sft.data.Timer;

import java.util.ArrayList;

/**
 * @author moopslc
 */
public class SniffyLetterCollection {
    private final ArrayList<SniffyLetter> letters = new ArrayList<SniffyLetter>();
    private static final int  Height = 32;
    private final Recti[] positions = new Recti[]{
        new Recti(0, 0, 24, Height),
        new Recti(24, 0, 10, Height),
        new Recti(38, 0, 1, Height),
        new Recti(41, 0, 9, Height),
        new Recti(50, 0, 9, Height),
        new Recti(59, 0, 9, Height),
        new Recti(68, 0, 3, Height),
        new Recti(71, 0, 7, Height),
    };
    private final Timer nextTimer = new Timer(10);
    private int t = 0;
    private boolean hasBegun = false;
    public SniffyLetterCollection() {
        for (Recti rect :positions) {
            letters.add(new SniffyLetter(rect));
        }
    }
    private int startIndex = 0;
    public void begin() {
        hasBegun = true;
    }

    public void update() {
        t += 1;
        if (hasBegun) {
            for (int i = 0; i < letters.size(); i += 1) {
                if (i < startIndex) {
                    letters.get(i).begin();
                }
            }
            if (nextTimer.incrementUntilDone()) {
                startIndex += 1;
            }
        }
        for (SniffyLetter letter:letters) {
            letter.update();
        }
    }

    public void draw() {
        for (SniffyLetter letter:letters) {
            letter.draw();
        }
    }
}
