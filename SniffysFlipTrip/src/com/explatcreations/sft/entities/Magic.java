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

import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.grid.Grid;
import com.explatcreations.sft.tiles.TeleporterTile;
import com.explatcreations.sft.tiles.Tile;

/**
 * @author deweyvm
 */
public abstract class Magic extends GridEntity {
    protected Point2i force;
    protected Point2i dir;
    protected final Grid grid;
    protected boolean exploding = false;

    private Timer timer = new Timer(Ball.MoveTime);

    public Magic(Grid grid, Point2i spriteOffset, AnimatedSprite sprite) {
        super(spriteOffset, sprite);
        this.grid = grid;
    }

    protected static String getMoveName(Point2i dir) {
        return "move" + dir.getDirection();
    }

    protected static String getExplodeName(Point2i dir) {
        return "explode" + dir.getDirection();
    }

    public boolean isMoving() {
        return force != null;
    }

    public void applyForce(Point2i origin, Point2i force, int extent) {
        if (force == null) {
            throw new RuntimeException();
        }
        setIndex(origin);
        this.force = force;
        this.dir = force;
        this.timer = new Timer(Ball.MoveTime * extent);

        sprite.play(getMoveName(force));

        testForward();
    }

    @Override
    public void update() {
        super.update();
        if (force != null) {
            final Point2i index = getIndex();
            sprite.x = (int)(Tile.Size * (index.x + force.x * timer.getProgress()));
            sprite.y = (int)(Tile.Size * (index.y + force.y * timer.getProgress()));
            if (timer.isMax()) {
                this.leaveTile(grid.getTile(getIndex()), getIndex());

                addIndex(force);
                timer.reset();
                if (force == null) {
                    //throw new Error("ERROR");
                }

                testForward();
            }
            timer.increment();
        }
    }

    private void testForward() {
        final Tile newTile = grid.getTile(getIndex());
        if (newTile instanceof TeleporterTile) {
            final TeleporterTile teleporter = (TeleporterTile)newTile;
            final Point2i telePos = grid.getPartner(getIndex(), teleporter);
            if (telePos != null) {
                setIndex(telePos);
                Game.assets.TeleportSound.play();
            } else {
                Game.assets.TelefailSound.play();
            }
        }
        final Tile forwardTile = grid.getTile(getIndex().add(force));
        this.affectTile(grid.getTile(getIndex()), forwardTile, getIndex());
        if (force == null) {
            return;
        }
        if (!canPass(forwardTile)) {
            if (this instanceof IceMagic){ //HACK
                affectTile(forwardTile, null, getIndex().add(force));
            }
            leaveTile(grid.getTile(getIndex()), getIndex());

            cease();
        }
    }

    protected abstract void explode();
    public abstract boolean canPass(Tile tile);
    public abstract void leaveTile(Tile tile, Point2i p);
    public abstract void affectTile(Tile tile, Tile forwardTile, Point2i p);

    public boolean isDone() {
        return exploding && sprite.isFinished();
    }

    public void cease() {
        exploding = true;
        this.explode();
        force = null;
    }
}
