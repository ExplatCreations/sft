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

import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.graphics.TextSprite;
import com.explatcreations.sft.input.Controls;

import java.util.ArrayList;
import java.util.List;

/**
 * @author deweyvm
 */
public class YesNoPanel extends Panel {
    private TextSprite textSprite;
    private List<ISelectable> buttons = new ArrayList<ISelectable>();
    private ClickyCursor cursor;
    final int x = Game.RenderWidth/2;
    public YesNoPanel(String text, final IAction yesFunction, final IAction noFunction) {
        super(noFunction);

        this.textSprite = new TextSprite(100, 127, text);
        textSprite.x = x - textSprite.getWidth()/2;
        buttons.add(makeYesButton(yesFunction));
        buttons.add(makeNoButton(noFunction));
        this.cursor = new ClickyCursor(buttons.size() - 1);
    }

    private ISelectable makeYesButton(final IAction yesFunction) {
        final IAction yesAction = new IAction() {
            @Override
            public void eval() {
                changeUnsetter(yesFunction);
                cancel();
            }
        };
        return new PanelButton("yes", x - 60, 150, yesAction);

    }

    private ISelectable makeNoButton(final IAction noFunction) {
        final IAction noAction = new IAction() {
            @Override
            public void eval() {
                changeUnsetter(noFunction);
                cancel();
            }
        };
        return new PanelButton("no", x + 40, 150, noAction);
    }

    @Override
    protected int getYOffset() {
        return 10;
    }

    @Override
    protected int getWidth() {
        return 220;
    }

    @Override
    protected int getHeight() {
        return 90;
    }

    @Override
    public void update() {
        super.update();
        if(Controls.Left.justPressed()) {
            cursor.decrement();
        } else if (Controls.Right.justPressed()) {
            cursor.increment();
        }
        buttons.get(cursor.getValue()).updateSelected();
    }

    @Override
    protected void drawSelf(Point2i offset) {
        textSprite.drawWithOffset(offset);

        for (int i = 0; i < buttons.size(); i += 1) {
            buttons.get(i).draw(offset, i == cursor.getValue());
        }
    }
}
