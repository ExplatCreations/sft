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
import com.explatcreations.sft.entities.Ball;
import com.explatcreations.sft.graphics.AnimatedSprite;

/**
 * @author deweyvm
 */
public class ShutterTile extends Tile {
    private static final Texture spriteClass = Game.assets.ShutterTileGraphic;
    private static final String FinishedName = "finished";

    private Ball ball;

    private static AnimatedSprite makeSprite() {
        final AnimatedSprite sprite = Assets.makeTileSprite(spriteClass);
        final String shutName= "play";
        sprite.addAnimation(shutName, 12, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8}, false);
        sprite.play(shutName);
        sprite.addAnimation(FinishedName, 12, new int[]{8}, false);
        return sprite;
    }

    public ShutterTile finishAnimation() {
        getSprite().play(FinishedName);
        return this;
    }

    public ShutterTile(Ball ball) {
        super(makeSprite());
        this.ball = ball;
    }

    @Override
    public boolean isPassable() {
        return true;
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public void draw(int i, int j, Point2i offset) {
        ball.forceDraw(i, j, offset);
        super.draw(i, j, offset);
    }


}
