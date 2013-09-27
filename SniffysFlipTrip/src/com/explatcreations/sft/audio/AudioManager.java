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

import com.explatcreations.sft.Game;

import java.util.ArrayList;

/**
 * @author deweyvm
 */
public class AudioManager {
    private ArrayList<AudioInstance> playingSfx = new ArrayList<AudioInstance>();
    private ArrayList<AudioInstance> playingMusic = new ArrayList<AudioInstance>();
    private final ArrayList<AudioInstance> allAudio = new ArrayList<AudioInstance>();
    private boolean sfxPaused = false;
    private boolean musicPaused = false;

    public AudioManager() {
        setMusicVolume(Game.settings.getMusicVolume());
        setSfxVolume(Game.settings.getSfxVolume());
    }

    public void addSfx(Sfx sfx) {
        playingSfx.add(sfx);
    }

    public void addMusic(SftMusic music) {
        playingMusic.add(music);
    }

    public void register(AudioInstance audio) {
        allAudio.add(audio);
    }

    public void update() {
        if (!sfxPaused) {
            ArrayList<AudioInstance> newSfx = new ArrayList<AudioInstance>();
            for(AudioInstance audio : playingSfx) {
                if (audio.isPlaying()) {
                    newSfx.add(audio);
                }
            }
            playingSfx = newSfx;
        }

        if (!musicPaused) {
            ArrayList<AudioInstance> newMusic = new ArrayList<AudioInstance>();
            for(AudioInstance audio : playingMusic) {
                if (audio.isPlaying()) {
                    newMusic.add(audio);
                }
            }
            playingMusic = newMusic;
        }
    }

    public float getSfxVolume() {
        return Game.settings.getSfxVolume();
    }

    public float getMusicVolume() {
        return Game.settings.getMusicVolume();
    }

    public void setSfxVolume(float newVolume) {
        Game.settings.setSfxVolume(newVolume);
        for (AudioInstance audio : allAudio) {
            if (audio instanceof Sfx) {
                audio.setVolume(newVolume);
            }
        }
    }

    public void setMusicVolume(float newVolume) {
        Game.settings.setMusicVolume(newVolume);
        for (AudioInstance audio : allAudio) {
            if (audio instanceof SftMusic) {
                audio.setVolume(newVolume);
            }
        }
    }

    public void pauseSfx() {
        if (!sfxPaused) {
            sfxPaused = true;
            for(AudioInstance audio : playingSfx) {
                audio.pause();
            }
        }
    }

    public void resumeSfx() {
        if (sfxPaused) {
            sfxPaused = false;
            for(AudioInstance audio : playingSfx) {
                audio.resume();
            }
        }
    }

    public void pauseMusic() {
        if (!musicPaused) {
            musicPaused = true;
            for(AudioInstance audio : playingMusic) {
                audio.pause();
            }
        }
    }

    public void resumeMusic() {
        if (musicPaused) {
            musicPaused = false;
            for(AudioInstance audio : playingMusic) {
                audio.resume();
            }
        }
    }

    public void pause() {
        pauseMusic();
        pauseSfx();
    }

    public void resume() {
        resumeMusic();
        resumeSfx();
    }

}
