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

package com.explatcreations.sft.data;

/**
 * @author deweyvm
 */
public class Point2i {
    public static final Point2i Zero = new Point2i(0,0);
    public static final String UpString = "Up";
    public static final String LeftString = "Left";
    public static final String RightString = "Right";
    public static final String DownString = "Down";
    public static final Point2i Up = new Point2i(0, -1);
    public static final Point2i Down = new Point2i(0, 1);
    public static final Point2i Left = new Point2i( -1, 0);
    public static final Point2i Right = new Point2i(1, 0);
    public final int x;
    public final int y;
    public Point2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point2i add(int i, int j) {
        return new Point2i(x + i, y + j);
    }

    public Point2i add(Point2i other) {
        return new Point2i(x + other.x, y + other.y);
    }

    public Point2i sub(int i, int j) {
        return new Point2i(x - i, y - j);
    }

    public Point2i sub(Point2i other) {
        return new Point2i(x - other.x, y - other.y);
    }

    public Point2i scale(int factor) {
        return new Point2i(x*factor, y*factor);
    }

    public Point2i scale(float factor) {
        return new Point2i((int)(x*factor), (int)(y*factor));
    }

    public Point2i setY(int newY) {
        return new Point2i(x, newY);
    }

    public Point2i setX(int newX) {
        return new Point2i(newX, y);
    }

    public float magnitude() {
        return toPoint2f().magnitude();
    }

    public Point2i neg() {
        return new Point2i(-x, -y);
    }

    public boolean isUp() {
        return y < 0;
    }

    public boolean isDown() {
        return y > 0;
    }

    public boolean isRight() {
        return x > 0;
    }

    public boolean isLeft() {
        return x < 0;
    }

    public static Point2i fromString(String s) {
        final String[] parts = s.split("\\s*,\\s*");
        final int x = Integer.parseInt(parts[0]);
        final int y = Integer.parseInt(parts[1]);
        return new Point2i(x, y);
    }

    public String getDirection(String elseDo) {
        if (isRight()) {
            return RightString;
        } else if (isLeft()) {
            return LeftString;
        } else if (isUp()) {
            return UpString;
        } else if (isDown()) {
            return DownString;
        }
        return elseDo;
    }

    public Point2f toPoint2f() {
        return new Point2f(x, y);
    }

    public String getDirection() {
        return getDirection(Point2i.UpString);
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Point2i)) {
            return false;
        }
        final Point2i p = (Point2i)other;
        return p.x == x && p.y == y;

    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }

}
