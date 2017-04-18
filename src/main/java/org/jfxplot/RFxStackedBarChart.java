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
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

/**
 * A chart that displays rectangular bars with heights indicating data values for categories. Used for displaying
 * information when at least one axis has discontinuous or discrete data.
 */
public class RFxStackedBarChart extends StackedBarChart {

    private String subtitle = null;

    public RFxStackedBarChart(Axis xAxis, Axis yAxis) {
        super(xAxis, yAxis);
    }

    public RFxStackedBarChart(Axis xAxis, Axis yAxis, ObservableList<XYChart.Series> data) {
        super(xAxis, yAxis, data);
    }

    public RFxStackedBarChart(Axis xAxis, Axis yAxis, ObservableList<XYChart.Series> data, double gap) {
        super(xAxis, yAxis, data, gap);
    }

    public void setSubtitle(String text) {
        subtitle = text;
        this.requestLayout();
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
    }

    public void setBarColors() {
        //set first bar color
        for (Node n : lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill: blue;");
            System.out.println("node " + n.getStyle() + " " + n.getStyleClass().toString());
        }
        //second bar color
        for (Node n : lookupAll(".default-color1.chart-bar")) {
            n.setStyle("-fx-bar-fill: yellow;");
            System.out.println("node " + n.getStyle());
        }
        for (Node n : lookupAll(".chart-legend-item-symbol")) {
            //for (Node n : lookupAll(".chart-legend-item-symbol.chart-bar.series0")) {
            System.out.println("node " + n.getStyle() + " " + n.getStyleClass().toString());
            n.setStyle("-fx-background-color: " + "blue" + ";");
        }
        this.requestLayout();
    }

}
