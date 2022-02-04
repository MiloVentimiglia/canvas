package core

object HelpersImplicits{

  implicit class RichOptionConvert(val s: String) extends AnyVal {
    def safeToInt: Option[Int] =
      try {
        Some(s.toInt)
      } catch {
        case _: NumberFormatException => None
      }
  }

}


trait Helpers {

  import DrawingProgram.{CanvasHorizontalLine, Colour}
  import HelpersImplicits._

  def isInsideCanvas(xs: List[Int], ys: List[Int], width: Int, height: Int): Boolean = {
    val xsInsideCanvas: Boolean = xs.map(x => x > 0 && x < width).reduce(_ && _)
    val ysInsideCanvas: Boolean = ys.map(y => y > 0 && y < height).reduce(_ && _)

    if (xsInsideCanvas && ysInsideCanvas) true else false
  }

  def isCanvasEmpty(implicit program: DrawingProgram): Boolean = {
    program.canvas.isEmpty
  }

  def isAlreadyColored(x: Int, y: Int, color: Colour)(implicit program: DrawingProgram): Boolean = {
    val line: CanvasHorizontalLine = program.canvas(y)
    val colorAtXY: String = line.substring(x)
    if (colorAtXY == color) true else false
  }

  def isAlreadyColored(x: Int, color: Colour, line: CanvasHorizontalLine): Boolean = {
    val colorAtXY: String = line.substring(x)
    if (colorAtXY == color) true else false
  }

  def checkCoordinatesTypes(coordinates: List[String]): Boolean =
    coordinates.map(_.safeToInt).forall(_.isDefined)
}
