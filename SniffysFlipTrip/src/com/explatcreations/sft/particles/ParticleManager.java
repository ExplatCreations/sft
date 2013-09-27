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

import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.LevelIndex;
import com.explatcreations.sft.data.Point2f;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.graphics.Drawable;
import com.explatcreations.sft.modes.ending.Tower;

import java.util.ArrayList;

/**
 * @author deweyvm
 */
public class ParticleManager {
    enum State {
        Running,
        Suspended
    }

    private static State state;
    private static ArrayList<Particle> particles = new ArrayList<Particle>();
    private static ArrayList<IAction> callbacks = new ArrayList<IAction>();
    private static Particle noMagic;

    public static void sendCallback(IAction func) {
        callbacks.add(func);
    }

    public static void suspend() {
        state =  State.Suspended;
    }

    public static void resume()  {
        state = State.Running;
    }

    public static void reset()  {
        particles = new ArrayList<Particle>();
        state =  State.Running;
    }

    public static void spawnLevelUnlockParticle(LevelIndex levelIndex) {
        final int x = 170;
        final int y = 120;
        final String label = "Stage " + levelIndex.stageName() + " now unlocked! ";
        final Drawable drawable = new IndicatorBase(label , new Point2i(x, y));
        addParticle(new Particle(drawable, new Point2f(0, -0.25f), 180));
    }

    public static void spawnNewNoteIndicator() {
        final NewNoteIndicator drawable = new NewNoteIndicator();
        addParticle(new NoteParticle(drawable));
    }

    public static void spawnCantCastHere(Point2i pos) {
        final Drawable drawable = new IndicatorBase("Cant cast here", pos.add(-22, -8));
        addParticle(new Particle(drawable, new Point2f(0, -0.25f), 60));
    }

    public static void spawnNoMagic(Point2i pos) {
        final Drawable drawable = new NoMagicIndicator(pos.add(-22, -8));
        noMagic = new Particle(drawable, new Point2f(0, -0.25f), 60);
    }

    public static void spawnStar(int x, int y, boolean largeSpread) {
        final int numStars = 4;
        for (int i = 0; i < numStars; i += 1) {
            final float xDraw = x + (i / (float)numStars) * Tower.Width - 4;
            final Drawable drawable = new StarSprite((int)xDraw, y - 4);
            final float r = (float)Math.random() - 0.5f;
            final float xvel = largeSpread? 10*r : r;
            final float yvel = -1 - 3 * (float)Math.random();
            addParticle(new Particle(drawable, new Point2f(xvel, yvel), 210));
        }
        Game.assets.Disintegrate2Sound.play();
    }

    private static void addParticle(Particle p) {
        particles.add(p);
    }

    public static boolean isSuspended() {
        return state == State.Suspended;
    }

    public static void update() {
        if (isSuspended()) {
            return;
        }

        final ArrayList<Particle> newParticles = new ArrayList<Particle>();
        for (Particle p:particles) {
            p.update();
            if (!p.isDone()) {
                newParticles.add(p);
            }
        }

        if (noMagic != null) {
            noMagic.update();
            if (noMagic.isDone()) {
                noMagic = null;
            }
        }

        particles = newParticles;
    }

    public static void draw() {
        if (isSuspended()) {
            return;
        }
        for (IAction callback:callbacks) {
            callback.eval();
        }
        callbacks = new ArrayList<IAction>();
        for (Particle p:particles) {
            p.draw();
        }

        if (noMagic != null) {
            noMagic.draw();
        }
    }


}
