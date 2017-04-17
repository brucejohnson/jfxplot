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

import org.renjin.sexp.Vector;

/**
 *
 * @author brucejohnson
 */
public class PlotAttributes {

    String main = "";
    String sub = "";
    String xlab = "";
    String ylab = "";
    Vector xlim = null;
    Vector ylim = null;
    boolean xLog = false;
    boolean yLog = false;
    boolean beside = false;
    String type = "p";
    boolean drawLegend = false;
    String[] legends = null;
    boolean drawAxisNames = false;
    String[] axisNames = null;

    public PlotAttributes() {
    }

    public PlotAttributes log(String log) {
        switch (log) {
            case "":
                xLog = false;
                yLog = false;
                break;
            case "x":
                xLog = true;
                yLog = false;
                break;
            case "y":
                yLog = true;
                xLog = false;
                break;
            case "xy":
                xLog = true;
                yLog = true;
                break;
            case "yx":
                xLog = true;
                yLog = true;
                break;
            default:
                throw new IllegalArgumentException("log must be \"\", x, y, xy or yx");
        }
        return this;
    }

    public PlotAttributes main(String main) {
        this.main = main;
        return this;
    }

    public PlotAttributes sub(String sub) {
        this.sub = sub;
        return this;
    }

    public PlotAttributes xlab(String xlab) {
        this.xlab = xlab;
        return this;
    }

    public PlotAttributes ylab(String ylab) {
        this.ylab = ylab;
        return this;
    }

    public PlotAttributes type(String type) {
        this.type = type;
        return this;
    }

    public PlotAttributes xlim(Vector xlim) {
        this.xlim = xlim;
        return this;
    }

    public PlotAttributes ylim(Vector ylim) {
        this.ylim = ylim;
        return this;
    }

    public PlotAttributes beside(boolean beside) {
        this.beside = beside;
        return this;
    }

    public PlotAttributes drawLegend(boolean drawLegend) {
        this.drawLegend = drawLegend;
        return this;
    }

    public PlotAttributes legends(Vector vec) {
        if (vec != null) {
            legends = new String[vec.length()];
            for (int i = 0; i < legends.length; i++) {
                legends[i] = vec.getElementAsString(i);
            }
        }
        return this;
    }

    public PlotAttributes drawAxisNames(boolean drawAxisNames) {
        this.drawAxisNames = drawAxisNames;
        return this;
    }

    public PlotAttributes axisNames(Vector vec) {
        if (vec != null) {
            axisNames = new String[vec.length()];
            for (int i = 0; i < axisNames.length; i++) {
                axisNames[i] = vec.getElementAsString(i);
            }
        }
        return this;
    }

}
