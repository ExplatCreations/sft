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
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2f;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.functions.IFunction1;

import java.util.ArrayList;
import java.util.List;

/**
 * @author deweyvm
 */
public class Sepiaizer {
    private Fbo fbo;
    private Mesh mesh;
    private Shader shader;
    private List<IAction> draws = new ArrayList<IAction>();

    private Shader makeShader() {
        final Shader shader = new Shader("default.vert", "sepia.frag");
        shader.setInt("u_texture0", new IFunction1<Integer>() {
            @Override
            public Integer apply() {
                return 0;
            }
        });
        shader.setFloat2("u_renderSize", new IFunction1<Point2f>() {
            @Override
            public Point2f apply() {
                return new Point2f(Game.RenderWidth, Game.RenderHeight);
            }
        });
        return shader;
    }

    public Sepiaizer() {
        this.fbo = new Fbo(Game.RenderWidth, Game.RenderHeight);
        this.shader = makeShader();
        this.mesh = MeshHelper.makeMesh();
    }

    public void update() {
        fbo.draw(new IAction() {
            @Override
            public void eval() {
                Renderer.clearScreen();
            }
        });
        for (IAction act : draws) {
            fbo.draw(act);
        }
        draws.clear();
    }

    public void addDrawer(IAction action) {
        draws.add(action);
    }

    public void render() {
        shaderDraw();
    }

    private void shaderDraw() {
        Renderer.batch.end();
        shader.draw(new IAction() {
            @Override
            public void eval() {
                fbo.getTexture().bind(0);
                mesh.render(shader.getShader(), GL10.GL_TRIANGLE_FAN);
            }
        });
        Renderer.batch.begin();
    }
}
