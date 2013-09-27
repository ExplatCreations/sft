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
import com.explatcreations.sft.enums.BallType;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.grid.IGrid;
import com.explatcreations.sft.tiles.TeleporterTile;
import com.explatcreations.sft.tiles.Tile;

/**
 * @author deweyvm
 */
public class Ball extends GridEntity {
    private static final Texture pushBlockClass = Game.assets.PushBlockGraphic;
    private static final Texture ballClass = Game.assets.BallGraphic;
    private static final Texture paintBallClass = Game.assets.PaintBallGraphic;

    private static final String StopName = "stop";

    public static final int MoveTime = 8;

    private final Timer moveTimer = new Timer(MoveTime);
    private Point2i force;
    private Point2i lastRestPosition;
    private IGrid grid;
    private boolean isToggler;
    private boolean isSlider;
    private BallType type;
    private int id;
    private boolean playing = false;
    private static int total;

    private final Point2i outOfBoundsIndex = new Point2i( -10, -10);

    public static AnimatedSprite makeSprite(BallType type) {
        if (type == BallType.Block) {
            final AnimatedSprite result = Assets.makeTileSprite(pushBlockClass);
            result.addAnimation(StopName, 1, new int[]{0}, false);
            result.play(StopName);
            return result;
        } else if (type == BallType.NormalBall) {
            return makeBallSprite(ballClass);
        } else {//if (type.isPaintBall())
            return makeBallSprite(paintBallClass);
        }
    }

    private static AnimatedSprite makeBallSprite(Texture spriteClass) {
        final AnimatedSprite sprite = Assets.makeTileSprite(spriteClass);
        sprite.addAnimation(StopName, 18, new int[]{0}, false);
        final int speed = 10;
        sprite.addAnimation(getMoveName(Point2i.Right), speed, new int[]{0, 1, 2, 3}, true);
        sprite.addAnimation(getMoveName(Point2i.Left), speed, new int[]{0, 3, 2, 1}, true);
        sprite.addAnimation(getMoveName(Point2i.Down), speed, new int[]{4, 5, 6, 7}, true);
        sprite.addAnimation(getMoveName(Point2i.Up), speed, new int[]{4, 7, 6, 5}, true);
        return sprite;
    }

    private static String getMoveName(Point2i dir) {
        return "move" + dir.getDirection();
    }

    public Ball(IGrid grid, BallType type, Point2i pos, Point2i offset) {
        super(offset, makeSprite(type));

        setIndex(pos);
        this.type = type;
        this.grid = grid;
        this.isToggler = type == BallType.PaintBall;
        this.isSlider = !(type == BallType.Block);
        this.id = total;
        total += 1;
    }

    public BallType getType() {
        return type;
    }

    public boolean isOutOfBounds() {
        return getIndex().equals(outOfBoundsIndex);
    }

    private void goOutOfBounds() {
        setIndex(outOfBoundsIndex);
    }

    public void applyForce(Point2i force) {
        applyForce(force, false);
    }

    public void applyForce(final Point2i force, boolean overrideSlide) {
        if (overrideSlide) {
            isSlider = true;
        }
        this.force = force;
        this.lastRestPosition = getIndex();
        final Point2i p = getIndex();
        grid.addTransform(new IAction() {
            @Override
            public void eval() {
                revert(p);
                if (force != null && !(type == BallType.Block)) {
                    stopAnimation();
                }
            }
        });
        Game.assets.RollSound.play();
    }

    private void stopAnimation() {
        sprite.play(StopName);
        playing = false;
    }

    public void revert(Point2i p) {
        setIndex(p);
        moveTimer.reset();
        force = null;
    }

    @Override
    public void update() {
        super.update();
        if (force != null) {
            if (moveTimer.isMax()) {
                grid.allowCommit();
                Game.assets.RollSound.play();
                addIndex(force);
                moveTimer.reset();
                final Tile newTile = grid.getTile(getIndex());
                if (newTile instanceof TeleporterTile) {
                    final TeleporterTile teleporter = (TeleporterTile)newTile;
                    final Point2i telePos = grid.getPartner(getIndex(), teleporter);
                    if (telePos != null) {
                        setIndex(telePos);
                        Game.assets.TeleportSound.play();
                    } else {
                        Game.assets.TelefailSound.play();
                    }
                }

                final Tile forwardTile = grid.getTile(getIndex().add(force));
                if (newTile.isHole()) {
                    doFallIn(newTile);
                    return;
                } else if (   !forwardTile.isPassable()
                           || grid.hasBall(getIndex().add(force))
                           || !isSlider) {
                    force = null;
                    Game.assets.BallhitSound.play();
                    isSlider = !(type == BallType.Block);
                }

                if (isToggler) {
                    grid.checkerBallAffectTile(getIndex());
                }
                if (force == null) {
                    stopAnimation();
                }
            } else {
                if (type == BallType.NormalBall || type == BallType.PaintBall) {
                    if (!playing) {
                        playing = true;
                        sprite.play(getMoveName(force));
                    }
                }
                final Point2i index = getIndex();
                setPosition((int)((index.x + moveTimer.getProgress() * force.x) * Tile.Size),
                            (int)((index.y + moveTimer.getProgress() * force.y) * Tile.Size));
                moveTimer.increment();
            }
        }
    }

    public void forceRest() {
        grid.addTransform(new IAction() {
            @Override
            public void eval() {
                revert(lastRestPosition);
            }
        });
        //maybe force commit too?
        lastRestPosition = getIndex();
    }

    public void doFallIn(Tile tile) {
        force = null;
        grid.fallIn(this, tile, this.type, getIndex());
        grid.addTransform(new IAction() {
            @Override
            public void eval() {
                revert(lastRestPosition);
            }
        });
        goOutOfBounds();
    }

    public boolean isMoving() {
        return force != null;
    }

    public void forceDraw(int i, int j, Point2i offset) {
        sprite.offset = offset;
        sprite.x = i * Tile.Size;
        sprite.y = j * Tile.Size;
        sprite.play(StopName);
        sprite.draw();
    }

    public AnimatedSprite getSprite() {
        return sprite;
    }
}
