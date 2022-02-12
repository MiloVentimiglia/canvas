package core

trait Helpers {

  import DrawingProgram.{CanvasHorizontalLine}

  def isCanvasEmpty(implicit program: DrawingProgram): Boolean = {
    program.canvas.isEmpty
  }

  def isAlreadyColored(x: Int, y: Int, colour: String)(implicit program: DrawingProgram): Boolean = {
    val line: CanvasHorizontalLine = program.canvas(y)
    val colorAtXY: String = line.substring(x)
    if (colorAtXY == colour) true else false
  }

  def isAlreadyColored(x: Int, color: String, line: CanvasHorizontalLine): Boolean = {
    val colorAtXY: String = line.substring(x)
    if (colorAtXY == color) true else false
  }
}
