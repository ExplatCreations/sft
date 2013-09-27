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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.audio.MusicManager;
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.graphics.TextSprite;

/**
 * @author deweyvm
 */
public class GoodEndingWidget extends BaseEndingWidget {
    private static final Texture stumpClass = Game.assets.StumpGraphic;
    private static SpriteBase stumpSprite = makeStumpSprite();

    private static SpriteBase makeStumpSprite() {
        final SpriteBase sprite = Assets.makeSpriteFrame(stumpClass, 11, 7, 0);
        sprite.x = 240;
        sprite.y = 217;
        return sprite;
    }

    private TowerSnake tower = new TowerSnake();
    private boolean hasPlayed = false;
    private final Timer playTimer = new Timer(120);

    @Override
    protected TextSprite makeTextSprite(String string) {
        return TextSprite.makeWrappedSprite(0, 0, string, getWrapWidth(), BitmapFont.HAlignment.CENTER);
    }

    @Override
    protected String[] makeStrings() {
        return new String[]{
            "Unbelievable!",
            "Releasing the totality of the tower's energy it will vanish forever.",
            "\nCelebrate victory.",
            "\nEND"
        };
    }

    @Override
    protected int getWrapWidth() {
        return Game.RenderWidth;
    }

    @Override
    public void update() {

        super.update();
        tower.update();
        if (tower.isExplodingDone()) {
            begin();
            if (!hasPlayed) {
                if (playTimer.incrementUntilDone()) {
                    hasPlayed = true;
                    MusicManager.play(Game.assets.GoodTimesMusic);
                }
            }
        }
    }

    @Override
    public void draw() {
        super.draw();
        stumpSprite.draw();
        tower.draw();
    }
}
