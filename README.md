# jfxplot
JFXPlot is a plotting library that can be used with Renjin, the JVM-based interpreter for the R language ( http://renjin.org )

This code is mostly an experiment in learning about R graphics, Renjin, and testing the possibility of adding graphics to Renjin.

The plot and barplot commands currently work by using the JavaFX chart library.

A prototype of a Graphics Device that uses the JavaFX Canvas is included.

# Example

Add the jar file, plot-0.1-SNAPSHOT.jar, to the renjin dependencies directory.

Load in library and create some test data

    import(org.jfxplot.PlotApp)
    import(org.jfxplot.GraphicsState)
    library(org.jfxplot.plot)

    x = seq(-2.0*pi,2.0*pi,4.0*pi/100)
    y = sin(x)

    data <- c(1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0)

    m <- matrix(data,3, 3)
    m <- m * 100.0
    rownames(m) <- c("A","B","C")
    colnames(m) <- c("X","Y","Z")


Plot x,y data

    plot(x,y)

Create new JavaFX Stage and plot barplot

    dev.new()
    barplot(m)

Create new JavaFX stage and show example of canvas.
Canvas is an example of an R graphics device built-on the JavaFX Canvas

    dev.new()
    canvas()
