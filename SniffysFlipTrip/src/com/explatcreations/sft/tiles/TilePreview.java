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

import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.entities.Ball;
import com.explatcreations.sft.enums.BallType;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.grid.GridMock;

import java.util.HashMap;
import java.util.Map;

/**
 * @author deweyvm
 */
public class TilePreview {
    private static final Map<BallType,SpriteBase> miniBallCache= initMiniBalls();
    private static final Map<BallType,SpriteBase> ballCache = initBalls();

    private MiniTile miniTile;
    private SpriteBase ball;
    private SpriteBase miniBall;
    public TilePreview(Tile tile, BallType ballType) {
        this.miniTile = tile.getMiniTile();
        if (ballType != null) {
            this.ball = ballCache.get(ballType);
            this.miniBall = miniBallCache.get(ballType);
        }
    }

    public void draw(int i, int j, Point2i pos) {
        if (miniTile != null) {
            miniTile.draw(i, j, pos);
        }
        if (ball != null) {
            MiniTile.drawSprite(miniBall, i, j, pos);
        }
    }

    private static Map<BallType,SpriteBase> initMiniBalls() {
        final Map<BallType,SpriteBase> result = new HashMap<BallType, SpriteBase>();
        for (BallType type : BallType.values()) {
            final Ball ball = new Ball(new GridMock(), type, Point2i.Zero, Point2i.Zero);
            result.put(type, MiniTile.createSmallSprite(ball.getSprite()));
        }
        return result;
    }

    private static Map<BallType,SpriteBase> initBalls() {
        final Map<BallType,SpriteBase> result = new HashMap<BallType, SpriteBase>();
        for (BallType type : BallType.values()) {
            result.put(type, new Ball(new GridMock(), type, Point2i.Zero, Point2i.Zero).getSprite());
        }
        return result;
    }
}
