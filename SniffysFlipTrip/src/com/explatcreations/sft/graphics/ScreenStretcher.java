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
public class ScreenStretcher {
    private final Fbo fbo = new Fbo(Game.RenderWidth, Game.RenderHeight);
    private final Shader shader = makeShader();
    private final Mesh mesh = MeshHelper.makeMesh();

    private static Shader makeShader() {
        final Shader shader = new Shader("default.vert", "aspect-ratio.frag");
        shader.setFloat2("u_renderSize", new IFunction1<Point2f>() {
            @Override
            public Point2f apply() {
                return new Point2f(Game.RenderWidth, Game.RenderHeight);
            }
        });
        shader.setFloat2("u_windowSize", new IFunction1<Point2f>() {
            @Override
            public Point2f apply() {
                return new Point2f(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            }
        });
        shader.setInt("u_isWide", new IFunction1<Integer>() {
            @Override
            public Integer apply() {
                return isWide() ? 1 : 0;
            }
        });
        shader.setInt("u_texture", new IFunction1<Integer>() {
            @Override
            public Integer apply() {
                return 0;
            }
        });
        return shader;
    }

    public static boolean isWide() {
        final float renderScale = (float)Game.RenderWidth/Game.RenderHeight;
        final float windowScale = (float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
        return renderScale <= windowScale;
    }

    private List<IAction> layers = new ArrayList<IAction>();

    public void addLayer(IAction drawer) {
        layers.add(drawer);
    }

    public void render() {
        fbo.draw(new IAction() {
            @Override
            public void eval() {
                for(IAction layer : layers) {
                    layer.eval();
                }
            }
        });
        shader.draw(new IAction() {
            @Override
            public void eval() {
                fbo.getTexture().bind(0);
                mesh.render(shader.getShader(), GL10.GL_TRIANGLE_FAN);
            }
        });

        layers.clear();
    }


}
