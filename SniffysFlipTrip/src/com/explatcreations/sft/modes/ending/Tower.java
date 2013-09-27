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

package com.explatcreations.sft.modes.ending;

import com.badlogic.gdx.graphics.Texture;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.graphics.SpriteBase;

/**
 * @author moopslc
 */
public class Tower {
    private static final Texture towerGraphic = Game.assets.TowerGraphic;
    private static final Texture towerShadowGraphic = Game.assets.TowerShadowGraphic;
    public static final int Width = 27;
    public static final int Height = 189;
    public static final int X = 232;
    public static final int Y = 42;
    public static final int ShadowX = X + 13;
    public static final int ShadowY = Y + (192 - 26);
    public static final int ShadowWidth = 108;
    public static final int ShadowHeight = 38;

    public static SpriteBase makeTower() {
        final SpriteBase tower = Assets.makeSpriteFrame(towerGraphic, Tower.Width, Tower.Height, 0);
        tower.x = X;
        tower.y = Y;
        return tower;
    }

    public static SpriteBase makeShadow() {
        return makeShadow(0.3f);
    }

    public static SpriteBase makeShadow(float alpha) {
        final SpriteBase sprite = Assets.makeSpriteFrame(towerShadowGraphic, ShadowWidth, ShadowHeight, 0);
        sprite.x = ShadowX;
        sprite.y = ShadowY;
        sprite.setAlpha(alpha);
        return sprite;
    }
}
