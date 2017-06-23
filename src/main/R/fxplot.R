fxplot <- function(x, y = NULL, type = "p",  xlim = NULL, ylim = NULL, log = "", main = NULL, sub = NULL, xlab = NULL, ylab = NULL, all=FALSE) {
     fxinit()

     if (is.null(main)) main=""
     if (is.null(sub)) sub=""
     if (is.null(xlab)) xlab=""
     if (is.null(ylab)) ylab=""

     if (is.matrix(x)) {
         if (is.null(xlim)) xlim <- range(0, x[,1] , na.rm = TRUE)
         if (all) {
             if (is.null(ylim)) ylim <- range(0, m[,2:dim(m)[2]] , na.rm = TRUE)
             PlotManager$xyPlot(m,NULL,type, xlim, ylim, log, main, sub, xlab, ylab)
         } else {
             if (is.null(ylim)) ylim <- range(0, x[,2] , na.rm = TRUE)
             PlotManager$xyPlot(x[,1],x[,2],type, xlim, ylim, log, main, sub, xlab, ylab)
         }
     } else {
        if (is.null(xlim)) xlim <- range(0, x , na.rm = TRUE)
        if (is.null(ylim)) ylim <- range(0, y , na.rm = TRUE)
        PlotManager$xyPlot(x,y,type, xlim, ylim, log, main, sub, xlab, ylab)
     }
}
fxplot.chart <- function(xlim, ylim, log = "", main = NULL, sub = NULL, xlab = NULL, ylab = NULL, all=FALSE) {
     fxinit()

     if (is.null(main)) main=""
     if (is.null(sub)) sub=""
     if (is.null(xlab)) xlab=""
     if (is.null(ylab)) ylab=""

        if (is.null(xlim)) xlim <- range(0, x , na.rm = TRUE)
        if (is.null(ylim)) ylim <- range(0, y , na.rm = TRUE)
        PlotManager$xyPlot(xlim, ylim, log, main, sub, xlab, ylab)
}

fxplot.xy <- function(xy, type, col = par("col"), lty = par("lty"), pch = par("pch"),bg = NA, cex = 1, lwd = par("lwd"), ...) {
     fxinit()
     PlotManager$addSeries(xy$x,xy$y,type,col)
}

fxplot.xye <- function(xy, extra, type, col = par("col"), lty = par("lty"), pch = par("pch"),bg = NA, cex = 1, lwd = par("lwd"), ...) {
     fxinit()
     PlotManager$addSeries(xy$x,xy$y,extra,type,col)
}

fxplot.xyold <- function(xy, type = "p",  xlim = NULL, ylim = NULL, log = "", main = NULL, sub = NULL, all=FALSE) {

     fxinit()
     xlab = xy$xlab
     ylab = xy$ylab
     if (is.null(main)) main=""
     if (is.null(sub)) sub=""
     if (is.null(xlab)) xlab=""
     if (is.null(ylab)) ylab=""

     if (is.null(xlim)) xlim <- range(0, xy$x , na.rm = TRUE)
     if (is.null(ylim)) ylim <- range(0, xy$y , na.rm = TRUE)
     PlotManager$addSeries(xy$x,xy$y)
#,type, xlim, ylim, log, main, sub, xlab, ylab)
}
