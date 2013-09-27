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

package com.explatcreations.sft.modes.ending;

import com.badlogic.gdx.graphics.Texture;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.audio.MusicManager;
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.graphics.SpriteBase;

/**
 * @author deweyvm
 */
public class MidEndingWidget extends BaseEndingWidget {
    private static final Texture flagGraphic = Game.assets.FlagGraphic;
    public static final String RaiseName = "raise";
    public static final String FinishedName = "finish";
    public static final String WavingName = FinishedName;

    private SpriteBase tower = Tower.makeTower();
    private AnimatedSprite flagSprite = makeFlagSprite();
    private boolean hasPlayed = false;
    private boolean hasPlayedWave = false;
    private final Timer flagRaiseTimer = new Timer(60);
    private final Timer playTimer= new Timer(85);
    private boolean hasRaisedFlag = false;
    public static AnimatedSprite makeFlagSprite() {
        final AnimatedSprite result = Assets.makeSpriteFrame(flagGraphic, 24, 24, 5);
        final String stoppedName = "stopped";
        result.addAnimation(FinishedName, 9, new int[]{10, 11, 12, 9}, true);
        result.addAnimation(RaiseName, 4, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, false);
        result.addAnimation(stoppedName, 9, new int[]{0}, false);
        result.play(stoppedName);
        result.x = Tower.X + 7;
        result.y = Tower.Y - 16;
        return result;
    }

    @Override
    protected String[] makeStrings() {
        return new String[] {
               "With all the floors cleared ",
               "Sniffy stands triumphant.",
               "Raising the flag of celebration",
               "Well Done!"
        };
    }

    public MidEndingWidget() {
    }

    @Override
    public void update() {
        if (!hasPlayed && playTimer.isMax()) {
            hasPlayed = true;
            MusicManager.play(Game.assets.MidEndingMusic);
        } else if (hasRaisedFlag) {

            playTimer.increment();
        }
        if (!hasRaisedFlag) {
            if (flagRaiseTimer.incrementUntilDone()) {
                hasRaisedFlag = true;
                flagSprite.play(RaiseName);
                Game.assets.GoldJingleSound.play();
            }
        } else if (!hasPlayedWave && flagSprite.isFinished()) {
            flagSprite.play(WavingName);
            hasPlayedWave = true;
        }

        begin();
        super.update();
        flagSprite.update();
    }

    @Override
    protected int getWrapWidth() {
        return Game.RenderWidth / 2 - 8;
    }

    @Override
    public void draw() {
        super.draw();
        tower.draw();
        flagSprite.draw();
    }
}
