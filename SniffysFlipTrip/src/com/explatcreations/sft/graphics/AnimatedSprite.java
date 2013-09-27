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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.explatcreations.sft.Debug;

import java.util.HashMap;
import java.util.Map;

/**
 * @author moopslc
 */
public class AnimatedSprite extends SpriteBase {
    private final Texture texture;

    private Animation currentAnimation;
    private Map<String, Animation> animations = new HashMap<String, Animation>();
    private String currentName;
    private boolean dirty;
    private float alpha = 1;
    public AnimatedSprite(Texture texture, int width, int height) {
        super(width, height);
        this.texture = texture;
    }

    public void addAnimation(String name, int speed, int[] frames, boolean looped) {

        if (animations.containsKey(name)) {
            Debug.warning("animation already with name <" + name + ">");
        }
        if (speed == 0) {
            Debug.warning("speed was zero");
            speed = 1;
        }
        if (frames.length == 0) {
            Debug.warning("animation contained no frames");
            frames = new int[]{0};
        }
        final Animation animation = new Animation(texture, getWidth(), getHeight(), speed, looped, frames);
        animations.put(name, animation);
    }

    public StaticSprite getFrame() {
        if (currentAnimation == null) {
            throw new RuntimeException("animation has not played");
        }
        return currentAnimation.getFrame();
    }

    public void setFrame(int frame) {
        currentAnimation.setFrame(frame);
    }

    public void resetCurrentAnimation() {
        if (currentAnimation != null) {
            currentAnimation.reset();
        }
    }

    @Override
    public TextureRegion getRegion() {
        return getFrame().getRegion();
    }

    @Override
    public void setColor(Color color) {
        getFrame().setColor(color);
    }

    @Override
    public void setAlpha(float a) {
        alpha = a;
        dirty = true;
    }

    public void play(String name) {
        currentAnimation = animations.get(name);
        if (currentAnimation == null) {
            throw new RuntimeException("no such animation with name " + name);
        }
        currentName = name;
    }

    public boolean isFinished() {
        if (currentAnimation == null) {
            return false;
        } else {
            return currentAnimation.isFinished();
        }
    }

    public void forceFinish() {
        if (currentAnimation != null) {
            currentAnimation.forceFinish();
        }
    }

    @Override
    public void update() {
        if (currentAnimation != null) {
            dirty |= currentAnimation.update();
        }
    }

    @Override
    public void drawSelf(float x, float y) {
        if (currentAnimation != null) {
            if (dirty) {
                currentAnimation.setAlpha(alpha);
                dirty = false;
            }
            currentAnimation.draw(x, y);
        }
    }
}
