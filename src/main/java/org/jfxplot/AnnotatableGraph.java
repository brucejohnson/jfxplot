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

import com.emxsys.chart.extension.XYAnnotations;
import com.emxsys.chart.extension.XYLineAnnotation;
import com.emxsys.chart.extension.XYTextAnnotation;
import javafx.geometry.Pos;
import javafx.scene.chart.Axis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.paint.Color;

/**
 *
 * @author brucejohnson
 */
public interface AnnotatableGraph {

    public XYAnnotations getAnnotations();

    public Axis getXAxis();

    public Axis getYAxis();

    public void requestLayout();

    public default void addLine(Color color, double lineWidth) {
        ValueAxis xAxis = (ValueAxis) getXAxis();
        ValueAxis yAxis = (ValueAxis) getYAxis();
        getAnnotations().add(new XYLineAnnotation(
                xAxis.getUpperBound(), yAxis.getLowerBound(),
                xAxis.getLowerBound(), yAxis.getUpperBound(),
                lineWidth,
                color),
                XYAnnotations.Layer.FOREGROUND);
        requestLayout();

    }

    public default void addText(String text, double x, double y, String posString) {
        Pos pos;
        if ((posString == null) || (posString.equals(""))) {
            pos = Pos.CENTER;
        } else {
            pos = Pos.valueOf(posString);

        }
        XYTextAnnotation xyText = new XYTextAnnotation(text, x, y, pos);
        getAnnotations().add(xyText, XYAnnotations.Layer.FOREGROUND);
        requestLayout();

    }

}
