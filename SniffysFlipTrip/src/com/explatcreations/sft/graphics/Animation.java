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

package com.explatcreations.sft.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.data.Timer;

/**
 * @author deweyvm
 */
public class Animation {
    private final Texture texture;
    private final int width;
    private final int height;
    private final boolean looped;
    private final int[] indices;
    private final Timer timer;
    private final StaticSprite[] sprites;
    private final int length;
    private int ptr;
    private boolean paused;
    public Animation(Texture texture, int width, int height, int speed, boolean looped, int[] indices) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.looped = looped;
        this.indices = indices;
        this.timer = new Timer(speed);
        this.sprites = makeSprites();
        this.length = sprites.length;
    }



    private StaticSprite[] makeSprites() {
        final StaticSprite[] result = new StaticSprite[indices.length];
        final int cols = texture.getWidth()/width;
        for (int i = 0; i < indices.length; i += 1) {
            int index = indices[i];
            int x = (index % cols) * width;
            int y = (index / cols) * height;
            TextureRegion next = Assets.makeTextureRegion(texture, x, y, width, height);
            result[i] = new StaticSprite(next);
        }
        return result;
    }

    public void setFrame(int frame) {
        ptr = frame % length;
    }

    public void setPause(boolean pause) {
        this.paused = pause;
    }

    public void reset() {
        ptr = 0;
        timer.reset();
    }

    public boolean isFinished() {
        return ptr == length - 1;
    }

    public void forceFinish() {
        ptr = length - 1;
        timer.reset();
    }

    public StaticSprite getFrame() {
        return sprites[ptr];
    }

    public void setAlpha(float alpha) {
        getFrame().setAlpha(alpha);
    }

    /**
     *
     * @return true iff sprite frame has changed
     */
    public boolean update() {
        if (paused) return false;
        int prevPtr = ptr;
        if (timer.incrementUntilDone()) {
            timer.reset();
            if (looped) {
                ptr = (ptr + 1) % length;
            } else {
                ptr = MathUtils.clamp(ptr + 1, 0, indices.length - 1);
            }
        }

        getFrame().update();
        return prevPtr != ptr;
    }

    public void draw(float x, float y) {
        final SpriteBase frame = getFrame();
        frame.x = (int)x;
        frame.y = (int)y;
        getFrame().draw();
    }
}
