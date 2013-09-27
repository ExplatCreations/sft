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

package com.explatcreations.sft.gui;

import com.badlogic.gdx.graphics.Color;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.input.Control;
import com.explatcreations.sft.input.DualControl;

/**
 * @author deweyvm
 */
public class Button implements ISelectable {
    protected final ParchmentLabel label;
    protected final IAction onPress;
    private Control<Boolean> shortcut;
    private final SpriteBase hotkeySprite;

    public Button(String labelText, int x, int y, IAction onPress, DualControl shortcut) {
        this.onPress = onPress;
        this.label = new ParchmentLabel(x, y, labelText);

        this.hotkeySprite = shortcut.getIcon(Color.WHITE, false);
        setX(x);
        hotkeySprite.y = label.text.y;

        this.shortcut = shortcut;
    }

    public void setX(int x) {
        label.setX(x);
        hotkeySprite.x = label.text.x + label.getWidth() + 8;
    }

    public int getX() {
        return label.bg.x;
    }

    public int getWidth() {
        return hotkeySprite.x + hotkeySprite.getWidth() - label.bg.x;
    }

    public int getHeight() {
        return label.getHeight();
    }

    private boolean justShortcutPressed() {
        return shortcut != null && shortcut.justPressed();
    }

    @Override
    public void updateSelected() {
        if (justShortcutPressed()) {
            onPress.eval();
            Game.assets.ButtonclickSound.play();
        }
    }


    public void draw(Point2i offset) {
        draw(offset, false);
    }

    @Override
    public void draw(Point2i offset, boolean selected) {
        label.draw(offset);
        hotkeySprite.drawWithOffset(offset);
    }
}