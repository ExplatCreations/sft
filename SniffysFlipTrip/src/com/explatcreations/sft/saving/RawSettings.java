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

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.enums.ControlName;
import com.explatcreations.sft.enums.MDisplayMode;
import com.explatcreations.sft.functions.IAction1;
import com.explatcreations.sft.functions.IFunction1;
import com.explatcreations.sft.graphics.MDisplay;
import com.explatcreations.sft.input.JoypadButton;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author moopslc
 */
public class RawSettings {
    public HashMap<String, String> joypadConfig;
    public HashMap<String, Float> keyConfig;
    public MDisplayMode display;
    public float sfx;
    public float music;
    private RawSettings() { }

    public static final IFunction1<RawSettings> factory = new IFunction1<RawSettings>() {
        @Override
        public RawSettings apply() {
            return makeNew();
        }
    };
    public static final IAction1<RawSettings> verifier = new IAction1<RawSettings>() {
        @Override
        public void eval(RawSettings arg1) {
            verify(arg1);
        }
    };

    public static void verify(RawSettings settings) {
        settings.sfx = MathUtils.clamp(settings.sfx, 0, 1);
        settings.music = MathUtils.clamp(settings.music, 0, 1);
        settings.display = MDisplay.verify(settings.display);


        settings.joypadConfig = verifyMap(settings.joypadConfig, new IFunction1<HashMap<String, String>>() {
            @Override
            public HashMap<String, String> apply() {
                return makeButtonMap();
            }
        });
        settings.joypadConfig = verifyButtonConfig(settings.joypadConfig, new IFunction1<HashMap<String, String>>() {
            @Override
            public HashMap<String, String> apply() {
                return makeButtonMap();
            }
        });
        settings.keyConfig = verifyMap(settings.keyConfig, new IFunction1<HashMap<String, Float>>() {
            @Override
            public HashMap<String, Float> apply() {
                return makeKeyMap();
            }
        });

    }

    private static HashMap<String, String> verifyButtonConfig(HashMap<String, String> buttonConfig, IFunction1<HashMap<String, String>> makeDefault) {
        for (String name : buttonConfig.keySet()) {
            if (!JoypadButton.contains(name)) {
                return makeDefault.apply();
            }
        }
        return buttonConfig;
    }


    private static <T> HashMap<String, T> verifyMap(HashMap<String, T> map, IFunction1<HashMap<String, T>> makeDefault) {
        if (map == null) {
            return makeDefault.apply();
        }
        final ArrayList<T> foundNames = new ArrayList<T>();
        for (ControlName name:ControlName.values()) {
            T button = map.get(name.toString());
            if (button == null || foundNames.contains(button)) {
                //fixme -- if two buttons are bound to the same control. could possibly be nicer than resetting over this
                return makeDefault.apply();
            }
            foundNames.add(button);
        }
        return map;
    }

    public static RawSettings makeNew() {
        final RawSettings result = new RawSettings();
        result.display =  new MDisplayMode(Game.WindowWidth, Game.WindowHeight, MDisplayMode.DisplayType.Windowed);
        result.sfx = 0.1f;
        result.music = 0.1f;
        result.keyConfig = makeKeyMap();
        result.joypadConfig = makeButtonMap();
        return result;
    }

    public static HashMap<String, Float> makeKeyMap() {
        final HashMap<String, Float> keyConfig = new HashMap<String, Float>();
        keyConfig.put(ControlName.Up.toString(),      Input.Keys.UP + 0f);
        keyConfig.put(ControlName.Down.toString(),    Input.Keys.DOWN + 0f);
        keyConfig.put(ControlName.Left.toString(),    Input.Keys.LEFT + 0f);
        keyConfig.put(ControlName.Right.toString(),   Input.Keys.RIGHT + 0f);
        keyConfig.put(ControlName.Back.toString(),    Input.Keys.ESCAPE + 0f);
        keyConfig.put(ControlName.Magic.toString(),   Input.Keys.ENTER + 0f);
        keyConfig.put(ControlName.Undo.toString(),    Input.Keys.TAB + 0f);
        keyConfig.put(ControlName.Notes.toString(),   Input.Keys.N + 0f);
        keyConfig.put(ControlName.Options.toString(), Input.Keys.O + 0f);
        keyConfig.put(ControlName.Retry.toString(),   Input.Keys.R + 0f);
        return keyConfig;
    }

    public static HashMap<String, String> makeButtonMap() {
        final HashMap<String, String> joypadConfig = new HashMap<String, String>();
        joypadConfig.put(ControlName.Up.toString(),      "DPadUp");
        joypadConfig.put(ControlName.Down.toString(),    "DPadDown");
        joypadConfig.put(ControlName.Left.toString(),    "DPadLeft");
        joypadConfig.put(ControlName.Right.toString(),   "DPadRight");
        joypadConfig.put(ControlName.Back.toString(),    "1");
        joypadConfig.put(ControlName.Magic.toString(),   "2");
        joypadConfig.put(ControlName.Undo.toString(),    "6");
        joypadConfig.put(ControlName.Notes.toString(),   "7");
        joypadConfig.put(ControlName.Options.toString(), "8");
        joypadConfig.put(ControlName.Retry.toString(),   "5");
        return joypadConfig;
    }
}
