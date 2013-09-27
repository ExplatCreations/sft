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

package com.explatcreations.sft.saving;

import com.explatcreations.sft.enums.ControlName;
import com.explatcreations.sft.enums.MDisplayMode;
import com.explatcreations.sft.input.Controls;
import com.explatcreations.sft.input.JoypadButton;

/**
 * @author deweyvm
 */
public class Settings {
    private final static String directory = LoadUtils.Directory;
    private final static String filename = "settings.json";

    private RawSettings raw;


    public Settings(RawSettings raw) {
        this.raw = raw;
    }

    public void verify() {
        RawSettings.verify(raw);
    }

    public int getKeyCode(ControlName name) {
        final Float rawValue = raw.keyConfig.get(name.toString());
        return (int)rawValue.floatValue();
    }

    public void setKey(ControlName name, int code) {
        raw.keyConfig.put(name.toString(), code + 0f);
    }


    public JoypadButton getButton(ControlName name) {
        return JoypadButton.fromString(raw.joypadConfig.get(name.toString()));
    }

    public void setButton(ControlName name, JoypadButton button) {
        raw.joypadConfig.put(name.toString(), button.toString());
    }

    public float getSfxVolume() {
        return raw.sfx;
    }

    public int getIntSfxVolume() {
        return (int)(100*raw.sfx);
    }

    public void setSfxVolume(float value) {
        raw.sfx = value;
    }

    public float getMusicVolume() {
        return raw.music;
    }

    public int getIntMusicVolume() {
        return (int)(100*raw.music);
    }

    public void setMusicVolume(float value) {
        raw.music = value;
    }

    public MDisplayMode getDisplayMode() {
        return raw.display;
    }

    public void setDisplayMode(MDisplayMode mode) {
        raw.display = mode;
        flush();
    }

    public void resetControls() {
        raw.keyConfig = RawSettings.makeKeyMap();
        raw.joypadConfig = RawSettings.makeButtonMap();
        Controls.syncControls();
        flush();
    }

    public static Settings load() {
        final RawSettings raw = LoadUtils.load(RawSettings.class, directory, filename, RawSettings.factory, RawSettings.verifier);
        return new Settings(raw);
    }

    public void flush() {
        flush(raw);
    }

    public static void flush(RawSettings settings) {
        RawSettings.verify(settings);
        LoadUtils.flush(settings, directory, filename);
    }
}
