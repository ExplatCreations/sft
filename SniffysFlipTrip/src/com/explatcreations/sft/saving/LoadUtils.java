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

package com.explatcreations.sft.saving;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.explatcreations.sft.Debug;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.functions.IAction1;
import com.explatcreations.sft.functions.IFunction1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Scanner;

/**
 * @author moopslc
 */
public class LoadUtils {

    private static String getJarPath() {
        if (Game.globals.IsDebugMode) {
            return ".";
        }


        String path = LoadUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (!new File(path).isDirectory()) {
            path = System.getProperty("user.dir");
        }

        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee);
        }
        return path;
    }

    public static final String Directory = getJarPath();
    public static final Json json = new Json();
    public static String loadFile(String path) throws FileNotFoundException {
        final File file = new File(path);
        final Scanner scanner = new Scanner(file);
        scanner.useDelimiter("\\Z");
        return scanner.next();
    }

    public static <T> T load(Class cl, String dir, String name, IFunction1<T> makeNew, IAction1<T> verify) {
        T result;
        try {
            ensureDirectory(dir);
            final String path = dir + "/" + name;
            result = (T)json.fromJson(cl, loadFile(path));
        } catch (Exception e) {
            Debug.warning(e.getMessage());
            result = makeNew.apply();
            flush(result, dir, name);
        }
        verify.eval(result);
        return result;
    }

    public static <T> void flush(T thing, String dir, String name) {
        try {
            final String path = dir + "/" + name;
            final File file = new File(path);
            if (!file.exists() && !file.createNewFile()) {
                throw new RuntimeException(String.format("Failed to create file '%s' because <unknown>.", path));
            }
            final PrintWriter writer = new PrintWriter(path);
            json.setOutputType(JsonWriter.OutputType.minimal);
            writer.print(json.prettyPrint(thing));
            writer.close();
        } catch (Exception e) {
            Debug.warning(e.getMessage());
            //???
        }
    }

    public static void ensureDirectory(String path) {
        final File file = new File(path);
        if (file.exists() && !file.isDirectory()) {
            throw new RuntimeException(String.format("Expected file '%s' to be a directory. If it is safe to do so, rename that file or delete it and try again.", path));
        } else if (!file.exists() && !file.mkdir()) {
            throw new RuntimeException(String.format("Failed to create directory '%s' because <unknown>.", path));
        }
    }


}
