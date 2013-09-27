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

import com.badlogic.gdx.audio.Sound;
import com.explatcreations.sft.Engine;
import com.explatcreations.sft.Game;

/**
 * @author moopslc
 */
public class OverlapSfx extends Sfx {
    private float adjVolume = 1f;
    private final int maxFrequency;
    private int lastPlayed;
    private final Sound sound;
    public OverlapSfx(Sound sound, int maxFrequency) {
        super(null, maxFrequency);
        Game.audio.register(this);
        this.sound = sound;
        this.maxFrequency = maxFrequency;
        setVolume(Game.settings.getSfxVolume());
    }

    @Override
    protected void register() {

    }

    @Override
    public void play() {
        if (Engine.getFrame() - lastPlayed > maxFrequency) {
            lastPlayed = Engine.getFrame();
            Game.audio.addSfx(this);
            sound.play(Game.settings.getSfxVolume() * adjVolume);
        }
    }

    public Sfx setAdjustVolume(float adjVol) {
        adjVolume = adjVol;
        setVolume(Game.settings.getSfxVolume());
        return this;
    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void setVolume(float newVolume) {
        //not implemented
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void stop() {
        sound.stop();
    }


}
