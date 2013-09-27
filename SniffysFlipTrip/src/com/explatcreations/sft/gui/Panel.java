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

package com.explatcreations.sft.gui;

import com.badlogic.gdx.graphics.Color;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.graphics.RectSprite;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.input.Controls;
import com.explatcreations.sft.particles.ParticleManager;

/**
 * @author deweyvm
 */
public abstract class Panel {
    public static final int Width = 300;
    public static final int Height = 250;

    enum State {
        FlyIn, Normal, FlyOut
    }

    private State state = State.FlyIn;

    private Point2i offset;

    private final Timer flyInTimer = new Timer(20);
    private final Timer flyOutTimer = new Timer(20);
    private final int MaxDistance = Game.RenderHeight;

    private Patch frame;
    private SpriteBase bg;
    private IAction unset;
    public Panel(IAction unset) {
        Game.assets.PageflipSound.play();
        offset = new Point2i(0, MaxDistance);
        this.unset = unset;
        this.bg = new RectSprite(Game.RenderWidth, Game.RenderHeight, new Color(0,0,0,0.73f));

        this.frame = makePatch(false);
        frame.setPosition((Game.RenderWidth - frame.getWidth()) / 2 + getXOffset(),
                          (Game.RenderHeight - frame.getHeight()) / 2 + getYOffset());

        ParticleManager.suspend();
    }

    protected int getXOffset() {
        return 0;
    }

    protected int getYOffset() {
        return 0;
    }

    protected int getWidth() {
        return Width;
    }

    protected int getHeight() {
        return Height;
    }

    protected boolean isNormal() {
        return state == State.Normal;
    }

    protected Patch makePatch(boolean charred) {
        final int width = getWidth();
        final int height = getHeight();
        if (charred) {
            return Patch.makeCharred(width, height);
        } else {
            return Patch.makeParchment(width, height);
        }
    }

    protected int getFrameX() {
        return frame.x;
    }

    protected int getFrameY() {
        return frame.y;
    }

    protected static int getFrameWidth() {
        return Width;
    }

    protected abstract void drawSelf(Point2i offset);

    protected void doUnset() {
        unset.eval();
        ParticleManager.resume();
    }

    public void cancel() {
        setState(State.FlyOut);
        Game.assets.PageflipoutSound.play();
    }

    private void setState(State newState) {
        state = newState;
    }

    public void update() {
        ParticleManager.suspend();
        if (state == State.Normal && Controls.Cancel.justPressed()) {
            cancel();
            return;
        }
        if (state == State.FlyIn) {
            if (Controls.Enter.justPressed() && flyInTimer.getValue() > 2) {
                finishFlyIn();
                return;
            }
            updateFlyIn();
        } else if (state == State.FlyOut) {
            if (Controls.Enter.justPressed() && flyOutTimer.getValue() > 2) {
                finishFlyOut();
                return;
            }
            updateFlyOut();
        } else if (state == State.Normal) {
            updateNormal();
        }
    }

    private void finishFlyOut() {
        setState(State.Normal);//just in case?...
        offset = new Point2i(0, MaxDistance);
        doUnset();
    }

    private void updateFlyOut() {
        if (flyOutTimer.incrementUntilDone()) {
            flyOutTimer.reset();
            finishFlyOut();
            return;
        }
        offset = new Point2i(0, (int)(flyOutTimer.getProgress2() * MaxDistance));
    }


    private void finishFlyIn() {
        setState(State.Normal);
        offset = new Point2i(0, 0);
    }


    private void updateFlyIn() {
        if (flyInTimer.incrementUntilDone()) {
            finishFlyIn();
            return;
        }
        offset = new Point2i(0, (int)(flyInTimer.getOneMinusProgress2() * MaxDistance));
    }

    protected void updateNormal() {

    }

    protected void changeUnsetter(IAction unset) {
        this.unset = unset;
    }

    public void draw() {
        if (state == State.FlyIn) {
            bg.setAlpha(flyInTimer.getProgress());
        } else if (state == State.FlyOut) {
            bg.setAlpha(1 - flyOutTimer.getProgress());
        } else {
            bg.setAlpha(1);
        }

        bg.draw();

        frame.draw(offset);
        drawSelf(offset);

    }


}
