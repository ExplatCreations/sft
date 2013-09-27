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

package com.explatcreations.sft.modes.ending;

import com.badlogic.gdx.graphics.Color;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.graphics.TextSprite;
import com.explatcreations.sft.gui.Button;
import com.explatcreations.sft.input.Controls;
import com.explatcreations.sft.modes.IEndingWidget;
import com.explatcreations.sft.modes.title.StarCollection;

/**
 * @author deweyvm
 */
public abstract class BaseEndingWidget implements IEndingWidget {
    enum State {
        None,
        Wait,
        Flip,
        Done
    }
    
    private State state = State.None;

    private final StarCollection stars;
    protected final String[] strings = makeStrings();
    protected final SpriteBase[] stringSprites = makeSprites();
    private int ptr;
    private Timer waitTimer = new Timer(getWaitTime());
    private Timer flipTimer = new Timer(getFlipTime());

    private Button returnButton = makeReturnButton();
    private Timer returnTimer = new Timer(30);
    private Point2i returnButtonOffset = getReturnButtonOffset();

    private static Button makeReturnButton() {
        final IAction onPress = new IAction() {
            @Override
            public void eval() {
                Game.resetGame();
            }
        };
        final Button result = new Button("Return ", 0, Game.RenderHeight - 25, onPress, Controls.Cancel);
        result.setX(Game.RenderWidth - result.getWidth() - 50);
        return result;
    }
    
    protected BaseEndingWidget() {
        stars = new StarCollection();
    }

    public void begin() {
        if (state == State.None) {
            state = State.Wait;
        }
    }

    protected abstract int getWrapWidth();
    protected TextSprite makeTextSprite(String string) {
        return TextSprite.makeWrappedSprite(0, 0, string, getWrapWidth());
    }


    protected final SpriteBase[] makeSprites() {
        final SpriteBase[] result = new SpriteBase[strings.length];
        for (int i = 0; i < strings.length; i += 1) {
            final String string = strings[i];
            final TextSprite nextSprite = makeTextSprite(string);
            nextSprite.setColor(Color.WHITE);
            nextSprite.x = 3;
            nextSprite.y = i * 40 + 30;
            result[i] = nextSprite;
        }
        return result;
    }

    protected int getWaitTime() { return 180; }
    protected int getFlipTime() { return 60; }

    protected abstract String[] makeStrings();

    public boolean isDone() {
        return state == State.Done;
    }

    public void update() {
        stars.update();
        if (state == State.Wait) {
            if (waitTimer.incrementUntilDone()) {
                state = State.Flip;
            }
        } else if (state == State.Flip) {
            if (flipTimer.incrementUntilDone()) {
                ptr += 1;
                if (ptr > stringSprites.length) {
                    state = State.Done;
                } else {
                    state = State.Wait;
                }
            }
        } else if (state == State.Done) {
            returnTimer.increment();
            returnButton.updateSelected();
        }
        returnButtonOffset = getReturnButtonOffset();
    }

    private Point2i getReturnButtonOffset() {
        final float y = (returnTimer.getProgress2() - 1) * returnButton.getHeight();
        return new Point2i(0, (int)y);
    }

    private float getAlpha() {
        final int step = 5;
        return ((int)(step * flipTimer.getProgress())) / (float)step;
    }

    public void draw() {
        stars.draw(Point2i.Zero);
        for (int i = 0; i < Math.min(ptr + 1, stringSprites.length); i += 1) {
            final SpriteBase sprite = stringSprites[i];
            final float alpha = i == ptr? getAlpha() : 1;
            sprite.setAlpha(alpha);
            sprite.draw();
        }
        returnButton.draw(returnButtonOffset.neg());
    }
}
