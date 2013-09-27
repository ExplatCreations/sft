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

/**
 * @author moopslc
 */
public class TileUtils {
    public static Tile makeTileFromRaw(int type, boolean isDark) {
        if (TileCodes.isButton(type)) {
            return new LeverTile();
        } else if (TileCodes.isTrapdoor(type)) {
            return new TrapdoorTile(TileCodes.isTrapdoorBlack(type), TileCodes.isTrapdoorOpen(type));
        } else if (type == TileCodes.Floor) {
            return new FloorTile();
        } else if (TileCodes.isTeleporter(type)) {
            return new TeleporterTile(TileCodes.codeToTeleportColor(type));
        } else if (type == TileCodes.WallBreakable) {
            return new BreakableWallTile(isDark);
        } else if (type == TileCodes.WallSolid) {
            return new WallTile(isDark);
        } else if (type == TileCodes.FlipBlue
                || type == TileCodes.FlipYellow) {
            return new FlipTile(type == TileCodes.FlipYellow, false);
        } else if (type == TileCodes.FlipBlueCracked
                || type == TileCodes.FlipYellowCracked) {
            return new CrackedFlipTile(type == TileCodes.FlipYellowCracked);
        } else if (type == TileCodes.IceFloor) {
            return new IceFloorTile();
        } else if (type == TileCodes.Water) {
            return new WaterTile();
        } else if (TileCodes.isDoor(type)) {
            return new DoorTile(TileCodes.codeToDoorColor(type));
        } else if (type == TileCodes.IceFloorCracked) {
            return new CrackedIceTile();
        } else if (TileCodes.isKey(type)) {
            return new KeyTile(TileCodes.codeToKeyColor(type));
        } else if (TileCodes.isMagic(type)) {
            return new MagicTile(TileCodes.codeToMagicType(type));
        } else if (type == TileCodes.IceBlock) {
            return new IceBlockTile();
        } else if (type == TileCodes.Pit) {
            return new PitTile();
        } else if (type == TileCodes.FlipYellowPit
                || type == TileCodes.FlipBluePit) {
            return new BrokenCrackedFlipTile(type == TileCodes.FlipYellowPit, true);
        } else {
            return new NullTile();
        }
    }
}
