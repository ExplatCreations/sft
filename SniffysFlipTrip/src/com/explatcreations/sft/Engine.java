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

import com.explatcreations.sft.audio.MusicManager;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.graphics.Renderer;
import com.explatcreations.sft.input.Controls;
import com.explatcreations.sft.particles.ParticleManager;

/**
 * @author moopslc
 */
public class Engine {
    private static int frame = 0;
    public static int getFrame() {
        return frame;
    }

    public Engine() {

    }

    public void update() {
        MusicManager.update();
        Controls.update();
        Game.mode.update();
        ParticleManager.update();
        frame += 1;
    }

    public void draw() {

        Game.mode.renderFbos();
        Game.screenStretcher.addLayer(new IAction() {
            @Override
            public void eval() {
                Renderer.draw(true, new IAction() {
                    @Override
                    public void eval() {
                        Game.mode.draw();
                        ParticleManager.draw();
                    }
                });
            }
        });

        Game.screenStretcher.render();

    }
}
