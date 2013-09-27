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
import com.explatcreations.sft.enums.ControlName;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.graphics.TextSprite;
import com.explatcreations.sft.input.Controls;

import java.util.ArrayList;
import java.util.List;

/**
 * @author deweyvm
 */
public class ControlsPanel extends Panel {
    private final TextSprite title;
    private final List<ISelectable> widgets = new ArrayList<ISelectable>();
    private final ClickyCursor cursor;
    public ControlsPanel(IAction unset) {
        super(unset);
        this.title = new TextSprite(110, 16, "Configure Controls");
        final IAction resyncAll = new IAction() {
            @Override
            public void eval() {
                for (ISelectable widget : widgets) {
                    if (widget instanceof ControlWidget) {
                        ((ControlWidget)widget).resync();
                    }
                }
            }
        };
        final ControlName[] values = ControlName.values();
        for (int i = 0; i < values.length; i += 1) {
            ControlName name = values[i];
            widgets.add(new ControlWidget(name, 120, /*5 + i*26*/40 + i*19, resyncAll));
        }
        widgets.add(new PanelButton("Reset to default", 110, 227, new IAction() {
            @Override
            public void eval() {
                Game.settings.resetControls();
                resyncAll.eval();
            }
        }));
        widgets.add(new PanelButton("Back", 110, 246, new IAction() {
            @Override
            public void eval() {
                cancel();
            }
        }));
        this.cursor = new ClickyCursor(widgets.size() - 1);
    }

    @Override
    public void updateNormal() {
        title.update();
        for (int i = 0; i < widgets.size(); i += 1) {
            if (cursor.getValue() == i) {
                widgets.get(i).updateSelected();
            }
        }
        final ISelectable widget = widgets.get(cursor.getValue());
        if ((widget instanceof ControlWidget) && ((ControlWidget)widget).isActive()) {
            return;
        }

        if (Controls.Up.menuZip()) {
            cursor.decrement();
        } else if (Controls.Down.menuZip()) {
            cursor.increment();
        }

    }

    @Override
    protected int getWidth() {
        return super.getWidth() + 10;
    }

    @Override
    protected int getHeight() {
        return super.getHeight() + 32;
    }

    @Override
    protected int getYOffset() {
        return super.getYOffset() + 4;
    }

    @Override
    public void drawSelf(Point2i offset) {
        for (int i = 0; i < widgets.size(); i += 1) {
            widgets.get(i).draw(offset, cursor.getValue() == i);
        }
        title.drawWithOffset(offset);
    }
}
