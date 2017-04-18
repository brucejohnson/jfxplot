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

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Bruce Johnson
 */
public class CanvasExample {

    public void show(Canvas canvas, GraphicsContext gC) {
        CanvasGraphicsDevice gDev = new CanvasGraphicsDevice(gC);
        RGraphicsContext rGC = new RGraphicsContext();
        System.out.println(canvas.getWidth() + " " + canvas.getHeight());
        gDev.drawLine(100.0, 100.0, 300.0, 100.0, rGC);
        rGC.setStroke(Color.RED);
        rGC.setFill(Color.YELLOW);
        gDev.drawRect(120, 120, 200, 180, rGC);
        gDev.drawCircle(200.0, 250.0, 30.0, rGC);
    }
}
