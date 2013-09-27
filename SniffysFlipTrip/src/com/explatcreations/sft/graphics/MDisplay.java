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

package com.explatcreations.sft.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.enums.MDisplayMode;
import org.lwjgl.opengl.Display;

/**
 * @author deweyvm
 */
public class MDisplay {
    public static void setDisplay(MDisplayMode mode) {
        if (mode.type == MDisplayMode.DisplayType.FullScreen) {
            final Graphics.DisplayMode desktopMode = Gdx.graphics.getDesktopDisplayMode();
            Gdx.graphics.setDisplayMode(desktopMode.width, desktopMode.height, true);
        } else if (mode.type == MDisplayMode.DisplayType.WindowedFullScreen) {
            final Graphics.DisplayMode desktopMode = Gdx.graphics.getDesktopDisplayMode();
            System.setProperty("org.lwjgl.opengl.Window.undecorated","true");
            Display.setResizable(false);
            Gdx.graphics.setDisplayMode(desktopMode.width, desktopMode.height, false);
        } else {//Windowed
            System.setProperty("org.lwjgl.opengl.Window.undecorated","false");
            Display.setResizable(true);
            Gdx.graphics.setDisplayMode(Game.WindowWidth, Game.WindowHeight, false);
        }
        Game.refreshJoypadHelper();
    }

    public static MDisplayMode verify(MDisplayMode mode) {
        if (mode == null || mode.width <= 100 || mode.width > 2000 || mode.height <= 100 || mode.height > 2000 || mode.type == null) {
            return MDisplayMode.makeNew();
        } else {
            return mode;
        }
    }

}
