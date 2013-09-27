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

package com.explatcreations.sft;

import com.badlogic.gdx.graphics.Color;
import com.explatcreations.sft.functions.IFunction1;
import com.explatcreations.sft.graphics.Renderer;
import com.explatcreations.sft.graphics.TextSprite;
import com.explatcreations.sft.modes.MenuMode;
import com.explatcreations.sft.modes.Mode;
import com.explatcreations.sft.modes.SplashPage;
import com.explatcreations.sft.modes.TransitionMode;
import com.explatcreations.sft.modes.title.TitleMode;

/**
 * @author deweyvm
 */
public class ModeManager {
    private Mode currentMode = createInitialMode();
    private Mode nextMode;


    private final TextSprite demoMarker;

    public ModeManager() {
        this.demoMarker = new TextSprite(100,100,"DEMO VERSION", Color.YELLOW);
        demoMarker.y = 1;
        demoMarker.x = Game.RenderWidth - demoMarker.getWidth() - 1;
    }

    public void update() {
        if (nextMode != null) {
            currentMode = nextMode;
            nextMode = null;
        }
        currentMode.update();
    }

    public void renderFbos() {
        Renderer.batch.begin();
        Game.sepiaizer.update();
        Renderer.batch.end();
    }

    public Mode getMode() {
        return currentMode;
    }

    public void draw() {
        currentMode.draw();

        if (Game.globals.IsDemo) {
            demoMarker.draw();
        }
    }

    public void setMode(Mode newMode) {
        nextMode = newMode;
    }

    public void transitionTo(IFunction1<Mode> newMode) {
        setMode(new TransitionMode(newMode));
    }

    private static Mode createInitialMode() {
        if (!Game.globals.IsDebugMode) {
            if (Game.globals.SkipIntro) {
                return new TitleMode();
            } else {
                return new SplashPage();
            }
        } else {
            //for easy jumping to different modes if desired
            //return new TitleMode();
            //return new PlayMode(new Grid(LevelIndex.getAll()[66]), false);
            //return new InterimMode(new Grid(LevelIndex.getAll()[67]), new Grid(LevelIndex.getAll()[67]), 100, new ArrayList<LevelIndex>());

            //
            //return new EndingMode(Ending.Demo);
            //return new EndingMode(Ending.Good);
            return new MenuMode(0);
        }

    }
}
