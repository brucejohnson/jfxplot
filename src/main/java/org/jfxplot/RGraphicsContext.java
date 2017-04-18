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
    private Color stroke = Color.BLUE;

    /* fill colour (for polygons, circles, rects, ...) original int*/
    private Color fill = Color.YELLOW;

    /* Gamma correction */
    private double gamma;

    /*
     * Line characteristics
     */

 /* Line width (roughly number of pixels) */
    private double lwd;
    /* Line type (solid, dashed, dotted, ...) */
    private int lty;

    /* line join original enum*/
    private StrokeLineJoin lineJoin = StrokeLineJoin.MITER;

    /* Line end original enum*/
    private StrokeLineCap lineCap = StrokeLineCap.BUTT;

    /* line mitre */
    private double lmitre;

    private Font font = Font.getDefault();
    /*
     * Text characteristics
     */

 /* Character expansion (font size = fontsize*cex) */
    private double cex;

    /* Font size in points */
    private double ps;

    /* Line height (multiply by font size) */
    private double lineheight;


    /* Font face (plain, italic, bold, ...) */
    private int fontface;

    /* Font family  original char array*/
    private String fontFamily;

    public Color getFill() {
        return fill;
    }

    public Color getStroke() {
        return stroke;
    }

    public Font getFont() {
        return font;
    }

    /**
     * @param stroke the stroke to set
     */
    public void setStroke(Color stroke) {
        this.stroke = stroke;
    }

    /**
     * @param fill the fill to set
     */
    public void setFill(Color fill) {
        this.fill = fill;
    }

    /**
     * @return the gamma
     */
    public double getGamma() {
        return gamma;
    }

    /**
     * @param gamma the gamma to set
     */
    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    /**
     * @return the lwd
     */
    public double getLwd() {
        return lwd;
    }

    /**
     * @param lwd the lwd to set
     */
    public void setLwd(double lwd) {
        this.lwd = lwd;
    }

    /**
     * @return the lty
     */
    public int getLty() {
        return lty;
    }

    /**
     * @param lty the lty to set
     */
    public void setLty(int lty) {
        this.lty = lty;
    }

    /**
     * @return the lineJoin
     */
    public StrokeLineJoin getLineJoin() {
        return lineJoin;
    }

    /**
     * @param lineJoin the lineJoin to set
     */
    public void setLineJoin(StrokeLineJoin lineJoin) {
        this.lineJoin = lineJoin;
    }

    /**
     * @return the lineCap
     */
    public StrokeLineCap getLineCap() {
        return lineCap;
    }

    /**
     * @param lineCap the lineCap to set
     */
    public void setLineCap(StrokeLineCap lineCap) {
        this.lineCap = lineCap;
    }

    /**
     * @return the lmitre
     */
    public double getLmitre() {
        return lmitre;
    }

    /**
     * @param lmitre the lmitre to set
     */
    public void setLmitre(double lmitre) {
        this.lmitre = lmitre;
    }

    /**
     * @param font the font to set
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * @return the cex
     */
    public double getCex() {
        return cex;
    }

    /**
     * @param cex the cex to set
     */
    public void setCex(double cex) {
        this.cex = cex;
    }

    /**
     * @return the ps
     */
    public double getPs() {
        return ps;
    }

    /**
     * @param ps the ps to set
     */
    public void setPs(double ps) {
        this.ps = ps;
    }

    /**
     * @return the lineheight
     */
    public double getLineheight() {
        return lineheight;
    }

    /**
     * @param lineheight the lineheight to set
     */
    public void setLineheight(double lineheight) {
        this.lineheight = lineheight;
    }

    /**
     * @return the fontface
     */
    public int getFontface() {
        return fontface;
    }

    /**
     * @param fontface the fontface to set
     */
    public void setFontface(int fontface) {
        this.fontface = fontface;
    }

    /**
     * @return the fontFamily
     */
    public String getFontFamily() {
        return fontFamily;
    }

    /**
     * @param fontFamily the fontFamily to set
     */
    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

}
