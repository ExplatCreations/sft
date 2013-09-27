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

import com.explatcreations.sft.hardware.HardwareInfo;

import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author moopslc
 */
public class Logger {
    public static void attachCrashLogger() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread _, Throwable e) {
                try {
                    final String dateString = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
                    final String filename = String.format("crash_log_%s.log", dateString);

                    System.err.printf("The application has crashed. Please consult %s for additional information.\n", filename);
                    final File file = new File(filename);
                    file.createNewFile();
                    final PrintStream stream = new PrintStream(file);
                    e.printStackTrace(stream);

                    stream.append(new HardwareInfo().toString());
                    stream.close();
                } catch (Throwable throwable) {
                    System.err.println("Failed to create log file. Printing exception:");
                    e.printStackTrace();
                    throwable.printStackTrace();
                }
            }
        });
    }
}
