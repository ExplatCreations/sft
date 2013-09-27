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

package com.explatcreations.sft.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.functions.IAction1;
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.graphics.SpriteBase;

/**
 * @author deweyvm
 */
public abstract class Tile {
    public static final int Size = 16;
    private AnimatedSprite sprite;

    private static final Texture wallShadowClass = Game.assets.TileShadowGraphic;
    private static AnimatedSprite wallShadowRightSprite;
    private static AnimatedSprite wallShadowBottomSprite;
    private static AnimatedSprite wallShadowBottomRightSprite;

    public Tile(AnimatedSprite sprite) {
        this.sprite = sprite;
        if (wallShadowBottomRightSprite == null) {
            initShadows();
        }
    }

    private static void initShadows() {
        wallShadowRightSprite = makeShadowSprite(1);
        wallShadowBottomSprite = makeShadowSprite(2);
        wallShadowBottomRightSprite = makeShadowSprite(3);
    }

    private static AnimatedSprite makeShadowSprite(int frame) {
        return Assets.makeTileFrame(wallShadowClass, frame);
    }

    protected AnimatedSprite getSprite() {
        return sprite;
    }

    protected void setSprite(AnimatedSprite s) {
        this.sprite = s;
    }

    public void giveSprite(IAction1<AnimatedSprite> action) {
        action.eval(getSprite());
    }

    public void update() {
        if (sprite != null) {
            sprite.update();
        }
    }

    public void draw(int i, int j, Point2i offset) {
        drawSprite(sprite, i, j, offset);
    }

    public static void drawSprite(SpriteBase sprite, int i, int j, Point2i offset) {
        if (sprite != null) {
            sprite.x = i*Tile.Size + offset.x;
            sprite.y = j*Tile.Size + offset.y;
            sprite.draw();
        }
    }

    public static void drawRightShadow(int i, int j, Point2i offset) {
        drawSprite(wallShadowRightSprite, i, j, offset);
    }

    public static void drawBottomShadow(int i, int j, Point2i offset) {
        drawSprite(wallShadowBottomSprite, i, j, offset);
    }

    public static void drawBottomRightShadow(int i, int j, Point2i offset) {
        drawSprite(wallShadowBottomRightSprite, i, j, offset);
    }

    public Tile pressButton() {
        return this;
    }

    public Tile stepOn() {
        return this;
    }

    public Tile leave() {
        return this;
    }

    /** used to denote whether or not any tile is off for level completion */
    public boolean isOff() {
        return false;
    }

    public boolean isWalkable() {
        return true;
    }

    public boolean isSlippery() {
        return false;
    }

    public boolean isEscapable() {
        return true;
    }

    /**
     *
     * @return Whether or not a pushable thing can fall 'into' this tile.
     * Defaults to false
     */
    public boolean isHole() {
        return false;
    }

    /**
     *
     * @return Whether or not a thing can pass over the tile.
     */
    public boolean isPassable() {
        return true;
    }

    public boolean doneClearing() {
        return true;
    }

    public void beginClearing() {
        //nothing
    }

    public MiniTile getMiniTile() {
        return new MiniTile(getSprite().getFrame());
    }

}
