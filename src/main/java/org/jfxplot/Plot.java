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

import java.util.HashMap;
import javafx.scene.chart.XYChart;

/**
 * A chart that displays rectangular bars with heights indicating data values for categories. Used for displaying
 * information when at least one axis has discontinuous or discrete data.
 */
public class Plot {

    private GraphicDevice gDev;
    private HashMap<XYChart.Series, GraphAttributes> xyAttrData = null;

    public Plot(GraphicDevice gDev) {
        this.gDev = gDev;
    }

    double getDisplayPosition(double value) {
        return value;
    }

    void getDisplayPosition(double[] xy, double x, double y) {
        xy[0] = x;
        xy[1] = y;
    }

    protected void plotXY(double[] xArray, double[] yArray, GraphAttributes gAttr, RGraphicsContext rGC) {
        int n = xArray.length;
        if (gAttr.showLines) {
//            for (i = 0; i < n; i++) {
//                xx = x[i];
//                yy = y[i];
//                /* do the conversion now to check for non-finite */
//                GConvert( & xx,  & yy, USER, DEVICE, dd);
//                if ((R_FINITE(xx) && R_FINITE(yy))
//                        && !(R_FINITE(xold) && R_FINITE(yold))) {
//                    start = i;
//                } else if ((R_FINITE(xold) && R_FINITE(yold))
//                        && !(R_FINITE(xx) && R_FINITE(yy))) {
//                    if (i - start > 1) {
//                        GPolyline(i - start, x + start, y + start, USER, dd);
//                    }
//                } else if ((R_FINITE(xold) && R_FINITE(yold)) && (i == n - 1)) {
//                    GPolyline(n - start, x + start, y + start, USER, dd);
//                }
//                xold = xx;
//                yold = yy;
//            }

            boolean first = true;
            //gC.setLineDashes(dashes);
//            gDev.drawPolyLine(n, xArray, yArray, GraphicDevice.TR.INCH);

            double xOld = Double.NaN;
            double yOld = Double.NaN;
            int start = 0;
            for (int i = 0; i < n; i++) {
                double x = xArray[i];
                double y = yArray[i];
                x = getDisplayPosition(x);
                y = getDisplayPosition(y);
                if ((Double.isFinite(x) && Double.isFinite(y)) && !(Double.isFinite(xOld) && Double.isFinite(yOld))) {
                    start = i;
                } else if ((Double.isFinite(xOld) && Double.isFinite(yOld)) && !(Double.isFinite(x) && Double.isFinite(y))) {
                    if (i - start > 1) {
                        gDev.drawPolyLine(i - start, xArray, yArray, rGC);
                    }
                } else if ((Double.isFinite(xOld) && Double.isFinite(yOld)) && (i == n - 1)) {
                    gDev.drawPolyLine(n - start, xArray, yArray, rGC);
                }
                xOld = x;
                yOld = y;
            }
        }
        if (gAttr.showSymbols) {
            int nColors = gAttr.fill.length;
            int iSym = 0;
            double[] xy = new double[2];
            for (int i = 0; i < n; i++) {
//                gC.setStroke(gAttr.stroke[iSym % nColors]);
//                gC.setFill(gAttr.fill[iSym % nColors]);
                double x = xArray[i];
                double y = yArray[i];
                getDisplayPosition(xy, x, y);
                double diameter = 10.0;
//                gC.strokeOval(xy[0] - diameter / 2, xy[1] - diameter / 2, diameter, diameter);
                iSym++;

            }
        }
    }
}
