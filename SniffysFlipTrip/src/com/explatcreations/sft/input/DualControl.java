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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.enums.ControlName;
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.graphics.TextSprite;
import com.explatcreations.sft.gui.ControlIcon;
import com.explatcreations.sft.input.triggers.JoypadTrigger;
import com.explatcreations.sft.input.triggers.KeyboardTrigger;

/**
 * @author moopslc
 */
public class DualControl implements Control<Boolean> {

    public final ControlName name;

    private KeyboardTrigger keyboardTrigger;
    private JoypadTrigger joypadTrigger;

    public DualControl(ControlName name, int key, JoypadButton button) {
        this.name = name;
        this.keyboardTrigger = new KeyboardTrigger(key);
        this.joypadTrigger = new JoypadTrigger(button);
    }

    public ControlIcon getKeyIcon(Color color, boolean isOnParchment) {
        String name = Controls.getKeyName(keyboardTrigger.code);
        if (name == null) {
            name = "???";
        }
        if (name.matches("Left|Right|Up|Down")) {
            final SpriteBase sprite = Assets.makeArrowSprite(name);
            sprite.setColor(color);
            return ControlIcon.makeKeyIcon(sprite, isOnParchment);
        }
        return ControlIcon.makeKeyIcon(new TextSprite(0, 0, name, color), isOnParchment);
    }

    public ControlIcon getButtonIcon(Color color, boolean isOnParchment) {
        final Integer dPadIndex = joypadTrigger.button.getDPadIndex();
        final boolean isDPad = dPadIndex != null;
        if (isDPad) {
            final Texture t = isOnParchment? Game.assets.DPadGraphic: Game.assets.DPadClearGraphic;
            final AnimatedSprite dpad = Assets.makeSpriteFrame(t, t.getHeight(), t.getHeight(), dPadIndex);
            final ControlIcon icon = ControlIcon.makeButtonIcon(dpad, isOnParchment, isDPad);
            icon.offset = new Point2i(-9, 0);
            return icon;
        } else {
            final String name = joypadTrigger.button.descriptor;
            return ControlIcon.makeButtonIcon(new TextSprite(0, 0, name, color), isOnParchment, isDPad);
        }
    }

    public ControlIcon getIcon(Color color, boolean isOnParchment) {
        if (Game.joypadHelper.controller == null) {
            return getKeyIcon(color, isOnParchment);
        } else {
            return getButtonIcon(color, isOnParchment);
        }
    }

    public int getKeyCode() {
        return keyboardTrigger.code;
    }

    public JoypadButton getButton() {
        return joypadTrigger.button;
    }

    public void setKey(int code) {
        keyboardTrigger = new KeyboardTrigger(code);
        flag = 2;
    }

    public void setButton(JoypadButton button) {
        joypadTrigger = new JoypadTrigger(button);
        flag = 2;
    }


    private int flag;
    private int prevFlag;

    @Override
    public void update() {
        prevFlag = flag;
        if (keyboardTrigger.isPressed() || joypadTrigger.isPressed()) {
            flag = MathUtils.clamp(flag + 1, 0, Integer.MAX_VALUE - 1);
        } else {
            flag = 0;
        }
    }

    @Override
    public Boolean isPressed() {
        return flag > 0;
    }

    @Override
    public Boolean justPressed() {
        return flag == 1;
    }

    @Override
    public Boolean justReleased() {
        return flag == 0 && prevFlag != 0;
    }

    @Override
    public Boolean zip(int start, int num) {
        return justPressed() || (flag > start && flag % num == 0);
    }

    public boolean menuZip() {
        return zip(25, 4);
    }

    public void hackIncrement() {
        flag = 2;
    }
}
