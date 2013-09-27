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
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.tiles.Tile;

/**
 * @author deweyvm
 */
public class GridEntity {
    private int i = 0;
    private int j = 0;
    protected AnimatedSprite sprite;
    protected final Point2i offset;
    public GridEntity(Point2i offset, final AnimatedSprite sprite) {
        this.offset = offset;
        if (sprite == null) {
            this.sprite = Game.assets.BlankSprite;
        } else {
            this.sprite = sprite;
        }
        this.sprite.offset = offset;
    }

    protected void sync() {
        sprite.x = i*Tile.Size;
        sprite.y = j*Tile.Size;
    }

    public Point2i getIndex() {
        return new Point2i(i, j);
    }

    public Point2i getPosition(Point2i gridOffset) {
        return new Point2i(i*Tile.Size, j*Tile.Size).add(gridOffset);
    }

    public void setPosition(int x, int y) {
        sprite.x = x;
        sprite.y = y;
    }

    public void setIndex(Point2i index) {
        i = index.x;
        j = index.y;
        sync();
    }

    public void addIndex(Point2i point) {
        i += point.x;
        j += point.y;
        sync();
    }

    public void update() {
        sprite.update();
    }

    public void draw() {
        sprite.draw();
    }
}
