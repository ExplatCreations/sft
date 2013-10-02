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

package com.explatcreations.sft.gui;

import com.badlogic.gdx.math.MathUtils;
import com.explatcreations.sft.Debug;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.gui.pages.*;
import com.explatcreations.sft.input.Controls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author deweyvm
 */
public class NotesPanel extends Panel {
    private static final NoteInfo[] noteInfos = new NoteInfo[]{
            new NoteInfo("Welcome", new HowToPlayPage()),
            new NoteInfo("Decay & Ruin", new CracksPage()),
            new NoteInfo("Doors", new DoorsPage()),
            new NoteInfo("Levers", new LeversPage()),
            new NoteInfo("Teleporters", new TeleportersPage()),
            new NoteInfo("Icy Height", new FrozenPage()),
            new NoteInfo("Blocks & Pits", new PushablesPage()),
            new NoteInfo("Earth", new EarthPage()),
            new NoteInfo("Fire", new FirePage()),
            new NoteInfo("Ice", new IcePage()),
            new NoteInfo("Wind", new WindPage()),
            new NoteInfo("Credits", new CreditsPage())
    };

    public static final int NumNotes = 12 - 1;//noteInfos.length // causes dependency spiral;

    private final ArrayList<ISelectable> tabs = new ArrayList<ISelectable>();
    private final List<Integer> unlockedNotes;
    private ClickyCursor cursor;
    private static final int xOffset = -50;
    private final Button backButton;

    public NotesPanel(int initialIndex, IAction unset) {
        super(unset);
        this.unlockedNotes = Game.save.getUnlockedNotes();
        initTabs();
        this.cursor = new ClickyCursor(MathUtils.clamp(initialIndex, 0, unlockedNotes.size() - 1), unlockedNotes.size() - 1);
        final IAction back = new IAction() {
            @Override
            public void eval() {
                cancel();
            }
        };
        this.backButton = new Button("Back", Game.RenderWidth - 150, Game.RenderHeight - 32, back, Controls.Cancel);
    }

    private void initTabs() {
        final List<Integer> toRemove = new ArrayList<Integer>();
        for (int i : unlockedNotes) {
            if (i > noteInfos.length - 1) {
                toRemove.add(i);
            }
        }
        for(Integer i : toRemove) {
            unlockedNotes.remove(i);
        }

        for (int i : unlockedNotes) {
            if (i > noteInfos.length - 1) {
                Debug.warning("note index " + i + " out of bounds");
                continue;
            }
            final int xStart = Game.RenderWidth/2 + 80;
            final int longWidth = 95 + 24;
            final int shortWidth = 48 + 8;
            final int ySpace = 20;

            //fixme -- killme pls
            int x;
            int y;
            int width;
            if (i <= 1) {
                x = xStart;
                y = (i + 1) * ySpace;
                width = longWidth;
            } else if (i == 2 || i == 3) {
                if (i == 2) {
                    x = xStart;
                    width = shortWidth;
                } else {
                    x = xStart + shortWidth - 8;
                    width = shortWidth + 8;
                }
                y = 3 * ySpace;
            } else if (i <= 6) {
                x = xStart;
                y = i * ySpace;
                width = longWidth;
            } else if (i == 7 || i == 8) {
                x = xStart + shortWidth * (i - 7);
                y = 7 * ySpace;
                width = shortWidth;
            } else if (i == 9 || i == 10) {
                x = xStart + shortWidth * (i - 9);
                y = 8 * ySpace;
                width = shortWidth;
            } else {
                x = xStart;
                y = (i - 2) * ySpace;
                width = longWidth;
            }
            final NoteTab tab = new NoteTab(noteInfos[i].getLabel(), x + 12, y + 18, width);
            if (unlockedNotes.indexOf(i) == -1) {
                continue;
            }
            tabs.add(tab);
        }
    }

    @Override
    protected int getXOffset() {
        return xOffset;
    }

    private IPage getPage() {
        return getPage(unlockedNotes.get(cursor.getValue()));
    }

    public static IPage getPage(int index) {
        return noteInfos[index].getPage();
    }

    @Override
    public int getWidth() {
        return 300;
    }

    @Override
    public int getHeight() {
        return 250;
    }


    @Override
    public void update() {
        super.update();
        backButton.updateSelected();
        getPage().update();
        if (!isNormal()) return;

        tabs.get(cursor.getValue()).updateSelected();


        if(Controls.Up.menuZip() || Controls.Left.menuZip()) {
            cursor.decrement();
        } else if (Controls.Down.menuZip() || Controls.Right.menuZip()) {
            cursor.increment();
        }
    }

    @Override
    protected void drawSelf(Point2i offset) {
        getPage().draw(offset.add(getFrameX(), getFrameY()));
        for (int i = 0; i < unlockedNotes.size(); i += 1) {
            ISelectable tab = tabs.get(i);
            tab.draw(offset, cursor.getValue() == i);
        }
        backButton.draw(offset);
    }

    public static ArrayList<Integer> getRandomPages() {
        final ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < noteInfos.length; i += 1) {
            result.add(i);
        }
        Collections.sort(result, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Math.random() > 0.5? -1 : 1;
            }
        });
        return result;
    }
}
