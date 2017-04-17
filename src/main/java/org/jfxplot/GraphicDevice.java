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

import javafx.scene.image.Image;
import org.renjin.sexp.*;

/**
 *
 * @author Bruce Johnson
 */
public abstract class GraphicDevice {

    static double inchToDev = 72.0;
    GraphicsState state;

    class Units {

    }

    enum TR {
        INCH() {
            double to(double x) {
                return x / inchToDev;
            }

            double from(double x) {
                return x * inchToDev;
            }

            double fromX(double x) {
                return from(x);
            }

            double fromY(double x) {
                return from(x);
            }

        },
        DEV() {
            double to(double x) {
                return x;
            }

            double from(double x) {
                return x;
            }

            double fromX(double x) {
                return from(x);
            }

            double fromY(double x) {
                return from(x);
            }

        };

        abstract double from(double x);

        abstract double to(double x);

        abstract double fromX(double x);

        abstract double fromY(double x);
    }

// void GECircle(double x, double y, double radius, const pGEcontext gc, pGEDevDesc dd);

    /* Draw a circle, centred on (x,y) with radius r (in inches). */
    abstract void drawCircle(double x, double y, double radius, RGraphicsContext rGC);

//    void GESetClip(double x1, double y1, double x2, double y2, pGEDevDesc dd);
///* Set clipping region (based on current setting of dd->gp.xpd).
// * Only clip if new clipping region is different from the current one */
    abstract void setClip(double x1, double y1, double x2, double y2);

//void GClip(pGEDevDesc);
///* Polygon clipping: */
    abstract int clipPolygon(double[] x, double[] y, int n, TR coords, int store, double[] xout, double[] yout);

    /* Always clips */
//void GForceClip(pGEDevDesc);
    abstract void forceClip();

// void GELine(double x1, double y1, double x2, double y2, const pGEcontext gc);
///* Draw a line from (x1,y1) to (x2,y2): */
    abstract void drawLine(double x1, double y1, double x2, double y2, RGraphicsContext rGC);
///* Return the location of the next mouse click: */
//Rboolean GLocator(double*, double*, int, pGEDevDesc);
///* Return the height, depth, and width of the specified
// * character in the specified units: */

    //void GENewPage(const pGEcontext gc, pGEDevDesc dd);
    abstract void newPage(RGraphicsContext rGC);

    class MetricInfo {

    }

    abstract MetricInfo getMetricInfo(char charVal, Units units);
///* Set device "mode" (drawing or not drawing) here for windows and mac drivers.
// */

    abstract void setMode(int mode);

    //void GEPath(double *x, double *y,int npoly, int *nper,     Rboolean winding, const pGEcontext gc);
    /* Draw a path using the specified lists of x and y values: */
    abstract void drawPath(double[] x, double[] y, int n, int[] nper, boolean winding, RGraphicsContext rGC);

    //void GEPolygon(int n, double *x, double *y, const pGEcontext gc);

    /* Draw a polygon using the specified lists of x and y values: */
    abstract void drawPolygon(int n, double[] x, double[] y, RGraphicsContext rGC);

// void GEPolyline(int n, double *x, double *y, const pGEcontext gc);
    /* Draw series of straight lines using the specified lists of x and y values: */
    abstract void drawPolyLine(int n, double[] x, double[] y, RGraphicsContext rGC);

    //void GERect(double x0, double y0, double x1, double y1,const pGEcontext gc);

    /* Draw a rectangle given two opposite corners: */
    abstract void drawRect(double x1, double y1, double x2, double y2, RGraphicsContext rGC);

    // SEXP GECap(pGEDevDesc dd);
    abstract SEXP cap();

//SEXP GEXspline(int n, double *x, double *y, double *s, Rboolean open,
//               Rboolean repEnds, Rboolean draw,
//               const pGEcontext gc, pGEDevDesc dd);
    // void GERaster(unsigned int *raster, int w, int h,
    //         double x, double y, double width, double height,
    //          double angle, Rboolean interpolate,
    //          const pGEcontext gc, pGEDevDesc dd);
    /* Draw a raster image given two opposite corners: */
    abstract void drawImage(Image image, int w, int h, double x, double y, double width, double height1, double angle, boolean interpolate, RGraphicsContext rGC);

//double GEStrHeight(const char *str, cetype_t enc,  const pGEcontext gc, pGEDevDesc dd);

    /* Return the height of the specified string in the specified units: */
    abstract double getStringHeight(String text, int cetype, RGraphicsContext rGC);

    //double GEStrWidth(const char *str, cetype_t enc, const pGEcontext gc, pGEDevDesc dd);

    /* Return the width of the specified string in the specified units */
    abstract double getStringWidth(String text, int cetype, RGraphicsContext rGC);

    //void GEText(double x, double y, const char * const str, cetype_t enc, double xc, double yc, double rot,
    // const pGEcontext gc, pGEDevDesc dd);
    /* Draw the specified text at location (x,y) with the specified
 * rotation and justification: */
    abstract void drawText(double x, double y, String text, int cetype, double xc, double yc, double rotation, RGraphicsContext rGC);

    /*
void GEMode(int mode, pGEDevDesc dd);
void GESymbol(double x, double y, int pch, double size,
              const pGEcontext gc, pGEDevDesc dd);
void GEPretty(double *lo, double *up, int *ndiv);
void GEMetricInfo(int c, const pGEcontext gc,
                  double *ascent, double *descent, double *width,
                  pGEDevDesc dd);
void GEStrMetric(const char *str, cetype_t enc, const pGEcontext gc,
                 double *ascent, double *descent, double *width,
                 pGEDevDesc dd);
int GEstring_to_pch(SEXP pch);

    
    
     */
}
