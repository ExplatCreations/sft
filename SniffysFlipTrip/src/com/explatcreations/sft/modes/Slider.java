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

package com.explatcreations.sft.modes;

import com.badlogic.gdx.math.MathUtils;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.enums.MDisplayMode;
import com.explatcreations.sft.functions.IAction1;
import com.explatcreations.sft.functions.IFunction2;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.graphics.TextSprite;
import com.explatcreations.sft.gui.ISelectable;
import com.explatcreations.sft.input.Controls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author deweyvm
 */
public class Slider<T> implements ISelectable {

    private final List<T> values;

    private final TextSprite textSprite;
    private final TextSprite valueSprite;
    private final SpriteBase upSprite;
    private final SpriteBase downSprite;
    private final SpriteBase selectRect;
    private final IAction1<T> setValue;
    private final IFunction2<T, String> makeString;
    private final boolean looped;

    private int index;
    private int maxIndex;
    public Slider(int x,
                  int y,
                  String label,
                  List<T> values,
                  IAction1<T> setValue,
                  IFunction2<T, String> makeString,
                  int initialIndex,
                  boolean looped,
                  int width) {
        this.values = values;
        this.makeString = makeString;
        this.textSprite = new TextSprite(x, y, label);

        textSprite.x -= textSprite.getWidth();
        this.selectRect = textSprite.makeHighlight();

        this.index = initialIndex;
        this.valueSprite = new TextSprite(x + 29, y, getValueString());
        this.downSprite = new TextSprite(x + 6, y, "<");

        this.upSprite = new TextSprite(width + valueSprite.x + 5, y, ">");
        this.setValue = setValue;
        this.maxIndex = values.size();
        this.looped = looped;
    }

    @Override
    public void updateSelected() {
        selectRect.update();
        if (Controls.Right.justPressed() && (index < maxIndex - 1 || looped)) {
            changeIndex(1);
        }
        if (Controls.Left.justPressed() && (index > 0 || looped)) {
            changeIndex(-1);
        }
    }

    protected int getIndex() {
        return index;
    }

    private void changeIndex(int amount) {
        int prevIndex = index;
        if (looped) {
            index = (index + amount + maxIndex) % maxIndex;
        } else {
            index = MathUtils.clamp(index + amount, 0, maxIndex);
        }
        if (prevIndex != index) {
            changeValue();
        }
    }

    private void changeValue() {
        setValue.eval(values.get(index));
        valueSprite.setText(getValueString());
    }

    private String getValueString() {
        return makeString.apply(values.get(index));
    }

    @Override
    public void draw(Point2i offset, boolean isSelected) {
        if (isSelected) {
            selectRect.drawWithOffset(offset);
            if (index > 0 || looped) {
                downSprite.drawWithOffset(offset);
            }
            if (index < maxIndex - 1 || looped) {
                upSprite.drawWithOffset(offset);
            }
        }
        textSprite.drawWithOffset(offset);
        valueSprite.x = (upSprite.x - downSprite.x)/2 + downSprite.x - valueSprite.getWidth()/2;
        valueSprite.drawWithOffset(offset);
    }

    public static List<MDisplayMode.DisplayType> getVideoOptions() {
        return new ArrayList<MDisplayMode.DisplayType>(Arrays.asList(MDisplayMode.DisplayType.values()));
    }

    public static List<Integer> getVolumeOptions() {
        final List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i <= 100; i += 1) {
            if (i <= 5 || i % 5 == 0) {
                result.add(i);
            }

        }
        return result;
    }

    public static <T> int getIndex(List<T> list, T value, int defaultIndex) {
        int index = 0;
        for (T t : list) {
            if (t.equals(value)) {
                return index;
            }
            index += 1;
        }
        return defaultIndex;
    }

    public static int getIndex(List<Integer> list, Integer value) {
        int index = 0;
        int closestDiff = Integer.MAX_VALUE;
        int closestIndex = 0;
        for (Integer t : list) {
            int diff = Math.abs(t - value);
            if (diff < closestDiff) {
                closestIndex = index;
                closestDiff = diff;
            }
            if (t.equals(value)) {
                return index;
            }
            index += 1;
        }
        return closestIndex;
    }
}
