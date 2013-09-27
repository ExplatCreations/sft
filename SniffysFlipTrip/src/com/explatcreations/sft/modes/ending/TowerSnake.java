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
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.particles.ParticleManager;

import java.util.ArrayList;

/**
 * @author deweyvm
 */
public class TowerSnake {
    private static final Texture towerClass = Game.assets.TowerGraphic;
    private static final Texture shadowClass = Game.assets.TowerShadowGraphic;
    
    enum State {
        Still,
        Wiggle,
        Explode,
        Done
    }

    private State state = State.Still;

    private final ArrayList<SpriteBase> towerSprites = makeTowerSprites();
    private final ArrayList<SpriteBase> shadowSprites = makeShadowSprites();
    private int t;
    private int jPtr;
    private final Timer ptrTimer = new Timer(3);
    private final Timer stillTimer = new Timer(180);
    private final Timer wiggleTimer = new Timer(30);
    private final int speed = 1;

    private static ArrayList<SpriteBase> makeTowerSprites() {
        final ArrayList<SpriteBase> sprites = new ArrayList<SpriteBase>();
        for (int i = 0; i < Tower.Height; i += 1) {
            final SpriteBase next = Assets.makeSpriteFrame(towerClass, Tower.Width, 1, i);
            next.x = Tower.X;
            next.y = Tower.Y + i;
            sprites.add(next);
        }
        return sprites;
    }

    private static ArrayList<SpriteBase> makeShadowSprites() {
        final ArrayList<SpriteBase> sprites = new ArrayList<SpriteBase>();
        for (int i = 0; i < Tower.ShadowWidth; i += 1) {
            final SpriteBase next = Assets.makeSpriteFrame(shadowClass, 1, Tower.ShadowHeight, i);
            next.x = Tower.ShadowX + i;
            next.y = Tower.ShadowY;
            next.setAlpha(0.3f);
            sprites.add(next);
        }
        return sprites;
    }

    public boolean isExplodingDone() {
        return jPtr > Tower.Height;
    }

    public void update() {
        if (state == State.Still) {
            if (stillTimer.incrementUntilDone()) {
                state = State.Wiggle;
                Game.assets.WiggleSound.play();
            }
        } else if (state == State.Wiggle) {
            if (wiggleTimer.incrementUntilDone()) {
                state = State.Explode;
            }
        } else if (state == State.Explode) {
            updateExplode();

        } else {//if (state == State.Done)


        }



        if (state == State.Wiggle || state == State.Explode) {
            for (int j = 0; j < Tower.Height; j += 1) {
                final SpriteBase next = towerSprites.get(j);
                next.x = getTowerX(t, j);
            }
            for (int i = 0; i < Tower.ShadowWidth; i += 1) {
                final SpriteBase next = shadowSprites.get(i);
                next.y = getShadowY(t, i);
            }
            t += 1;
        }
    }

    private void updateExplode() {
        if (ptrTimer.incrementUntilDone()) {
            jPtr += speed;
            if (!isExplodingDone()) {
                ParticleManager.spawnStar(getTowerX(t, jPtr), Tower.Y + jPtr, false);
            } else {
                Game.assets.ExplodefinalSound.play();
                for (int i = 0; i < 10; i += 1) {
                    ParticleManager.spawnStar(getTowerX(t, jPtr), Tower.Y + jPtr, true);
                }
                state = State.Done;
            }
        }
    }

    private static int getShadowY(int t, int i) {
        final float h = Tower.ShadowWidth - 10;
        if (i < 15) {
            return Tower.ShadowY;
        } else {
            final float result = Tower.ShadowY + (i / h) * getOffset(t, i);
            return (int)result;
        }
    }

    private static int getTowerX(int t, int i) {
        final float h = Tower.Height - 20;
        if (i > h) {
            return Tower.X;
        } else {
            final float result = Tower.X + ((h - i - 1) / h) * getOffset(t, i);
            return (int)result;
        }
    }

    private static float getOffset(int t, int i) {
        return (float)(getAmplitude(t) * Math.sin((i + t) / (1f + t / 100f)));
    }


    private static float getAmplitude(int t) {
        return (10f * (t / 50f));
    }

    public void draw() {
        for (int j = jPtr; j < Tower.Height; j += 1) {
            final SpriteBase sprite = towerSprites.get(j);
            sprite.draw();
        }
        final int max = (int)(((float)jPtr / Tower.Height) * Tower.ShadowWidth);
        for (int i = 0; i < Tower.ShadowWidth - max; i += 1) {
            final SpriteBase sprite = shadowSprites.get(i);
            if (sprite == null) return;
            sprite.draw();
        }
    }
}
