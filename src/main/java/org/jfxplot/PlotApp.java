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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import org.renjin.sexp.*;

/**
 * A chart that displays rectangular bars with heights indicating data values for categories. Used for displaying
 * information when at least one axis has discontinuous or discrete data.
 */
public class PlotApp extends Application {

    static private boolean launched = false;
    static private PlotApp myApp = null;
    static private Stage myStage = null;
    static private XYChart currentChart = null;
    static private Stage[] stageList = new Stage[63];
    static int iStage = -1;

    @Override
    public void start(Stage primaryStage) throws Exception {
        myApp = this;
        myStage = primaryStage;
        addStage(primaryStage);
        launched = true;
        show();
    }

    private void show() {
        Parent parent = new BorderPane();
        myStage.setScene(new Scene(parent));
        myStage.show();
        myStage.setOnCloseRequest((e) -> {
            removeStage(myStage);
        });
    }

    private static void removeStage(Stage stage) {
        for (int i = 0; i < stageList.length; i++) {
            if (stageList[i] == stage) {
                stageList[i] = null;
            }
        }
    }

    private synchronized static void addStage(Stage stage) {
        for (int i = 0; i < stageList.length; i++) {
            if (stageList[i] == null) {
                stageList[i] = stage;
                iStage = i;
                break;
            }
        }
        stage.setTitle("FX Stage " + (iStage + 2));
    }

    public static int curDevice() {
        return iStage;
    }

    public static int setDevice(int which) {
        if (stageList[which] == null) {
            which = nextDevice(which);
        }
        myStage = stageList[which];
        return which;

    }

    public static int prevDevice(int which) {
        int next = -1;
        for (int i = which - 1; i >= 0; i--) {
            if (stageList[i] != null) {
                next = i;
                break;
            }
        }
        if (next == -1) {
            for (int i = stageList.length - 1; i >= which; i--) {
                if (stageList[i] != null) {
                    next = i;
                    break;
                }
            }
        }
        return next;

    }

    public static int nextDevice(int which) {
        int next = -1;
        for (int i = which + 1; i < stageList.length; i++) {
            if (stageList[i] != null) {
                next = i;
                break;
            }
        }
        if (next == -1) {
            for (int i = 0; i <= which; i++) {
                if (stageList[i] != null) {
                    next = i;
                    break;
                }
            }
        }
        return next;

    }

    private static void showChart(XYChart chart) {
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(chart);
        currentChart = chart;
        myStage.setScene(new Scene(borderPane));
        myStage.show();
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
        Platform.runLater(() -> {
            barPlotNow(sexp, plotAttr);
        });
    }
    public static void addSeries(SEXP x, SEXP y, String type, Vector colorNames) {
        addSeries(x, y, null, type, colorNames);
    }

    public static void addSeries(SEXP x, SEXP y, SEXP extra, String type, Vector colorNames) {
        final GraphAttributes gAttr = new GraphAttributes();
        System.out.println("type is " + type);
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
        for (int i=0;i<nColors;i++) {
            gAttr.stroke[i] = Color.web(colorNames.getElementAsString(i));
            gAttr.fill[i] = gAttr.stroke[i];
        }
        System.out.println(gAttr.toString());

        Platform.runLater(() -> {
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
        Platform.runLater(() -> {
            xyPlotNow(x, y, plotAttr);
        });
    }

    public static void xyPlot(Vector xlim, Vector ylim, String log,
            String main, String sub, String xlab, String ylab) {
        final PlotAttributes plotAttr = new PlotAttributes().main(main).sub(sub).
                xlab(xlab).ylab(ylab).xlim(xlim).ylim(ylim).log(log);
        Platform.runLater(() -> {
            XYChart chart = XYPlotFactory.createXYPlot(plotAttr);
            showChart(chart);
        });
    }

    public static void drawLine() {
        if (currentChart instanceof AnnotatableGraph) {
            Platform.runLater(() -> {
                ((AnnotatableGraph) currentChart).addLine(Color.BLUE, 2);
            });
        }
    }

    public static void drawText(String text, double x, double y, String pos) {
        if (currentChart instanceof AnnotatableGraph) {
            Platform.runLater(() -> {
                ((AnnotatableGraph) currentChart).addText(text, x, y, pos);
            });
        }
    }

    private static void runAndWait(Runnable runnable) throws InterruptedException, ExecutionException {
        FutureTask<Integer> future = new FutureTask(runnable, null);
        Platform.runLater(future);
        future.get();
    }

    public static int stage() {
        if (!launched) {
            initApp(true);
        } else {
            try {
                runAndWait(() -> {
                    myStage = new Stage();
                    addStage(myStage);
                    myStage.setOnCloseRequest((e) -> {
                        removeStage((Stage) e.getSource());
                    });
                    myStage.show();
                });
            } catch (InterruptedException | ExecutionException e) {
            }
        }
        return iStage;
    }

    public static void exitApp() {
        Platform.exit();
        System.exit(0);
    }

    public static void initApp() {
        initApp(false);
    }

    public static void initApp(final boolean showStage) {
        if (launched) {
        } else {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    String[] args = new String[0];
                    Application.launch(PlotApp.class, args);
                    Platform.setImplicitExit(false);
                }
            };
            thread.start();
            // cheap trick to make sure javafx started before trying to do anything
            while (myApp == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
