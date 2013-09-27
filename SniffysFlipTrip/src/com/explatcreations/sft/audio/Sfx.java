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

package com.explatcreations.sft.audio;

import com.badlogic.gdx.audio.Music;
import com.explatcreations.sft.Engine;
import com.explatcreations.sft.Game;

/**
 * @author deweyvm
 */
public class Sfx implements AudioInstance {
    private float adjVolume = 1f;
    private final int maxFrequency;
    private int lastPlayed = -1;
    private final Music music;
    private final String name;
    public Sfx(Music music, int maxFrequency) {
        register();
        this.name = "<unspecified>";
        this.music = music;
        this.maxFrequency = maxFrequency;
        setVolume(Game.settings.getSfxVolume());
    }

    public Sfx(Music music, int maxFrequency, String name) {
        register();

        this.name = name;
        this.music = music;
        this.maxFrequency = maxFrequency;
        setVolume(Game.settings.getSfxVolume());
    }

    protected void register() {
        Game.audio.register(this);
    }

    @Override
    public void play() {
        if (Engine.getFrame() - lastPlayed >= maxFrequency) {
            lastPlayed = Engine.getFrame();
            Game.audio.addSfx(this);
            music.play();
        }
    }

    public Sfx setAdjustVolume(float adjVol) {
        adjVolume = adjVol;
        setVolume(Game.settings.getSfxVolume());
        return this;
    }

    @Override
    public boolean isPlaying() {
        return music.isPlaying();
    }

    @Override
    public void setVolume(float newVolume) {
        music.setVolume(newVolume * adjVolume);
    }

    @Override
    public void pause() {
        music.pause();
    }

    @Override
    public void resume() {
        music.play();
    }

    @Override
    public void stop() {
        music.stop();
    }


}
