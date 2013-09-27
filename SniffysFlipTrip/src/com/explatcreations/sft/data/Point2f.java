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
public class Point2f {
    public final static Point2f Zero = new Point2f(0,0);
    public final float x;
    public final float y;
    public Point2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point2f scale(float factor) {
        return new Point2f(x * factor, y * factor);
    }

    public float magnitude() {
        return (float)Math.sqrt(x * x + y * y);
    }

    public float magSquared() {
        return x * x + y * y;
    }

    public Point2f normalize() {
        final float mag = magnitude();
        if (mag == 0) {
            return new Point2f(1, 0);
        }
        return new Point2f(x / mag, y / mag);
    }

    public Point2f rotate(float angleRadians) {
        final double xp = x * Math.cos(angleRadians) - y * Math.sin(angleRadians);
        final double yp = x * Math.sin(angleRadians) + y * Math.cos(angleRadians);
        return new Point2f((float)xp, (float)yp);
    }

    public Point2i toPoint2i() {
        return new Point2i((int)x, (int)y);
    }

    public Point2f add(Point2f other) {
        return new Point2f(x + other.x, y + other.y);
    }

    public Point2f bound(float max) {
        final float mag = magnitude();
        if (max > mag) {
            return normalize().scale(mag);
        }
        return normalize().scale(max);
    }

    public Point2f sub(Point2f other) {
        return new Point2f(x - other.x, y - other.y);
    }

    public Point2f neg() {
        return new Point2f( -x, -y);
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
