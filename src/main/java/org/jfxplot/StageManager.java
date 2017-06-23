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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Bruce Johnson
 */
public class StageManager {

    static private Stage currentStage = null;
    static private Stage[] stageList = new Stage[63];
    static int iStage = -1;
    static private boolean launched = false;

    public void start(Stage primaryStage) throws Exception {
        currentStage = primaryStage;
        addStage(primaryStage);
        launched = true;
        show();
    }

    public static void setLaunched() {
        launched = true;
    }

    void show() {
        currentStage.show();
        currentStage.setOnCloseRequest((e) -> {
            removeStage(currentStage);
        });
    }

    public static Stage getCurrentStage() {
        return currentStage;
    }

    private static void runAndWait(Runnable runnable) throws InterruptedException, ExecutionException {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            FutureTask<Integer> future = new FutureTask(runnable, null);
            Platform.runLater(future);
            future.get();
        }
    }

    private static void removeStage(Stage stage) {
        for (int i = 0; i < stageList.length; i++) {
            if (stageList[i] == stage) {
                stageList[i] = null;
            }
        }
    }

    synchronized static void addStage(Stage stage) {
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
        currentStage = stageList[which];
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

    public static void clear() {
        if (launched) {
            return;
        }
        Platform.runLater(() -> {
//            BorderPane borderPane = new BorderPane();
//            currentStage.setScene(new Scene(borderPane));
            currentStage.show();
        });
    }

    public static int stage() {
        try {
            runAndWait(() -> {
                currentStage = new Stage();
                addStage(currentStage);
                currentStage.setOnCloseRequest((e) -> {
                    removeStage((Stage) e.getSource());
                });
                BorderPane pane = new BorderPane();
                currentStage.setScene(new Scene(pane));

                currentStage.show();
            });
        } catch (InterruptedException | ExecutionException e) {
        }

        return iStage;
    }

    public static void exitApp() {
        Platform.exit();
        System.exit(0);
    }
}
