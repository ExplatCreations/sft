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

package com.explatcreations.sft.grid;

import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.enums.Corner;
import com.explatcreations.sft.graphics.Renderer;
import com.explatcreations.sft.gui.Patch;
import com.explatcreations.sft.tiles.MiniTile;
import com.explatcreations.sft.tiles.TilePreview;

/**
 * @author deweyvm
 */
public class MiniGrid {
    private final int cols;
    private final int rows;
    private final int width;
    private final int height;
    private TilePreview[] sprites;
    private Point2i pos;
    private Patch background;

    public MiniGrid(Grid grid, Corner corner) {
        this.cols = grid.getCols();
        this.rows = grid.getRows();
        this.width = (cols + 3) * MiniTile.Size;
        this.height = (rows + 3) * MiniTile.Size;
        this.pos = getPos(corner);
        this.background = Patch.makeSmallParchment(width, height);
        background.setPosition(pos.x - MiniTile.Size, pos.y - MiniTile.Size);
        makeSprites(grid);
    }

    private Point2i getPos(Corner corner) {
        final int buffer = 8;
        if (corner == Corner.BottomLeft) {
            final int y = Game.RenderHeight - height - buffer;
            return new Point2i(buffer, y);
        } else if (corner == Corner.TopRight) {
            final int x = Game.RenderWidth - width - buffer;
            return new Point2i(x, buffer);
        } else if (corner == Corner.BottomRight) {
            final int y = Game.RenderHeight - height - buffer;
            return new Point2i(buffer, y);
        } else if (corner == Corner.TopLeft) {
            return new Point2i(buffer, buffer);
        } else {
            final int x = Game.RenderWidth/2 - width/2;
            final int y = Game.RenderHeight/2 - height/2;
            return new Point2i(x, y);
        }
    }

    public int getWidth() {
        return cols*MiniTile.Size;
    }

    public int getHeight() {
        return rows*MiniTile.Size;
    }

    private void makeSprites(Grid grid) {
        this.sprites = new TilePreview[rows*cols];
        for (int i = 0; i < cols; i += 1) {
            for (int j = 0; j < rows; j += 1) {
                final TilePreview tile = grid.getTilePreview(i, j);
                putTile(i, j, tile);
            }
        }
    }

    private void putTile(int i, int j, TilePreview tile) {
        sprites[i + j*cols] = tile;
    }

    private TilePreview getTile(int i, int j) {
        return sprites[i + j * cols];
    }


    public void draw() {
        drawBackground();
    }

    public void drawTiles() {
        for (int j = 0; j < rows; j += 1) {
            for (int i = 0; i < cols; i += 1) {
                final TilePreview tile = getTile(i, j);
                tile.draw(i, j, pos);
            }
        }
        Renderer.batch.flush();//magic.jpg
    }

    public void drawBackground() {
        background.draw(Point2i.Zero);
    }
}
