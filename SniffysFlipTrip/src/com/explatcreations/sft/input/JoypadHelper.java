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

package com.explatcreations.sft.input;


import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.utils.Array;

/**
 * @author deweyvm
 */
public class JoypadHelper {
    private Controller rawController = getRawController();
    public final com.badlogic.gdx.controllers.desktop.JoypadWrapper controller = rawController == null? null : new com.badlogic.gdx.controllers.desktop.JoypadWrapper(rawController);

    private Controller getRawController() {
        try {
            final Array<Controller> allControllers = Controllers.getControllers();
            if (allControllers.size == 0) {
                return null;
            } else {
                return allControllers.get(0);
            }
        } catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
    }

    public void removeListener() {
        if (controller != null) {
            controller.removeListener();
        }
    }

    public static int round(float axisValue){
        if (Math.abs(axisValue) < 0.4f) {
            return 0;
        }
        return (int)Math.signum((int)(100*axisValue));
    }

}
