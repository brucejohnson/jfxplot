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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import org.renjin.primitives.matrix.Matrix;
import org.renjin.sexp.*;

/**
 * A chart that displays rectangular bars with heights indicating data values for categories. Used for displaying
 * information when at least one axis has discontinuous or discrete data.
 */
public class BarPlotFactory {

    public static XYChart createBarPlot(SEXP height, PlotAttributes bAttr) {
        AttributeMap attributes = height.getAttributes();
        Vector dim = attributes.getDim();
        Vector vector = (Vector) height;
        ObservableList<BarChart.Series> barChartData = FXCollections.observableArrayList();
        BarChart.Series series;

        ObservableList categories = FXCollections.observableArrayList();
        int nCategories;
        if ((dim != null) && (dim.length() > 1)) {
            Matrix matrix = new Matrix(vector);
            nCategories = matrix.getNumCols();
            Vector colNames = matrix.getColNames();
            if (!bAttr.drawAxisNames || (bAttr.axisNames == null)) {
                for (int i = 0; i < nCategories; i++) {
                    categories.add(i + "");
                }
            } else {
                for (int i = 0; i < nCategories; i++) {
                    categories.add(bAttr.axisNames[i]);
                }
            }
            for (int j = 0; j < matrix.getNumRows(); j++) {
                String seriesName;
                if (bAttr.drawLegend && (bAttr.legends != null)) {
                    seriesName = bAttr.legends[j];
                } else {
                    seriesName = j + "";
                }
                series = new BarChart.Series(seriesName, FXCollections.observableArrayList());
                for (int i = 0; i < nCategories; i++) {
                    series.getData().add(new BarChart.Data(categories.get(i), matrix.getElementAsDouble(j, i)));
                }
                barChartData.add(series);

            }

        } else {
            nCategories = vector.length();
            Vector colNames = vector.getNames();
            if (colNames == null) {
                for (int i = 0; i < nCategories; i++) {
                    categories.add(i + "");
                }
            } else {
                for (int i = 0; i < nCategories; i++) {
                    categories.add(colNames.getElementAsString(i));
                }
            }
            series = new BarChart.Series("", FXCollections.observableArrayList());
            for (int i = 0; i < nCategories; i++) {
                series.getData().add(new BarChart.Data(categories.get(i), vector.getElementAsDouble(i)));
            }
            barChartData.add(series);
            series.getNode().setStyle("-fx-bar-fill: blue;");
        }

        double ymin = bAttr.ylim.getElementAsDouble(0);
        double ymax = bAttr.ylim.getElementAsDouble(1);
        double ydelta = (ymax - ymin) / 4;

        CategoryAxis xAxis = new CategoryAxis();
        ValueAxis<Number> yAxis;
        if (bAttr.yLog) {
            throw new UnsupportedOperationException("Not supported yet.");
        } else {
            yAxis = new NumberAxis(bAttr.ylab, ymin, ymax, ydelta);
        }

        xAxis.setCategories(categories);

        XYChart chart;
        double gap = 25.0;
        if (bAttr.beside) {
            RFxBarChart barChart = new RFxBarChart(xAxis, yAxis, barChartData, gap);
            barChart.setSubtitle(bAttr.sub);
            chart = barChart;

        } else {
            RFxStackedBarChart barChart = new RFxStackedBarChart(xAxis, yAxis, barChartData, gap);
            barChart.setSubtitle(bAttr.sub);
            //barChart.setBarColors();
            chart = barChart;
        }
        chart.setTitle(bAttr.main);
        chart.setLegendVisible(bAttr.drawLegend);
        xAxis.setTickLabelsVisible(bAttr.drawAxisNames);
        return chart;
    }

}
