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
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.explatcreations.sft.Debug;
import com.explatcreations.sft.data.Point2f;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.functions.IFunction1;

import java.util.HashMap;
import java.util.Map;


/**
 * @author moopslc
 */
class Shader {
    private static final String Root = "shaders/";

    private Map<String, IAction> actions= new HashMap<String, IAction>();

    private ShaderProgram shader;

    public final String vert;
    public final String frag;

    public Shader(String vert, String frag) {
        this.vert = vert;
        this.frag = frag;
        this.shader = compileShader();
    }

    private ShaderProgram compileShader() {

        final ShaderProgram result = new ShaderProgram(Gdx.files.internal(Root + vert),
                                                       Gdx.files.internal(Root + frag));
        final String log = result.getLog();
        //disallow any warnings
        if ( !result.isCompiled() || (log.length() != 0 && log.contains("warning"))) {
            Debug.warning(log);
            throw new RuntimeException(log);
        }

        return result;
    }

    private void makeAction(final String name, final IAction func) {
        actions.put(name, new IAction() {
            @Override
            public void eval() {
                if (shader.hasUniform(name)) {
                    func.eval();
                }
            }
        });
    }

    public void setMat(final String name, final IFunction1<Matrix4> func) {
        IAction setter = new IAction() {
            @Override
            public void eval() {
                shader.setUniformMatrix(name, func.apply());
            }
        };
        makeAction(name, setter);
    }

    public void setInt(final String name, final IFunction1<Integer> func) {
        IAction setter = new IAction() {
            @Override
            public void eval() {
                shader.setUniformi(name, func.apply());
            }
        };
        makeAction(name, setter);
    }

    public void setFloat(final String name, final IFunction1<Float> func) {
        IAction setter = new IAction() {
            @Override
            public void eval() {
                shader.setUniformf(name, func.apply());
            }
        };
        makeAction(name, setter);
    }


    public void setFloat2(final String name, final IFunction1<Point2f> func) {
        final IAction setter = new IAction() {
            @Override
            public void eval() {
                Point2f p = func.apply();
                shader.setUniformf(name, p.x, p.y);
            }
        };
        makeAction(name, setter);
    }

    public ShaderProgram getShader() {
        return shader;
    }

    private void begin() {
        shader.begin();
        for (IAction action : actions.values()) {
            action.eval();
        }
    }

    private void end() {
        shader.end();
    }

    public void draw(IAction func) {
        begin();
        func.eval();
        end();
    }
}