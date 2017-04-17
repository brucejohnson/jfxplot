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

import javafx.scene.chart.Axis;
import com.emxsys.chart.extension.Subtitle;
import com.emxsys.chart.extension.XYAnnotations;
import java.util.HashMap;
import java.util.Iterator;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import static org.jfxplot.GraphicDevice.TR.INCH;

/**
 * A chart that displays rectangular bars with heights indicating data values for categories. Used for displaying
 * information when at least one axis has discontinuous or discrete data.
 */
public class RFxCanvasChart extends ScatterChart implements AnnotatableGraph {

    private final Subtitle subtitle;
    private XYAnnotations annotations;
    private Canvas canvas;
    private GraphicsContext gC;
    private HashMap<XYChart.Series, GraphAttributes> xyAttrData = null;

    public RFxCanvasChart(Axis xAxis, Axis yAxis) {
        super(xAxis, yAxis);
        subtitle = new Subtitle(this, getChildren(), getLegend());
        annotations = new XYAnnotations(this, getChartChildren());
    }

    public RFxCanvasChart(Axis xAxis, Axis yAxis, ObservableList<XYChart.Series> data) {
        super(xAxis, yAxis, data);
        subtitle = new Subtitle(this, getChildren(), getLegend());
        annotations = new XYAnnotations(this, getChartChildren());
    }

    public RFxCanvasChart(Axis xAxis, Axis yAxis, ObservableList<XYChart.Series> data, HashMap<XYChart.Series, GraphAttributes> xyAttrData) {
        super(xAxis, yAxis, data);
        this.xyAttrData = xyAttrData;
        subtitle = new Subtitle(this, getChildren(), getLegend());
        annotations = new XYAnnotations(this, getChartChildren());
    }

    public void addAttrData(XYChart.Series series, GraphAttributes gAttr) {
        if (xyAttrData == null) {
            xyAttrData = new HashMap<>();
        }
        System.out.println(gAttr.toString());
        xyAttrData.put(series, gAttr);
    }

    void addDataListener(ObservableList<XYChart.Series> data) {
        data.addListener((ListChangeListener.Change<? extends XYChart.Series> change) -> {
            System.out.println("data changed");
        });
    }

    public void setSubtitle(String text) {
        subtitle.clearSubtitles();
        if ((text != null) && (text.length() != 0)) {
            subtitle.addSubtitle(text);
            System.out.println("subtitle " + text + " bbb " + subtitle.getSubtitles().toString() + " " + subtitle.getSubtitles().size());
        }
        this.requestLayout();
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        // layoutSubtitle with resize the chartContent member in chartChildren
        subtitle.layoutSubtitles();
        if (annotations != null) {
            annotations.layoutAnnotations();
        }
    }

    GraphAttributes getGraphAttributes(XYChart.Series series) {
        GraphAttributes gAttr = null;
        if (xyAttrData != null) {
            gAttr = xyAttrData.get(series);
        }
        if (gAttr == null) {
            gAttr = new GraphAttributes();
        }
        return gAttr;
    }

    protected void drawSeries(XYChart.Series series, ValueAxis xAxis, ValueAxis yAxis) {
        GraphAttributes gAttr = getGraphAttributes(series);
        System.out.println("draw with " + gAttr);
        gC.setStroke(gAttr.stroke[0]);
        gC.setLineWidth(gAttr.lineWidth);
        System.out.println(gAttr.toString());

        if (gAttr.showLines) {
            System.out.println("show lines");
            boolean first = true;
            //gC.setLineDashes(dashes);
            Iterator<XYChart.Data> dataIter = getDisplayedDataIterator(series);
            gC.beginPath();
            while (dataIter.hasNext()) {
                XYChart.Data data = dataIter.next();
                double x = (Double) data.getXValue();
                double y = (Double) data.getYValue();
                x = xAxis.getDisplayPosition(x);
                y = yAxis.getDisplayPosition(y);
                if (!first) {
                    gC.lineTo(x, y);
                } else {
                    gC.moveTo(x, y);
                    first = false;
                }
            }

            gC.stroke();
        }
        if (gAttr.showSymbols) {
            System.out.println("show sym");
            Iterator<XYChart.Data> dataIter = getDisplayedDataIterator(series);
            int nColors = gAttr.fill.length;
            int iSym = 0;
            while (dataIter.hasNext()) {
                gC.setStroke(gAttr.stroke[iSym % nColors]);
                XYChart.Data data = dataIter.next();
                double x = (Double) data.getXValue();
                double yR = (Double) data.getYValue();
                x = xAxis.getDisplayPosition(x);
                double y = yAxis.getDisplayPosition(yR);
                double diameter = 10.0;
                gC.strokeOval(x - diameter / 2, y - diameter / 2, diameter, diameter);
                Object extraValue = data.getExtraValue();
                if (gAttr.showRange && (extraValue != null) && (extraValue instanceof Double)) {
                    double dY = (Double) extraValue;
                    double y1 = yAxis.getDisplayPosition(yR + dY);
                    double y2 = yAxis.getDisplayPosition(yR - dY);
                    gC.strokeLine(x, y1, x, y2);

                }
                iSym++;

            }
        }
        if (gAttr.showRange) {
            System.out.println("show sym");
            Iterator<XYChart.Data> dataIter = getDisplayedDataIterator(series);
            int nColors = gAttr.fill.length;
            int iSym = 0;
            while (dataIter.hasNext()) {
                gC.setFill(gAttr.fill[iSym % nColors]);
                XYChart.Data data = dataIter.next();
                double x = (Double) data.getXValue();
                double yR = (Double) data.getYValue();
                x = xAxis.getDisplayPosition(x);
                double y = yAxis.getDisplayPosition(yR);
                Object extraValue = data.getExtraValue();
                if ((extraValue != null) && (extraValue instanceof Double)) {
                    double dY = (Double) extraValue;
                    double y1 = yAxis.getDisplayPosition(yR + dY);
                    double y2 = yAxis.getDisplayPosition(yR - dY);
                    gC.strokeLine(x, y1, x, y2);

                }
                iSym++;

            }
        }

    }

    @Override
    protected void layoutPlotChildren() {
        //super.layoutPlotChildren();
        if (canvas == null) {
            canvas = new Canvas();
            gC = canvas.getGraphicsContext2D();
            getPlotChildren().add(0, canvas);
        }
        ValueAxis xAxis = (ValueAxis) getXAxis();
        ValueAxis yAxis = (ValueAxis) getYAxis();
        double width = xAxis.getWidth();
        double height = yAxis.getHeight();
        gC.clearRect(0, 0, width, height);
        canvas.setWidth(width);
        canvas.setHeight(height);
        Iterator<XYChart.Series> seriesIter = getDisplayedSeriesIterator();
        seriesIter.forEachRemaining((series) -> {
            drawSeries(series, xAxis, yAxis);
        });
    }

    @Override
    public XYAnnotations getAnnotations() {
        return annotations;
    }

}
