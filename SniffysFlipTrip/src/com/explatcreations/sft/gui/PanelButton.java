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

import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.graphics.TextSprite;
import com.explatcreations.sft.input.Controls;

/**
 * @author moopslc
 */
public class PanelButton implements ISelectable {
    private final TextSprite text;
    private final SpriteBase border;
    private final IAction onPress;
    public PanelButton(String label, int x, int y, IAction onPress) {
        this.text = new TextSprite(x, y, label);
        this.border = text.makeHighlight();
        this.onPress = onPress;
    }

    @Override
    public void updateSelected() {
        border.update();
        if (Controls.Enter.justPressed()) {
            onPress.eval();
        }
    }
    @Override
    public void draw(Point2i offset, boolean selected) {
        if (selected) {
            border.drawWithOffset(offset);
        }
        text.drawWithOffset(offset);
    }

}
