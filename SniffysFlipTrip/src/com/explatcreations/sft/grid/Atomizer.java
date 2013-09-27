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

package com.explatcreations.sft.grid;

import com.badlogic.gdx.graphics.Texture;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2f;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.graphics.StaticSprite;
import com.explatcreations.sft.particles.AtomizerParticle;

/**
 * @author moopslc
 */
public class Atomizer {
    private final AtomizerParticle[] entities;
    private final int size = 2;
    private int cols;
    private int rows;
    private boolean done;
    private boolean playedSound;
    private boolean reversed;
    public Atomizer(AnimatedSprite source, Point2i targetPos, boolean reverse) {
        this.reversed = reverse;
        this.cols = source.getWidth() / size;
        this.rows = source.getHeight() / size;
        this.entities = new AtomizerParticle[cols*rows];
        final Texture frame = source.getFrame().getRegion().getTexture();
        for (int i = 0; i < cols; i += 1) {
            for (int j = 0; j < rows; j += 1) {
                final int x = i*size;
                final int y = j*size;
                final StaticSprite next = new StaticSprite(Assets.makeTextureRegion(frame, x, y, size, size));

                final Point2f st = getStart().toPoint2f();
                final float xEnd = targetPos.x + i * size;
                final float yEnd = targetPos.y + j * size;
                Point2f end;
                Point2f start;
                if (reverse) {
                    start = new Point2f(xEnd, yEnd);
                    end = st;
                } else {
                    end = new Point2f(xEnd, yEnd);
                    start = st;
                }

                putEntity(i, j, new AtomizerParticle(next, start, end, reverse));
            }
        }
    }
    public Point2i getStart() {
        final float rand = (float)Math.random();
        final int buff = 20;
        double x;
        double y;
        if (rand < 0.25f) {
            x = -buff;
            y = Game.RenderHeight * Math.random();
        } else if (rand < 0.5f) {
            x = Game.RenderWidth + buff;
            y = Game.RenderHeight * Math.random();
        } else if (rand < 0.75f) {
            x = Game.RenderWidth * Math.random();
            y = -buff;
        } else {
            x = Game.RenderWidth * Math.random();
            y = Game.RenderHeight + buff;
        }
        return new Point2i((int)x, (int)y);
    }

    public boolean isDone() {
        return done;
    }

    private void putEntity(int i, int j, AtomizerParticle s) {
        entities[i + j * cols] = s;
    }

    private AtomizerParticle getEntity(int i, int j) {
        return entities[i + j * cols];
    }

    private void tryPlaySound() {
        if (!playedSound) {
            playedSound = true;
            if (reversed) {
                Game.assets.TeleoutSound.play();
            } else {
                Game.assets.TeleinSound.play();
            }
        }
    }

    public void update() {
        tryPlaySound();
        boolean nowDone = true;
        for (int i = 0; i < cols; i += 1) {
            for (int j = 0; j < rows; j += 1) {
                final AtomizerParticle entity = getEntity(i, j);
                if (!entity.isDone()) {
                    nowDone = false;
                }
                entity.update();

            }
        }
        if (nowDone) {
            done = true;
        }
    }

    public void draw() {
        for (int i = 0; i < cols; i += 1) {
            for (int j = 0; j < rows; j += 1) {
                final AtomizerParticle entity = getEntity(i, j);
                entity.draw();
            }
        }
    }
}
