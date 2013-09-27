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

package com.explatcreations.sft.graphics.background;

import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.gui.NotesPanel;
import com.explatcreations.sft.gui.Patch;
import com.explatcreations.sft.gui.pages.IPage;

/**
 * @author deweyvm
 */
public class ParchmentPiece {
    enum State {
        Waiting,
        FlyIn,
        Done,
        FlyOut,
        DoneReverse
    }
    

    private final Timer timer = new Timer(30);
    private State state = State.Waiting;
    private int dir;
    private Point2i pos;
    private Point2i start;
    private Point2i end;
    private Patch sprite;
    private IPage note;
    private boolean isLeft;
    private boolean isReversing;
    
    public ParchmentPiece(Point2i end, boolean isLeft, Patch sprite, int pageIndex) {
        this.end = end;
        this.sprite = sprite;
        this.note = NotesPanel.getPage(pageIndex);
        this.isLeft = isLeft;
        if (isLeft) {
            dir = 1;
            pos = end.add(ParchmentBackground.Width*2, 0);
        } else {
            dir = -1;
            pos = end.sub(ParchmentBackground.Width*2, 0);
        }
        this.start = pos;
    }

    public void setPosition(Point2i pos) {
        this.pos = pos;
    }

    public void begin() {
        if (state == State.Waiting) {
            state = State.FlyIn;
        }
    }

    public boolean isDone() {
        return state == State.Done;
    }

    public void forceFinish() {
        state = State.Done;
        pos = end;
    }

    public void update() {
        if (state == State.Waiting) {
            //nothing
        } else if (state == State.FlyIn) {
            if (timer.incrementUntilDone()) {
                state = State.Done;
                return;
            }
            final float x = dir * ParchmentBackground.Width * timer.getOneMinusProgress2();
            pos = end.add((int)x, 0);
        } else if (state == State.FlyOut) {
            if (timer.incrementUntilDone()) {
                state = State.DoneReverse;
                return;
            }
            final float x = -dir * ParchmentBackground.Width * timer.getOneMinusProgress2();
            pos = start.add((int)x, 0);
        } else if (state == State.Done) {
            pos = end;
        } else if (state == State.DoneReverse) {
            pos = start;
        }
    }

    public void draw() {
        sprite.draw(pos);
        note.draw(pos.add(0, 0));
    }
}
