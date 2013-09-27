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

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.explatcreations.sft.functions.IAction;

/**
 * @author moopslc
 */
class Fbo {
    private static boolean isDrawing = false;
    public final int width;
    public final int height;
    private FrameBuffer fbo;
    public Fbo(int width, int height) {
        this.width = width;
        this.height = height;
        this.fbo = new NearestNeighborFrameBuffer(Pixmap.Format.RGBA8888, width, height, true);
    }


    public void draw(IAction func) {
        fbo.begin();
        if (isDrawing) {
            throw new RuntimeException();
        }
        isDrawing = true;
        func.eval();
        isDrawing = false;
        fbo.end();
    }

    public void drawSimple(final Shader shader, final Mesh mesh, IAction func) {
        draw(func);

        shader.draw(new IAction() {
            @Override
            public void eval() {
                getTexture().bind();
                mesh.render(shader.getShader(), GL10.GL_TRIANGLE_FAN);
            }
        });

    }

    public Texture getTexture() {
        return fbo.getColorBufferTexture();
    }
}