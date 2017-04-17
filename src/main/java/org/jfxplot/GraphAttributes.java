/*
 * Copyright (C) 2016 Bruce Johnson
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jfxplot;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 *
 * @author brucejohnson
 */
public class GraphAttributes {

    Color[] stroke = {Color.BLACK};
    Color[] fill = {Color.BLACK};
    double lineWidth = 1;
    boolean showLines = true;
    boolean showSymbols = true;
    boolean showRange = false;
    Paint rangeStroke = Color.BLACK;
    double rangeWidth = 1;

    public GraphAttributes() {

    }

    public GraphAttributes(Color[] stroke, Color[] fill, double lineWidth, boolean showLines, boolean showSymbols) {
        this.stroke = stroke;
        this.fill = fill;
        this.lineWidth = lineWidth;
        this.showLines = showLines;
        this.showSymbols = showSymbols;
    }

    public String toString() {
        return String.format("%s %s %3.1f %b %b %b", stroke.toString(), fill.toString(), lineWidth, showLines, showSymbols, showRange);
    }

}
