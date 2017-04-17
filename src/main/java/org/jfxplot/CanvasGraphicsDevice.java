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

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.renjin.sexp.SEXP;

/**
 *
 * @author Bruce Johnson
 */
public class CanvasGraphicsDevice extends GraphicDevice {

    Text sampleText = new Text();
    GraphicsContext gC;
    Font defaultFont = new Font(12);

    public CanvasGraphicsDevice(GraphicsContext gC) {
        this.gC = gC;
    }

    @Override
    void drawCircle(double x, double y, double radius, RGraphicsContext rGC) {
        gC.setFill(rGC.getFill());
        gC.setStroke(rGC.getStroke());
        gC.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        gC.strokeOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    @Override
    void setClip(double x1, double y1, double x2, double y2) {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    int clipPolygon(double[] x, double[] y, int n, TR coords, int store, double[] xout, double[] yout) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void forceClip() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void drawLine(double x1, double y1, double x2, double y2, RGraphicsContext rGC) {
        if (Double.isFinite(x1) && Double.isFinite(y1) && Double.isFinite(x2) && Double.isFinite(y2)) {
            gC.strokeLine(x1, y1, x2, y2);
        }
    }

    @Override
    MetricInfo getMetricInfo(char charVal, Units units) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void setMode(int mode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void drawPath(double[] x, double[] y, int n, int[] nper, boolean winding, RGraphicsContext rGC) {
        Color stroke = rGC.getStroke();
        if (stroke != null) {
            gC.setStroke(rGC.getStroke());

            gC.beginPath();
            gC.moveTo(x[0], y[0]);
            for (int i = 1; i < n; i++) {
                gC.lineTo(x[i], y[i]);
            }
            gC.stroke();
        }
    }

    @Override
    void drawPolygon(int n, double[] x, double[] y, RGraphicsContext rGC) {
        Color fill = rGC.getFill();
        if (fill != null) {
            gC.setFill(fill);
            gC.fillPolygon(x, y, n);
        }
        Color stroke = rGC.getStroke();
        if (stroke != null) {
            gC.setStroke(rGC.getStroke());
            gC.strokePolygon(x, y, n);
        }
    }

    @Override
    void drawPolyLine(int n, double[] x, double[] y, RGraphicsContext rGC) {
        Color stroke = rGC.getStroke();
        if (stroke != null) {
            gC.setStroke(rGC.getStroke());

            gC.beginPath();
            for (int i = 0; i < n; i++) {
                double x1 = x[i];
                double y1 = y[i];
                if (i == 0) {
                    gC.moveTo(x1, y1);
                } else {
                    gC.lineTo(x1, y1);
                }
            }
            gC.stroke();
        }
    }

    @Override
    void drawRect(double x1, double y1, double x2, double y2, RGraphicsContext rGC) {
        Color fill = rGC.getFill();
        if (fill != null) {
            gC.setFill(fill);
            gC.fillRect(x1, y1, x2 - x1, y2 - y1);
        }
        Color stroke = rGC.getStroke();
        if (stroke != null) {
            gC.setStroke(rGC.getStroke());
            gC.strokeRect(x1, y1, x2 - x1, y2 - y1);
        }

    }

    @Override
    void drawImage(Image image, int w, int h, double x0, double y0, double width, double height, double angle, boolean interpolate, RGraphicsContext rGC) {
        gC.drawImage(image, x0, y0, width, height);
    }

    Bounds getTextBounds(String text, Font font, boolean trimSpace) {
        sampleText.setText(text);
        sampleText.setFont(font);
        Bounds bds = sampleText.getBoundsInLocal();
        if (trimSpace) {
            Rectangle rect = new Rectangle(bds.getMinX(), bds.getMinY(), bds.getWidth(), bds.getHeight());
            bds = Shape.intersect(sampleText, rect).getBoundsInLocal();
        }
        return bds;
    }

    @Override
    double getStringHeight(String text, int cetype, RGraphicsContext rGC) {
        Bounds bounds = getTextBounds(text, defaultFont, true);
        return bounds.getHeight();
    }

    @Override
    double getStringWidth(String text, int cetype, RGraphicsContext rGC) {
        Bounds bounds = getTextBounds(text, defaultFont, true);
        return bounds.getWidth();
    }

    @Override
    void drawText(double x, double y, String text, int cetype, double xc, double yc, double rotation, RGraphicsContext rGC) {
        gC.setFont(rGC.getFont());
        gC.strokeText(text, x, y);
    }

    @Override
    void newPage(RGraphicsContext rGC) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    SEXP cap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
