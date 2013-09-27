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

package com.explatcreations.sft.tiles;

import com.explatcreations.sft.enums.*;

/**
 * @author deweyvm
 */
public class TileCodes {
    public static final int Floor = 34;

    public static final int FlipBlue = 2;
    public static final int FlipYellow = 18;

    public static final int FlipBlueCracked = 3;
    public static final int FlipBluePit = 4;
    public static final int FlipYellowCracked = 19;
    public static final int FlipYellowPit = 20;

    public static final int ButtonWhite = 5;
    public static final int ButtonBlack = 21;

    public static final int TrapdoorWhiteClosed = 6;
    public static final int TrapdoorWhiteOpen = 7;

    public static final int TrapdoorBlackClosed = 22;
    public static final int TrapdoorBlackOpen = 23;

    public static final int IceBlock = 49;
    public static final int IceFloor = 50;
    public static final int IceFloorCracked = 51;
    public static final int Water = 52;

    public static final int WallSolid = 33;

    public static final int WallBreakable = 32;
    public static final int Pit = 36;

    public static final int PushBlock = 12;
    public static final int BallNormal = 13;
    public static final int BallPaint = 14;

    public static final int DoorRed = 8;
    public static final int DoorYellow = 9;
    public static final int DoorGreen = 10;
    public static final int DoorBlue = 11;

    public static final int KeyRed = 40;
    public static final int KeyYellow = 41;
    public static final int KeyGreen = 42;
    public static final int KeyBlue = 43;

    public static final int TeleporterRed = 24;
    public static final int TeleporterYellow = 25;
    public static final int TeleporterGreen = 26;
    public static final int TeleporterBlue = 27;

    public static final int MagicFire = 56;
    public static final int MagicEarth = 57;
    public static final int MagicWind = 58;
    public static final int MagicIce = 59;

    public static boolean isButton(int tile) {
        return tile == TileCodes.ButtonWhite
            || tile == TileCodes.ButtonBlack;
    }

    public static boolean isTrapdoor(int tile) {
        return tile == TileCodes.TrapdoorBlackClosed
            || tile == TileCodes.TrapdoorBlackOpen
            || tile == TileCodes.TrapdoorWhiteClosed
            || tile == TileCodes.TrapdoorWhiteOpen;
    }

    public static boolean isTrapdoorBlack(int tile) {
        return tile == TileCodes.TrapdoorBlackClosed
            || tile == TileCodes.TrapdoorBlackOpen;
    }

    public static boolean isTrapdoorOpen(int tile) {
        return tile == TileCodes.TrapdoorWhiteOpen
            || tile == TileCodes.TrapdoorBlackOpen;
    }

    public static boolean isDoor(int tile) {
        return tile == TileCodes.DoorRed
            || tile == TileCodes.DoorYellow
            || tile == TileCodes.DoorGreen
            || tile == TileCodes.DoorBlue;
    }

    public static DoorColor codeToDoorColor(int tile) {
        if (tile == TileCodes.DoorRed) {
            return DoorColor.Red;
        } else if (tile == TileCodes.DoorYellow) {
            return DoorColor.Yellow;
        } else if (tile == TileCodes.DoorGreen) {
            return DoorColor.Green;
        } else {
            return DoorColor.Blue;
        }
    }

    public static boolean isKey(int tile) {
        return tile == TileCodes.KeyRed
            || tile == TileCodes.KeyGreen
            || tile == TileCodes.KeyYellow
            || tile == TileCodes.KeyBlue;
    }

    public static KeyColor codeToKeyColor(int tile) {
        if (tile == TileCodes.KeyRed) {
            return KeyColor.Red;
        } else if (tile == TileCodes.KeyYellow) {
            return KeyColor.Yellow;
        } else if (tile == TileCodes.KeyGreen) {
            return KeyColor.Green;
        } else {
            return KeyColor.Blue;
        }
    }


    public static boolean isMagic(int tile) {
        return tile == TileCodes.MagicFire
            || tile == TileCodes.MagicEarth
            || tile == TileCodes.MagicWind
            || tile == TileCodes.MagicIce;
    }

    public static MagicType codeToMagicType(int tile) {
        if (tile == TileCodes.MagicFire) {
            return MagicType.Fire;
        } else if (tile == TileCodes.MagicEarth) {
            return MagicType.Earth;
        } else if (tile == TileCodes.MagicWind) {
            return MagicType.Wind;
        } else {
            return MagicType.Ice;
        }
    }

    public static boolean isTeleporter(int tile) {
        return tile == TileCodes.TeleporterRed
            || tile == TileCodes.TeleporterGreen
            || tile == TileCodes.TeleporterYellow
            || tile == TileCodes.TeleporterBlue;
    }

    public static TeleporterColor codeToTeleportColor(int tile) {
        if (tile == TileCodes.TeleporterRed) {
            return TeleporterColor.Red;
        } else if (tile == TileCodes.TeleporterYellow) {
            return TeleporterColor.Yellow;
        } else if (tile == TileCodes.TeleporterGreen) {
            return TeleporterColor.Green;
        } else {
            return TeleporterColor.Blue;
        }
    }

    public static boolean isPushable(int tile) {
        return tile == TileCodes.BallNormal
            || tile == TileCodes.BallPaint
            || tile == TileCodes.PushBlock;
    }

    public static BallType codeToBallType(int tile) {
        if (tile == TileCodes.BallNormal) {
            return BallType.NormalBall;
        } else if (tile == TileCodes.BallPaint) {
            return BallType.PaintBall;
        } else {
            return BallType.Block;
        }
    }
}
