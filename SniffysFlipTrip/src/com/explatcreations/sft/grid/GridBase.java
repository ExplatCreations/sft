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
import com.explatcreations.sft.data.LevelIndex;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.entities.Ball;
import com.explatcreations.sft.enums.BallType;
import com.explatcreations.sft.enums.TeleporterColor;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.functions.IAction1;
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.input.Controls;
import com.explatcreations.sft.loading.LevelData;
import com.explatcreations.sft.tiles.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author moopslc
 */
public abstract class GridBase implements IGrid {
    protected final ArrayList<Ball> balls = new ArrayList<Ball>();
    protected final Map<TeleporterColor, List<Point2i>> teleporters = new HashMap<TeleporterColor, List<Point2i>>();

    protected int cols;
    protected int rows;
    protected final int goldSteps;
    public final LevelIndex levelIndex;
    protected Point2i startPos;

    private ArrayList<IAction> currentMove = new ArrayList<IAction>();
    private ArrayList<ArrayList<IAction>> undoStack = new ArrayList<ArrayList<IAction>>();
    private int heldTimer;

    protected Point2i offset;
    /** only check if the level is completed if this variable is true */
    protected boolean tileChanged = false;
    protected Tile[] tiles;
    protected LevelData tileMap;
    protected boolean isSecondSequence;
    public GridBase(LevelIndex levelIndex) {
        this.levelIndex = levelIndex;
        this.isSecondSequence = levelIndex.isSecondSequence();
        this.tileMap = Game.world.getLevel(levelIndex);
        this.startPos = tileMap.start;
        this.cols = tileMap.cols;
        this.rows = tileMap.rows;
        this.goldSteps = tileMap.goldSteps;
        this.tiles = new Tile[rows*cols];
        for (int i = 0; i < cols; i += 1) {
            for (int j = 0; j < rows; j += 1) {
                final int e = tileMap.tiles[i][j];
                setTile(i, j, TileUtils.makeTileFromRaw(e, isSecondSequence));
            }
        }
        final int xStart = 130;//how much space the controls take up worst case
        final int xOffset = xStart + ((Game.RenderWidth - xStart) - cols * Tile.Size)/2;
        final int yOffset = (Game.RenderHeight - rows * Tile.Size) / 2 - Tile.Size/2;
        this.offset = new Point2i(xOffset, yOffset);
    }

    public abstract void allowCommit();

    public void setTile(int i, int j, Tile tile) {
        tileChanged = true;
        tiles[i + j * cols] = tile;
    }

    public void setTile(Point2i p, Tile tile) {
        setTile(p.x, p.y, tile);
    }

    public Tile getTile(int i, int j) {
        final int index = i + j*cols;

        if (index >= tiles.length || index < 0) {
            return new NullTile();
        }
        final Tile result = tiles[index];
        if (result == null) {
            return new NullTile();
        }
        return result;
    }

    public Tile getTile(Point2i p) {
        return getTile(p.x, p.y);
    }

    public void update() {
        tileChanged = false;
        for (int i = 0; i < cols; i += 1) {
            for (int j = 0; j < rows; j += 1) {
                final Tile tile = getTile(i, j);
                tile.update();
            }
        }
    }

    public Point2i getPartner(Point2i pos, TeleporterTile tile) {
        final List<Point2i> arr = teleporters.get(tile.getColor());
        final Point2i first = arr.get(0);
        Point2i result;
        if (first.equals(pos)) {
            result = arr.get(1);
        } else {
            result = arr.get(0);
        }
        if (hasBall(result)) {
            return null;
        }
        return result;
    }

    public boolean hasBall(Point2i p) {
        for (Ball b : balls) {
            if (b.getIndex().equals(p)) {
                return true;
            }
        }
        return false;
    }

    public void addTransform(IAction func) {
        currentMove.add(func);
    }

    public boolean tryUndo(boolean boardActive) {
        return tryUndo(boardActive, false);
    }

    protected abstract boolean isClearing();

    public boolean tryUndo(boolean boardActive, boolean force) {
        if (isClearing()) {
            return false;
        }
        if (Controls.Undo.isPressed() || force) {
            heldTimer += 1;
        } else {
            heldTimer = 0;
        }
        final boolean undoPressed = heldTimer == 1
                                 || (heldTimer > 30 && heldTimer % 3 == 0);
        if (undoPressed && (undoStack.size() > 0 || currentMove.size() > 0 || boardActive)) {
            final ArrayList<IAction> current;
            if (currentMove.size() > 0) {
                current = currentMove;
                currentMove = new ArrayList<IAction>();
            } else if (boardActive) {
                current = new ArrayList<IAction>();
            } else {
                current = undoStack.get(undoStack.size() - 1);
                undoStack.remove(undoStack.size() - 1);
            }
            for(IAction trans:current) {
                trans.eval();
            }
            Game.assets.UndoSound.play();
            return true;
        }
        return false;
    }

    public void tryCommit(boolean canCommit) {
        if (currentMove.size() > 0 && canCommit) {
            undoStack.add(currentMove);
            currentMove = new ArrayList<IAction>();
        }
    }

    public void pushButton() {
        LeverTile.toggle();
        for (int i = 0; i < cols; i += 1) {
            for (int j = 0; j < rows; j += 1) {
                final Tile tile = getTile(i, j);
                if (tile instanceof LeverTile) {
                    continue;
                }
                final Tile newTile = tile.pressButton();
                setTile(i, j, newTile);
                if (newTile.isHole()) {
                    final Ball ball = getBall(new Point2i(i, j));
                    if (ball != null) {
                        ball.forceRest();
                        ball.doFallIn(newTile);
                    }
                }
            }
        }
    }

    public abstract Ball getBall(Point2i p);

    protected void commitButtonPush() {
        Game.assets.ButtonpushSound.play();
        pushButton();

        addTransform(new IAction() {
            @Override
            public void eval() {
                pushButton();
            }
        });
    }

    /**
     * @return false iff the location can't accept earth magic.
     */
    public boolean tryApplyEarth(final Point2i p) {
        final Tile tile = getTile(p);
        if (tile.isHole()) {
            setTile(p, new FloorTile());
        } else if (tile.isPassable() && !(tile instanceof TeleporterTile) && !(tile instanceof FlipTile)) {
            final BreakableWallTile newTile = new BreakableWallTile(isSecondSequence);
            tile.giveSprite(new IAction1<AnimatedSprite>() {
                @Override
                public void eval(AnimatedSprite s) {
                    newTile.doBuildAnimation(s);
                }
            });
            setTile(p, newTile);
        } else {
            return false;
        }

        addTransform(new IAction() {
            @Override
            public void eval() {
                setTile(p, tile);
            }
        });
        Game.assets.GrowblockSound.play();
        return true;
    }

    public void checkerBallAffectTile(final Point2i p) {
        final Tile tile = getTile(p);

        if (tile instanceof LeverTile) {
            commitButtonPush();
        } else if (tile instanceof FlipTile) {
            setTile(p, tile.stepOn());

            addTransform(new IAction() {
                @Override
                public void eval() {
                    setTile(p, tile);
                }
            });
        }
    }

    public void fallIn(Ball ball, final Tile tile, BallType ballType, final Point2i p) {
        setTile(p, new ShutterTile(ball));

        addTransform(new IAction() {
            @Override
            public void eval() {
                setTile(p, tile);
            }
        });

    }

    protected void leaveTile(final Point2i p) {
        final Tile tile = getTile(p);
        final Tile leftTile = tile.leave();
        if (tile == leftTile) {
            return;
        }
        setTile(p, tile.leave());
        addTransform(new IAction() {
            @Override
            public void eval() {
                setTile(p, tile);
            }
        });
    }

    public void draw(boolean drawPlayer) {
        for (int i = cols - 1; i >= 0; i -= 1) {
            for (int j = rows - 1; j >= 0; j -= 1) {
                final Tile tile = getTile(i, j);
                if (tile instanceof NullTile) {
                    continue;
                }

                tile.draw(i, j, offset);
                final Tile right = getTile(i + 1, j);
                if (!tile.isPassable() && right.isPassable()) {
                    Tile.drawRightShadow(i + 1, j, offset);
                }

                final Tile bottom = getTile(i, j + 1);
                if (!tile.isPassable() && bottom.isPassable()) {
                    Tile.drawBottomShadow(i, j + 1, offset);
                }

                final Tile bottomRight = getTile(i + 1, j + 1);
                if (!tile.isPassable() && bottomRight.isPassable()) {
                    Tile.drawBottomRightShadow(i + 1, j + 1, offset);
                }
            }
        }
    }
}
