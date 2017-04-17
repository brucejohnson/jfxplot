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

import com.emxsys.chart.extension.LogarithmicAxis;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import org.renjin.primitives.matrix.Matrix;
import org.renjin.sexp.*;

/**
 * A chart that displays rectangular bars with heights indicating data values for categories. Used for displaying
 * information when at least one axis has discontinuous or discrete data.
 */
public class XYPlotFactory {

    public static void addSeries(XYChart chart, SEXP x, SEXP y, SEXP extra, GraphAttributes gAttr) {
        ObservableList<XYChart.Series> xyChartData = chart.getData();
        AttributeMap attributes = x.getAttributes();
        Vector dim = attributes.getDim();
        Vector xVector = (Vector) x;
        Vector yVector = (Vector) y;
        Vector extraVector = (Vector) extra;
        XYChart.Series series;

        ObservableList<String> categories = FXCollections.observableArrayList();
        int nValues;
        if ((dim != null) && (dim.length() > 1)) {
            System.out.println("add matrix");
            Matrix matrix = new Matrix(xVector);
            nValues = matrix.getNumRows();
            int nCategories = matrix.getNumCols() - 1;
            for (int i = 0; i < nCategories; i++) {
                categories.add(i + "");
            }
            for (int j = 0; j < nCategories; j++) {
                series = new XYChart.Series(categories.get(j), FXCollections.observableArrayList());
                for (int i = 0; i < nValues; i++) {
                    series.getData().add(new XYChart.Data(matrix.getElementAsDouble(i, 0), matrix.getElementAsDouble(i, j + 1)));
                }
                xyChartData.add(series);
                if (chart instanceof RFxCanvasChart) {
                    ((RFxCanvasChart) chart).addAttrData(series, gAttr);
                }
            }
        } else {
            nValues = xVector.length();
            series = new XYChart.Series("", FXCollections.observableArrayList());
            for (int i = 0; i < nValues; i++) {
                if (extra == null) {
                    series.getData().add(new XYChart.Data(xVector.getElementAsDouble(i), yVector.getElementAsDouble(i), 0.0));
                } else {
                    series.getData().add(new XYChart.Data(xVector.getElementAsDouble(i), yVector.getElementAsDouble(i), extraVector.getElementAsDouble(i)));

                }
            }
            xyChartData.add(series);
            if (chart instanceof RFxCanvasChart) {
                System.out.println(series);
                System.out.println(gAttr);
                ((RFxCanvasChart) chart).addAttrData(series, gAttr);
            }
        }
    }

    public static void addSeriesToChart(XYChart chart, SEXP x, SEXP y, SEXP extra, GraphAttributes gAttr) {
        addSeries(chart, x, y, extra, gAttr);
    }

    public static XYChart createXYPlot(SEXP x, SEXP y, PlotAttributes bAttr) {
        ObservableList<XYChart.Series> xyChartData = FXCollections.observableArrayList();

        double ymin = bAttr.ylim.getElementAsDouble(0);
        double ymax = bAttr.ylim.getElementAsDouble(1);
        double ydelta = (ymax - ymin) / 4;
        double xmin = bAttr.xlim.getElementAsDouble(0);
        double xmax = bAttr.xlim.getElementAsDouble(1);
        double xdelta = (xmax - xmin) / 4;

        ValueAxis<Number> xAxis;
        ValueAxis<Number> yAxis;
        if (bAttr.xLog) {
            xAxis = new LogarithmicAxis(bAttr.xlab, xmin, xmax, 1);
        } else {
            xAxis = new NumberAxis(bAttr.xlab, xmin, xmax, xdelta);
        }
        if (bAttr.yLog) {
            yAxis = new LogarithmicAxis(bAttr.ylab, ymin, ymax, 1);
        } else {
            yAxis = new NumberAxis(bAttr.ylab, ymin, ymax, ydelta);
        }

        XYChart chart;
        HashMap<XYChart.Series, GraphAttributes> xyAttrData = new HashMap<>();
        for (XYChart.Series series : xyChartData) {
            GraphAttributes gAttr = new GraphAttributes();
            if (bAttr.type.equals("p")) {
                gAttr.showLines = false;
                gAttr.showSymbols = true;
            } else if (bAttr.type.equals("b")) {
                gAttr.showLines = true;
                gAttr.showSymbols = true;
            } else if (bAttr.type.equals("l")) {
                gAttr.showLines = true;
                gAttr.showSymbols = false;
            }
            xyAttrData.put(series, gAttr);
        }
        RFxCanvasChart xyChart = new RFxCanvasChart(xAxis, yAxis, xyChartData, xyAttrData);
        xyChart.setSubtitle(bAttr.sub);
        chart = xyChart;
        chart = xyChart;
//        if (bAttr.type.equals("p")) {
//            RFxCanvasChart xyChart = new RFxCanvasChart(xAxis, yAxis, xyChartData);
//            xyChart.setSubtitle(bAttr.sub);
//            chart = xyChart;
//        } else if (bAttr.type.equals("b")) {
//            RFxXYLineChart xyChart = new RFxCanvasChart(xAxis, yAxis, xyChartData);
//            xyChart.setSubtitle(bAttr.sub);
//            chart = xyChart;
//        } else {
//            RFxXYLineChart xyChart = new RFxCanvasChart(xAxis, yAxis, xyChartData);
//            xyChart.setSubtitle(bAttr.sub);
//            xyChart.setCreateSymbols(false);
//            chart = xyChart;
//
//        }
        addSeries(chart, x, y, null, null);

        chart.setTitle(bAttr.main);
        chart.setLegendVisible(false);
        return chart;

    }

    public static XYChart createXYPlot(PlotAttributes bAttr) {
        double ymin = bAttr.ylim.getElementAsDouble(0);
        double ymax = bAttr.ylim.getElementAsDouble(1);
        double ydelta = (ymax - ymin) / 4;
        double xmin = bAttr.xlim.getElementAsDouble(0);
        double xmax = bAttr.xlim.getElementAsDouble(1);
        double xdelta = (xmax - xmin) / 4;

        ValueAxis<Number> xAxis;
        ValueAxis<Number> yAxis;
        if (bAttr.xLog) {
            xAxis = new LogarithmicAxis(bAttr.xlab, xmin, xmax, 1);
        } else {
            xAxis = new NumberAxis(bAttr.xlab, xmin, xmax, xdelta);
        }
        if (bAttr.yLog) {
            yAxis = new LogarithmicAxis(bAttr.ylab, ymin, ymax, 1);
        } else {
            yAxis = new NumberAxis(bAttr.ylab, ymin, ymax, ydelta);
        }

        XYChart chart;
        RFxCanvasChart xyChart = new RFxCanvasChart(xAxis, yAxis);
        xyChart.setSubtitle(bAttr.sub);
        chart = xyChart;
        chart = xyChart;
        chart.setTitle(bAttr.main);
        chart.setLegendVisible(false);
        return chart;

    }

}
