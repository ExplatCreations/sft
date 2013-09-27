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

import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.graphics.TextSprite;
import com.explatcreations.sft.input.Controls;

/**
 * @author deweyvm
 */
public class SaveDeleteButton implements ISelectable {
    private TextSprite text;
    private TextSprite labelText;
    private final SpriteBase selectRect;
    private final int maxSeconds = 5;

    private int counter = 0;
    private int prevLeft = -1;

    public SaveDeleteButton(int x, int y) {
        this.text = new TextSprite(x + 10, y, "");
        this.labelText = new TextSprite(x, y, "Delete Save:");
        labelText.x -= labelText.getWidth();
        this.selectRect = labelText.makeHighlight();
    }

    private String getString(int num) {
        return "Hold for "+num+" seconds";
    }

    @Override
    public void updateSelected() {
        selectRect.update();
        if (Controls.Magic.isPressed()) {
            counter += 1;
            final int seconds = counter / 60;
            final int left = (maxSeconds - seconds);
            if (left != prevLeft) {
                prevLeft = left;

                setText(getString(left));
                Game.assets.DeleteblipSound.play();
            }
            if (left == 0) {
                Game.save.deleteAllSaveData();
                Game.resetGame();
                Game.assets.DeleteexplosionSound.play();
            }
        } else {
            if (counter > 0) {
                setText("");
            }
            counter = 0;
            prevLeft = -1;
        }
    }

    private void setText(String s) {
        text.setText(s);
    }

    @Override
    public void draw(Point2i offset, boolean isSelected) {
        if (isSelected) {
            selectRect.drawWithOffset(offset);
        }
        labelText.drawWithOffset(offset);

        text.drawWithOffset(offset);
    }
}
