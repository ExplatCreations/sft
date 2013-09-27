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

import com.badlogic.gdx.graphics.Color;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2f;
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.graphics.RectSprite;
import com.explatcreations.sft.graphics.SpriteBase;

import java.util.ArrayList;

/**
 * @author deweyvm
 */
public class AtomizerParticle {
    private static ArrayList<RectSprite> trailSprites;
    private static final Color[] colors = new Color[]{
            new Color(1,0,0,1),
            new Color(1, 0.5f, 0,1),
            new Color(1, 1, 0,1),
            new Color(0, 1, 0,1),
            new Color(0, 1, 0,1),
            new Color(0.3f, 0, 1,1)
    };
    private final ArrayList<Point2f> prevPos = new ArrayList<Point2f>();
    private final int trailLength = 5;
    private final int speedMax = 8;
    private final float BigG = 10000;
    private final Timer timer = new Timer(10);
    private int particlePtr = (int)(Math.random() * colors.length);
    private SpriteBase sprite;
    private Point2f end;
    private Point2f pos;
    private Point2f vel;
    private boolean done;
    private boolean reversed;
    public AtomizerParticle(SpriteBase sprite, Point2f start, Point2f end, boolean reversed) {
        this.reversed = reversed;
        if (trailSprites == null) {
            initTrailSprites();
        }
        this.sprite = sprite;
        this.pos = start;
        this.end = end;
        this.vel = end.sub(start).normalize().rotate((float)(Math.random() + 0.4f));
        if (reversed) {
            vel = vel.neg();
        }
    }

    private static void initTrailSprites() {
        trailSprites = new ArrayList<RectSprite>();
        for (Color color : colors) {
            trailSprites.add(new RectSprite(1, 1, color));
        }
    }

    public boolean isDone() {
        return done;
    }

    private void inc() {
        particlePtr = (particlePtr + 1) % colors.length;
    }

    public void update() {
        if (prevPos.size() < trailLength) {
            prevPos.add(pos);
        } else {
            prevPos.remove(0);
            prevPos.add(pos);
        }

        timer.increment();
        if (timer.isMax()) {
            timer.reset();
            inc();
        }
        final Point2f diff = end.sub(pos);
        float factor = diff.magSquared();
        if (isDone()) {
            return;
        }
        if (reversed) {
            final int buff = 20;
            if (   pos.x < -buff
                || pos.x > Game.RenderWidth + buff
                || pos.y < -buff
                || pos.y > Game.RenderHeight + buff) {
                pos = end;
                done = true;
                return;
            }
        } else {
            if (factor < speedMax*speedMax) {
                pos = end;
                done = true;
                return;
            }
        }
        if (factor == 0) {
            factor = 1;
        } else {
            factor = 1 / factor;
        }
        Point2f dir = diff.normalize();
        Point2f acc = dir.scale(BigG * factor);
        if (reversed)  {
            acc = new Point2f(0, -.98f);
            vel = new Point2f(0, -1 - 1 * (float)Math.random());
        }
        vel = vel.add(acc);
        vel = vel.bound(speedMax);
        pos = pos.add(vel);

    }


    public void draw() {
        if (!isDone()) {
            for (int i = 0; i < prevPos.size() ; i += 1) {
                final SpriteBase trail = trailSprites.get((particlePtr + i) % prevPos.size());
                final Point2f p = prevPos.get(i);
                trail.x = (int)p.x;
                trail.y = (int)p.y;
                trail.draw();
            }
        }
        sprite.x = (int)pos.x;
        sprite.y = (int)pos.y;
        sprite.draw();
    }
}
