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
import com.explatcreations.sft.Game;

/**
 * @author moopslc
 */
public class SftMusic implements AudioInstance {
    private float adjVolume = 0.8f;
    private final boolean looping;
    private final Music music;

    public SftMusic(Music music, boolean looping) {
        Game.audio.register(this);
        this.looping = looping;
        this.music = music;
        music.setLooping(looping);
        setVolume(Game.settings.getMusicVolume());
    }

    @Override
    public void play() {
        Game.audio.addMusic(this);
        music.play();
    }

    public void setAdjustVolume(float adjVol) {
        adjVolume = adjVol;
        setVolume(Game.settings.getMusicVolume());
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
