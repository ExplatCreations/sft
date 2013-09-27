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
import com.explatcreations.sft.tiles.*;

/**
 * @author moopslc
 */
public class FireMagic extends Magic {
    private static final Texture spriteClass = Game.assets.FireballGraphic;

    private static AnimatedSprite makeSprite() {
        final AnimatedSprite sprite = Assets.makeTileSprite(spriteClass);
        final int speed = 6;
        sprite.addAnimation(getMoveName(Point2i.Right), speed, new int[]{ 0,  1,  2,  3}, true);
        sprite.addAnimation(getMoveName(Point2i.Down), speed,  new int[]{ 8,  9, 10, 11}, true);
        sprite.addAnimation(getMoveName(Point2i.Left), speed,  new int[]{16, 17, 18, 19}, true);
        sprite.addAnimation(getMoveName(Point2i.Up), speed,    new int[]{24, 25, 26, 27}, true);

        sprite.addAnimation(getExplodeName(Point2i.Right), speed, new int[]{ 4,  5,  6,  7}, false);
        sprite.addAnimation(getExplodeName(Point2i.Down), speed,  new int[]{12, 13, 14, 15}, false);
        sprite.addAnimation(getExplodeName(Point2i.Left), speed,  new int[]{20, 21, 22, 23}, false);
        sprite.addAnimation(getExplodeName(Point2i.Up), speed,    new int[]{28, 29, 30, 31}, false);
        return sprite;
    }

    public FireMagic(Grid grid, Point2i spriteOffset) {
        super(grid, spriteOffset, makeSprite());
    }

    @Override
    protected void explode() {
        Game.assets.FireballhitSound.play();
        sprite.play(getExplodeName(force));
    }

    @Override
    public void applyForce(Point2i origin, Point2i force, int extent) {
        super.applyForce(origin, force, extent);
        Game.assets.FireballcastSound.play();
    }

    @Override
    public void leaveTile(final Tile tile, final Point2i p) {
        if (tile instanceof IceFloorTile || tile instanceof CrackedIceTile) {
            grid.setTile(p, new WaterTile());
            grid.addTransform(new IAction() {
                @Override
                public void eval() {
                    grid.setTile(p, tile);
                }
            });
        }
    }

    @Override
    public void affectTile(final Tile tile, Tile forwardTile, final Point2i p) {
        if (tile instanceof IceBlockTile) {
            final MeltingIceBlockTile newTile = new MeltingIceBlockTile();
            grid.setTile(p, newTile);

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
        return tile.isPassable() || tile instanceof IceBlockTile;
    }
}
