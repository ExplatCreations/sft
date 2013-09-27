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


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.explatcreations.sft.enums.MDisplayMode;
import com.explatcreations.sft.saving.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * @author deweyvm
 */
public class Main {

    public static void main(String[] args) {
        handleArgs(Arrays.asList(args));

        if (!Game.globals.IsDebugMode) {
            Logger.attachCrashLogger();
        }

        final LwjglApplicationConfiguration cfg = makeConfig();
        new LwjglApplication(new Game(), cfg);
    }

    /**
     * fixme -- gross side effects
     */
    private static LwjglApplicationConfiguration makeConfig() {
        final LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Sniffy's Flip Trip";
        cfg.useGL20 = true;
        final MDisplayMode mode = Game.settings.getDisplayMode();
        cfg.width = mode.width;
        cfg.height = mode.height;
        if (mode.type == MDisplayMode.DisplayType.FullScreen) {
            cfg.fullscreen = true;
        } else if (mode.type == MDisplayMode.DisplayType.WindowedFullScreen) {
            cfg.resizable = false;
            System.setProperty("org.lwjgl.opengl.Window.undecorated","true");
        }
        cfg.foregroundFPS = 60;
        cfg.vSyncEnabled = true;
        return cfg;
    }

    private static void handleArgs(List<String> args) {

        final boolean isDebug = args.contains("--debug");
        Game.globals.IsDebugMode = isDebug;
        Game.globals.CheatsEnabled = isDebug;
        final boolean doSkipIntro = args.contains("--nointro");
        Game.globals.SkipIntro |= doSkipIntro;

        final boolean forceDemo = args.contains("--demo");
        Game.globals.IsDemo |= forceDemo;

        final boolean diagnoseGamepad = args.contains("--diagnose-gamepad");
        Game.globals.DiagnoseGamepad |= diagnoseGamepad;

        final boolean displayHardware = args.contains("--display-hardware");
        Game.globals.DisplayHardware |= displayHardware;
    }
}
