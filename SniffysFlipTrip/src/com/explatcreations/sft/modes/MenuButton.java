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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.LevelIndex;
import com.explatcreations.sft.data.LevelState;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.graphics.RectSprite;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.graphics.TextSprite;
import com.explatcreations.sft.gui.NumberStamp;
import com.explatcreations.sft.input.Controls;

/**
 * @author deweyvm
 */
public class MenuButton {
    private SpriteBase selectedText;
    private SpriteBase unselectedText;
    private SpriteBase statusSprite;
    private IAction confirmCallback;
    private IAction selectCallback;

    public static final int Size = 14;
    private static final Texture checkmarkClass = Game.assets.CheckmarkGraphic;
    private static final Texture goldstarClass = Game.assets.GoldStarGraphic;
    private static final Texture exclamationClass = Game.assets.ExclamationGraphic;

    private static SpriteBase makeStatusSprite(Texture c) {
        return Assets.makeSpriteFrame(c, 16, 16, 0);
    }

    public static SpriteBase makeCheckmarkSprite() {
        return makeStatusSprite(checkmarkClass);
    }

    public static SpriteBase makeGoldStarSprite() {
        return makeStatusSprite(goldstarClass);
    }

    private static SpriteBase chooseStatusSprite(boolean isUnskippable, LevelState state) {
        if (isUnskippable && state.eq(LevelState.Unlocked)) {
            return makeStatusSprite(exclamationClass);
        } else if (state.eq(LevelState.Completed)) {
            return makeCheckmarkSprite();
        } else if (state.eq(LevelState.Golden)) {
            return makeGoldStarSprite();
        }
        return new RectSprite(16, 16, Color.CLEAR);
    }

    //public static Point2i offset;

    private Point2i pos = Point2i.Zero;
    private boolean isSelected = false;
    private LevelState levelState;

    private Point2i textOffset;

    private LevelIndex levelIndex;
    private boolean isLocked;

    private static final Texture reticleClass = Game.assets.ReticleGraphic;
    private static final SpriteBase reticleSprite = makeReticleSprite();
    public MenuButton(LevelState levelState,
                      LevelIndex levelIndex,
                      float x,
                      float y,
                      IAction confirmCallback,
                      IAction selectCallback) {
        this.levelState = levelState;
        this.levelIndex = levelIndex;
        this.pos = new Point2i((int)x, (int)y);

        this.selectedText = NumberStamp.getWhiteSprite(levelIndex);

        this.isLocked = LevelState.Locked.eq(levelState);
        if (isLocked) {
            final TextSprite text = new TextSprite(0, 0, levelIndex.stageName());
            text.setColor(new Color(0.53f, 0.53f, 0.53f, 1));
            this.unselectedText = text;
        } else {
            this.unselectedText = NumberStamp.getBrownSprite(levelIndex);
        }
        final int xOffset = (Size - selectedText.getWidth()) / 2;

        this.textOffset = new Point2i(-2 + xOffset, 0);

        syncPosition();
        final boolean isUnskippable = Game.save.isUnskippable(levelIndex);
        this.statusSprite = chooseStatusSprite(isUnskippable, levelState);

        this.confirmCallback = confirmCallback;
        this.selectCallback = selectCallback;
    }

    public static void updateReticle() {
        reticleSprite.update();
    }

    private static SpriteBase makeReticleSprite() {
        final AnimatedSprite sprite = Assets.makeSpriteFrame(reticleClass, 24, 24, 0);
        final String name = "play";
        sprite.addAnimation(name, 10, new int[]{0, 1, 2, 3, 2, 1}, true);
        sprite.play(name);
        return sprite;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public LevelIndex getLevelIndex() {
        return levelIndex;
    }

    private void syncPosition() {
        selectedText.x = pos.x + textOffset.x + 3;
        selectedText.y = pos.y + textOffset.y + 3;
        unselectedText.x = selectedText.x;
        unselectedText.y = selectedText.y;
        if (statusSprite != null) {
            statusSprite.x = pos.x;
            statusSprite.y = pos.y;
        }
    }

    public void update() {
        syncPosition();
        if (isSelected && Controls.Enter.justPressed()) {
            confirmCallback.eval();
        }
    }

    public void deselect() {
        isSelected = false;
    }

    public void select() {
        if (isSelected || isLocked) {
            return;
        }
        Game.assets.ButtonclickSound.play();
        isSelected = true;
    }

    public void forceSelect() {
        if (isLocked) return;
        select();
        selectCallback.eval();
    }

    private SpriteBase getText() {
        return isSelected
                ? selectedText
                : unselectedText;
    }

    public void draw(Point2i offset) {
        if (isLocked) return;

        if (statusSprite != null) {
            statusSprite.drawWithOffset(offset);
        }
        if (isSelected) {
            reticleSprite.x = pos.x - 4;
            reticleSprite.y = pos.y - 4;
            reticleSprite.drawWithOffset(offset);
        }
        getText().drawWithOffset(offset);
    }
}
