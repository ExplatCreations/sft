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
import com.explatcreations.sft.enums.Ending;
import com.explatcreations.sft.functions.IFunction1;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.graphics.StaticSprite;
import com.explatcreations.sft.modes.CustomMode;
import com.explatcreations.sft.modes.IEndingWidget;
import com.explatcreations.sft.modes.Mode;

/**
 * @author moopslc
 */
public class EndingMode extends CustomMode {
    private static final Texture bgClass = Game.assets.TitleScreenScene;
    private static SpriteBase bgSprite;
    private IEndingWidget widget;

    public EndingMode(Ending ending) {
        if (bgSprite == null) {
            bgSprite = new StaticSprite(Assets.makeTextureRegion(bgClass, 0, 0, bgClass.getWidth(), bgClass.getHeight()));
        }
        if (ending == Ending.Good) {
            this.widget = new GoodEndingWidget();
        } else if (ending == Ending.Mid) {
            this.widget = new MidEndingWidget();
        } else if (ending == Ending.Bad) {
            this.widget = new BadEndingWidget();
        } else {
            this.widget = new DemoEndingWidget();
        }
    }

    public static IFunction1<Mode> getFactory(final Ending index) {
        return new IFunction1<Mode>() {
            @Override
            public EndingMode apply() {
                return new EndingMode(index);
            }
        };
    }

    @Override
    public void update() {
        widget.update();
    }

    @Override
    public void draw() {
        super.draw();
        bgSprite.draw();
        widget.draw();
    }
}
