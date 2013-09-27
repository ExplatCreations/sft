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

package com.explatcreations.sft.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.enums.ControlName;
import com.explatcreations.sft.input.*;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.graphics.TextSprite;

/**
 * @author deweyvm
 */
public class ControlWidget implements ISelectable {
    private boolean isActive;
    private final SpriteBase label;
    private final SpriteBase highlight;
    private final ControlName name;
    private ControlIcon keyIcon;
    private ControlIcon joyIcon;
    private final int xStart;
    private final IAction resyncAllAction;
    public ControlWidget(ControlName name, int x, int y, IAction resyncAllAction) {
        this.label = new TextSprite(x, y, name.descriptor);
        this.xStart = x + 30;
        this.name = name;
        resync();
        label.x -= label.getWidth() - 100;
        this.highlight = label.makeHighlight();
        this.resyncAllAction = resyncAllAction;
    }

    public void resync() {
        final DualControl control = Controls.getControl(name);
        this.keyIcon = control.getKeyIcon(TextSprite.DefaultColor, true);
        keyIcon.x = xStart + 110;
        keyIcon.y = label.y - 3;

        this.joyIcon = control.getButtonIcon(TextSprite.DefaultColor, true);
        joyIcon.x = keyIcon.x + 85;
        joyIcon.y = keyIcon.y;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public void updateSelected() {
        highlight.update();
        if (Controls.Enter.justPressed() && !isActive()) {
            final ControllerAdapter controllerListener = new ControllerAdapter() {
                @Override
                public boolean buttonDown(Controller controller, int buttonIndex) {
                    for (JoypadButton button: JoypadButton.getAll()) {
                        if (!(button.info instanceof FaceInfo)) {
                            continue;
                        }
                        final FaceInfo info = (FaceInfo)button.info;
                        if (info.code == buttonIndex) {
                            Controls.setButton(name, button);
                            isActive = false;
                            resyncAllAction.eval();
                            Gdx.input.setInputProcessor(null);
                            Controllers.removeListener(this);
                        }
                    }
                    return false;
                }

                private void changeAxis(int axisIndex, int sign) {
                    for (JoypadButton button: JoypadButton.getAll()) {
                        if (!(button.info instanceof AxisInfo)) {
                            continue;
                        }
                        final AxisInfo info = (AxisInfo)button.info;
                        if (info.axisIndex == axisIndex && info.sign == sign) {
                            Controls.setButton(name, button);
                            isActive = false;
                            resyncAllAction.eval();
                            Gdx.input.setInputProcessor(null);
                            Controllers.removeListener(this);
                        }

                    }
                }

                @Override
                public boolean axisMoved (Controller controller, int axisIndex, float value) {
                    final int rounded = JoypadHelper.round(value);
                    changeAxis(axisIndex, rounded);
                    return false;
                }

                @Override
                public boolean povMoved (Controller controller, int povIndex, PovDirection value) {
                    final int sign;
                    final int axisIndex;
                    if (value == PovDirection.east) {
                        axisIndex = Controls.HorizontalAxis;
                        sign = 1;
                    } else if (value == PovDirection.north) {
                        axisIndex = Controls.VerticalAxis;
                        sign = -1;
                    } else if (value == PovDirection.south) {
                        axisIndex = Controls.VerticalAxis;
                        sign = 1;
                    } else if (value == PovDirection.west) {
                        axisIndex = Controls.HorizontalAxis;
                        sign = -1;
                    } else {
                        return false;
                    }
                    changeAxis(axisIndex, sign);
                    return false;
                }
            };
            final InputAdapter inputListener = new InputAdapter() {
                @Override
                public boolean keyDown(int keycode) {
                    isActive = false;
                    Controls.setKey(name, keycode);
                    resyncAllAction.eval();
                    Gdx.input.setInputProcessor(null);
                    Controllers.removeListener(controllerListener);
                    return false;
                }
            };

            Gdx.input.setInputProcessor(inputListener);
            Controllers.addListener(controllerListener);
            isActive = true;
        } else if (isActive()) {


        }
    }

    @Override
    public void draw(Point2i offset, boolean isSelected) {
        if (isSelected && !isActive()) {
            highlight.drawWithOffset(offset);
        } else if (isActive()) {
            highlight.drawSelf(label.x + label.getWidth() + 8, highlight.y);
            highlight.drawSelf(label.x + label.getWidth() + 97, highlight.y);
        }
        label.drawWithOffset(offset);
        keyIcon.drawWithOffset(offset);
        joyIcon.drawWithOffset(offset);
    }

}
