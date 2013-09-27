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

package com.explatcreations.sft.modes;

import com.explatcreations.sft.Game;
import com.explatcreations.sft.audio.MusicManager;
import com.explatcreations.sft.data.LevelIndex;
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.grid.Grid;
import com.explatcreations.sft.gui.Hud;
import com.explatcreations.sft.particles.ParticleManager;

/**
 * @author moopslc
 */
public class PlayMode extends CustomMode {
    private final Timer flyInTimer = new Timer(Hud.FlyTime);
    private final Grid grid;
    private final Hud hud;
    public PlayMode(Grid grid, boolean isResetting) {
        final LevelIndex levelIndex = grid.levelIndex;
        this.grid = grid;
        this.hud = grid.makeHud();
        if (Game.save.hasNewNote(levelIndex)) {
            ParticleManager.spawnNewNoteIndicator();
            Game.save.unlockNote(levelIndex);
        }
        if (!isResetting) {
            if (levelIndex.isFirstSequence()) {
                MusicManager.play(Game.assets.EasyTileFlippinMusic);
            } else {
                MusicManager.play(Game.assets.ElevatedMusic);
            }
        }
    }

    @Override
    public void update() {
        super.update();

        if (hud.isTopActive()) {
            hud.update();
        } else {
            grid.update();
            hud.update();
        }
        if (!grid.isFinishing()) {
            flyInTimer.increment();
        } else {
            flyInTimer.decrement();
        }
    }

    @Override
    public void draw() {
        super.draw();
        grid.draw(true);
        hud.draw();

        if (Game.globals.IsDebugMode) {
            //fpsText.draw();
        }
    }
}
