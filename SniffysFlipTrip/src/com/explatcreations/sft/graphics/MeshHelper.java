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

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;

/**
 * @author moopslc
 */
public class MeshHelper {
    public static Mesh makeMesh() {
        final Mesh mesh = new Mesh(true, 4, 0,
        new VertexAttribute(VertexAttributes.Usage.Position, 3, "a_position" ),
        new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "a_texCoords"));
        final float[] vertices = new float[]{-1f, -1f, 0, 0, 1,
                                              1f, -1f, 0, 1, 1,
                                              1f,  1f, 0, 1, 0,
                                             -1f,  1f, 0, 0, 0};
        mesh.setVertices(vertices);
        return mesh;
    }
}
