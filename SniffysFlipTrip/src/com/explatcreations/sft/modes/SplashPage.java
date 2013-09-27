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

import com.badlogic.gdx.graphics.Color;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.graphics.RectSprite;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.graphics.TextSprite;
import com.explatcreations.sft.input.Controls;
import com.explatcreations.sft.modes.title.TitleMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author moopslc
 */
public class SplashPage implements Mode {
    enum State {
        FadeIn,
        Wait,
        FadeOut

    }
    private static final int FadeTime = 60;
    private final Timer timer = new Timer(FadeTime);
    private final RectSprite overlay = RectSprite.screen(Color.BLACK);
    private final List<SpriteBase> sprites = new ArrayList<SpriteBase>();
    private final Mode title = new TitleMode();
    private State state = State.FadeIn;

    public SplashPage() {
        final SpriteBase page = Assets.makeStaticSprite(Game.assets.SplashPageGraphic);

        page.x = (Game.RenderWidth - page.getWidth())/2;
        page.y = (Game.RenderHeight - page.getHeight())/2 - 30;
        sprites.add(page);

        final SpriteBase text = new TextSprite(0, page.y + page.getHeight() + 18, "Presents...");
        text.x = (Game.RenderWidth - text.getWidth())/2;
        text.setColor(Color.WHITE);
        sprites.add(text);

    }

    @Override
    public void update() {
        if (state == State.FadeIn) {
            if (timer.incrementUntilDone()) {
                state = State.Wait;
                return;
            }
            overlay.setAlpha(1 - timer.getProgress());
        } else if (state == State.Wait) {
            if (Controls.Enter.isPressed() || timer.incrementUntilDone()) {
                state = State.FadeOut;
                timer.reset();
                return;
            }
        } else {
            if (timer.incrementUntilDone()) {
                Game.mode.setMode(title);
                return;
            }
            overlay.setAlpha(timer.getProgress());
        }
    }

    private void drawSprites() {
        for(SpriteBase sprite:sprites) {
            sprite.draw();
        }
    }

    @Override
    public void draw() {
        if (state == State.FadeIn) {
            drawSprites();
            overlay.draw();
        } else if (state == State.Wait) {
            drawSprites();
        } else {
            drawSprites();
            //title.draw();
            overlay.draw();
        }
    }

}
