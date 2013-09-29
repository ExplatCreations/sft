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

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector3;
import com.explatcreations.sft.audio.AudioManager;
import com.explatcreations.sft.audio.MusicManager;
import com.explatcreations.sft.data.LevelIndex;
import com.explatcreations.sft.graphics.ScreenStretcher;
import com.explatcreations.sft.graphics.Sepiaizer;
import com.explatcreations.sft.hardware.HardwareInfo;
import com.explatcreations.sft.input.JoypadHelper;
import com.explatcreations.sft.modes.title.TitleMode;
import com.explatcreations.sft.particles.ParticleManager;
import com.explatcreations.sft.saving.Save;
import com.explatcreations.sft.saving.Settings;
import org.lwjgl.opengl.Display;

import java.nio.ByteBuffer;

/**
 * @author moopslc
 */
public class Game implements ApplicationListener {
    public static final int Zoom = 2;
    public static final int WindowWidth = 960;
    public static final int WindowHeight = 540;
    public static final int RenderWidth = WindowWidth/Zoom;
    public static final int RenderHeight = WindowHeight/Zoom;


    private Engine engine;
    public static Globals globals = new Globals();
    public static AudioManager audio;
    public static Assets assets;
    public static ModeManager mode;
    public static ScreenStretcher screenStretcher;
    public static HardwareInfo.OS os = HardwareInfo.getOS();
    public static Settings settings = Settings.load();
    public static Save save = Save.load();
    public static JoypadHelper joypadHelper;
    public static Sepiaizer sepiaizer;
    public static World world;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_ERROR);
        Game.world = new World();
        refreshJoypadHelper();
        Game.globals.IsDemo |= Game.world.getLevel(LevelIndex.Last) == null;
        Game.audio = new AudioManager();
        Game.assets = new Assets();
        setIcons();
        Game.mode = new ModeManager();
        Game.screenStretcher = new ScreenStretcher();
        Game.sepiaizer = new Sepiaizer();
        this.engine = new Engine();
        if (Game.globals.DisplayHardware) {
            System.out.println(new HardwareInfo().toString());
            exit();
        }
        if (Game.globals.DiagnoseGamepad) {
            diagnoseGamepad();
        }
    }

    private static void diagnoseGamepad() {
        
        System.out.println("Controller Name = " + Controllers.getControllers().get(0).getName());
        Controllers.addListener(new ControllerAdapter() {
            @Override
            public boolean buttonDown(Controller controller, int buttonIndex) {
                System.out.printf("buttonDown <%d>\n", buttonIndex);
                return false;
            }

            @Override
            public boolean axisMoved(Controller controller, int axisIndex, float value) {
                if (Math.abs(value) < 0.1f) {
                    return false;
                }

                System.out.printf("axisMoved <%d,%f>\n", axisIndex, value);
                return false;
            }

            @Override
            public boolean povMoved (Controller controller, int povIndex, PovDirection value) {
                System.out.printf("povMoved <%d, %s>\n", povIndex, value);
                return false;
            }

            @Override
            public boolean xSliderMoved (Controller controller, int sliderIndex, boolean value) {
                System.out.printf("xSliderMoved <%d, %b>\n", sliderIndex, value);
                return false;
            }

            @Override
            public boolean ySliderMoved (Controller controller, int sliderIndex, boolean value) {
                System.out.printf("ySliderMoved <%d, %b>\n", sliderIndex, value);
                return false;
            }

            @Override
            public boolean accelerometerMoved (Controller controller, int accelerometerIndex, Vector3 value) {
                System.out.printf("accelerometerMoved <%d, (%s)> ", accelerometerIndex, value.toString());
                return false;
            }
        });
    }

    public static void refreshJoypadHelper() {
        if (Game.joypadHelper != null) {
            Game.joypadHelper.removeListener();
        }
        Game.joypadHelper = new JoypadHelper();
    }



    private static void setIcons() {
        if (os == HardwareInfo.OS.Windows) {
            final Pixmap small = Game.assets.SmallIcon;
            final Pixmap medium = Game.assets.MediumIcon;
            Display.setIcon(new ByteBuffer[]{small.getPixels(), medium.getPixels()});
        } else if (os == HardwareInfo.OS.Mac) {
            final Pixmap large = Game.assets.LargeIcon;
            Display.setIcon(new ByteBuffer[]{large.getPixels()});
        } else {
            final Pixmap medium = Game.assets.MediumIcon;
            Display.setIcon(new ByteBuffer[]{medium.getPixels()});
        }
    }

    @Override
    public void dispose() {
    }

    @Override
    public void render() {
        engine.update();
        engine.draw();
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    public static void resetGame() {
        ParticleManager.resume();
        MusicManager.startFadeOut(10);
        Game.mode.transitionTo(TitleMode.factory);
    }

    public static void exit() {
        System.exit(0);
    }

}
