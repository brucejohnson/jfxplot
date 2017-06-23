fxstage <- function() {
    iStage = StageManager$stage()+2L
    return(iStage)
}
fxinit <- function() {
    PlotApp$initApp()
}
fxline <- function() {
    StageManager$drawLine()
}

fxseries <- function(x, y) {
    StageManager$addSeries(x, y)
}

fxtext <- function(text="",x=0,y=0,pos="CENTER") {
    StageManager$drawText(text, x, y, pos)
}


q <- function() {
    StageManager$exitApp()
    quit()
}

fxdev.new <- function() {
  fxstage()
}

fxdev.cur <- function() {
  return(StageManager$curDevice()+2L)
}

fxdev.set <- function(which) {
  return(StageManager$setDevice(as.integer(which)-2L)+2L)
}

fxdev.prev <- function(which) {
  return(StageManager$prevDevice(as.integer(which)-2L)+2L)
}

fxdev.next <- function(which) {
  return(StageManager$nextDevice(as.integer(which)-2L)+2L)
}

C_plotXY <- function(...) {
    args <- list(...)
    do.call(fxplot.xy,args)
}
C_plotXYE <- function(...) {
    args <- list(...)
    do.call(fxplot.xye,args)
}

.External.graphics <- function(cmd,...) {
    args <- list(...)
    do.call(cmd,args)
}

par <- function(argName) {
    state <- GraphicsState$defaultState
    return(state$getValue(argName))
}

canvas <- function(...) {
     StageManager$clear()
     StageManager$showCanvas()
}
