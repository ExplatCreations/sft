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

package com.explatcreations.sft.gui.pages;

import com.badlogic.gdx.graphics.Texture;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.graphics.IDoodad;
import com.explatcreations.sft.graphics.StaticSprite;
import com.explatcreations.sft.graphics.TextSprite;

/**
 * @author moopslc
 */
public class BasePage implements IPage {
    private static final Texture arrowClass = Game.assets.RightArrowGraphic;
    protected final StaticSprite arrowSprite = new StaticSprite(Assets.makeTextureRegion(arrowClass, 0, 0, 16, 8));
    private TextSprite textSprite;

    protected final TextSprite plusSprite = new TextSprite(0, 0, "+");
    protected IDoodad[] doodads = new IDoodad[0];
    protected final int StartX3 = 90;
    protected final int EndX3 = StartX3 + 80;

    private final int X5Space = 55;
    protected final int StartX5 = 80;
    protected final int MidX5 = StartX5 + X5Space;
    protected final int EndX5 = MidX5 + X5Space;
    protected final int BackMid5 = (StartX5 + MidX5) / 2;
    protected final int FrontMid5 = (MidX5 + EndX5) / 2;

    public BasePage(String text) {
        this.textSprite = TextSprite.makeWrappedSprite(25, 12, text.replace(",", ", "), 240);
    }

    protected void centerArrow() {
        arrowSprite.x = (StartX3 + EndX3)/2;
    }

    public void update() {
        for (IDoodad tile : doodads) {
            tile.update();
        }
    }

    public void draw(Point2i offset) {
        textSprite.offset = offset;
        textSprite.draw();
        for (IDoodad tile : doodads) {
            tile.draw(offset);
        }
    }
}
