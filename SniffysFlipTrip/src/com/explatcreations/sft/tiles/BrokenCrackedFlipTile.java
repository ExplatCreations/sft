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
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.particles.ParticleManager;

/**
 * @author moopslc
 */
public class BrokenCrackedFlipTile extends Tile {
    private static final int StarTime = 30;

    private static final Texture spriteClass = Game.assets.FlipTileGraphic;
    private boolean isOn;
    private boolean isStar = false;
    private AnimatedSprite pitStarSprite;
    private Timer starTimer;

    private static AnimatedSprite makeSprite(boolean isOn) {
        final AnimatedSprite sprite = Assets.makeTileSprite(spriteClass);
        int[] frames;
        if (!isOn) {
            frames = new int[] {2, 3, 4, 5, 6, 7, 8};
        } else {
            frames = new int[] {16, 17, 18, 19, 20, 21, 22};
        }
        final String name = "play";
        sprite.addAnimation(name, 10, frames, false);
        sprite.play(name);
        return sprite;
    }

    public BrokenCrackedFlipTile(boolean isOn, boolean initial) {
        super(makeSprite(isOn));
        this.isOn = isOn;
        if (initial) {
            setToPitTile();
        }
    }

    private void setToPitTile() {
        setSprite(PitTile.makeSprite());
    }

    public BrokenCrackedFlipTile endAnimation() {
        setToPitTile();
        return this;
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public boolean isHole() {
        return true;
    }

    public static AnimatedSprite makePitStarSprite() {
        final AnimatedSprite sprite = FlipTile.makeRawStarSprite();
        final String starName = "star";
        sprite.addAnimation(starName, 12, new int[]{10, 11, 12, 13, 14}, true);
        sprite.play(starName);
        return sprite;
    }

    @Override
    public void update() {
        super.update();
        if (!isStar && getSprite().isFinished()) {
            setToPitTile();
            pitStarSprite = makePitStarSprite();
            starTimer = new Timer(StarTime);
            isStar = true;
        } else if (isStar) {
            pitStarSprite.update();
        }
    }

    @Override
    public void draw(final int i, final int j, final Point2i offset) {
        super.draw(i, j, offset);
        if (!isStar) {
            return;
        }

        if (starTimer.isMax()) {
            return;
        }

        ParticleManager.sendCallback(new IAction() {
            @Override
            public void eval() {
                starTimer.increment();
                final float newOffset = Game.RenderHeight * starTimer.getProgress2();
                pitStarSprite.x = offset.x + i*Tile.Size;
                pitStarSprite.y = (int)(offset.y + j*Tile.Size - newOffset);
                pitStarSprite.draw();
            }
        });
    }

}
