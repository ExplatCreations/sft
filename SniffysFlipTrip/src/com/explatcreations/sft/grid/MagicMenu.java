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

package com.explatcreations.sft.grid;

import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.entities.IMagicAcceptor;
import com.explatcreations.sft.enums.MagicType;
import com.explatcreations.sft.gui.AimWidget;
import com.explatcreations.sft.gui.ChooseWidget;
import com.explatcreations.sft.gui.IControlWidget;
import com.explatcreations.sft.gui.NeutralWidget;
import com.explatcreations.sft.input.Controls;

/**
 * @author deweyvm
 */
public class MagicMenu {
    enum State {
        Inactive,
        ChooseMagic,
        ChooseDirection
    }

    private IControlWidget controlWidget;
    private final IMagicAcceptor player;
    private State state = State.Inactive;
    private Point2i aimDirection;
    private MagicType type;
    public MagicMenu(IMagicAcceptor player) {
        this.player = player;
        this.controlWidget = new NeutralWidget(player);
    }

    public boolean isActive() {
        return state != State.Inactive;
    }

    public void deactivate() {
        state = State.Inactive;
    }

    public void beginCasting(Point2i playerPos) {
        state = State.ChooseMagic;
        Game.assets.MagicmenuselectSound.play();
        controlWidget = new ChooseWidget(player.getInventory());
    }


    private boolean tryCancel() {
        if (Controls.Cancel.justPressed()) {
            doCancel();
            Controls.Cancel.hackIncrement();
            return true;
        }
        return false;
    }

    private void doCancel() {
        type = null;
        aimDirection = null;
        state = State.Inactive;
        controlWidget = new NeutralWidget(player);
    }


    public void update() {
        controlWidget.update();
        if (state == State.ChooseMagic) {
            if (tryCancel()) {
                return;
            }
            final Point2i magicType = Controls.directionJustPressed(Point2i.Zero);
            if (magicType.isUp()) {
                type = MagicType.Fire;
            } else if (magicType.isRight()) {
                type = MagicType.Ice;
            } else if (magicType.isDown()) {
                type = MagicType.Earth;
            } else if (magicType.isLeft()) {
                type = MagicType.Wind;
            } else {
                type = null;
            }
            if (type != null) {
                if (player.getInventory().hasMagic(type)) {
                    state = State.ChooseDirection;
                    Game.assets.MagicmenuselectSound.play();
                    controlWidget = new AimWidget();
                } else {
                    Game.assets.MagicemptySound.play();
                }
            }


        } else if (state == State.ChooseDirection) {
            if (tryCancel()) {
                return;
            }
            aimDirection = Controls.directionJustPressed(null);
            if (aimDirection != null) {
                player.commitMagic(type, aimDirection);
                doCancel();
            }
        }

    }

    public void draw() {
        if (controlWidget != null) {
            controlWidget.draw();
        }
    }


}
