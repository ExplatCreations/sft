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

package com.explatcreations.sft.graphics.background;

import com.badlogic.gdx.graphics.Color;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.graphics.RectSprite;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.gui.NotesPanel;
import com.explatcreations.sft.gui.Panel;
import com.explatcreations.sft.gui.Patch;

import java.util.ArrayList;

/**
 * @author moopslc
 */
public class ParchmentBackground {
    public static final int Width = Panel.Width;
    public static final int Height = Panel.Height;
    private final Point2i[] right = new Point2i[]{
            new Point2i(122 - 100, 260),
            new Point2i(178 - 100, 197),
            new Point2i(126 - 100, 134),
            new Point2i(156 - 100, 72),
    };

    private final Point2i[] left = new Point2i[]{
            new Point2i(130 - 100, 260),
            new Point2i(160 - 100, 214),
            new Point2i(195 - 100, 157),
            new Point2i(105 - 100, 106),
            new Point2i(180 - 100, 41),
    };

    private final ArrayList<ParchmentPiece> pieces = new ArrayList<ParchmentPiece>();
    private static final float MaxAlpha = 1 - 0.125f;

    private SpriteBase fade;

    public ParchmentBackground() {

        final Patch bg = Patch.makeCharred(Width + 8, Height + 8);
        this.fade = new RectSprite(Game.RenderWidth, Game.RenderHeight, new Color(0, 0, 0, MaxAlpha));
        final ArrayList<Integer> pages = NotesPanel.getRandomPages();
        int pageIndex = 0;
        for (int i = 0; i < Math.max(left.length, right.length); i += 1) {
            if (i < left.length) {
                final Point2i leftPos = new Point2i(Width, Height).sub(left[i]);
                pieces.add(new ParchmentPiece(leftPos, true, bg, pages.get(pageIndex)));
                pageIndex += 1;
            }
            if (i < right.length) {
                final Point2i rightPos = new Point2i(0, Height).sub(right[i]);
                pieces.add(new ParchmentPiece(rightPos, false, bg, pages.get(pageIndex)));
                pageIndex += 1;
            }
        }

        for(ParchmentPiece piece : pieces) {
            piece.forceFinish();
        }
    }

    public void draw() {
        for (ParchmentPiece p : pieces) {
            p.draw();
        }
        fade.draw();
    }
}
