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
import com.explatcreations.sft.audio.MusicManager;
import com.explatcreations.sft.data.LevelIndex;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.entities.Ball;
import com.explatcreations.sft.entities.GridEntity;
import com.explatcreations.sft.entities.Player;
import com.explatcreations.sft.enums.*;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.gui.Hud;
import com.explatcreations.sft.input.Controls;
import com.explatcreations.sft.modes.InterimMode;
import com.explatcreations.sft.modes.LastStageMode;
import com.explatcreations.sft.modes.ending.EndingMode;
import com.explatcreations.sft.tiles.*;

import java.util.ArrayList;

/**
 * @author moopslc
 */
public class Grid extends GridBase implements IGridView {
    private boolean levelComplete;
    private Player player;

    //variable indicating whether or not a movement is "in progress"
    private boolean canCommit;
    private int steps;
    private Atomizer atomizer;
    private boolean doSkipFrame = false;
    private Hud hud;

    private void initObjects() {
        for (TeleporterColor color: TeleporterColor.values()) {
            teleporters.put(color, new ArrayList<Point2i>());
        }
    }


    public Grid(LevelIndex levelIndex) {
        super(levelIndex);
        initObjects();

        initTeleporters();
        this.player = new Player(startPos, offset, new Inventory(this, goldSteps));
    }

    public void skipFrame() {
        doSkipFrame = true;
    }

    public void clickUndo() {
        tryUndo(isBoardActive(), true);
        ceaseMagic();
        player.ceaseMoving();
        skipFrame();
    }

    //allows comitting for a single frame
    @Override
    public void allowCommit() {
        canCommit = true;
    }

    public TilePreview getTilePreview(int i, int j) {
        final Ball ball = getBall(new Point2i(i, j));
        final BallType ballType = ball == null? null : ball.getType();
        return new TilePreview(getTile(i, j), ballType);
    }

    public Point2i getPlayerStart() {
        final int x = startPos.x * Tile.Size;
        final int y = startPos.y * Tile.Size;
        return offset.add(x, y);
    }

    public Point2i getPlayerPosition() {
        return player.getPosition(offset);
    }

    public Hud makeHud() {
        if (hud == null) {
            hud = new Hud(player.getInventory(), this);
        }

        return hud;
    }

    @Override
    public int getSteps() {
        return steps;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    private void initTeleporters() {
        for (int i = 0; i < cols; i += 1) {
            for (int j = 0; j < rows; j += 1) {
                final Point2i p = new Point2i(i, j);
                final int tile = tileMap.tiles[i][j];
                if (TileCodes.isTeleporter(tile)) {
                    final TeleporterColor color = TileCodes.codeToTeleportColor(tile);
                    teleporters.get(color).add(p);
                }

                if (TileCodes.isPushable(tile)) {
                    setTile(p, new FloorTile());
                    balls.add(new Ball(this, TileCodes.codeToBallType(tile), p, offset));
                }
            }
        }
    }

    /**
     * Returns true if and only if the player may walk on the
     * tile at p.
     * @param    p The index of the tile being queried.
     */
    private boolean tryWalk(final Point2i p) {
        final Tile tile = getTile(p);
        if (tile instanceof DoorTile) {
            final DoorTile door = (DoorTile)tile;
            final KeyColor key = door.toKey();
            final Inventory inventory = player.getInventory();
            if (!inventory.hasKey(key)) {
                return false;
            }
            addTransform(new IAction() {
                @Override
                public void eval() {
                    setTile(p, tile);
                    inventory.addKey(key);
                }
            });

            setTile(p, door.getOpen());

            inventory.removeKey(key);
            return false;
        }

        final Ball ball = getBall(p);
        if (ball != null) {
            final Point2i force = p.sub(player.getIndex());
            final Point2i pushedPos = ball.getIndex().add(force);
            final Tile pushedTile = getTile(pushedPos);
            final boolean blockInWay = hasBall(pushedPos);

            if (!blockInWay && pushedTile.isPassable()) {
                ball.applyForce(force);
            }
            player.doPush(force);
            return false;
        }

        return tile.isWalkable();
    }

    public boolean isAnyObjectActive() {

        for (Ball b: balls) {
            if (b.isMoving()) {
                return true;
            }
        }

        if (player.isMagicActive()) {
            return true;
        }
        return false;
    }

    private int flipCount;
    private ArrayList<ArrayList<FlipTile>> flipTiles;
    private FlipTile lastTile;
    private final Timer clearTimer = new Timer(5);
    private final Timer finalTimer = new Timer(180);

    private void beginClearing() {
        player.doVictory();
        flipTiles = new ArrayList<ArrayList<FlipTile>>();
        for (int d = 0; d < rows + cols; d += 1) {
            final ArrayList<FlipTile> next = new ArrayList<FlipTile>();
            for (int j = 0; j < rows; j += 1) {
                for (int i = 0; i < cols; i += 1) {
                    if (i + j == d) {
                        final Tile nextTile = getTile(i, j);
                        if (nextTile == null || !(nextTile instanceof FlipTile)) {
                            continue;
                        }
                        final FlipTile tile = (FlipTile)getTile(i, j);
                        if (tile != null) {
                            flipCount += 1;
                            next.add(tile);
                        }
                    }
                }
            }
            flipTiles.add(next);
        }
    }

    private boolean clearTiles() {
        if (flipTiles == null) {
            player.updateInventory();
            beginClearing();
        } else if (lastTile != null) {
            if (lastTile.doneClearing()) {
                finalTimer.increment();
                return finalTimer.isMax();
            }
        } else {
            if (clearTimer.isMax()) {
                clearTimer.reset();

                if (flipTiles.size() == 1) {
                    lastTile = flipTiles.get(0).get(0);
                }
                final ArrayList<FlipTile> nextTiles = flipTiles.get(0);
                flipTiles.remove(0);
                for (FlipTile t:nextTiles) {
                    flipCount -= 1;
                    if (flipCount == 0) {
                        lastTile = t;
                    }
                    t.beginClearing();
                    Game.assets.BloopSound.play();
                }
            }
            clearTimer.increment();
        }
        return false;
    }

    public boolean isFinishing() {
        return finalTimer.getValue() != 0;
    }

    public boolean isAtomizing() {
        return atomizer != null;
    }

    private boolean hasFaded;
    private void processClearLevel() {
        if (!hasFaded) {
            hasFaded = true;
            MusicManager.startFadeOut(60);
        }
        player.updateAnimation();
        final boolean cleared = clearTiles();
        if (finalTimer.getValue() == 60) {
            Game.assets.WinJingleMusic.play();
        }

        if (cleared) {
            if (atomizer == null) {
                atomizer = new Atomizer(Player.makeSprite(), getPlayerPosition(), true);
            } else {
                atomizer.update();
            }
            if (atomizer.isDone()) {
                doTransition();
            }
        }
    }

    private void doPlayerMove() {
        final Point2i move = player.getMove();
        Point2i index;
        if (!player.isMoving() && !isAnyObjectActive()) {//if this is removed, doors can be opened strangely without walking onto them

            final int dx = move.x;
            int dy = move.y;
            if (dx != 0) {
                dy = 0;
            }
            if (dx != 0 || dy != 0) {
                index = player.getIndex().add(dx, dy);
                final Tile stepped = getTile(player.getIndex());
                if (!stepped.isEscapable()) {
                    return;
                }
                if (tryWalk(index)) {
                    final int extent = getExtent(player.getIndex(), dx, dy);
                    player.confirm(dx*extent, dy*extent);
                }
            }
        }
    }

    public void updateTiles() {
        super.update();
    }

    @Override
    public void update() {
        super.update();
        if (doSkipFrame) {
            doSkipFrame = false;
            return;
        }


        if (levelComplete) {
            processClearLevel();
            return;
        }

        final boolean undid = super.tryUndo(isBoardActive());
        if (undid) {
            ceaseMagic();
            player.ceaseMoving();
            return;
        }

        final SpawnMagicResult didMagic = player.trySpawnMagic(this);
        player.processTryCastMagic(didMagic);

        updateObjects();

        doPlayerMove();

        player.update();
        final Point2i partialPos = player.isTileDone();
        if (partialPos != null) {
            leaveTile(partialPos);
        }

        if (player.isFinished()) {
            canCommit = true;
            final Point2i pos = player.getIndex();
            final Point2i queued = player.getQueuedMove();

            player.completeMove();
            playerAffectTile(player.getIndex());
            leaveTile(pos);
            steps += 1;
            addTransform(new IAction() {
                @Override
                public void eval() {
                    player.setIndex(pos);
                    player.setFacing(queued);
                    steps -= 1;
                }
            });

        } else {
            canCommit |= didMagic == SpawnMagicResult.Success;
            canCommit |= player.isMagicFinished();
            canCommit &= !isAnyObjectActive();
            canCommit &= !player.isMoving();
        }
        super.tryCommit(canCommit);

        if ((tileChanged && checkCompleted()) || (Game.globals.CheatsEnabled && Controls.CheatSkip.justPressed())) {
            levelComplete = true;
        }
    }

    private boolean isBoardActive() {
        return player.isMoving();
    }

    /**
     * Move on to the next stage or appropriate screen.
     */
    private void doTransition() {
        final boolean isGold = getSteps() <= Game.world.getLevel(levelIndex).goldSteps;
        final ArrayList<LevelIndex> unlockedLevels = Game.save.completeLevel(levelIndex, isGold, steps);

        final Ending ending = Game.save.getEnding(levelIndex);
        if (ending != null) {
            Game.save.setHasShownEnding(ending, true);
            Game.mode.transitionTo(EndingMode.getFactory(ending));
        } else {
            if (levelIndex.isLast()) {
                Game.mode.transitionTo(LastStageMode.getFactory(Grid.this, getSteps()));
            } else {
                Game.mode.transitionTo(InterimMode.getFactory(Grid.this, new Grid(levelIndex.successor()), getSteps(), unlockedLevels));
            }
        }
    }

    private void ceaseMagic() {
        player.ceaseMagic();
    }


    private int getExtent(Point2i start, int dx, int dy) {
        int result = 1;
        Tile tile = getTile(start);
        int i = start.x;
        int j = start.y;
        while (tile != null) {
            final Point2i p = new Point2i(i + dx, j + dy);
            final boolean hasBall = hasBall(p);
            if (hasBall) {
                return result - 1;
            }
            if (!getTile(p).isSlippery()) {
                return result;
            }
            result += 1;
            i += dx;
            j += dy;
            tile = getTile(i, j);

            if (!getTile(i+dx, j+dy).isWalkable() || hasBall) {
                result -= 1;
                break;
            }
        }
        return result;
    }

    private void updateObjects() {
        for (Ball b: balls) {
            b.update();
        }
    }

    private boolean checkCompleted() {
        if (isAnyObjectActive()) {
            return false;
        }
        for (int i = 0; i < cols; i += 1) {
            for (int j = 0; j < rows; j += 1) {
                Tile tile = getTile(i, j);
                if (tile.isOff()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void playerAffectTile(final Point2i p) {
        final Tile tile = getTile(p);
        final Inventory inventory = player.getInventory();
        if (tile instanceof FlipTile) {
            final Tile newTile = tile.stepOn();
            setTile(p, newTile);
            addTransform(new IAction() {
                @Override
                public void eval() {
                    setTile(p, tile);
                }
            });

        } else if (tile instanceof FloorTile) {
            Game.assets.StepSound.play();
        } else if (tile instanceof LeverTile) {
            commitButtonPush();
        } else if (tile instanceof TeleporterTile) {
            final Point2i partner = getPartner(p, (TeleporterTile)tile);
            if (partner != null) {
                player.setIndex(partner);
                Game.assets.TeleportSound.play();
            } else {
                Game.assets.TelefailSound.play();
            }
        } else if (tile instanceof KeyTile) {
            final KeyTile key = (KeyTile)tile;
            setTile(p, key.stepOn());
            Game.assets.CollectSound.play();
            inventory.addKey(key.getColor());
            addTransform(new IAction() {
                @Override
                public void eval() {
                    setTile(p, tile);
                    inventory.removeKey(key.getColor());
                }
            });

        } else if (tile instanceof MagicTile) {
            final MagicTile magic = (MagicTile)tile;
            Game.assets.MagicpickupSound.play();
            addTransform(new IAction() {
                @Override
                public void eval() {
                    setTile(p, tile);
                    inventory.removeMagic(magic.getType());
                }
            });
            setTile(p, magic.stepOn());
            inventory.addMagic(magic.getType());

        }

    }

    @Override
    public Ball getBall(Point2i p) {
        for (Ball b : balls) {
            Point2i index = b.getIndex();
            if (index.equals(p)) {
                return b;
            }
        }
        return null;
    }



    @Override
    public void draw(boolean drawPlayer) {
        super.draw(drawPlayer);
        for (GridEntity o:balls) {
            if (o instanceof Ball && ((Ball)o).isOutOfBounds()) {
                continue;
            }
            o.draw();

        }

        if (!isAtomizing() && drawPlayer) {
            player.draw();
        } else if (atomizer != null) {
            atomizer.draw();
        }
        player.drawMagic();

    }
}
