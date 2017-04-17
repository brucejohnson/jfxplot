/*
 * Copyright (C) 2017 Bruce Johnson
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
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;

/**
 *
 * @author Bruce Johnson
 */
public class RGraphicsContext {

    /* pen colour (lines, text, borders, ...) original int*/
    Color stroke;

    /* fill colour (for polygons, circles, rects, ...) original int*/
    Color fill;

    /* Gamma correction */
    double gamma;

    /*
     * Line characteristics
     */

 /* Line width (roughly number of pixels) */
    double lwd;
    /* Line type (solid, dashed, dotted, ...) */
    int lty;

    /* line join original enum*/
    StrokeLineJoin lineJoin = StrokeLineJoin.MITER;

    /* Line end original enum*/
    StrokeLineCap lineCap = StrokeLineCap.BUTT;

    /* line mitre */
    double lmitre;

    Font font = Font.getDefault();
    /*
     * Text characteristics
     */

 /* Character expansion (font size = fontsize*cex) */
    double cex;

    /* Font size in points */
    double ps;

    /* Line height (multiply by font size) */
    double lineheight;


    /* Font face (plain, italic, bold, ...) */
    int fontface;

    /* Font family  original char array*/
    String fontFamily;

    public Color getFill() {
        return fill;
    }

    public Color getStroke() {
        return stroke;
    }

    public Font getFont() {
        return font;
    }

}
