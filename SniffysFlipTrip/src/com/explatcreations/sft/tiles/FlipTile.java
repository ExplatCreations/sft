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
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.particles.ParticleManager;

/**
 * @author deweyvm
 */
public class FlipTile extends Tile {
    private static final Texture spriteClass = Game.assets.FlipTileGraphic;
    private static final Texture starClass = Game.assets.StarGraphic;

    private static final String StepName = "step";
    private static final String StarName = "star";

    protected boolean isOn;
    private boolean isStar;
    private boolean isRising;
    private float riseOffset = 0;
    private AnimatedSprite starSprite;

    public FlipTile(boolean isOn, boolean steppedOn) {
        super(isOn?makeOnSprite():makeOffSprite());
        this.isOn = isOn;
        if (steppedOn) {
            getSprite().play(StepName);
            getSprite().resetCurrentAnimation();
        }
    }

    private static AnimatedSprite makeOnSprite() {
        final AnimatedSprite sprite = Assets.makeTileSprite(spriteClass);
        sprite.addAnimation(StepName, 6, new int[]{9, 10, 11, 12, 13, 14}, false);
        sprite.play(StepName);
        sprite.forceFinish();
        return sprite;
    }

    private static AnimatedSprite makeOffSprite() {
        final AnimatedSprite sprite = Assets.makeTileSprite(spriteClass);
        sprite.addAnimation(StepName, 6, new int[]{23, 24, 25, 26, 27, 0}, false);
        sprite.play(StepName);
        sprite.forceFinish();
        return sprite;
    }

    public static AnimatedSprite makeRawStarSprite() {
        return Assets.makeTileSprite(starClass);
    }

    private static AnimatedSprite makeStarSprite() {
        final AnimatedSprite sprite = makeRawStarSprite();
        final String riseName = "rise";
        sprite.addAnimation(riseName, 6, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, false);
        sprite.addAnimation(StarName, 6, new int[]{10, 11, 12, 13, 14}, true);
        sprite.play(riseName);
        return sprite;
    }

    @Override
    public void beginClearing() {
        if (!isStar) {
            setSprite(FloorTile.makeSprite());
            starSprite = makeStarSprite();
            isStar = true;
        }
    }

    public static void playSound(boolean isOn) {
        if (isOn) {
            Game.assets.FlipOnSound.play();
        } else {

            Game.assets.FlipOffSound.play();
        }
    }

    @Override
    public Tile stepOn() {
        playSound(!isOn);
        return new FlipTile(!isOn, true);
    }

    @Override
    public boolean isOff() {
        return !isOn;
    }

    @Override
    public void update() {
        super.update();
        if (isStar) {
            starSprite.update();
            if (starSprite.isFinished() && !isRising) {
                starSprite.play(StarName);
                isRising = true;
            }

            if (isRising) {
                riseOffset -= 4;
            }
        }
    }

    @Override
    public void draw(final int i, final int j, final Point2i offset) {
        if (isStar) {
            FloorTile.drawFloorTileSprite(i, j, offset);
            ParticleManager.sendCallback(new IAction() {
                @Override
                public void eval() {
                    Tile.drawSprite(starSprite, i, j, offset.add(0, (int)riseOffset));
                }
            });
        }
        super.draw(i, j, offset);
    }


}
