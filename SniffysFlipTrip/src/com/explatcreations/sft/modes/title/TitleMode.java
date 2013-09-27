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

package com.explatcreations.sft.modes.title;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.audio.MusicManager;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.enums.Ending;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.functions.IFunction1;
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.graphics.RectSprite;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.graphics.TextSprite;
import com.explatcreations.sft.gui.Button;
import com.explatcreations.sft.input.Controls;
import com.explatcreations.sft.modes.BackgroundManager;
import com.explatcreations.sft.modes.CustomMode;
import com.explatcreations.sft.modes.MenuMode;
import com.explatcreations.sft.modes.Mode;
import com.explatcreations.sft.modes.ending.MidEndingWidget;
import com.explatcreations.sft.modes.ending.Tower;

/**
 * @author deweyvm
 */
public class TitleMode extends CustomMode {
    enum State {
        FadeInBlack,
        ScrollDown,
        FadeInTitle,
        Normal
    }

    private State state = State.FadeInBlack;

    private static final Texture letteringClass = Game.assets.TitleLettering;
    private static final Texture titleClass = Game.assets.TitleScreenScene;
    private static final int Height = Game.RenderHeight;

    private Button continueButton;

    private AnimatedSprite flagSprite;
    private SpriteBase flipTripLettering;
    private SpriteBase titleGraphic;
    private SpriteBase skyGraphic;
    private SpriteBase tower;
    private SpriteBase shadow;
    private SpriteBase versionText;
    private SniffyLetterCollection sniffyLettering;
    private FloatingBitCollection floatyBits;
    private StarCollection stars;

    private float letterAlpha = 0;

    private final Timer alphaTimer = new Timer(180);
    private final Timer flyInTimer = new Timer(60);
    private final Timer fadeInTimer = new Timer(30);
    private final SpriteBase screenCover = RectSprite.screen(Color.BLACK);

    private Point2i offset = new Point2i(0, Height);
    private boolean started = false;

    public TitleMode() {
        if (Game.save.hasShownEnding(Ending.Mid)) {
            flagSprite = MidEndingWidget.makeFlagSprite();
            flagSprite.play(MidEndingWidget.WavingName);
        }
        this.versionText = new TextSprite(2, 0, Game.globals.VersionString);
        versionText.y = Game.RenderHeight - 2 - versionText.getHeight();
        versionText.setColor(Color.WHITE);

        this.flipTripLettering = Assets.makeSpriteFrame(letteringClass, letteringClass.getWidth(), letteringClass.getHeight(), 0);
        flipTripLettering.x = Game.RenderWidth/2 - flipTripLettering.getWidth()/2;
        flipTripLettering.y = 50;
        this.titleGraphic = Assets.makeSpriteFrame(titleClass, titleClass.getWidth(), titleClass.getHeight(), 0);
        this.sniffyLettering = new SniffyLetterCollection();
        this.tower = Tower.makeTower();
        this.shadow = Tower.makeShadow();
        final Color skyColor = new Color(49f/256, 19f/256, 83f/256, 1);
        this.skyGraphic = new RectSprite(Game.RenderWidth, Game.RenderHeight, skyColor);
        skyGraphic.y = -Height;
        this.floatyBits = new FloatingBitCollection();
        this.stars = new StarCollection();

        final int buttonX = Game.RenderWidth - 80;
        final int buttonY = Game.RenderHeight - 32;
        final IAction toMenu = new IAction() {
            @Override
            public void eval() {
                MusicManager.startFadeOut(60);
                Game.mode.transitionTo(new IFunction1<Mode>() {
                    @Override
                    public Mode apply() {
                        BackgroundManager.start();
                        return new MenuMode(0);
                    }
                });
            }
        };
        this.continueButton = new Button("Continue", buttonX, buttonY, toMenu, Controls.Enter);
        continueButton.setX(Game.RenderWidth - continueButton.getWidth() - 5);

    }

    public static IFunction1<Mode> factory = new IFunction1<Mode>() {
        @Override
        public Mode apply() {
            return new TitleMode();
        }
    };

    @Override
    public void update() {
        super.update();
        stars.update();
        if (state == State.FadeInBlack) {
            if (fadeInTimer.incrementUntilDone()) {
                state = State.ScrollDown;
            } else {
                screenCover.setAlpha(1 - fadeInTimer.getProgress());
            }
            return;
        }
        sniffyLettering.update();
        floatyBits.update();
        if (!started) {
            started = true;
            MusicManager.play(Game.assets.TitleBGMMusic);
        }
        if (state == State.Normal) {
            continueButton.updateSelected();

        }

        if (flagSprite != null) {
            flagSprite.update();
        }

        if (state == State.ScrollDown) {
            updateScrollDown();
            if (Controls.Enter.justPressed()) {
                finishScrollDown();
            }
        } else if (state == State.FadeInTitle) {
            updateFadeIn();
            if (Controls.Enter.justPressed()) {
                finishFadeIn();
            }
        } else {
            updateNormal();
        }

    }

    private void finishScrollDown() {
        offset = new Point2i(0, 0);
        state = State.FadeInTitle;
    }

    private void updateScrollDown() {
        if (flyInTimer.incrementUntilDone()) {
            finishScrollDown();
            return;
        }
        final float y = Height * (flyInTimer.getOneMinusProgress2());
        offset = new Point2i(0, (int)y);
    }

    private void finishFadeIn() {
        state = State.Normal;
        letterAlpha = 1;
    }

    private void updateFadeIn() {
        sniffyLettering.begin();
        if (alphaTimer.incrementUntilDone()) {
            finishFadeIn();
        } else {
            letterAlpha = alphaTimer.getProgress();
        }
    }

    private void updateNormal() {

    }

    @Override
    public void draw() {
        super.draw();
        skyGraphic.drawWithOffset(offset);
        titleGraphic.drawWithOffset(offset);
        tower.drawWithOffset(offset);
        stars.draw(offset);

        flipTripLettering.setAlpha(letterAlpha);
        flipTripLettering.drawWithOffset(offset);

        floatyBits.draw(letterAlpha);

        sniffyLettering.draw();
        shadow.drawWithOffset(offset);
        if (state == State.Normal) {
            versionText.drawWithOffset(offset);
            continueButton.draw(offset);
        }

        if (flagSprite != null) {
            flagSprite.drawWithOffset(offset);
        }
        if (state == State.FadeInBlack) {
            screenCover.draw();
        }
    }
}
