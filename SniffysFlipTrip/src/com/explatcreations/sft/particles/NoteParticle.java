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
import com.explatcreations.sft.data.Point2f;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.data.Timer;

/**
 * @author deweyvm
 */
public class NoteParticle extends Particle {
    enum State {
        Wait,
        FlyAway
    }
    private State state = State.Wait;

    private static final int TotalTime = 180;
    private static final int WaitTime = TotalTime - 60;
    private final Timer waitTimer = new Timer(WaitTime);
    private final Timer flyTimer = new Timer(TotalTime - WaitTime);

    private Point2i destination;

    private int xDistance;
    private int yDistance;
    private int xStart;
    private int yStart;

    private boolean soundPlayed = false;

    public NoteParticle(NewNoteIndicator note) {
        super(note, Point2f.Zero, TotalTime);
        destination = new Point2i(0, 3);
        xStart = note.getX();
        yStart = note.getY();
        xDistance = destination.x - xStart;
        yDistance = destination.y - yStart;
    }

    @Override
    public void update() {
        super.update();
        if (state == State.Wait) {
            if (waitTimer.incrementUntilDone()) {
                state = State.FlyAway;
            }
        } else {
            flyTimer.increment();
            final float amt = flyTimer.getProgress2();
            super.pos = new Point2f(xStart + xDistance * amt,
                                    yStart + yDistance * amt);
        }
    }

    @Override
    public void draw() {
        if (timer.getProgress() > 0.1) {
            if (!soundPlayed) {
                soundPlayed = true;
                Game.assets.NewnoteSound.play();
            }
            super.draw();
        }
    }
}
