package core

import core.DrawingService.DrawingProgram

trait Helpers {

  def isCanvasEmpty(implicit program: DrawingProgram): Boolean = {
    program.canvas.isEmpty
  }

  def isAlreadyColored(x: Int, y: Int, colour: String)(implicit program: DrawingProgram): Boolean = {
    val line: String = program.canvas(y)
    val colorAtXY: String = line.substring(x)
    if (colorAtXY == colour) true else false
  }

  def isAlreadyColored(x: Int, colour: String, line: String): Boolean = {
    val colorAtXY: String = line.substring(x)
    if (colorAtXY == colour) true else false
  }
}
