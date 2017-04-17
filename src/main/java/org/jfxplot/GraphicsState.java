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
import org.renjin.sexp.DoubleArrayVector;
import org.renjin.sexp.DoubleVector;
import org.renjin.sexp.IntArrayVector;
import org.renjin.sexp.IntVector;
import org.renjin.sexp.SEXP;
import org.renjin.sexp.StringArrayVector;
import org.renjin.sexp.StringVector;
import org.renjin.sexp.Vector;

/**
 *
 * @author Bruce Johnson
 */
public class GraphicsState {

    public static GraphicsState defaultState = new GraphicsState();

    /* **R ONLY** Background color */
    Color bgColor = Color.WHITE;
    /* Box type */
    char bty;
    /* Character expansion */
    double cex = 1.0;
    /* Plotting Color */
    Color color = Color.BLACK;
    /* Character/string rotation */
    double crt;
    /* **R ONLY** Foreground Color */
    Color fgColor;
    /* Text font */
    int font;
    /* Axis labelling */
 /* [0] = # ticks on x-axis */
 /* [1] = # ticks on y-axis */
 /* [2] = length of axis labels */
    int lab[] = new int[3];
    /* Label style (rotation) */
    int las;
    /* Line texture */
    int lineTexture = 1;
    /* Line width */
    double lineWidth = 1.0;
    /* **R ONLY** Line mitre limit */
    double lmitre;
    /* Plotting character */
    int plotChar = 1;
    /* X Axis annotation */
 /* [0] = coordinate of lower tick */
 /* [1] = coordinate of upper tick */
 /* [2] = num tick intervals */
 /* almost always used internally */
    double xaxp[] = new double[3];
    /* Log Axis for X */
    boolean xlog = false;
    /* Y Axis annotation */
    double yaxp[] = new double[3];
    /* Log Axis for Y */
    boolean ylog = false;

    /* Main title font */
    int fontmain;
    /* Xlab and ylab font */
    int fontlab;
    /* Subtitle font */
    int fontsub;
    /* Axis label fonts */
    int fontaxis;

    /* Main title color */
    Color colmain = Color.BLACK;
    /* Xlab and ylab color */
    Color collab = Color.BLACK;
    /* Subtitle color */
    Color colsub = Color.BLACK;
    /* Axis label color */
    Color colaxis = Color.BLACK;

    int numrows;
    int numcols;
    int currentFigure;
    int lastFigure;
    /* Plot type */
    char pty;

    private String colorToString(Color color) {
        int red = (int) Math.round(color.getRed() * 255);
        int blue = (int) Math.round(color.getBlue() * 255);
        int green = (int) Math.round(color.getGreen() * 255);
        int alpha = (int) Math.round(color.getOpacity() * 255);

        return String.format("#%2H%2H%2H%2H", red, green, blue, alpha).replace(' ', '0');
    }

    public SEXP getValue(String valueName) {
        SEXP value;
        switch (valueName) {
            case "col":
                value = new StringArrayVector(colorToString(color));
                break;
            case "colmain":
                value = StringVector.valueOf(colorToString(colmain));
                break;
            case "collab":
                value = StringVector.valueOf(colorToString(collab));
                break;
            case "colsub":
                value = StringVector.valueOf(colorToString(colsub));
                break;
            case "bg":
                value = StringVector.valueOf(colorToString(bgColor));
                break;
            case "fg":
                value = StringVector.valueOf(colorToString(fgColor));
                break;
            case "colaxis":
                value = StringVector.valueOf(colorToString(colaxis));
                break;
            case "lwd": {
                DoubleVector vec = new DoubleArrayVector(lineWidth);
                value = vec;
                break;
            }
            case "pch": {
                IntVector vec = new IntArrayVector(plotChar);
                value = vec;
                break;
            }
            case "lty": { // should be attribute list?
                IntVector vec = new IntArrayVector(lineTexture);
                value = vec;
                break;
            }
            default:
                throw new IllegalArgumentException("Unknown value name + " + valueName);
        }
        return value;
    }

    public void setValue(String valueName, Object valueObj) {
        switch (valueName) {
            case "col":
                color = getColor(valueObj);
                break;
            case "colmain":
                colmain = getColor(valueObj);
                break;
            case "collab":
                collab = getColor(valueObj);
                break;
            case "colsub":
                colsub = getColor(valueObj);
                break;
            case "colaxis":
                colaxis = getColor(valueObj);
                break;
            case "fg":
                fgColor = getColor(valueObj);
                break;
            case "bg":
                bgColor = getColor(valueObj);
                break;
            case "lwd":
                lineWidth = getDouble(valueObj);
                break;
            case "lty":
                lineTexture = getInteger(valueObj);
                break;
            case "pch":
                plotChar = getInteger(valueObj);
                break;
        }
    }

    Color getColor(Object valueObj) throws IllegalArgumentException {
        Color color;
        if (valueObj instanceof Color) {
            color = (Color) valueObj;
        } else {
            String colorName;
            if (valueObj instanceof String) {
                colorName = (String) valueObj;
            } else if (valueObj instanceof SEXP) {
                SEXP sexp = (SEXP) valueObj;
                colorName = sexp.asString();
            } else {
                colorName = String.valueOf(valueObj);
            }
            color = Color.web(colorName);
        }
        return color;
    }

    double getDouble(Object valueObj) throws IllegalArgumentException {
        double newValue;
        if (valueObj instanceof Double) {
            newValue = (Double) valueObj;
        } else if (valueObj instanceof String) {
            newValue = Double.parseDouble((String) valueObj);
        } else if (valueObj instanceof Vector) {
            Vector vector = (Vector) valueObj;
            newValue = vector.getElementAsDouble(0);
        } else {
            newValue = Double.parseDouble(String.valueOf(valueObj));
        }
        return newValue;
    }

    int getInteger(Object valueObj) throws IllegalArgumentException {
        int newValue;
        if (valueObj instanceof Integer) {
            newValue = (Integer) valueObj;
        } else if (valueObj instanceof String) {
            newValue = Integer.parseInt((String) valueObj);
        } else if (valueObj instanceof Vector) {
            Vector vector = (Vector) valueObj;
            newValue = vector.getElementAsInt(0);
        } else {
            newValue = Integer.parseInt(String.valueOf(valueObj));
        }
        return newValue;
    }

}
