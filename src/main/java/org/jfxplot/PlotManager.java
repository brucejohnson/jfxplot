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

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.renjin.sexp.SEXP;
import org.renjin.sexp.Vector;

/**
 * A chart that displays rectangular bars with heights indicating data values for categories. Used for displaying
 * information when at least one axis has discontinuous or discrete data.
 */
public class PlotManager {

    static private boolean launched = false;
    static private PlotManager myApp = null;
    static private XYChart currentChart = null;
    static private Parent currentParent = null;
    static private boolean studioMode = false;

    public void start() throws Exception {
        myApp = this;
    }

    public void setStudioMode(Parent parent) {
        currentParent = parent;
        studioMode = true;
    }

    public static void showCanvasNow() {
        BorderPane borderPane = new BorderPane();
        Canvas canvas = new Canvas(500, 500);
        borderPane.setCenter(canvas);
        Stage stage = StageManager.getCurrentStage();
        stage.setScene(new Scene(borderPane));
        stage.show();
        GraphicsContext gC = canvas.getGraphicsContext2D();
        CanvasExample cExample = new CanvasExample();
        cExample.show(canvas, gC);
    }

    public static void showCanvas() {
        runOnPlatform(() -> {
            showCanvasNow();
        });
    }

    public static void showChart(XYChart chart) {
        if (studioMode) {
            BorderPane pane = (BorderPane) currentParent;
            pane.setCenter(chart);
            currentChart = chart;
            return;
        }
        Stage stage = StageManager.getCurrentStage();

        Parent parent = null;
        if (stage.getScene() != null) {
            if ((currentParent != null) && (currentParent.getScene() == stage.getScene())) {
                parent = currentParent;
            } else {
                parent = stage.getScene().getRoot();
            }
        }
        if (parent instanceof BorderPane) {
            BorderPane pane = (BorderPane) parent;
            pane.setCenter(chart);
            stage.setWidth(stage.getWidth());
            stage.setHeight(stage.getHeight());
        } else if (parent instanceof Pane) {
            Pane pane = (Pane) parent;
            pane.getChildren().setAll(chart);
        } else {
            parent = new BorderPane();
            BorderPane pane = (BorderPane) parent;
            pane.setCenter(chart);
            stage.setScene(new Scene(parent));
        }
        currentParent = parent;
        currentChart = chart;
        stage.show();
    }

    private static void barPlotNow(SEXP height, PlotAttributes bAttr) {
        XYChart chart = BarPlotFactory.createBarPlot(height, bAttr);
        showChart(chart);
    }

    public static void barPlot(SEXP sexp, boolean beside, String main, String sub, String xlab, String ylab,
            Vector ylim, String log, boolean drawAxisNames, Vector axisNames, boolean drawLegend, Vector legends) {
        final PlotAttributes plotAttr = new PlotAttributes().main(main).sub(sub).ylim(ylim).
                log(log).xlab(xlab).ylab(ylab).beside(beside).drawLegend(drawLegend).legends(legends).
                drawAxisNames(drawAxisNames).axisNames(axisNames);
        runOnPlatform(() -> {
            barPlotNow(sexp, plotAttr);
        });
    }

    public static void addSeries(SEXP x, SEXP y, String type, Vector colorNames) {
        addSeries(x, y, null, type, colorNames);
    }

    public static void runOnPlatform(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater(runnable);
        }
    }

    public static void addSeries(SEXP x, SEXP y, SEXP extra, String type, Vector colorNames) {
        final GraphAttributes gAttr = new GraphAttributes();
        switch (type) {
            case ("p"): {
                gAttr.showLines = false;
                gAttr.showSymbols = true;
                break;
            }
            case ("b"): {
                gAttr.showLines = true;
                gAttr.showSymbols = true;
                break;
            }
            case ("l"): {
                gAttr.showLines = true;
                gAttr.showSymbols = false;
                break;
            }
        }
        gAttr.showRange = extra != null;
        int nColors = colorNames.length();
        gAttr.stroke = new Color[nColors];
        gAttr.fill = new Color[nColors];
        for (int i = 0; i < nColors; i++) {
            gAttr.stroke[i] = Color.web(colorNames.getElementAsString(i));
            gAttr.fill[i] = gAttr.stroke[i];
        }

        runOnPlatform(() -> {
            XYPlotFactory.addSeriesToChart(currentChart, x, y, extra, gAttr);
        });
    }

    private static void xyPlotNow(SEXP x, SEXP y, PlotAttributes bAttr) {
        XYChart chart = XYPlotFactory.createXYPlot(x, y, bAttr);
        showChart(chart);
    }

    public static void xyPlot(SEXP x, SEXP y, String type, Vector xlim, Vector ylim, String log,
            String main, String sub, String xlab, String ylab) {
        final PlotAttributes plotAttr = new PlotAttributes().main(main).sub(sub).
                xlab(xlab).ylab(ylab).xlim(xlim).ylim(ylim).log(log).type(type);
        runOnPlatform(() -> {
            xyPlotNow(x, y, plotAttr);
        });
    }

    public static void xyPlot(Vector xlim, Vector ylim, String log,
            String main, String sub, String xlab, String ylab) {
        final PlotAttributes plotAttr = new PlotAttributes().main(main).sub(sub).
                xlab(xlab).ylab(ylab).xlim(xlim).ylim(ylim).log(log);
        runOnPlatform(() -> {
            XYChart chart = XYPlotFactory.createXYPlot(plotAttr);
            showChart(chart);
        });
    }

    public static void drawLine() {
    }

    public static void drawText(String text, double x, double y, String pos) {
    }

}
