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

import com.explatcreations.sft.data.LevelIndex;
import com.explatcreations.sft.data.LevelState;
import com.explatcreations.sft.enums.Ending;
import com.explatcreations.sft.functions.IAction1;
import com.explatcreations.sft.functions.IFunction1;
import com.explatcreations.sft.gui.NotesPanel;

import java.util.*;

/**
 * @author moopslc
 */
public class RawSave {
    public HashMap<String, LevelInfo> levelInfos;
    public ArrayList<Integer> notesShown;
    public HashMap<String, String> shownEndings;


    private static RawSave makeNew() {
        final RawSave save = new RawSave();

        save.levelInfos = new HashMap<String, LevelInfo>();
        save.notesShown = new ArrayList<Integer>();
        for (LevelIndex index:LevelIndex.getAll()) {
            final String raw = index.getKey();
            final LevelInfo info = new LevelInfo();
            info.state = index.defaultSetting();
            info.recordSteps = 0;
            save.levelInfos.put(raw, info);
        }
        save.notesShown.add(0);
        save.notesShown.add(NotesPanel.NumNotes);
        save.shownEndings = new HashMap<String, String>();
        for (Ending ending : Ending.values()) {
            save.shownEndings.put(ending.id + "", "false");
        }
        return save;
    }

    private static void verify(RawSave raw) {
        Collections.sort(raw.notesShown);
    }

    public static final IFunction1<RawSave> factory = new IFunction1<RawSave>() {
        @Override
        public RawSave apply() {
            return makeNew();
        }
    };

    public static final IAction1<RawSave> verifier = new IAction1<RawSave>() {
        @Override
        public void eval(RawSave arg1) {
            verify(arg1);
        }
    };


}

class LevelInfo {
    public LevelState state;
    public int recordSteps;
}
