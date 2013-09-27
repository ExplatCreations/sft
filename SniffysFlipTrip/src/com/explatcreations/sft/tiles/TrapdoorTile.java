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
public class TrapdoorTile extends Tile {

    private static final String ClosedName = "closed";
    private static final String OpenedName = "opened";
    private static final String ClosingName = "closing";
    private static final String OpeningName = "opening";

    private static final Texture spriteClass = Game.assets.TrapdoorGraphic;
    private static final int animationSpeed = 10;

    private static AnimatedSprite makeSprite(boolean isBlack, boolean isOpen) {
        final AnimatedSprite sprite = Assets.makeTileSprite(spriteClass);
        if (isBlack) {
            sprite.addAnimation(ClosedName, animationSpeed, new int[]{0}, false);
            sprite.addAnimation(OpeningName, animationSpeed, new int[]{1, 2, 3, 4, 5}, false);
            sprite.addAnimation(ClosingName, animationSpeed, new int[]{5, 4, 3, 2, 1, 0}, false);
            sprite.addAnimation(OpenedName, animationSpeed, new int[]{5}, false);
        } else {
            sprite.addAnimation(ClosedName, animationSpeed, new int[]{6}, false);
            sprite.addAnimation(OpeningName, animationSpeed, new int[]{7, 8, 9, 10, 11}, false);
            sprite.addAnimation(ClosingName, animationSpeed, new int[]{11, 10, 9, 8, 7, 6}, false);
            sprite.addAnimation(OpenedName, animationSpeed, new int[]{11}, false);
        }

        if (isOpen) {
            sprite.play(OpenedName);
        } else {
            sprite.play(ClosedName);
        }
        return sprite;
    }

    private boolean isBlack;
    private boolean isOpen;
    public TrapdoorTile(boolean isBlack, boolean isOpen) {
        super(makeSprite(isBlack, isOpen));
        this.isBlack = isBlack;
        this.isOpen = isOpen;
    }

    @Override
    public Tile pressButton() {
        final boolean newIsOpen = !isOpen;
        final TrapdoorTile result = new TrapdoorTile(isBlack, newIsOpen);
        if (newIsOpen) {
            result.getSprite().play(OpeningName);
        } else {
            result.getSprite().play(ClosingName);
        }

        return result;
    }

    @Override
    public boolean isWalkable() {
        return !isOpen;
    }

    @Override
    public boolean isHole() {
        return isOpen;
    }

    @Override
    public boolean isEscapable() {
        return !isOpen;
    }
}
