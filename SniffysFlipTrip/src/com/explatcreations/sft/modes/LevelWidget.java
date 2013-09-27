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
import com.explatcreations.sft.data.LevelIndex;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.graphics.TextSprite;
import com.explatcreations.sft.gui.Patch;
import com.explatcreations.sft.loading.LevelData;

import java.util.ArrayList;

/**
 * @author moopslc
 */
public class LevelWidget {
    enum State {
        FlyIn,
        Normal,
        FlyOut,
        None
    }

    private State state;

    private final Timer timer = new Timer(30);
    private ArrayList<SpriteBase> sprites;
    private Patch bg;

    private static Point2i offset = Point2i.Zero;
    public static final int Width = 200;
    public static final int Height = 96;
    public static final int TextWidth = Width - 28;

    private String name;
    private int goldSteps;

    public LevelWidget(LevelIndex levelIndex) {
        this.sprites = makeSprites(levelIndex);
        this.bg = Patch.makeParchment(Width, Height);
        bg.setPosition(4, 4);
    }

    public String getName() {
        return name;
    }

    public int getGoldSteps() {
        return goldSteps;
    }

    private static final int xStart = 22;
    private static final int yStart = 11;

    private ArrayList<SpriteBase> makeSprites(LevelIndex levelIndex) {
        final ArrayList<SpriteBase> sprites = new ArrayList<SpriteBase>();
        sprites.add(new TextSprite(xStart, yStart, "LEVEL " + levelIndex.stageName()));
        final LevelData levelData = Game.world.getLevel(levelIndex);
        this.name = levelData.name;
        this.goldSteps = levelData.goldSteps;

        final String titleText = name;
        final String gold = goldSteps + "";
        final String record = Game.save.getRecordSteps(levelIndex) + "";
        final String recordText = "\n\n" +
                                  "  Record steps\n" +
                                  "  Gold steps";

        final String valueText = "\n\n" +
                                 "  : " + record + "\n" +
                                 "  : " + gold + "\n";
        sprites.add(TextSprite.makeWrappedSprite(xStart, yStart + 12, titleText, TextWidth));
        sprites.add(TextSprite.makeMultilineSprite(xStart, yStart + 12, recordText));
        sprites.add(TextSprite.makeMultilineSprite(xStart + 100, yStart + 12, valueText));


        return sprites;
    }

    public void update() {
        if (state == State.FlyIn) {
            if (timer.incrementUntilDone()) {
                state = State.Normal;
                return;
            }
            final float x = (Width + 10) * timer.getOneMinusProgress2();
            offset = new Point2i((int)x, 0);
        } else if (state == State.FlyOut) {
            if (timer.incrementUntilDone()) {
                state = State.None;
                return;
            }
            final float x = -(Width + 10)* timer.getProgress2();
            offset = new Point2i((int)x, 0);
        }
    }

    public void draw() {
        bg.draw(offset);
        for (SpriteBase sprite: sprites) {
            sprite.offset = offset;
            sprite.draw();
        }
    }
}
