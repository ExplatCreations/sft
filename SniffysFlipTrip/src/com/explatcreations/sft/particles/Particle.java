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

package com.explatcreations.sft.particles;

import com.explatcreations.sft.data.Point2f;
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.graphics.Drawable;

/**
 * @author deweyvm
 */
public class Particle {
    protected Drawable sprite;
    protected Timer timer;
    protected Point2f pos;
    protected Point2f vel;
    public Particle(Drawable sprite, Point2f vel, int lifespan) {
        this.sprite = sprite;
        this.pos = new Point2f(sprite.getX(), sprite.getY());
        this.vel = vel;
        this.timer = new Timer(lifespan);
        updatePositions();
    }

    public boolean isDone() {
        return timer.isMax();
    }

    private void updatePositions() {
        timer.increment();
        sprite.setX((int)pos.x);
        sprite.setY((int)pos.y);
        pos = pos.add(vel);
        sprite.update();
    }

    public void update() {
        updatePositions();
    }

    public void draw() {
        sprite.draw();
    }
}
