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

import com.explatcreations.sft.data.Timer;

/**
 * @author moopslc
 */
public class MusicManager {
    enum State {
        Stopped,
        Playing,
        FadeOut
    }

    private static State state = State.Stopped;
    private static SftMusic nextSong;
    private static SftMusic playingSong;
    private static Timer fadeTimer;
    private boolean loop;

    public static void play(SftMusic music) {
        if (playingSong != null) {
            playingSong.stop();
        }
        playingSong = music;
        playingSong.play();
        state = State.Playing;
    }

    public static void startFadeOut(int frames) {
        if (state != State.Playing) {
            return;
        }

        state = State.FadeOut;
        fadeTimer = new Timer(frames);

    }

    public static void update() {
        if (state == State.Stopped || state == State.Playing) {

        } else {
            fadeTimer.increment();
            playingSong.setAdjustVolume(1 - fadeTimer.getProgress());
            if (fadeTimer.isMax()) {
                playingSong.stop();
                playingSong.setAdjustVolume(1);
                playingSong = null;
                state = State.Stopped;
            }
        }
    }
}
