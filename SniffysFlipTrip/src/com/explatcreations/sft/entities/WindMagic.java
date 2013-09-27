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

package com.explatcreations.sft.entities;

import com.badlogic.gdx.graphics.Texture;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.grid.Grid;
import com.explatcreations.sft.tiles.BreakableWallTile;
import com.explatcreations.sft.tiles.FloorTile;
import com.explatcreations.sft.tiles.IceBlockTile;
import com.explatcreations.sft.tiles.Tile;

/**
 * @author deweyvm
 */
public class WindMagic extends Magic {
    private static final Texture spriteClass = Game.assets.WindBallGraphic;

    private static AnimatedSprite makeSprite() {
        final AnimatedSprite sprite = Assets.makeTileSprite(spriteClass);
        final int speed = 5;

        sprite.addAnimation(getSpawnName(Point2i.Right), speed, new int[]{0, 1, 2}, false);
        sprite.addAnimation(getSpawnName(Point2i.Down), speed, new int[]{9, 10, 11}, false);
        sprite.addAnimation(getSpawnName(Point2i.Left), speed, new int[]{18, 19, 20}, false);
        sprite.addAnimation(getSpawnName(Point2i.Up), speed, new int[]{27, 28, 29}, false);

        sprite.addAnimation(getMoveName(Point2i.Right), speed, new int[]{2, 3}, false);
        sprite.addAnimation(getMoveName(Point2i.Down), speed, new int[]{11, 12}, false);
        sprite.addAnimation(getMoveName(Point2i.Left), speed, new int[]{20, 21}, false);
        sprite.addAnimation(getMoveName(Point2i.Up), speed, new int[]{29, 30}, false);

        sprite.addAnimation(getExplodeName(Point2i.Right), speed, new int[]{4, 5, 6, 7, 8}, false);
        sprite.addAnimation(getExplodeName(Point2i.Down), speed, new int[]{13, 14, 15, 16, 17}, false);
        sprite.addAnimation(getExplodeName(Point2i.Left), speed, new int[]{22, 23, 24, 25, 26}, false);
        sprite.addAnimation(getExplodeName(Point2i.Up), speed, new int[]{31, 32, 33, 34, 35}, false);
        return sprite;
    }

    private static String getSpawnName(Point2i dir) {
        return "spawn" + dir.getDirection();
    }

    public WindMagic(Grid grid, Point2i spriteOffset) {
        super(grid, spriteOffset, makeSprite());
    }

    @Override
    public void applyForce(Point2i origin, Point2i force, int extent) {
        super.applyForce(origin, force, extent);
        if (!exploding) {
            sprite.play(getSpawnName(force));
        }
        Game.assets.WindmagiccastSound.play();
    }

    @Override
    protected void explode() {
        Game.assets.WindmagichitSound.play();
        sprite.play(getExplodeName(force));
    }

    @Override
    public void update() {
        super.update();

        //hackish
        if (!isDone() && sprite.isFinished()) {
            sprite.play(getMoveName(force));
        }
    }

    @Override
    public void leaveTile(Tile tile, Point2i p) {
        //do nothing
    }

    @Override
    public void affectTile(final Tile tile, Tile forwardTile, final Point2i p) {
        final Ball ball = grid.getBall(getIndex());
        if (ball != null) {
            if (forwardTile.isPassable()) {
                ball.applyForce(force, false);
            }
            cease();
        }
        if (tile instanceof BreakableWallTile || tile instanceof IceBlockTile) {
            grid.setTile(p, new FloorTile());
            cease();
            grid.addTransform(new IAction() {
                @Override
                public void eval() {
                    grid.setTile(p, tile);
                }
            });
        }
    }

    @Override
    public boolean canPass(Tile tile) {
        return tile.isPassable() || (tile instanceof BreakableWallTile);
    }
}
