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

package com.explatcreations.sft.entities;

import com.badlogic.gdx.graphics.Texture;
import com.explatcreations.sft.Assets;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.data.Timer;
import com.explatcreations.sft.enums.MagicType;
import com.explatcreations.sft.enums.SpawnMagicResult;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.grid.Grid;
import com.explatcreations.sft.grid.Inventory;
import com.explatcreations.sft.grid.MagicMenu;
import com.explatcreations.sft.input.Controls;
import com.explatcreations.sft.particles.ParticleManager;
import com.explatcreations.sft.tiles.Tile;

/**
 * @author moopslc
 */
public class Player extends GridEntity implements IMagicAcceptor {
    public static final String VictoryName = "victory";
    /**
     * Number of frames it takes to move one block
     */
    public static final int MoveTime = 12;

    private static final Texture walkingImage = Game.assets.PlayerWalkGraphic;
    /**
     * This is the move that the player would like to make.
     * This variable is controller internally by the player.
     */
    private Point2i tryMove;

    /**
     * This is the move that is currently being made as dictated by the Grid
     * either because of sliding or because a move was confirmed.
     */
    private Point2i queuedMove;
    private Timer magicDelayTimer = new Timer(MoveTime).forceFinish();
    private Timer timer;
    private boolean finishedMove = false;
    private Magic magic = null;
    private Point2i castingDirection;
    private boolean isDancing = false;
    private boolean useWalkingSprite = false;
    private Point2i lastMove = new Point2i(0, 1);
    private Timer pushTimer;
    private Point2i pushDirection;

    private MagicType queuedMagic = null;
    private Point2i magicDir = null;
    private MagicMenu magicMenu;
    private Inventory inventory;

    public static AnimatedSprite makeSprite() {
        final AnimatedSprite sprite = Assets.makeTileSprite(walkingImage);
        final int speed = 12;
        sprite.addAnimation(getFaceString(Point2i.Down), speed, new int[]{0}, true);
        sprite.addAnimation(getFaceString(Point2i.Up), speed, new int[]{6}, true);
        sprite.addAnimation(getFaceString(Point2i.Right), speed, new int[]{12}, true);
        sprite.addAnimation(getFaceString(Point2i.Left), speed, new int[]{18}, true);

        sprite.addAnimation(getWalkString(Point2i.Down), speed, new int[]{0, 1, 2, 3}, true);
        sprite.addAnimation(getWalkString(Point2i.Up), speed, new int[]{6, 7, 8, 9}, true);
        sprite.addAnimation(getWalkString(Point2i.Right), speed, new int[]{12, 13, 14, 15}, true);
        sprite.addAnimation(getWalkString(Point2i.Left), speed, new int[]{18, 19, 20, 21}, true);

        sprite.addAnimation(getWindUpString(Point2i.Down), speed, new int[]{4}, false);
        sprite.addAnimation(getWindUpString(Point2i.Up), speed, new int[]{10}, false);
        sprite.addAnimation(getWindUpString(Point2i.Right), speed, new int[]{16}, false);
        sprite.addAnimation(getWindUpString(Point2i.Left), speed, new int[]{22}, false);

        sprite.addAnimation(getReleaseString(Point2i.Down), speed, new int[]{5}, false);
        sprite.addAnimation(getReleaseString(Point2i.Up), speed, new int[]{11}, false);
        sprite.addAnimation(getReleaseString(Point2i.Right), speed, new int[]{17}, false);
        sprite.addAnimation(getReleaseString(Point2i.Left), speed, new int[]{23}, false);

        sprite.addAnimation(VictoryName, 8, new int[]{0, 5}, true);

        sprite.play(getFaceString(Point2i.Down));
        return sprite;
    }


    private static String getFaceString(Point2i dir) {
        return "face" + dir.getDirection();
    }

    private static String getWalkString(Point2i dir) {
        return "walk" + dir.getDirection();
    }

    private static String getWindUpString(Point2i dir) {
        return "castWindUp" + dir.getDirection();
    }

    private static String getReleaseString(Point2i dir) {
        return "castRelease" + dir.getDirection();
    }

    public void doPush(Point2i dir) {
        pushTimer = new Timer(20);
        pushDirection = dir;
        lastMove = dir;
    }

    public boolean isPushing() {
        return pushTimer != null;
    }

    public Player(Point2i pos, Point2i offset, Inventory inventory) {
        super(offset, makeSprite());
        this.timer = new Timer(MoveTime);
        setIndex(pos);
        this.tryMove = Point2i.Zero;
        this.inventory = inventory;
        this.magicMenu = new MagicMenu(this);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }


    /** idempotent */
    public void doVictory() {
        if (!isDancing) {
            sprite.play(VictoryName);
            isDancing = true;
        }
    }

    public void setCastingAnimation(Point2i dir) {
        sprite.play(getWindUpString(dir));
    }

    public void setCastingAnimationDone(Point2i dir) {
        sprite.play(getReleaseString(dir));
        castingDirection = dir;
    }

    public void resetAnimationFromCasting()  {
        sprite.play(getFaceString(castingDirection));
        castingDirection = null;
    }

    public boolean isMagicActive() {
        return magic != null && magic.isMoving();
    }

    public void ceaseMagic() {
        magic = null;
        resetMagic();
    }

    private void updateMagic() {
        if (magic != null && magic.isDone()) {
            setCastingAnimationDone(magic.dir);
            magic = null;
        }
        if (magic != null) {
            magic.update();
        }
    }

    public void updateAnimation() {
        sprite.update();
    }

    public void updateInventory() {
        inventory.update();
    }

    @Override
    public void update() {
        updateInventory();
        if (!isMoving() && useWalkingSprite) {
            useWalkingSprite = false;
            chooseStandAnimation(lastMove);
        }
        if (isPushing()) {
            if (pushTimer.incrementUntilDone()) {
                pushTimer = null;
                pushDirection = null;
                chooseStandAnimation(lastMove);
            } else {
                setCastingAnimation(pushDirection);
            }
        }
        super.update();
        updateMagic();
        if (castingDirection != null && !isMagicActive()) {
            resetAnimationFromCasting();
        }

        if (magicMenu.isActive()) {
            return;
        }
        computeMove();
        if (queuedMove != null && magicDelayTimer.isMax()) {
            useWalkingSprite = true;
            final Point2i index = getIndex();
            sprite.x = (int)(Tile.Size * (index.x + queuedMove.x * timer.getProgress()));
            sprite.y = (int)(Tile.Size * (index.y + queuedMove.y * timer.getProgress()));
            timer.increment();
            if (timer.isMax()) {
                finishedMove = true;
                lastMove = queuedMove;
            }
        } else {
            magicDelayTimer.increment();
        }
    }

    public void setFacing(Point2i dir) {
        chooseStandAnimation(dir);
    }

    public void castMagic(Magic magic, Point2i origin, Point2i force, int extent) {
        magic.applyForce(origin, force, extent);

        magicDelayTimer.reset();
        setCastingAnimation(force);
        this.magic = magic;
    }

    public void completeMove() {
        addIndex(queuedMove);
        sync();
        timer.reset();
        queuedMove = null;
    }

    public boolean isMoving() {
        return queuedMove != null;
    }

    public void ceaseMoving() {
        if (queuedMove != null) {
            chooseStandAnimation(queuedMove);
        }
        queuedMove = null;

        setIndex(getIndex());
    }

    public Point2i getQueuedMove() {
        return queuedMove == null? Point2i.Zero : queuedMove;
    }

    public Point2i isTileDone() {
        if (timer.getMax() == MoveTime || queuedMove == null) {
            return null;
        }
        final int value = timer.getValue();
        if (value > 0 && (timer.getValue() % MoveTime == 0)) {
            int adjX = queuedMove.x > 0? 0 : 1;
            if (queuedMove.x == 0) adjX = 0;
            int adjY = queuedMove.y > 0? 0 : 1;
            if (queuedMove.y == 0) adjY = 0;
            return new Point2i(sprite.x / Tile.Size + adjX,
                               sprite.y / Tile.Size + adjY);
        }
        return null;
    }

    /**
     * Computes the direction the player "wants" to move based on input.
     * This is then stored in the tryMove field.
     */
    private void computeMove() {
        this.tryMove = Controls.directionIsPressed(Point2i.Zero);
    }

    public Point2i getMove() {
        return tryMove;
    }

    private void chooseWalkAnimation(Point2i p) {
        sprite.play(getWalkString(p));
    }

    private void chooseStandAnimation(Point2i p) {
        sprite.play(getFaceString(p));
    }

    public void confirm(int di, int dj) {
        queuedMove = new Point2i(di, dj);
        timer = new Timer((Math.abs(di) + Math.abs(dj)) * MoveTime);
        chooseWalkAnimation(queuedMove);
    }

    public boolean isMagicFinished() {
        return magic != null && magic.isDone();
    }

    public boolean isFinished() {
        final boolean saved = finishedMove;
        finishedMove = false;
        return saved;
    }

    public void drawMagic() {
        if (magic != null) {
            magic.draw();
        }
        if (magicMenu != null) {
            magicMenu.draw();
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////

    private void resetMagic() {
        queuedMagic = null;
        magicDir = null;
    }

    private Point2i getMagicMenuPosition() {
        return getPosition(offset);
    }

    public SpawnMagicResult trySpawnMagic(final Grid grid) {
        magicMenu.update();
        if (Controls.Magic.justPressed() && !grid.isAnyObjectActive() && !isMoving()) {
            final Point2i pos = getMagicMenuPosition();
            if (!inventory.hasAnyMagic()) {
                return SpawnMagicResult.NoMagic;
            } else {
                magicMenu.beginCasting(pos);
                //magicMenu = new MagicMenu(pos.x, pos.y, grid, inventory, commit);
            }
        }

        if (queuedMagic == null || magicDir == null) {
            return SpawnMagicResult.AlreadyCasting;
        }

        if (!inventory.hasMagic(queuedMagic)) {
            return SpawnMagicResult.NoMagic;
        }

        final MagicType equipped = queuedMagic;
        final IAction commitMagic = new IAction() {
            @Override
            public void eval() {
                inventory.spendMagic(equipped);
                grid.addTransform(new IAction() {
                    @Override
                    public void eval() {
                        inventory.addMagic(equipped);
                    }
                });
            }
        };
        final Point2i start = getIndex();
        if (equipped == MagicType.Earth) {
            final boolean success = grid.tryApplyEarth(start.add(magicDir));
            if (success) {
                commitMagic.eval();
                return SpawnMagicResult.Success;
            } else {
                return SpawnMagicResult.CantCastHere;
            }
        } else {
            Magic magic;
            if (equipped == MagicType.Fire) {
                magic = new FireMagic(grid, offset);
            } else if (equipped == MagicType.Ice) {
                magic = new IceMagic(grid, offset);
            } else { //if (equipped.isWind())
                magic = new WindMagic(grid, offset);
            }
            final Tile forwardTile = grid.getTile(start.add(magicDir));
            if (!magic.canPass(forwardTile)) {
                return SpawnMagicResult.CantCastHere;
            } else {
                castMagic(magic, start.add(magicDir), magicDir, 1);
                commitMagic.eval();
                return SpawnMagicResult.Success;
            }
        }
    }

    @Override
    public void commitMagic(MagicType type, Point2i dir) {
        queuedMagic = type;
        magicDir = dir;
        magicMenu.deactivate();
    }

    public void processTryCastMagic(SpawnMagicResult didMagic) {
        if (didMagic == SpawnMagicResult.Success) {
            resetMagic();
        } else if (didMagic == SpawnMagicResult.CantCastHere) {
            resetMagic();
            Game.assets.MagicemptySound.play();
            ParticleManager.spawnCantCastHere(getMagicMenuPosition());
        } else if (didMagic == SpawnMagicResult.NoMagic) {
            Game.assets.MagicemptySound.play();
            ParticleManager.spawnNoMagic(getMagicMenuPosition());
        }
    }
}
