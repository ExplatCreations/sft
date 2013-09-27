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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.functions.IAction;

/**
 * @author moopslc
 */
public class Renderer {
    public static final SpriteBatch batch = new SpriteBatch();
    private static final Camera camera = makeCamera();

    public static void cleanupTextures(int numTextures) {
        for (int i = 0; i < numTextures; i += 1) {
            Gdx.gl.glActiveTexture(GL20.GL_TEXTURE1 + i);
            Gdx.gl.glBindTexture(GL20.GL_TEXTURE_2D, 0);
        }
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
    }

    public static void draw(boolean clearScreen, IAction modeDraw) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        if (clearScreen) {
            clearScreen();
            Gdx.gl.glClearColor(0, 0, 0, 1);
        }
        batch.begin();
        modeDraw.eval();
        batch.end();
    }

    public static void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    }

    public static void draw(Sprite sprite) {
        sprite.draw(batch);
    }

    public static void draw(BitmapFontCache cache) {
        cache.draw(batch);
    }

    private static Camera makeCamera() {
        OrthographicCamera result = new OrthographicCamera();
        result.setToOrtho(true, Game.RenderWidth, Game.RenderHeight);
        return result;
    }


}
