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

package com.badlogic.gdx.controllers.desktop;

import com.badlogic.gdx.controllers.ControlType;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.PovDirection;
import com.explatcreations.sft.input.Controls;
import com.explatcreations.sft.input.JoypadHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author deweyvm
 */
public class JoypadWrapper {
    private final Controller controller;
    private final ControllerAdapter listener;
    private final Map<Integer,Boolean> buttonState = new HashMap<Integer, Boolean>();//.withDefault(false)

    private final Map<Integer,Integer> axisState = new HashMap<Integer, Integer>();//.withDefault(0)
    private boolean hasPov;
    public JoypadWrapper(Controller controller) {
        this.controller = controller;
        if (controller instanceof OisControllers.OisController) {
            final OisControllers.OisController ois = (OisControllers.OisController)controller;
            if (ois.getControlCount(ControlType.pov) > 0) {
                System.out.println("detected pov, disabling joysticks");
                hasPov = true;
            }
        }
        this.listener = new ControllerAdapter() {
            @Override
            public void connected(Controller controller) { }

            @Override
            public void disconnected(Controller controller) { }

            @Override
            public boolean buttonUp(Controller controller, int buttonCode) {
                buttonState.put(buttonCode, false);
                return false;
            }

            @Override
            public boolean buttonDown(Controller controller, int buttonCode) {
                buttonState.put(buttonCode, true);
                return false;
            }

            @Override
            public boolean axisMoved(Controller controller, int axisCode, float value) {
                if (hasPov) {
                    return false;
                }
                axisState.put(axisCode /*% 2*//*NOTE we only care about 1 dpad, so we ignore the code*/, JoypadHelper.round(value));
                return false;
            }

            @Override
            public boolean povMoved (Controller controller, int povIndex, PovDirection value) {
                final List<Integer> axisIndices = new ArrayList<Integer>();
                final List<Integer> signs = new ArrayList<Integer>();
                final int horizontalAxis = Controls.HorizontalAxis;
                final int verticalAxis = Controls.VerticalAxis;

                //fixme code clones
                if (value == PovDirection.east) {
                    axisIndices.add(horizontalAxis);
                    signs.add(1);
                } else if (value == PovDirection.west) {
                    axisIndices.add(horizontalAxis);
                    signs.add(-1);
                } else if (value == PovDirection.north) {
                    axisIndices.add(verticalAxis);
                    signs.add(-1);
                } else if (value == PovDirection.south) {
                    axisIndices.add(verticalAxis);
                    signs.add(1);
                } else if (value == PovDirection.northEast) {
                    axisIndices.add(horizontalAxis);
                    signs.add(1);
                    axisIndices.add(verticalAxis);
                    signs.add(1);
                } else if (value == PovDirection.northWest) {
                    axisIndices.add(horizontalAxis);
                    signs.add(-1);
                    axisIndices.add(verticalAxis);
                    signs.add(1);
                } else if (value == PovDirection.southEast) {
                    axisIndices.add(horizontalAxis);
                    signs.add(1);
                    axisIndices.add(verticalAxis);
                    signs.add(-1);
                } else if (value == PovDirection.southWest) {
                    axisIndices.add(horizontalAxis);
                    signs.add(-1);
                    axisIndices.add(verticalAxis);
                    signs.add(-1);

                } else /*(value == PovDirection.center)*/ {
                    //add 0 sign to both indices
                    axisIndices.add(horizontalAxis);
                    signs.add(0);
                    axisIndices.add(verticalAxis);
                    signs.add(0);
                }
                for (int i = 0; i < Math.min(axisIndices.size(), signs.size()); i += 1) {
                    final int sign = signs.get(i);
                    final int index = axisIndices.get(i);
                    axisState.put(index, sign);
                }
                return false;
            }
        };
        controller.addListener(listener);
    }

    public void removeListener() {
        controller.removeListener(listener);
    }

    public boolean isButtonPressed(int code) {
        if (buttonState.containsKey(code)) {
            return buttonState.get(code);
        }
        return false;
    }

    public float isAxisPressed(int code){
        if (axisState.containsKey(code)) {
            final Integer value = axisState.get(code);
            if (value == null) {
                System.out.println(code + " not found in dictionary");
            }
            return value != null ? value : 0;
        }
        return 0;
    }

}

