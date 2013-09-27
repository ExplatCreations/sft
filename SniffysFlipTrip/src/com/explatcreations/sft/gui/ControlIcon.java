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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.explatcreations.sft.graphics.SpriteBase;

/**
 * @author moopslc
 */
public class ControlIcon extends SpriteBase {
    private final SpriteBase background;
    private final SpriteBase icon;
    private final int xOffset;
    private final int yOffset;
    private ControlIcon(SpriteBase base, SpriteBase border, int xOffset, int yOffset) {
        super(0, 0);
        this.icon = base;
        this.background = border;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public static ControlIcon makeKeyIcon(SpriteBase base, boolean isOnParchment) {
        return new ControlIcon(base, base.makeKeyBorder(isOnParchment), 6, 3);
    }

    public static ControlIcon makeButtonIcon(SpriteBase base, boolean isOnParchment, boolean isDPad) {
        final SpriteBase border = isDPad? null : base.makeJoyBorder(isOnParchment) ;
        return new ControlIcon(base, border, 8, 4);
    }

    @Override
    public TextureRegion getRegion() {
        return icon.getRegion();
    }

    @Override
    public void setAlpha(float alpha) {
        icon.setAlpha(alpha);
        if (background != null) {
            background.setAlpha(alpha);
        }
    }

    @Override
    public void setColor(Color color) {
        icon.setColor(color);
        if (background != null) {
            background.setColor(color);
        }
    }

    @Override
    public int getHeight() {
        if (background != null) {
            return background.getHeight();
        }
        return icon.getHeight();
    }

    @Override
    public int getWidth() {
        if (background != null) {
            return background.getWidth();
        }
        return icon.getWidth();
    }

    @Override
    public void update() {

    }

    @Override
    public void drawSelf(float x, float y) {
        if (background != null) {
            background.x = (int)x - xOffset;
            background.y = (int)y - yOffset;
            background.draw();
        }
        icon.drawSelf(x, y);
    }

}
