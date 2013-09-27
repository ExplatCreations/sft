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

package com.explatcreations.sft.modes;

import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.functions.IFunction1;
import com.explatcreations.sft.gui.NotesPanel;
import com.explatcreations.sft.gui.Panel;
import com.explatcreations.sft.gui.Patch;
import com.explatcreations.sft.gui.pages.IPage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author moopslc
 */
public class TransitionMode extends CustomMode {
    private IFunction1<Mode> newModeMaker;
    private Mode currentMode;
    private TransitionParchmentCollection parchment;
    private boolean hasPlayedSound;
    public TransitionMode(IFunction1<Mode> newMode) {
        this(Game.mode.getMode(), newMode);
    }

    public TransitionMode(Mode oldMode, IFunction1<Mode> newMode) {
        this.currentMode = oldMode;
        this.newModeMaker = newMode;
        this.parchment = new TransitionParchmentCollection();
    }

    @Override
    public void update() {
        if (!hasPlayedSound) {
            Game.assets.PageflipSound.play();
            hasPlayedSound = true;
        }
        parchment.update();
        if (parchment.justClosed()) {
            currentMode = newModeMaker.apply();
        } else if (parchment.isDone()) {
            Game.mode.setMode(currentMode);
        }
    }

    @Override
    public void draw() {
        super.draw();
        currentMode.draw();
        parchment.draw();
    }
}

class TransitionParchmentCollection {
    private final int xStartLeft = -SimpleParchmentPiece.getWidth();
    private final int xEndLeft = -40;//SimpleParchmentPiece.getWidth();
    private final int xStartRight = Game.RenderWidth;
    private final int xEndRight = Game.RenderWidth/2 - 40;
    private final int yStart = -Game.RenderHeight/2;
    private Point2i[] starts = new Point2i[] {
            new Point2i(xStartRight, yStart + 210),
            new Point2i(xStartLeft, yStart + 180),
            new Point2i(xStartRight, yStart + 150),
            new Point2i(xStartLeft, yStart + 120),
            new Point2i(xStartRight, yStart + 90),
            new Point2i(xStartLeft, yStart + 60),
            new Point2i(xStartRight, yStart + 30),
            new Point2i(xStartLeft, yStart),

    };

    private Point2i[] ends = new Point2i[] {
            new Point2i(xEndRight, yStart + 210),
            new Point2i(xEndLeft, yStart + 180),
            new Point2i(xEndRight, yStart + 150),
            new Point2i(xEndLeft, yStart + 120),
            new Point2i(xEndRight, yStart + 90),
            new Point2i(xEndLeft, yStart + 60),
            new Point2i(xEndRight, yStart + 30),
            new Point2i(xEndLeft, yStart),

    };
    private List<SimpleParchmentPiece> pieces = new ArrayList<SimpleParchmentPiece>();
    public TransitionParchmentCollection() {
        final ArrayList<Integer> indices = NotesPanel.getRandomPages();
        final int max = Math.min(ends.length, Math.min(starts.length, indices.size()));
        for (int i = 0; i < max; i += 1) {
            final int random = (int)(Math.random()*20);
            final Point2i start = starts[i].add(random, 0);
            final Point2i end = ends[i].add(random, 0);
            pieces.add(new SimpleParchmentPiece(indices.get(i), start, end));
        }

    }

    public void update() {
        for (SimpleParchmentPiece piece : pieces) {
            piece.update();
        }
    }

    public void draw() {
        for (SimpleParchmentPiece piece : pieces) {
            piece.draw();
        }
    }

    public boolean justClosed() {
        return pieces.get(0).justClosed();
    }

    public boolean isDone() {
        boolean result = true;
        for (SimpleParchmentPiece piece : pieces) {
            result &= piece.isDone();
        }
        return result;
    }
}

class SimpleParchmentPiece {
    enum State {
        Close,
        Open,
        Done
    }

    private final IPage page;
    private final Patch bg;
    private final Point2i start;
    private final Point2i end;
    private final Timer timer;
    private boolean justClosed;
    private Point2i pos;
    private State state = State.Close;
    public SimpleParchmentPiece(int index, Point2i start, Point2i end) {
        this.page = NotesPanel.getPage(index);
        this.bg = Patch.makeCharred(getWidth(), Panel.Height + 8);
        this.start = start;
        this.end = end;
        this.timer = new Timer(25);
    }

    public static int getWidth() {
        return Panel.Width + 8;
    }

    public void update() {
        justClosed = false;
        if (state == State.Close) {
            pos = getProgress(start, end, timer.getProgress());
            if (timer.incrementUntilDone()) {
                state = State.Open;
                justClosed = true;
            }
        } else if (state == State.Open) {
            pos = getProgress(end, start, timer.getProgress());
            if (timer.incrementUntilDone()) {
                state = State.Done;
            }
        }
    }

    public boolean isDone() {
        return state == State.Done;
    }

    public boolean justClosed() {
        return justClosed;
    }



    private static Point2i getProgress(Point2i start, Point2i end, float progress) {
        final Point2i offset = end.sub(start).scale(progress);
        return start.add(offset);
    }

    public void draw() {
        bg.draw(pos);
        page.draw(pos);
    }
}
