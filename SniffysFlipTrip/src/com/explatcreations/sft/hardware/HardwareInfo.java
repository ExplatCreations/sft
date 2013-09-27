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

package com.explatcreations.sft.hardware;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

import java.util.Scanner;

/**
 * @author moopslc
 */
public class HardwareInfo {
    public enum OS {
        Mac,
        Linux,
        Windows
    }

    private final OS os;

    public HardwareInfo() {
        this.os = getOS();
    }

    public static HardwareInfo.OS getOS() {
        final String OSName = System.getProperty("os.name").toLowerCase();
        if (OSName.contains("win")) {
            return HardwareInfo.OS.Windows;
        } else if (OSName.contains("mac")) {
            return HardwareInfo.OS.Mac;
        } else {
            return HardwareInfo.OS.Linux;
        }
    }

    @Override
    public String toString() {
        final String numProcessors = getNumProcessors();
        final String processorName = getProcessorName();
        //final String memory = getMemory(); // cant figure out how to do this on windows q-q
        final String os = System.getProperty("os.name");
        final String gpu = getGpuInfo();
        final String linesep = System.getProperty("line.separator");
        return "---- System information ----" + linesep +
               "Operating system     : " + os + linesep +
               "Number of processors : " + numProcessors + linesep +
               "Processor name       : " + processorName + linesep +
               "Graphics device      : " + gpu;
    }

    private String getGpuInfo() {
        final String gpu = Gdx.gl.glGetString(GL10.GL_RENDERER);
        final String glVersion = Gdx.gl.glGetString(GL10.GL_VERSION);
        return gpu + ", GL version " + glVersion;
    }

    private String getNumProcessors() {
        if (os == OS.Windows) {
            return getWindowsNumProcessors();
        } else {
            return getLinuxNumProcessors();
        }
    }

    private String getProcessorName() {
        if (os == OS.Windows) {
            return getWindowsProcessorName();
        } else {
            return getLinuxProcessorName();
        }
    }

    private String getLinuxNumProcessors() {
        return getOutput("/bin/bash", "-c", "cat /proc/cpuinfo | grep 'processor' | wc -l");
    }

    private String getWindowsNumProcessors() {
        return System.getenv("NUMBER_OF_PROCESSORS");
    }

    private String getLinuxProcessorName() {
        return getOutput("/bin/bash", "-c", "cat /proc/cpuinfo | grep 'model name' | sed 's/model name\\s*:[ ]*//g' | head -n 1");
    }

    private String getWindowsProcessorName() {
        return System.getenv("PROCESSOR_IDENTIFIER");
    }

    public static String getOutput(String... command) {
        try {
            final ProcessBuilder pb = new ProcessBuilder(command);
            final Process p = pb.start();
            p.waitFor();
            final Scanner br = new Scanner(p.getInputStream());
            String sum = "";
            String line;
            try {
                while(br.hasNextLine() && (line = br.nextLine()) != null){
                    sum += line + "\n";
                }
            } catch (Throwable nse) {

            }
            return sum;
        } catch(Throwable t) {
            t.printStackTrace();
            return "Failure ";
        }
    }




}
