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
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.graphics.Sprite2d;
import com.explatcreations.sft.input.Controls;
import com.explatcreations.sft.input.DualControl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author moopslc
 */
public class AimWidget implements IControlWidget {
    private final ParchmentLabel aimLabel;
    private final List<ControlIcon> icons = new ArrayList<ControlIcon>();

    public AimWidget() {
        this.aimLabel = new ParchmentLabel(HudTop.X, HudTop.AuxWidgetY, "Aim");
        DualControl[] moveControls = Controls.MoveControls;
        for (int i = 0; i < moveControls.length; i += 1) {
            final DualControl control = moveControls[i];
            final ControlIcon icon = control.getIcon(Color.WHITE, false);
            icon.x = aimLabel.bg.x + aimLabel.getWidth() + 10;
            icon.y =  HudTop.AuxWidgetY + HudTop.ySpace*i + 5;
            icons.add(icon);

        }

    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        aimLabel.draw(Point2i.Zero);
        for (Sprite2d sprite : icons) {
            sprite.draw();
        }
    }
}
