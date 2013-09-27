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

package com.explatcreations.sft.modes.ending;

import com.explatcreations.sft.Game;
import com.explatcreations.sft.audio.MusicManager;
import com.explatcreations.sft.graphics.SpriteBase;

/**
 * @author deweyvm
 */
public class BadEndingWidget extends BaseEndingWidget {
    private SpriteBase tower = Tower.makeTower();
    private boolean hasPlayed = false;

    @Override
    protected int getWrapWidth() {
        return Game.RenderWidth / 2 - 8;
    }

    @Override
    protected String[] makeStrings() {
        return new String[] {
            "The final floor is vanquished",
            "But some business is left undone.",
            "\nTry completing every stage!"
        };
    }

    @Override
    public void update() {
        if (!hasPlayed) {
            hasPlayed = true;
            MusicManager.play(Game.assets.TimpoLivesMusic);
        }
        begin();
        super.update();
        tower.update();
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void draw() {
        super.draw();
        tower.draw();
    }
}
