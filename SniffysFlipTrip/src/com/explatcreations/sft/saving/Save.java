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

import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.LevelIndex;
import com.explatcreations.sft.data.LevelState;
import com.explatcreations.sft.enums.Ending;
import com.explatcreations.sft.gui.NotesPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author deweyvm
 */
public class Save {
    private static final String filename = "save.json";
    private static final String directory = LoadUtils.Directory;

    private static final int NumLevelsToUnlock = 3;
    private static final int[] noteStages = new int[]{0,2,5,8,13,16,19,21,32,36,39,43};
    private static final int[] unskippableStages = new int[]{0,2,5,8,13,16,19,21,25,28,32,36,39,43};

    private RawSave raw;

    private Save(RawSave raw) {
        this.raw = raw;
    }

    public static Save load() {
        final RawSave raw = LoadUtils.load(RawSave.class, directory, filename, RawSave.factory, RawSave.verifier);
        return new Save(raw);
    }

    public List<Integer> getUnlockedNotes() {
        if (Game.globals.DebugNotesPanel) {
            final List<Integer> result = new ArrayList<Integer>();
            for (int i = 0; i < NotesPanel.NumNotes + 1; i += 1) {
                result.add(i);
            }
            return result;
        }
        return new ArrayList<Integer>(raw.notesShown);
    }


    public void unlockAllStages() {
        for(LevelIndex index:LevelIndex.getAll()) {
            if (isLocked(index)) {
                setLevelState(index, LevelState.Unlocked);
            }
        }
        flush();
    }

    public boolean isLocked(LevelIndex index) {
        final LevelState state =getLevelState(index);
        return state.moreLockedThanOrEqual(LevelState.Locked);
    }

    public boolean isUnskippable(LevelIndex index) {
        return index.containedIn(unskippableStages);
    }

    public Ending getEnding(LevelIndex completedStage) {
        if (Game.globals.IsDemo && completedStage.isLastDemoStageOrGreater()) {
            return Ending.Demo;
        } else if (areAllLevelsGold() && !hasShownEnding(Ending.Good)) {
            return Ending.Good;
        } else if (areAllLevelsCompleted() && !hasShownEnding(Ending.Mid)) {
            return Ending.Mid;
        } else if (completedStage.isLast() && !hasShownEnding(Ending.Bad)) {
            return Ending.Bad;
        }
        return null;
    }

    public boolean hasShownEnding(Ending ending) {
        return Boolean.parseBoolean(raw.shownEndings.get(ending.id + ""));
    }

    public void setHasShownEnding(Ending ending, boolean value) {
        for (Ending e:Ending.values()) {
            if (e.id <= ending.id) {
                raw.shownEndings.put(ending.id + "", value + "");
            }
        }
        flush();
    }

    public int getNoteIndex() {
        final List<Integer> unlockedNotes = getUnlockedNotes();
        final int result = unlockedNotes.size() - 1 - 1/*skip credits*/;
        return result;
    }

    public boolean areAllLevelsGold() {
        return areAllLevelsStateOrMoreUnlocked(LevelState.Golden);
    }

    public boolean areAllLevelsCompleted() {
        return areAllLevelsStateOrMoreUnlocked(LevelState.Completed);
    }

    private boolean areAllLevelsStateOrMoreUnlocked(LevelState state) {
        for (LevelIndex index: LevelIndex.getAll()) {
            if (getLevelState(index).moreLockedThan(state)) {
                return false;
            }
        }
        return true;
    }

    public LevelState getLevelState(LevelIndex index) {
        return raw.levelInfos.get(index.getKey()).state;
    }

    public void setLevelState(LevelIndex index, LevelState state) {
        final LevelInfo info = raw.levelInfos.get(index.getKey());
        info.state = state;
        flush();
    }

    public int getRecordSteps(LevelIndex index) {
        return raw.levelInfos.get(index.getKey()).recordSteps;
    }

    private void setRecordSteps(LevelIndex index, int steps) {
        final LevelInfo info = raw.levelInfos.get(index.getKey());
        info.recordSteps = steps;
        flush();
    }

    public boolean hasNewNote(LevelIndex index) {
        final boolean isNoteStage = isNoteStage(index);
        return isNoteStage && !getNoteShownStatus(index);
    }

    public boolean isNoteStage(LevelIndex index) {
        return index.containedIn(noteStages);
    }

    private int getNoteIndexOfStage(LevelIndex index) {
        final ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i:noteStages) {
            list.add(i);
        }
        final int result = list.indexOf(index.getRawIndex());
        if (result == -1) {
            return -1;//not a note stage
        }
        return result;
    }

    public boolean getNoteShownStatus(LevelIndex index) {
        final int indexOfStage = getNoteIndexOfStage(index);
        return indexOfStage == -1;//what??
    }

    public void unlockNote(LevelIndex index) {
        final int indexOfStage = getNoteIndexOfStage(index);
        if(indexOfStage != -1 && !raw.notesShown.contains(indexOfStage)) {
            raw.notesShown.add(indexOfStage);
            RawSave.verifier.eval(raw);
            flush();
        }
    }

    public ArrayList<LevelIndex> completeLevel(LevelIndex levelIndex, boolean isGold, int numSteps) {
        final ArrayList<LevelIndex> result = new ArrayList<LevelIndex>();
        unlock(levelIndex, isGold ? LevelState.Golden : LevelState.Completed);
        trySetRecord(levelIndex, numSteps);
        LevelIndex index = levelIndex.successor();
        if (index == null) {
            return result;
        }
        for (int i = 0; i < NumLevelsToUnlock; i += 1) {
            if (isLocked(index)) {
                result.add(index);
            }

            unlock(index, LevelState.Unlocked);
            if (index.isLastDemoStageOrGreater()) {
                return result;
            }

            if (isUnskippable(index)) {
                return result;
            }
            index = index.successor();
            if (index == null) {
                return result;
            }
        }
        return result;
    }

    private void trySetRecord(LevelIndex index, int numSteps) {
        final int record = getRecordSteps(index);
        if (record == 0 || numSteps < record) {
            setRecordSteps(index, numSteps);
        }
    }

    public void unlock(LevelIndex index, LevelState state) {
        final LevelState current = getLevelState(index);
        if ((Game.globals.IsDemo && index.isGreaterThanLastDemoStage())) {
            return;
        }
        if (current.moreLockedThan(state)) {
            setLevelState(index, state);
        }
    }

    public void deleteAllSaveData() {
        this.raw = RawSave.factory.apply();
        flush();
    }

    public void flush() {
        LoadUtils.flush(raw, directory, filename);
    }
}
