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

package com.explatcreations.sft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.explatcreations.sft.data.LevelIndex;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.loading.ITiledMap;
import com.explatcreations.sft.loading.LevelData;
import com.explatcreations.sft.loading.MapLoader;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author deweyvm
 */
public class World {
    public static final int LevelsPerWorld = 10;
    public static final int NumWorlds = 7;
    public static final int NumLevels = LevelsPerWorld*NumWorlds;

    private List<LevelData> userLevels;
    private Map<LevelIndex, LevelData> levels = new HashMap<LevelIndex, LevelData>();
    public World() {
        for (LevelIndex index : LevelIndex.getAll()) {
            final LevelData data = loadLevel(index);
            if (data != null) {
                levels.put(index, data);
            }
        }
        Game.globals.LastDemoStageIndex = levels.keySet().size();
    }

    private LevelData loadLevel(LevelIndex level) {
        String id = String.format("%02d", (Integer.parseInt(level.stageName()) - 1));
        final String filename = "maps/" + id + ".tmx";
        return fromFile(filename);
    }

    public LevelData getLevel(LevelIndex index) {
        return levels.get(index);
    }

    //every 3 seconds check user maps for updates
    private List<String> getUserMaps() {
        final File directory = new File(System.getProperty("user.home") + "/SniffysFlipTrip");
        final List<String> result = new ArrayList<String>();
        final File[] matches = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".tmx");
            }
        });
        for (File file:matches) {
            result.add(file.getName());
        }

        return result;


    }

    private LevelData fromFile(String filename) {
        final FileHandle file = Gdx.files.internal(filename);
        if (!file.exists()) {
            return null;
        }
        final ITiledMap map = MapLoader.fromFile(file);
        final int[][] tiles = map.getTiles();
        final int cols = map.getCols();
        final int rows = map.getRows();
        final int goldSteps = Integer.parseInt(map.getProperty("gold"));
        final Point2i start = Point2i.fromString(map.getProperty("start"));
        final String name = map.getProperty("name");
        final String uuid = name + start.toString();
        return new LevelData(tiles, cols, rows, goldSteps, start, name, uuid);
    }


}
