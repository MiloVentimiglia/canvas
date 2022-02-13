package core.DrawingService

import scala.collection.mutable

class DrawingProgram {

  import DrawingProgram._

  var canvas: Canvas = mutable.HashMap[Int, String]()

  def canvasHeight: Int = canvas.size

  def canvasWidth: Int = canvas(0).length

  def createCanvas(width: Int, height: Int): DrawingProgram = {
    val cleanCanvas: Canvas = new Canvas()
    val heightAdjusted = height + 2

    for (h <- 0 until heightAdjusted) {
      if (h == 0 || h == heightAdjusted - 1) {
        cleanCanvas(h) = TOP_FRAME * width
      } else {
        cleanCanvas(h) = SIDE_FRAME + WHITESPACE * (width - 2) + SIDE_FRAME
      }
    }
    this.canvas = cleanCanvas
    this
  }

  def drawHorizontalLine(x1: Int, y1: Int, x2: Int): DrawingProgram = {
    val line: String = canvas(y1)
    val lineToAdd: String = LINEMARKER * (x2 - x1 + 1)
    canvas(y1) = line.substring(0, x1) + lineToAdd + line.substring(x2 + 1, canvasWidth)

    this.canvas = canvas
    this
  }

  def drawVerticalLine(x1: Int, y1: Int, x2: Int, y2: Int): DrawingProgram = {
    for (y <- y1 until y2 + 1) {
      val line: String = canvas(y)
      val lineToAdd: String = LINEMARKER
      canvas(y) = line.substring(0, x1) + lineToAdd + line.substring(x2 + 1, canvasWidth)
    }

    this.canvas = canvas
    this
  }

  def drawSquare(x1: Int, y1: Int, x2: Int, y2: Int): DrawingProgram = {

    for (y <- y1 until y2 + 1) {
      if (y == y1 || y == y2) {
        val line: String = canvas(y)
        val lineToAdd: String = LINEMARKER * (x2 - x1 + 1)
        canvas(y) = line.substring(0, x1) + lineToAdd + line.substring(x2 + 1, canvasWidth)
      } else {
        val line: String = canvas(y)
        val lineToAdd: String = LINEMARKER + WHITESPACE * (x2 - x1 - 1) + LINEMARKER
        canvas(y) = line.substring(0, x1) + lineToAdd + line.substring(x2 + 1, canvasWidth)
      }
    }

    this.canvas = canvas
    this
  }

  def displayCanvas: DrawingProgram = {
    for (y <- 0 until canvasHeight) {
      println(canvas(y))
    }
    this
  }
}

object DrawingProgram {

  type Canvas = mutable.HashMap[Int, String]
  type Coordinates = (Int, Int)

  val LINEMARKER = "x"
  val WHITESPACE = " "
  val TOP_FRAME = "-"
  val SIDE_FRAME = "|"
}