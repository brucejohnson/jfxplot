
fxstage <- function() {
    iStage = PlotApp$stage()+2L
    return(iStage)
}
fxinit <- function() {
    PlotApp$initApp()
}
fxline <- function() {
    PlotApp$drawLine()
}

fxseries <- function(x, y) {
    PlotApp$addSeries(x, y)
}

fxtext <- function(text="",x=0,y=0,pos="CENTER") {
    PlotApp$drawText(text, x, y, pos)
}


q <- function() {
    PlotApp$exitApp()
    quit()
}

fxdev.new <- function() {
  fxstage()
}

fxdev.cur <- function() {
  return(PlotApp$curDevice()+2L)
}

fxdev.set <- function(which) {
  return(PlotApp$setDevice(as.integer(which)-2L)+2L)
}

fxdev.prev <- function(which) {
  return(PlotApp$prevDevice(as.integer(which)-2L)+2L)
}

fxdev.next <- function(which) {
  return(PlotApp$nextDevice(as.integer(which)-2L)+2L)
}
