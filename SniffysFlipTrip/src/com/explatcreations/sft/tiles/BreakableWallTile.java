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
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.graphics.AnimatedSprite;

/**
 * @author deweyvm
 */
public class BreakableWallTile extends Tile {

    private static Texture darkSpriteClass = Game.assets.DarkBreakableWallGraphic;
    private static Texture lightSpriteClass = Game.assets.BreakableWallGraphic;

    private static final String BuildName = "build";
    private AnimatedSprite background;

    private static AnimatedSprite makeSprite(Texture t) {
        AnimatedSprite sprite = Assets.makeTileSprite(t);
        sprite.addAnimation(BuildName, 4, new int[]{0, 1, 2, 3, 4, 5}, false);
        final String stopName = "stop";
        sprite.addAnimation(stopName, 1, new int[]{5}, false);
        sprite.play(stopName);
        return sprite;
    }

    public BreakableWallTile(boolean isDark) {
        super(makeSprite(isDark?darkSpriteClass:lightSpriteClass));
    }

    public void doBuildAnimation(AnimatedSprite background) {
        this.background = background;
        getSprite().play(BuildName);
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public void update() {
        super.update();
        if (getSprite().isFinished()) {
            background = null;
        }
    }

    @Override
    public void draw(int i, int j, Point2i offset) {
        if (background == null) {
            FloorTile.drawFloorTileSprite(i, j, offset);
        } else {
            Tile.drawSprite(background, i, j, offset);
        }
        super.draw(i, j, offset);
    }

}
