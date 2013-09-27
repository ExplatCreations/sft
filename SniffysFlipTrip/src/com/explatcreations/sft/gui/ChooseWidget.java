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
import com.explatcreations.sft.enums.MagicType;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.grid.Inventory;
import com.explatcreations.sft.input.Controls;
import com.explatcreations.sft.tiles.MagicTile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author moopslc
 */
public class ChooseWidget implements IControlWidget {
    private final ParchmentLabel label;
    private final List<SpriteBase> orbSprites;
    private final List<ControlIcon> icons;
    public ChooseWidget(Inventory inventory) {

        this.label = new ParchmentLabel(HudTop.X, HudTop.AuxWidgetY, "Choose");

        this.orbSprites = new ArrayList<SpriteBase>();
        this.icons = new ArrayList<ControlIcon>();

        final MagicType[] allMagic = MagicType.values();
        for (int i = 0; i < allMagic.length; i += 1) {
            final MagicType magicType = allMagic[i];
            if (inventory.hasMagic(magicType)) {
                final SpriteBase orb = MagicTile.makeSprite(magicType);
                orb.x = label.getWidth() + 5;
                orb.y = HudTop.AuxWidgetY + i*HudTop.ySpace + 5;
                orbSprites.add(orb);
                final Color iconColor = Color.WHITE;
                final ControlIcon icon;
                //fixme put direction <=> magic correspondence in a fixed place
                if (magicType == MagicType.Fire) {
                    icon = Controls.Up.getIcon(iconColor, false);
                } else if (magicType == MagicType.Ice) {
                    icon = Controls.Right.getIcon(iconColor, false);
                } else if (magicType == MagicType.Earth) {
                    icon = Controls.Down.getIcon(iconColor, false);
                } else /*if (magicType == MagicType.Wind)*/ {
                    icon = Controls.Left.getIcon(iconColor, false);
                }
                icon.x = orb.x + orb.getWidth() + 9;
                icon.y = orb.y - 4;
                icons.add(icon);
            }
        }
    }

    @Override
    public void update() {
        for (SpriteBase orb:orbSprites) {
            orb.update();
        }
    }

    @Override
    public void draw() {
        label.draw(Point2i.Zero);
        for(SpriteBase s:orbSprites) {
            s.draw();
        }
        for(ControlIcon i:icons) {
            i.draw();
        }
    }
}
