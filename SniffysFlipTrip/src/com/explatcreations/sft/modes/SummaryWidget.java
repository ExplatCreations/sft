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

package com.explatcreations.sft.modes;

import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.graphics.TextSprite;
import com.explatcreations.sft.gui.Button;
import com.explatcreations.sft.gui.Patch;
import com.explatcreations.sft.input.Controls;

/**
 * @author deweyvm
 */
public class SummaryWidget {
    enum State {
        Wait,
        Done
    }
    private final Patch patch;
    private final Button retryButton;
    private final Button quitButton;
    private final Button continueButton;
    private final TextSprite[] textSprites;
    private final Timer waitTimer = new Timer(45);
    private final SpriteBase icon;
    private boolean isGold;
    private boolean showIcon;
    private State state = State.Wait;

    public SummaryWidget(LevelWidget levelWidget, int steps, IAction retryAction, IAction quitAction, IAction continueAction) {
        this.patch = Patch.makeParchment(LevelWidget.Width, LevelWidget.Height - 16);
        final int x = Game.RenderWidth - patch.getWidth() + 7;
        final int y = Game.RenderHeight - patch.getHeight() - 64 + 15;
        patch.setPosition(x,y);

        final String textLeft = "STAGE COMPLETE\n" +
                                "\n\n" +
                                "  Steps";
        final String textRight =  "\n\n\n" +//title
                                  ": " + steps + " / " + levelWidget.getGoldSteps() + "\n";
        this.textSprites = new TextSprite[] {
            TextSprite.makeWrappedSprite(x + 25, y + 5, "\n" + levelWidget.getName(), LevelWidget.TextWidth),
            TextSprite.makeMultilineSprite(x + 25, y + 5, textLeft),
            TextSprite.makeMultilineSprite(x + 75, y + 5, textRight)
        };

        this.isGold = steps <= levelWidget.getGoldSteps();
        if (isGold) {
            icon = MenuButton.makeGoldStarSprite();
        } else {
            icon = MenuButton.makeCheckmarkSprite();
        }

        icon.x = x + 155;
        icon.y = y + 2;

        final int yButton = patch.y + patch.getHeight() - 4;
        this.retryButton = new Button("Retry", patch.x - 100, yButton + 26, retryAction, Controls.Retry);
        this.quitButton = new Button("Back", patch.x + 35, yButton + 26, quitAction, Controls.Cancel);
        this.continueButton = new Button("Continue",  0, yButton, continueAction, Controls.Enter);
        continueButton.setX(-continueButton.getWidth() + quitButton.getWidth() + quitButton.getX());
    }

    public void update() {
        retryButton.updateSelected();
        quitButton.updateSelected();
        continueButton.updateSelected();
        if (state == State.Wait) {
            if (waitTimer.incrementUntilDone()) {
                state = State.Done;
                if (isGold) {
                    Game.assets.GoldJingleSound.play();
                } else {
                    Game.assets.StampSound.play();
                }
                showIcon = true;
            }
        }
    }

    public void draw(Point2i offset) {
        retryButton.draw(offset);
        continueButton.draw(offset);
        quitButton.draw(offset);
        patch.draw(offset);
        for (SpriteBase sprite:textSprites) {
            sprite.offset = offset;
            sprite.draw();
        }
        if (showIcon) {
            icon.drawWithOffset(offset);
        }
    }
}
