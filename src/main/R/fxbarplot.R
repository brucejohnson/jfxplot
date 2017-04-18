#  File originally derived from:
#  File src/library/graphics/R/barplot.R
#  Part of the R package, http://www.R-project.org
#
#  This program is free software; you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation; either version 2 of the License, or
#  (at your option) any later version.
#
#  This program is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#
#  A copy of the GNU General Public License is available at
#  http://www.r-project.org/Licenses/

barplot <- function(height, ...) UseMethod("barplot")

barplot.default <-
function(height, names.arg = NULL,
	 legend.text = NULL, beside = FALSE,
	 main = NULL, sub = NULL, xlab = NULL, ylab = NULL,
	 xlim = NULL, ylim = NULL,
	 axisnames = TRUE, add = FALSE,
         plot = TRUE, ...)
 {
    PlotApp$clear()

    if (plot && axisnames && is.null(names.arg))
	names.arg <-
	    if(is.matrix(height)) colnames(height) else names(height)

    if (is.vector(height)
	|| (is.array(height) && (length(dim(height)) == 1))) {
	## Treat vectors and 1-d arrays the same.
	height <- cbind(height)
	beside <- TRUE
	## The above may look strange, but in particular makes color
	## specs work as most likely expected by the users.
	if(is.null(col)) col <- "grey"
    } else if (is.matrix(height)) {
	## In the matrix case, we use "colors" by default.
	if(is.null(col))
	    col <- grey.colors(nrow(height))
    }
    else
	stop("'height' must be a vector or a matrix")

    drawLegend = FALSE
    legendNames = c("")
    if(is.logical(legend.text)) {
        if (legend.text) {
            if (is.matrix(height)) {
                legendNames =  rownames(height)
                drawLegend = TRUE
            }
        }
    } else {
        if (!is.null(legend.text)) {
            #rep(legend.text, length.out = NR)
	    legendNames <- legend.text
            drawLegend = TRUE
        }
    }

    if (!beside) {
	tops <- apply(height, 2L, sum)
    } else {
        tops <- height
    }
    # log not working on bar plots yet (first bar starts at 0.0)
    log=""
    if (is.null(ylim)) ylim <- range(0, tops , na.rm = TRUE)
    if(plot) { ##-------- Plotting :
        if (!add) {
            plot.new()
            #plot.window(xlim, ylim, log = log, ...)
        }

        if (is.null(main)) main=""
        if (is.null(sub)) sub=""
        if (is.null(xlab)) xlab=""
        if (is.null(ylab)) ylab=""
        PlotApp$barPlot(height, beside, main, sub, xlab, ylab, ylim, log, axisnames,names.arg,drawLegend,legendNames)
    }
}
