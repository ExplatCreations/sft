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

package com.explatcreations.sft.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.graphics.AnimatedSprite;

/**
 * @author deweyvm
 */
public class CrackedFlipTile extends FlipTile {
    private static final Texture spriteClass = Game.assets.FlipTileGraphic;
    private static AnimatedSprite onSprite;
    private static AnimatedSprite offSprite;

    private static void makeSprites() {
        offSprite = Assets.makeTileFrame(spriteClass, 1);
        onSprite = Assets.makeTileFrame(spriteClass, 15);
    }

    public CrackedFlipTile(boolean isOn) {
        super(isOn, false);
        this.isOn = isOn;
        if (onSprite == null) {
            makeSprites();
        }
        setSprite(isOn? onSprite:offSprite);
    }

    @Override
    public Tile stepOn() {
        FlipTile.playSound(!isOn);
        return new CrackedFlipTile(true);
    }

    @Override
    public Tile leave() {
        return new BrokenCrackedFlipTile(isOn, false);
    }

    @Override
    public boolean isOff() {
        return !isOn;
    }


}
