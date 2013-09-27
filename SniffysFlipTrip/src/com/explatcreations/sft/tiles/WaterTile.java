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
import com.explatcreations.sft.Engine;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.graphics.AnimatedSprite;

/**
 * @author deweyvm
 */
public class WaterTile extends Tile {
    private static final Texture waterSpriteClass = Game.assets.WaterTileGraphic;
    private static AnimatedSprite waterSprite = makeWaterSprite();

    private static final Texture brokenSpriteClass = Game.assets.IceSplashGraphic;

    private static AnimatedSprite makeWaterSprite() {
        final AnimatedSprite sprite = Assets.makeTileSprite(waterSpriteClass);
        final String name = "animate";
        sprite.addAnimation(name, 5, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, true);
        sprite.play(name);
        return sprite;
    }

    private static AnimatedSprite makeBreakSprite() {
        final AnimatedSprite sprite = Assets.makeTileSprite(brokenSpriteClass);
        final String name = "animate";
        sprite.addAnimation(name, 15, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, false);
        sprite.play(name);
        return sprite;
    }

    private static int lastUpdated = 0;
    private int splashTimer = 0;
    private boolean isBroken = false;

    public WaterTile() {
        super(waterSprite);
    }


    @Override
    public void update() {
        if (isBroken) {
            super.update();
            splashTimer += 1;
            if (splashTimer == 2/*arbitrary*/) {
                Game.assets.SplashSound.play();
            }
        } else if (Engine.getFrame() != lastUpdated) {
            super.update();
            lastUpdated = Engine.getFrame();
        }

        if (isBroken && getSprite().isFinished()) {
            setSprite(waterSprite);
            isBroken = false;
        }
    }

    public void beBrokenIce() {
        final AnimatedSprite newSprite = makeBreakSprite();
        setSprite(newSprite);
        isBroken = true;
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public boolean isHole() {
        return true;
    }
}
