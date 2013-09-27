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
import com.explatcreations.sft.entities.IMagicAcceptor;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.input.Controls;

/**
 * @author deweyvm
 */
public class NeutralWidget implements IControlWidget {
    private final Button magicButton;
    private final IMagicAcceptor player;
    public NeutralWidget(IMagicAcceptor player) {
        this.player = player;

        final int x = HudTop.X;
        final IAction dummyAction = new IAction(){@Override public void eval(){}};
        this.magicButton = new Button("Magic", x, HudTop.AuxWidgetY, dummyAction, Controls.Enter);

    }

    @Override
    public void update() {

    }


    @Override
    public void draw() {
        if (player.getInventory().hasAnyMagic()) {
            magicButton.draw(Point2i.Zero);
        }
    }

}
