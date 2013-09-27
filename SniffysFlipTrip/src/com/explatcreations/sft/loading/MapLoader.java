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

package com.explatcreations.sft.loading;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author deweyvm
 */
public class MapLoader {

    private static int[][] parseCsv(String csv) {
        final String[] rowStrings = csv.split("\\r\\n|\\r|\\n");
        final int rows = rowStrings.length;
        final int cols = rowStrings[0].split(",").length;//fixme -- wont work if there is only one row
        final int[][] result = new int[cols][rows];
        for (int i = 0; i < rows; i += 1) {
            String row = rowStrings[i].replace(",[ ]*$", "");
            String[] colStrings = row.split(",[ ]*");
            for (int j = 0; j < cols; j += 1) {
                result[j][i] = Integer.parseInt(colStrings[j].replace("\\r|\\r\\n|\\n", "")) - 1;
            }

        }
        return result;
    }

    public static ITiledMap fromFile(FileHandle file) {
        final XmlReader xml = new XmlReader();
        try {
            final XmlReader.Element root = xml.parse(file);
            final Map<String, String> propertiesMap = new HashMap<String, String>();
            final XmlReader.Element properties = root.getChildByName("properties");
            for(int i = 0; i < properties.getChildCount(); i += 1) {
                XmlReader.Element prop = properties.getChild(i);
                final String name = prop.getAttribute("name");
                final String value = prop.getAttribute("value");
                propertiesMap.put(name, value);
            }
            final XmlReader.Element layers = root.getChildByName("layer");
            final String csv = layers.getChild(0).getText();
            final int[][] tiles = parseCsv(csv);
            return new ITiledMap() {
                @Override
                public String getProperty(String key) {
                    return propertiesMap.get(key);
                }

                @Override
                public int[][] getTiles() {
                    return tiles;  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public int getCols() {
                    return tiles.length;
                }

                @Override
                public int getRows() {
                    return tiles[0].length;
                }
            };
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
