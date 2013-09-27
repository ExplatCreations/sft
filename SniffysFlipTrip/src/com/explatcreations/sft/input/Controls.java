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

import com.badlogic.gdx.Input;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.enums.ControlName;
import com.explatcreations.sft.input.triggers.KeyboardTrigger;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author moopslc
 */
public class Controls {
    public static final int HorizontalAxis = 1;
    public static final int VerticalAxis = 0;

    private static Map<Integer,String> keyNames = gatherKeyNames();


    private static final List<Control<?>> controls = new ArrayList<Control<?>>();
    private static final Map<ControlName, DualControl> allControls = initKeys();

    public static final DualControl Left = getControl(ControlName.Left);
    public static final DualControl Right = getControl(ControlName.Right);
    public static final DualControl Up = getControl(ControlName.Up);
    public static final DualControl Down = getControl(ControlName.Down);
    public static final DualControl Cancel = getControl(ControlName.Back);
    public static final DualControl Retry = getControl(ControlName.Retry);
    public static final DualControl Undo = getControl(ControlName.Undo);
    public static final DualControl Enter = getControl(ControlName.Magic);
    public static final DualControl Options = getControl(ControlName.Options);
    public static final DualControl Notes = getControl(ControlName.Notes);
    public static final DualControl Magic = Enter;


    public static final Control<Boolean> CheatSkip = makeKeyControl(Input.Keys.K);
    public static final Control<Boolean> CheatUnlock = makeKeyControl(Input.Keys.U);


    public static final DualControl[] MoveControls = new DualControl[] {
            Controls.Up, Controls.Right, Controls.Down, Controls.Left
    };

    private static Map<ControlName, DualControl> initKeys() {
        final Map<ControlName, DualControl> controls = new HashMap<ControlName, DualControl>();
        for (ControlName name : ControlName.values()) {
            controls.put(name, new DualControl(name, 0, JoypadButton.get("1")));
        }
        syncControls(controls);
        return controls;
    }

    public static void syncControls(Map<ControlName, DualControl> controls) {
        for (ControlName name : ControlName.values()) {
            final int keyCode = Game.settings.getKeyCode(name);
            final JoypadButton button = Game.settings.getButton(name);
            final DualControl control = controls.get(name);
            control.setKey(keyCode);
            control.setButton(button);
        }
    }

    public static void syncControls() {
        syncControls(allControls);
    }

    public static void setKey(ControlName name, int code) {
        ControlName prevControl = null;
        int prevCode = allControls.get(name).getKeyCode();
        for (DualControl control : allControls.values()) {
            if (control.getKeyCode() == code) {
                prevControl = control.name;
            }

        }
        allControls.get(name).setKey(code);
        if (prevControl != null) {
            allControls.get(prevControl).setKey(prevCode);
        }
        flush();
    }

    public static void setButton(ControlName name, JoypadButton button) {
        ControlName prevControl = null;
        JoypadButton prevButton = allControls.get(name).getButton();
        for (DualControl control : allControls.values()) {
            if (control.getButton() == button) {
                prevControl = control.name;
            }

        }
        allControls.get(name).setButton(button);
        if (prevControl != null) {
            allControls.get(prevControl).setButton(prevButton);
        }
        flush();
    }

    private static void flush() {
        for (ControlName name:ControlName.values()) {
            Game.settings.setKey(name, getControl(name).getKeyCode());
            Game.settings.setButton(name, getControl(name).getButton());
        }
        verify();
        Game.settings.flush();
    }

    private static void verify() {
        Game.settings.verify();
    }

    public static DualControl getControl(ControlName name) {
        return allControls.get(name);
    }

    private static Control<Boolean> makeKeyControl(Integer... keys) {
        List<KeyboardTrigger> triggers = new ArrayList<KeyboardTrigger>();
        for (Integer key : keys) {
            triggers.add(new KeyboardTrigger(key));
        }
        return new TriggerAggregate(triggers);
    }

    public static <T extends Control<?>> void add(T control) {
        controls.add(control);
    }

    public static boolean isSuspended() {
        return false;
    }

    public static Point2i directionJustPressed(Point2i $else) {
        if (Up.justPressed()) {
            return Point2i.Up;
        } else if (Down.justPressed()) {
            return Point2i.Down;
        } else if (Left.justPressed()) {
            return Point2i.Left;
        } else if (Right.justPressed()) {
            return Point2i.Right;
        }
        return $else;
    }

    public static Point2i directionIsPressed(Point2i $else) {
        if (Up.isPressed()) {
            return Point2i.Up;
        } else if (Down.isPressed()) {
            return Point2i.Down;
        } else if (Left.isPressed()) {
            return Point2i.Left;
        } else if (Right.isPressed()) {
            return Point2i.Right;
        }
        return $else;
    }

    public static void update() {
        for (Control<?> control : controls) {
            control.update();
        }
        for(Control<?> control : allControls.values()) {
            control.update();
        }
    }

    /**
     * fixme -- should not allow keys to be bound to unknown keycodes
     * @param code the raw key code
     * @return null if the code has no name known to libgdx
     */
    public static String getKeyName(int code) {
        return keyNames.get(code);
    }

    private static String SCREAMING_CAPS2CamelCase(String INPUT_STRING) {
        final String[] split  = INPUT_STRING.split("_");

        for (int i = 0; i < split.length; i += 1) {
            final String segment = split[i];
            String processed;
            if (segment.length() <= 1) {
                processed = segment.toUpperCase();
            } else {
                processed = segment.substring(0, 1).toUpperCase() + segment.substring(1, segment.length()).toLowerCase();
            }
            split[i] = processed;
        }

        return join(Arrays.asList(split));
    }

    private static String join(List<String> strings) {
        final StringBuilder builder = new StringBuilder();
        for (String s : strings) {
            builder.append(s);
        }
        return builder.toString();
    }

    private static Map<Integer,String> gatherKeyNames() {
        final Class cls = Input.Keys.class;
        final Field[] fields = cls.getFields();
        final Map<Integer, String> result = new HashMap<Integer, String>();
        for (Field f : fields) {
            try {
                result.put(f.getInt(null), SCREAMING_CAPS2CamelCase(f.getName()));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

}
