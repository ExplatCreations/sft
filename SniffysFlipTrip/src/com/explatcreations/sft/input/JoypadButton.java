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

package com.explatcreations.sft.input;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author deweyvm
 */
public class JoypadButton {
    private static Map<String, ButtonInfo> all;
    private static Map<String, JoypadButton> allButtons;
    static {
        all = new HashMap<String, ButtonInfo>();
        all.put("DPadUp", new AxisInfo(0, -1));
        all.put("DPadDown", new AxisInfo(0, 1));
        all.put("DPadLeft", new AxisInfo(1, -1));
        all.put("DPadRight", new AxisInfo(1, 1));
        all.put("1", new FaceInfo(0));
        all.put("2", new FaceInfo(1));
        all.put("3", new FaceInfo(2));
        all.put("4", new FaceInfo(3));
        all.put("5", new FaceInfo(4));
        all.put("6", new FaceInfo(5));
        all.put("7", new FaceInfo(6));
        all.put("8", new FaceInfo(7));
        all.put("9", new FaceInfo(8));
        all.put("10",new FaceInfo(9));

        allButtons = new HashMap<String, JoypadButton>();
        for (String key : all.keySet()) {
            ButtonInfo info = all.get(key);
            allButtons.put(key, new JoypadButton(key, info));
        }
    }

    public static boolean contains(String name) {
        return allButtons.get(name) != null;
    }

    public static JoypadButton get(String name) {
        if (allButtons.get(name) == null) {
            throw new RuntimeException();
        }
        return allButtons.get(name);
    }

    public static Collection<JoypadButton> getAll() {
        return allButtons.values();
    }

    public static JoypadButton fromString(String s) {
        final JoypadButton result = allButtons.get(s);
        if (result == null) {
            throw new RuntimeException();
        }
        return result;
    }

    public final String descriptor;
    public final ButtonInfo info;
    JoypadButton(String descriptor, ButtonInfo info) {
        this.descriptor = descriptor;
        this.info = info;
    }


    //fixme data clones
    public Integer getDPadIndex() {
        if (descriptor.equals("DPadUp")) return 1;
        if (descriptor.equals("DPadDown")) return 2;
        if (descriptor.equals("DPadLeft")) return 3;
        if (descriptor.equals("DPadRight")) return 4;
        return null;
    }

    @Override
    public String toString() {
        return descriptor;
    }
}

