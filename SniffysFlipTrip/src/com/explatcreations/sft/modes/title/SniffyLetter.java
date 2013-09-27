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

package com.explatcreations.sft.modes.title;

import com.badlogic.gdx.graphics.Texture;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.data.Recti;
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.graphics.StaticSprite;

/**
 * @author deweyvm
 */
public class SniffyLetter {
    enum State {
        Wait,
        FlyIn,
        Bounce,
        Done
    }
    private State state = State.Wait;


    private int t = 0;
    private static final int Height = 32;
    private static final Texture spriteClass = Game.assets.TitleScreenSniffyLettering;
    private Timer flyInTimer = new Timer(10);
    private StaticSprite sprite;
    public SniffyLetter(Recti rect) {
        this.sprite = Assets.makeStaticSprite(spriteClass, rect);
        sprite.x = rect.x;
        sprite.y = rect.y;
        sprite.offset = new Point2i(0, -Height);
        sprite.setOrigin(0, sprite.getHeight()*2);
    }

    public void begin() {
        if (state == State.Wait) {
            state = State.FlyIn;
        }
    }

    public void update() {
        if (state == State.Wait) {

        } else if (state == State.FlyIn) {
            if (flyInTimer.incrementUntilDone()) {
                state = State.Bounce;
                sprite.offset = sprite.offset.setY(0);
                return;
            }
            final float y = -Height * (1 - flyInTimer.getProgress());
            sprite.offset = sprite.offset.setY((int)y);
        } else if (state == State.Bounce) {
            t += 1;
            final float timeScale = 10.0f;
            final int maxScale = 4;
            final float yOffset = -maxScale * (float)Math.sin(t / timeScale);

            if (yOffset < 0) {
                final float scaleDiff = (Math.abs(yOffset) / maxScale);
                final float scaleFactor = 1 - 0.1f * scaleDiff * scaleDiff;
                final float y = -Height * 0.1f * scaleDiff * scaleDiff;
                sprite.offset = sprite.offset.setY((int)y);
                sprite.setScale(1, scaleFactor);
            } else {
                sprite.setScale(1, 1);
            }
            if (t / timeScale > Math.PI) {
                state = State.Done;
            }
        } else {
            sprite.offset = sprite.offset.setY(0);
            sprite.setScale(1, 1);
        }
    }

    public void draw() {
        sprite.draw();
    }
}
